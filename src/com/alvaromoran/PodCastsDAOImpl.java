package com.alvaromoran;

import com.alvaromoran.constants.GenericITunesConstants;
import com.alvaromoran.constants.ITunesSearchKeys;
import com.alvaromoran.constants.ITunesSpecificPodCastKeys;
import com.alvaromoran.data.dto.PodCastChannelDTO;
import com.alvaromoran.data.json.JsonRoot;
import com.alvaromoran.data.json.PodCastChannel;
import com.alvaromoran.exceptions.PodCastAccessConnectionException;
import com.alvaromoran.exceptions.PodCastAccessUriException;
import com.alvaromoran.factories.PodCastsFactory;
import com.alvaromoran.factories.PodCastsFactoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
public class PodCastsDAOImpl implements PodCastsDAO {

    /** Logger of the class */
    private static final Logger LOGGER = Logger.getLogger(PodCastsDAOImpl.class.getName());

    /** Maximum number of results when performing a query over ITunes */
    private static final int MAX_RESULTS = 200;

    /** Minimum number of results when performing a query over ITunes */
    private static final int MIN_RESULTS = 1;

    /** Object in charge of managing the connections over the different providers */
    private final ConnectionManager connectionManager;

    /** Set of parameters to perform a query over ITunes store*/
    private final HashMap<String, String> queryParametersMap;

    /** Perform the query when a parameter is added or wait to the caller event */
    private boolean autoQueryChannels = false;

    /** Gets information of paid and free channels (<code>true</code>) or only for free channels (<code>false</code>)*/
    private boolean searchPaidChannels = false;

    /** Factory to create channels and episodes information */
    private final PodCastsFactory factory;

    /**
     * Constructor of the class that initializes the connection object
     * and the ITunes parameters map
     */
    public PodCastsDAOImpl() {
        // Generate business objects
        this.connectionManager = new ConnectionManager();
        this.queryParametersMap = new HashMap<>();
        this.factory = new PodCastsFactoryImpl();
        LOGGER.setLevel(Level.INFO);
    }

//region PodCastsDAO implementation

    /**
     * Updates the main term parameter used to search in the ITunes store
     * @param term term value
     * @return channels returned as result of the query if the auto query option is enabled
     */
    @Override
    public List<PodCastChannelDTO> updateTermSearchParameter(String term) throws PodCastAccessUriException {
        // Store value
        if (term == null || term.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSearchKeys.TERM.toString());
        } else {
            addUriParameter(ITunesSearchKeys.TERM.toString(), term);
        }
        return (this.autoQueryChannels ? executeUriForITunes() : null);
    }

    /**
     * Updates the artist term parameter used to search in the ITunes store
     * @param artist artist value
     * @return channels returned as result of the query if the auto query option is enabled
     */
    @Override
    public List<PodCastChannelDTO> updateArtistSearchParameter(String artist) throws PodCastAccessUriException {
        // Store value
        if (artist == null || artist.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_ARTIST.toString());
        } else {
            addUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_ARTIST.toString(), artist);
        }
        return (this.autoQueryChannels? executeUriForITunes() : null);
    }

    /**
     * Updates the author term parameter used to search in the ITunes store
     * @param author author value
     * @return channels returned as result of the query if the auto query option is enabled
     */
    @Override
    public List<PodCastChannelDTO> updateAuthorSearchParameter(String author) throws PodCastAccessUriException {
        // Store value
        if (author == null || author.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_AUTHOR.toString());
        } else {
            addUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_AUTHOR.toString(), author);
        }
        return (this.autoQueryChannels? executeUriForITunes() : null);
    }

    /**
     * Sets the limit of channels returned per query. Default value is 50, minimum value is 1 and max value is 200
     * @param number max number of channels returned per query
     */
    @Override
    public void setChannelResultsLimit(int number) throws PodCastAccessUriException {
        if (number < MAX_RESULTS && number > MIN_RESULTS) {
            addUriParameter(ITunesSearchKeys.LIMIT.toString(), Integer.toString(number));
        } else if (number == 0) {
            removeUriParameter(ITunesSearchKeys.LIMIT.toString());
        } else {
            LOGGER.log(Level.WARNING,"Invalid results limit - Valid values are from 0 to 200. Instruction ignored");
        }
    }

    /**
     * Enables / disables the auto query option. If enabled, when the search parameters are updated, the query is
     * automatically executed. If disabled, the query is only executed when calling the executeQueryOnDemand method
     * @param autoQuery <code>true</code> enables the auto query functionality
     *                  <code>false</code> disables the auto query functionality
     */
    @Override
    public void setAutoQueryChannelsOption(boolean autoQuery) {
        this.autoQueryChannels = autoQuery;
    }

    /**
     * Executes the query instantly based on the stored parameters, if any
     * @return list of channels returned as result of the query
     */
    @Override
    public List<PodCastChannelDTO> executeQueryOnDemand() throws PodCastAccessUriException  {
        return executeUriForITunes();
    }

    /**
     * Gets enriched channel information from a particular channel passed as an argument. This method will add
     * information such as copyright, detailed descriptions, list of episodes... for the ChannelInformation object
     * @param selectedChannel channel to be updated with detailed information
     * @param getEpisodes <code>true</code> the channel is filled with episodes information - It may be a time consuming process
     *                    <code>false</code> then channel is not filled with episodes information
     */
    @Override
    public PodCastChannelDTO getEnrichedChannelInformation(PodCastChannelDTO selectedChannel, boolean getEpisodes) throws PodCastAccessConnectionException {
        if (selectedChannel != null) {
            Document channelAdditionalInfo = executeUriForFeed(selectedChannel.getFeedUrl());
            if (channelAdditionalInfo != null && !getEpisodes) {
                this.factory.createChannelExtended(selectedChannel, channelAdditionalInfo);
            } else if (channelAdditionalInfo != null && getEpisodes) {
                this.factory.createChannelExtendedAndEpisodes(selectedChannel, channelAdditionalInfo);
            }
        }
        return selectedChannel;
    }

    /**
     * Gets the list of episodes related to a particular channel. No additional information is added to the channel object
     * @param selectedChannel channel to get the episodes from
     * @return list of episodes
     */
    @Override
    public PodCastChannelDTO getListOfEpisodesFromChannel(PodCastChannelDTO selectedChannel) {
        if (selectedChannel != null) {
            Document channelAdditionalInfo = executeUriForFeed(selectedChannel.getFeedUrl());
            selectedChannel = this.factory.createChannelWithEpisodes(selectedChannel, channelAdditionalInfo);
        }
        return selectedChannel;
    }

    /**
     * Sets if the search of channels will be based on paid and free channels (<code>true</code>) or only over free
     * channels (<code>false</code>). By default the value of this flag is false.
     * @param searchPaidChannels new value for the flag
     */
    @Override
    public void setSearchPaidChannels(boolean searchPaidChannels) {
        this.searchPaidChannels = searchPaidChannels;
    }

//endregion

    /**
     * Perform the connections over a single Channel feed URL
     * @param feedUrl url to connect to
     * @return XML answer
     */
    private Document executeUriForFeed(String feedUrl) throws PodCastAccessConnectionException {
        if (feedUrl != null && isValidUri(feedUrl)) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setNamespaceAware(true); // namespace awareness
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                String notParsedMessage = this.connectionManager.performGetRequest(feedUrl);
                InputSource inputSource = new InputSource(new StringReader(notParsedMessage));
                Document parsedDoc = dBuilder.parse(inputSource);
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
     * Executes the GET REST request over the ITunes store
     * @return list of channels returned based on the search terms
     */
    private List<PodCastChannelDTO> executeUriForITunes() throws PodCastAccessConnectionException, PodCastAccessUriException {
        List<PodCastChannelDTO> returnedChannels = new ArrayList<>();
        String url = createFullQuery();
        if (url != null && isValidUri(url)) {
            // Perform the GET request and parse the answer
            List<PodCastChannel> roughChannels = parseRoughMessage(this.connectionManager.performGetRequest(url));
            // Parse each one of the DTOs into the channel information final object
            if (roughChannels != null && roughChannels.size() > 0) {
                for (int index = 0; index < roughChannels.size(); index++) {
                    // Create each channel
                    PodCastChannelDTO parsedChannel =  this.factory.createChannel(roughChannels.get(index), this.searchPaidChannels);
                    if (parsedChannel != null) {
                        returnedChannels.add(parsedChannel);
                    }
                }
            }
        } else {
            String msg = "Unable to generate a valid url with the provided parameters";
            LOGGER.log(Level.SEVERE,msg);
            throw new PodCastAccessUriException(msg);
        }
        return returnedChannels;
    }

    /**
     * Adds a query parameter for the URI that will be used to perform a GET request over the ITunes store
     * @param key parameters key
     * @param value parameters value
     */
    private void addUriParameter(String key, String value) {
        if (this.queryParametersMap != null) {
            if (key != null && value != null ) {
                try {
                    this.queryParametersMap.put(key, URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    // Error processing
                    String msg = "UnsupportedEncodingException when adding key to URL - Key: " +
                            key + ", Encoding: " + StandardCharsets.UTF_8.toString();
                    LOGGER.log(Level.SEVERE, msg);
                    throw new PodCastAccessUriException(msg);
                }
            } else {
                LOGGER.log(Level.WARNING,"Invalid key or value provided");
            }
        } else {
            LOGGER.log(Level.SEVERE,"Query parameter map not correctly initialized");
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
    private List<PodCastChannel> parseRoughMessage(String message) {
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

    /**
     * Creates a single parameter to be added to the URI in its final form (key plus value)
     * @param parameter key of the parameter to be added
     * @param value value of the parameter to be added
     * @param lastParameter flag to operate it as final parameter or not and add the separation
     * @return string with the parameter created
     */
    private String createParameterForUri(String parameter, String value, boolean lastParameter) {
        String parameterCreated = parameter + GenericITunesConstants.ITUNES_PARAMETER_EQUAL + value;
        if (!lastParameter) {
            return parameterCreated + GenericITunesConstants.ITUNES_PARAMETER_SEPARATION;
        } else {
            return parameterCreated;
        }
    }

    /**
     * Adds default URI parameters. Parameters added by default are:
     * - entity = podcast
     */
    private void addDefaultUriParameters() {
        this.queryParametersMap.put(ITunesSearchKeys.ENTITY.toString(),
                ITunesSpecificPodCastKeys.SEARCH_TERM_PODCAST.toString());
    }
}
