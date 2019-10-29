package com.alvaromoran;

import com.alvaromoran.data.ChannelInformation;
import com.alvaromoran.data.SingleEpisode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CastDroidStoreDAOTest {

    private static String VALID_SEARCH_TERM = "Cultureta";
    private static String GENERIC_SEARCH_TERM_1 = "PodCast";
    private static String GENERIC_SEARCH_TERM_2 = "historia";
    private static String VALID_SEARCH_ARTIST = "Onda Cero";
    private static String VALID_SEARCH_AUTHOR = "OndaCero";

    private CastDroidStoreDAO castDroidStoreDAO;

    @BeforeEach
    void initialize() {
        this.castDroidStoreDAO = new CastDroidStoreDAO();
        this.castDroidStoreDAO.setAutoQueryChannelsOption(true);
    }

    @Test
    void updateTermSearchParameterNull() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(null);
       assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateTermSearchParameterEmpty() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter("");
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateTermSearchParameterValid() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterNullWithoutTerm() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(null);
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateArtistSearchParameterEmptyWithoutTerm() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter("");
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateArtistSearchParameterValidWithoutTerm() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateArtistSearchParameterNullWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterEmptyWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterValidWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterNullWithoutTerm() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(null);
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithoutTerm() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter("");
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateAuthorSearchParameterValidWithoutTerm() {
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void updateAuthorSearchParameterNullWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterValidWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertNotNull(returnedValue);
    }


    @Test
    void setResultsLimitZero() {
        this.castDroidStoreDAO.setChannelResultsLimit(0);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setResultsLimitFive() {
        this.castDroidStoreDAO.setChannelResultsLimit(5);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        assertEquals(5, returnedValue.size());
    }

    @Test
    void setResultsLimitHundred() {
        this.castDroidStoreDAO.setChannelResultsLimit(100);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1); // generic search that returns a lot of values
        assertEquals(100, returnedValue.size());
    }

    @Test
    void setResultsLimitNegative() {
        this.castDroidStoreDAO.setChannelResultsLimit(-1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setAutoQueryChannelsOptionTrue() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(true);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void setAutoQueryChannelsOptionFalse() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNotNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandNotValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter("");
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertTrue(returnedValue != null && returnedValue.size() == 0);
    }

    @Test
    void executeQueryOnDemandNotValidQueryAfterValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNotNull(returnedValue);
        returnedValue = this.castDroidStoreDAO.updateTermSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void getListOfEpisodesFromUrl() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_2);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromUrl(returnedValue.get(0).getFeedUrl());
        assertTrue(episodes.size() != 0);
    }

    @Test
    void getListOfEpisodesFromUrlNull() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_2);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromUrl(null);
        assertEquals(0, episodes.size());
    }

    @Test
    void getListOfEpisodesFromUrlEmpty() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_2);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromUrl("");
        assertEquals(0, episodes.size());
    }

    @Test
    void getEnrichedChannelInformationGoodChannelAndEpisodes() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.castDroidStoreDAO.getEnrichedChannelInformation(returnedValue.get(0));
            assertNotNull(returnedValue.get(0).getEpisodes());
            assertNotNull(returnedValue.get(0).getCopyright());
        }
    }

    @Test
    void getEnrichedChannelInformationGoodChannelNoEpisodes() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.castDroidStoreDAO.getEnrichedChannelInformation(returnedValue.get(0), false);
            assertNull(returnedValue.get(0).getEpisodes());
            assertNotNull(returnedValue.get(0).getCopyright());
        }
    }

    @Test
    void getEnrichedChannelInformationGoodChannelEpisodesWithFlag() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.castDroidStoreDAO.getEnrichedChannelInformation(returnedValue.get(0), true);
            assertNotNull(returnedValue.get(0).getEpisodes());
            assertNotNull(returnedValue.get(0).getCopyright());
        }
    }

    @Test
    void getEnrichedChannelInformationNullChannel() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.castDroidStoreDAO.getEnrichedChannelInformation(null);
            assertNull(returnedValue.get(0).getEpisodes());
        }
    }

    @Test
    void getListOfEpisodesFromChannel() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromChannel(returnedValue.get(0));
            assertNotEquals(0, episodes.size());
        }
    }

    @Test
    void getListOfEpisodesFromNullChannel() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromChannel(null);
            assertEquals(0, episodes.size());
        }
    }

    @Test
    void getListOfEpisodesFromEmptyFeedChannel() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            returnedValue.get(0).setFeedUrl("");
            List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromChannel(returnedValue.get(0));
            assertEquals(0, episodes.size());
        }
    }

    @Test
    void getListOfEpisodesFromNullFeedChannel() {
        this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<ChannelInformation> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            returnedValue.get(0).setFeedUrl(null);
            List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromChannel(returnedValue.get(0));
            assertEquals(0, episodes.size());
        }
    }
}