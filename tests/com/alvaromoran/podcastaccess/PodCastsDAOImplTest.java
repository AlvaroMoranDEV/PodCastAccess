package com.alvaromoran.podcastaccess;

import com.alvaromoran.podcastaccess.data.dto.PodCastChannelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PodCastsDAOImplTest {

    private static final String VALID_SEARCH_TERM = "Cultureta";
    private static final String GENERIC_SEARCH_TERM_1 = "PodCast";
    private static final String VALID_SEARCH_ARTIST = "Onda Cero";
    private static final String VALID_SEARCH_AUTHOR = "OndaCero";

    private PodCastsDAOImpl podCastsDAOImpl;

    @BeforeEach
    public void initialize() {
        this.podCastsDAOImpl = new PodCastsDAOImpl();
        this.podCastsDAOImpl.setAutoQueryChannelsOption(true);
    }

    @Test
    void updateTermSearchParameterNull() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateTermSearchParameter(null);
        } catch (Exception e) {
            returnedException = e;
        }
       assertNotNull(returnedException);
    }

    @Test
    void updateTermSearchParameterEmpty() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateTermSearchParameter("");
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateTermSearchParameterValid() {
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterNullWithoutTerm() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateArtistSearchParameter(null);
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateArtistSearchParameterEmptyWithoutTerm() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateArtistSearchParameter("");
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateArtistSearchParameterValidWithoutTerm() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateArtistSearchParameterNullWithTerm() {
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateArtistSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterEmptyWithTerm() {
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateArtistSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterValidWithTerm() {
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterNullWithoutTerm() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateAuthorSearchParameter(null);
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithoutTerm() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateAuthorSearchParameter("");
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateAuthorSearchParameterValidWithoutTerm() {
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void updateAuthorSearchParameterNullWithTerm() {
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateAuthorSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithTerm() {
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateAuthorSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterValidWithTerm() {
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertNotNull(returnedValue);
    }


    @Test
    void setResultsLimitZero() {
        this.podCastsDAOImpl.setChannelResultsLimit(0);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setResultsLimitFive() {
        this.podCastsDAOImpl.setChannelResultsLimit(5);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        assertEquals(5, returnedValue.size());
    }

    @Test
    void setResultsLimitNegative() {
        this.podCastsDAOImpl.setChannelResultsLimit(-1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setAutoQueryChannelsOptionTrue() {
        this.podCastsDAOImpl.setAutoQueryChannelsOption(true);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void setAutoQueryChannelsOptionFalse() {
        this.podCastsDAOImpl.setAutoQueryChannelsOption(false);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandValidQuery() {
        this.podCastsDAOImpl.setAutoQueryChannelsOption(false);
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        assertNotNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandNotValidQuery() {
        this.podCastsDAOImpl.setAutoQueryChannelsOption(false);
        this.podCastsDAOImpl.updateTermSearchParameter("");
        Exception returnedException = null;
        try {
            this.podCastsDAOImpl.executeQueryOnDemand();
        } catch (Exception e) {
            returnedException = e;
        }
        assertNotNull(returnedException);
    }

    @Test
    void executeQueryOnDemandNotValidQueryAfterValidQuery() {
        this.podCastsDAOImpl.setAutoQueryChannelsOption(false);
        this.podCastsDAOImpl.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        assertNotNull(returnedValue);
        returnedValue = this.podCastsDAOImpl.updateTermSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void getEnrichedChannelInformationGoodChannelAndEpisodes() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.podCastsDAOImpl.getEnrichedChannelInformation(returnedValue.get(0), true);
            assertNotNull(returnedValue.get(0).getEpisodes());
            assertNotNull(returnedValue.get(0).getCopyright());
        }
    }

    @Test
    void getEnrichedChannelInformationGoodChannelNoEpisodes() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.podCastsDAOImpl.getEnrichedChannelInformation(returnedValue.get(0), false);
            assertNull(returnedValue.get(0).getEpisodes());
            assertNotNull(returnedValue.get(0).getCopyright());
        }
    }

    @Test
    void getEnrichedChannelInformationWeirdUrl_1() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            returnedValue.get(0).setFeedUrl("http://www.ondacero.es/rss/podcast/8502/podcast.xml");
            this.podCastsDAOImpl.getEnrichedChannelInformation(returnedValue.get(0), true);
        }


    }

    @Test
    void getEnrichedChannelInformationGoodChannelEpisodesWithFlag() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.podCastsDAOImpl.getEnrichedChannelInformation(returnedValue.get(0), true);
            assertNotNull(returnedValue.get(0).getEpisodes());
            assertNotNull(returnedValue.get(0).getCopyright());
        }
    }

    @Test
    void getEnrichedChannelInformationNullChannel() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            this.podCastsDAOImpl.getEnrichedChannelInformation(null, true);
            assertNull(returnedValue.get(0).getEpisodes());
        }
    }

    @Test
    void getListOfEpisodesFromChannel() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            PodCastChannelDTO channel = this.podCastsDAOImpl.getListOfEpisodesFromChannel(returnedValue.get(0));
            assertNotEquals(0, channel.getEpisodes().size());
        }
    }

    @Test
    void getListOfEpisodesFromNullChannel() {
        Exception returnedException = null;
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        try {
            List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
            if (returnedValue.size() > 0) {
                this.podCastsDAOImpl.getListOfEpisodesFromChannel(null);
            }
        } catch (Exception e) {
            returnedException = e;
        }
        assertNull(returnedException);
    }

    @Test
    void getListOfEpisodesFromEmptyFeedChannel() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            returnedValue.get(0).setFeedUrl("");
            PodCastChannelDTO channel = this.podCastsDAOImpl.getListOfEpisodesFromChannel(returnedValue.get(0));
            assertEquals(0, channel.getEpisodes().size());
        }
    }

    @Test
    void getListOfEpisodesFromNullFeedChannel() {
        this.podCastsDAOImpl.updateTermSearchParameter(GENERIC_SEARCH_TERM_1);
        List<PodCastChannelDTO> returnedValue = this.podCastsDAOImpl.executeQueryOnDemand();
        if (returnedValue.size() > 0) {
            returnedValue.get(0).setFeedUrl(null);
            PodCastChannelDTO channel = this.podCastsDAOImpl.getListOfEpisodesFromChannel(returnedValue.get(0));
            assertEquals(0, channel.getEpisodes().size());
        }
    }
}