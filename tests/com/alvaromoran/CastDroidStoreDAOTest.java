package com.alvaromoran;

import com.alvaromoran.data.PodCastChannelDTO;
import com.alvaromoran.data.SingleEpisode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CastDroidStoreDAOTest {

    private static String VALID_SEARCH_TERM = "Cultureta";
    private static String GENERIC_SEARCH_TERM = "PodCast";
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
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(null);
        assertNull(returnedValue);
    }

    @Test
    void updateTermSearchParameterEmpty() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void updateTermSearchParameterValid() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterNullWithoutTerm() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(null);
        assertNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterEmptyWithoutTerm() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterValidWithoutTerm() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterNullWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterEmptyWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterValidWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterNullWithoutTerm() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(null);
        assertNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithoutTerm() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterValidWithoutTerm() {
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterNullWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterValidWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertNotNull(returnedValue);
    }


    @Test
    void setResultsLimitZero() {
        this.castDroidStoreDAO.setResultsLimit(0);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setResultsLimitFive() {
        this.castDroidStoreDAO.setResultsLimit(5);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM);
        assertEquals(5, returnedValue.size());
    }

    @Test
    void setResultsLimitHundred() {
        this.castDroidStoreDAO.setResultsLimit(100);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM); // generic search that returns a lot of values
        assertEquals(100, returnedValue.size());
    }

    @Test
    void setResultsLimitNegative() {
        this.castDroidStoreDAO.setResultsLimit(-1);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setAutoQueryChannelsOptionTrue() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(true);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void setAutoQueryChannelsOptionFalse() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNotNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandNotValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter("");
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandNotValidQueryAfterValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNotNull(returnedValue);
        returnedValue = this.castDroidStoreDAO.updateTermSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void getEnrichedChannelInformation() {
    }

    @Test
    void getListOfEpisodesFromUrl() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter("historia");
        List<PodCastChannelDTO> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        List<SingleEpisode> episodes = this.castDroidStoreDAO.getListOfEpisodesFromUrl(returnedValue.get(0).feedUrl);
    }
}