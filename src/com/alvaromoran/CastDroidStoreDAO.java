package com.alvaromoran;

import com.alvaromoran.constants.GenericITunesConstants;
import com.alvaromoran.constants.ITunesSearchKeys;
import com.alvaromoran.constants.ITunesSpecificPodCastKeys;
import com.alvaromoran.constants.XmlFeedConstants;
import com.alvaromoran.data.EnrichedChannel;
import com.alvaromoran.data.SingleEpisode;
import com.alvaromoran.data.JsonRoot;
import com.alvaromoran.data.PodCastChannelDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main library class that will provide all functionality to get PodCasts channel information and
 * detailed channel information for selected channels
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class CastDroidStoreDAO implements PodCastsDAO {

    /** Logger of the class */
    private static final Logger LOGGER = Logger.getLogger(CastDroidStoreDAO.class.getName());

    /** Maximum number of results when performing a query over ITunes */
    private static final int MAX_RESULTS = 200;

    /** Minimum number of results when performing a query over ITunes */
    private static final int MIN_RESULTS = 1;

    /** Object in charge of managing the connections over the different providers */
    private ConnectionManager connectionManager;

    /** Set of parameters to perform a query over ITunes store*/
    private HashMap<String, String> queryParametersMap;

    /** Perform the query when a parameter is added or wait to the caller event */
    private boolean autoQueryChannels = false;

    /**
     * Constructor of the class that initializes the connection object
     * and the ITunes parameters map
     */
    public CastDroidStoreDAO() {
        this.connectionManager = new ConnectionManager();
        this.queryParametersMap = new HashMap<>();
        LOGGER.setLevel(Level.INFO);
    }

//region PodCastsDAO implementation

    @Override
    public List<PodCastChannelDTO> updateTermSearchParameter(String term) {
        // Store value
        if (term == null || term.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSearchKeys.TERM.toString());
        } else {
            addUriParameter(ITunesSearchKeys.TERM.toString(), term);
        }
        return (this.autoQueryChannels? executeUriForITunes() : null);
    }

    @Override
    public List<PodCastChannelDTO> updateArtistSearchParameter(String artist) {
        // Store value
        if (artist == null || artist.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_ARTIST.toString());
        } else {
            addUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_ARTIST.toString(), artist);
        }
        return (this.autoQueryChannels? executeUriForITunes() : null);
    }

    @Override
    public List<PodCastChannelDTO> updateAuthorSearchParameter(String author) {
        // Store value
        if (author == null || author.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_AUTHOR.toString());
        } else {
            addUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_AUTHOR.toString(), author);
        }
        return (this.autoQueryChannels? executeUriForITunes() : null);
    }

    @Override
    public void setResultsLimit(int number) {
        if (number < MAX_RESULTS && number > MIN_RESULTS) {
            addUriParameter(ITunesSearchKeys.LIMIT.toString(), Integer.toString(number));
        } else if (number == 0) {
            removeUriParameter(ITunesSearchKeys.LIMIT.toString());
        } else {
            LOGGER.log(Level.WARNING,"Invalid results limit - Valid values are from 0 to 200. Instruction ignored");
        }
    }

    @Override
    public void setAutoQueryChannelsOption(boolean autoQuery) {
        this.autoQueryChannels = autoQuery;
    }

    @Override
    public List<PodCastChannelDTO> executeQueryOnDemand() {
        return executeUriForITunes();
    }

    @Override
    public EnrichedChannel getEnrichedChannelInformation(PodCastChannelDTO selectedChannel) {
        return null;
    }

    @Override
    public List<SingleEpisode> getListOfEpisodesFromChannel(PodCastChannelDTO selectedChannel) {
        return getListOfEpisodesFromUrl(selectedChannel.feedUrl);
    }

    @Override
    public List<SingleEpisode> getListOfEpisodesFromUrl(String url) {
        // Check valid url
        Document deserializeMessage = executeUriForFeed(url);
        return getParsedListOfEpisodes(deserializeMessage);
    }

//endregion

    /**
     * Perform the connections over a single Channel feed URL
     * @param feedUrl url to connect to
     * @return XML answer
     */
    private Document executeUriForFeed(String feedUrl) {
        if (feedUrl != null && isValidUri(feedUrl)) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document parsedDoc = dBuilder.parse(feedUrl);
                parsedDoc.getDocumentElement().normalize();
                return parsedDoc;
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.log(Level.WARNING, "Unable to parse received information - Url: " + feedUrl);
                return null;
            }
        } else {
            LOGGER.log(Level.WARNING, "Provided invalid feed url catch detailed channel information - Url: " + feedUrl);
            return null;
        }
    }

    /**
     * Gets the full list of parsed episodes form a channel based on the returned XML
     * @param deserializedMessage document that contains the received XML
     * @return list of channel episodes
     */
    private List<SingleEpisode> getParsedListOfEpisodes(Document deserializedMessage) {
        // Look for the items of the deserialized message
        List<SingleEpisode> parsedEpisodes = new ArrayList<>();
        NodeList itemList = deserializedMessage.getElementsByTagName(XmlFeedConstants.XML_ITEM_TAG);
        for (int index = 0; index < itemList.getLength(); index++) {
            Node item = itemList.item(index);
            // Once the items are found, the method iterate through them to parse them into SingleEpisode elements
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element episodeToParse = (Element) item;
                SingleEpisode episode = EpisodesFactory.createEpisodeFromXml(episodeToParse);
                if (episode != null) {
                    parsedEpisodes.add(episode);
                }
            }
        }
        return parsedEpisodes;
    }

    /**
     * Executes the GET REST request over the ITunes store
     * @return list of channels returned based on the search terms
     */
    private List<PodCastChannelDTO> executeUriForITunes() {
        List<PodCastChannelDTO> returnedChannels = null;
        String url = createFullQuery();
        if (url != null && isValidUri(url)) {
            // Perform the GET request and parse the answer
            returnedChannels = parseRoughMessage(this.connectionManager.performGetRequest(url));
        } else {
            LOGGER.log(Level.WARNING,"Unable to generate a valid url with the provided parameters");
        }
        return returnedChannels;
    }

    /**
     * Adds a query parameter for the URI that will be used to perform a GET request over the ITunes store
     * @param key parameters key
     * @param value parameters value
     */
    private void addUriParameter(String key, String value) {
        try {
            if (this.queryParametersMap != null) {
                if (key != null && value != null ) {
                    this.queryParametersMap.put(key, URLEncoder.encode(value, "UTF-8"));
                } else {
                    LOGGER.log(Level.WARNING,"Invalid key or value provided");
                }
            } else {
                LOGGER.log(Level.SEVERE,"Query parameter map not correctly initialized");
            }
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, "Unable to encode the term " + value + "into URL format");
        }
    }

    /**
     * Removes a parameter used in the query over ITunes store
     * @param key key to be removed
     */
    private void removeUriParameter(String key) {
        this.queryParametersMap.remove(key);
    }

    /**
     * Checks if the URI to be used as GET request over ITunes store is a valid one
     * @param url string with the URL
     * @return <code>true</code> if the URL is well formed
     */
    private boolean isValidUri(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Url generated based is invalid: " + url);
            return false;
        }
    }

    /**
     * Creates the full query to be used as GET request over ITunes store based on the stored
     * parameters
     * @return full query
     */
    private String createFullQuery() {
        // Validations for the needed arguments
        if (this.queryParametersMap.get(ITunesSearchKeys.TERM.toString()) != null &&
                !"".equalsIgnoreCase(this.queryParametersMap.get(ITunesSearchKeys.TERM.toString()))) {
            StringBuilder queryBuilder = initializeUri();
            addDefaultUriParameters();
            fillUriParameters(queryBuilder);
            return queryBuilder.toString();
        }
        return null;
    }

    /**
     * Parses the received message from the ITunes store. The body of the message uses JSON formatting
     * and the Gson library will be used to deserialize it
     * @param message full received message
     * @return list or parsed channels
     */
    private List<PodCastChannelDTO> parseRoughMessage(String message) {
        JsonRoot fullMessageParsed;
        // Gson library to deserialize the JSON message
        Gson jsonDeserializer = new GsonBuilder().create();
        fullMessageParsed = jsonDeserializer.fromJson(message, JsonRoot.class);
        return new ArrayList<>(fullMessageParsed.results);
    }

    /**
     * Initializes the object used to build the query over the ITunes store. It also sets
     * the basic URL used to access the ITunes API
     * @return StringBuilder object with the basic URL already initialized
     */
    private StringBuilder initializeUri() {
        // Clean uri and compose the authority
        StringBuilder uriBuilder = new StringBuilder();
        return uriBuilder.append(GenericITunesConstants.ITUNES_AUTHORITY)
                .append(GenericITunesConstants.ITUNES_SEARCH_URI)
                .append(GenericITunesConstants.ITUNES_QUERY_CHARACTER);
    }

    /**
     * By using the StringBuilder input, it appends all keys and values of the set of parameters to generate
     * the full query
     * @param uriBuilder StringBuilder object used to build the URL
     */
    private void fillUriParameters(StringBuilder uriBuilder) {
        Iterator<Map.Entry<String, String>> iterator = this.queryParametersMap.entrySet().iterator();
        while (iterator.hasNext()) {
            // Runs through all the stored parameters
            Map.Entry<String, String> currentPair = iterator.next();
            if (iterator.hasNext()) {
                uriBuilder.append(createParameterForUri(currentPair.getKey(), currentPair.getValue(), false));
            } else {
                uriBuilder.append(createParameterForUri(currentPair.getKey(), currentPair.getValue(), true));
            }
        }
    }


    private String createParameterForUri(String parameter, String value, boolean lastParameter) {
        String parameterCreated = parameter + GenericITunesConstants.ITUNES_PARAMETER_EQUAL + value;
        if (!lastParameter) {
            return parameterCreated + GenericITunesConstants.ITUNES_PARAMETER_SEPARATION;
        } else {
            return parameterCreated;
        }
    }

    private void addDefaultUriParameters() {
        this.queryParametersMap.put(ITunesSearchKeys.ENTITY.toString(),
                ITunesSpecificPodCastKeys.SEARCH_TERM_PODCAST.toString());
    }
}
