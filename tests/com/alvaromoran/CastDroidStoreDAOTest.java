package com.alvaromoran;

import com.alvaromoran.data.PodCastChannel;
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
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(null);
        assertNull(returnedValue);
    }

    @Test
    void updateTermSearchParameterEmpty() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void updateTermSearchParameterValid() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterNullWithoutTerm() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(null);
        assertNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterEmptyWithoutTerm() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterValidWithoutTerm() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterNullWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterEmptyWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateArtistSearchParameterValidWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateArtistSearchParameter(VALID_SEARCH_ARTIST);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterNullWithoutTerm() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(null);
        assertNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithoutTerm() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterValidWithoutTerm() {
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterNullWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(null);
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterEmptyWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter("");
        assertNotNull(returnedValue);
    }

    @Test
    void updateAuthorSearchParameterValidWithTerm() {
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateAuthorSearchParameter(VALID_SEARCH_AUTHOR);
        assertNotNull(returnedValue);
    }


    @Test
    void setResultsLimitZero() {
        this.castDroidStoreDAO.setResultsLimit(0);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setResultsLimitFive() {
        this.castDroidStoreDAO.setResultsLimit(5);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM);
        assertEquals(5, returnedValue.size());
    }

    @Test
    void setResultsLimitHundred() {
        this.castDroidStoreDAO.setResultsLimit(100);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM); // generic search that returns a lot of values
        assertEquals(100, returnedValue.size());
    }

    @Test
    void setResultsLimitNegative() {
        this.castDroidStoreDAO.setResultsLimit(-1);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(GENERIC_SEARCH_TERM);
        assertEquals(50, returnedValue.size());
    }

    @Test
    void setAutoQueryChannelsOptionTrue() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(true);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNotNull(returnedValue);
    }

    @Test
    void setAutoQueryChannelsOptionFalse() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        assertNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNotNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandNotValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter("");
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNull(returnedValue);
    }

    @Test
    void executeQueryOnDemandNotValidQueryAfterValidQuery() {
        this.castDroidStoreDAO.setAutoQueryChannelsOption(false);
        this.castDroidStoreDAO.updateTermSearchParameter(VALID_SEARCH_TERM);
        List<PodCastChannel> returnedValue = this.castDroidStoreDAO.executeQueryOnDemand();
        assertNotNull(returnedValue);
        returnedValue = this.castDroidStoreDAO.updateTermSearchParameter("");
        assertNull(returnedValue);
    }

    @Test
    void getEnrichedChannelInformation() {
    }
}