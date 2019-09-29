package com.alvaromoran;

import com.alvaromoran.constants.GenericITunesConstants;
import com.alvaromoran.constants.ITunesSearchKeys;
import com.alvaromoran.constants.ITunesSpecificPodCastKeys;
import com.alvaromoran.data.EnrichedChannel;
import com.alvaromoran.data.JsonRoot;
import com.alvaromoran.data.PodCastChannel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CastDroidStoreDAO implements PodCastsDAO {

    private static final Logger LOGGER = Logger.getLogger(CastDroidStoreDAO.class.getName());

    private static final int MAX_RESULTS = 200;

    private static final int MIN_RESULTS = 1;

    private ConnectionManager connectionManager;

    private HashMap<String, String> queryParametersMap;

    private boolean autoQueryChannels = false;

    public CastDroidStoreDAO() {
        this.connectionManager = new ConnectionManager();
        this.queryParametersMap = new HashMap<>();
        LOGGER.setLevel(Level.INFO);
    }

    @Override
    public List<PodCastChannel> updateTermSearchParameter(String term) {
        // Store value
        if (term == null || term.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSearchKeys.TERM.toString());
        } else {
            addUriParameter(ITunesSearchKeys.TERM.toString(), term);
        }
        return (this.autoQueryChannels? executeUri() : null);
    }

    @Override
    public List<PodCastChannel> updateArtistSearchParameter(String artist) {
        // Store value
        if (artist == null || artist.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_ARTIST.toString());
        } else {
            addUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_ARTIST.toString(), artist);
        }
        return (this.autoQueryChannels? executeUri() : null);
    }

    @Override
    public List<PodCastChannel> updateAuthorSearchParameter(String author) {
        // Store value
        if (author == null || author.equalsIgnoreCase("")) {
            removeUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_AUTHOR.toString());
        } else {
            addUriParameter(ITunesSpecificPodCastKeys.SEARCH_ATTRIBUTE_PODCAST_AUTHOR.toString(), author);
        }
        return (this.autoQueryChannels? executeUri() : null);
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
    public List<PodCastChannel> executeQueryOnDemand() {
        return executeUri();
    }

    @Override
    public EnrichedChannel getEnrichedChannelInformation(PodCastChannel selectedChannel) {
        return null;
    }


    private List<PodCastChannel> executeUri() {
        List<PodCastChannel> returnedChannels = null;
        String url = createFullQuery();
        if (url != null && isValidUri(url)) {
            returnedChannels = parseRoughMessage(this.connectionManager.performGetRequest(url));
        } else {
            LOGGER.log(Level.WARNING,"Unable to generate a valid url with the provided parameters");
        }
        return returnedChannels;
    }

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

    private void removeUriParameter(String key) {
        this.queryParametersMap.remove(key);
    }

    private boolean isValidUri(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Url generated based is invalid: " + url);
            return false;
        }
    }

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

    private List<PodCastChannel> parseRoughMessage(String message) {
        JsonRoot fullMessageParsed;
        Gson jsonDeserializer = new GsonBuilder().create();
        fullMessageParsed = jsonDeserializer.fromJson(message, JsonRoot.class);
        return new ArrayList<>(fullMessageParsed.results);
    }

    private StringBuilder initializeUri() {
        // Clean uri and compose the authority
        StringBuilder uriBuilder = new StringBuilder();
        return uriBuilder.append(GenericITunesConstants.ITUNES_AUTHORITY)
                .append(GenericITunesConstants.ITUNES_SEARCH_URI)
                .append(GenericITunesConstants.ITUNES_QUERY_CHARACTER);
    }

    private void fillUriParameters(StringBuilder uriBuilder) {
        Iterator<Map.Entry<String, String>> iterator = this.queryParametersMap.entrySet().iterator();
        while (iterator.hasNext()) {
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
