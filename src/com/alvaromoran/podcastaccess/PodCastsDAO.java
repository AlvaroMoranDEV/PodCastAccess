package com.alvaromoran.podcastaccess;

import com.alvaromoran.podcastaccess.data.dto.PodCastChannelDTO;
import com.alvaromoran.podcastaccess.exceptions.PodCastAccessConnectionException;
import com.alvaromoran.podcastaccess.exceptions.PodCastAccessUriException;

import java.util.List;

/**
 * Interface used to access the DAO functionality, which allows to:
 * - Search channels information in the ITunes store
 * - Search particular channel information directly from the content provider
 * - Get the list of episodes from a particular channel
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public interface PodCastsDAO {

    /**
     * Updates the main term parameter used to search in the ITunes store
     * @param term term value
     * @return channels returned as result of the query if the auto query option is enabled
     */
    List<PodCastChannelDTO> updateTermSearchParameter(String term) throws PodCastAccessUriException;

    /**
     * Updates the artist term parameter used to search in the ITunes store
     * @param artist artist value
     * @return channels returned as result of the query if the auto query option is enabled
     */
    List<PodCastChannelDTO>  updateArtistSearchParameter(String artist) throws PodCastAccessUriException;

    /**
     * Updates the author term parameter used to search in the ITunes store
     * @param author author value
     * @return channels returned as result of the query if the auto query option is enabled
     */
    List<PodCastChannelDTO>  updateAuthorSearchParameter(String author) throws PodCastAccessUriException;

    /**
     * Sets the limit of channels returned per query. Default value is 50, minimum value is 1 and max value is 200
     * @param number max number of channels returned per query
     */
    void setChannelResultsLimit(int number) throws PodCastAccessUriException;

    /**
     * Sets if the search of channels will be based on paid and free channels (<code>true</code>) or only over free
     * channels (<code>false</code>). By default the value of this flag is false.
     * @param searchPaidChannels new value for the flag
     */
    void setSearchPaidChannels(boolean searchPaidChannels);

    /**
     * Enables / disables the auto query option. If enabled, when the search parameters are updated, the query is
     * automatically executed. If disabled, the query is only executed when calling the executeQueryOnDemand method
     * @param autoQuery <code>true</code> enables the auto query functionality
     *                  <code>false</code> disables the auto query functionality
     */
    void setAutoQueryChannelsOption(boolean autoQuery);

    /**
     * Executes the query instantly based on the stored parameters, if any
     * @return list of channels returned as result of the query
     */
    List<PodCastChannelDTO> executeQueryOnDemand();

    /**
     * Gets enriched channel information from a particular channel passed as an argument. This method will add
     * information such as copyright, detailed descriptions, list of episodes... for the ChannelInformation object
     * @param selectedChannel channel to be updated with detailed information
     * @param getEpisodes <code>true</code> the channel is filled with episodes information - It may be a time consuming process
     *                    <code>false</code> then channel is not filled with episodes information
     */
    PodCastChannelDTO getEnrichedChannelInformation(PodCastChannelDTO selectedChannel, boolean getEpisodes) throws PodCastAccessConnectionException;

    /**
     * Gets the list of episodes related to a particular channel. No additional information is added to the channel object
     * @param selectedChannel channel to get the episodes from
     * @return list of episodes
     */
    PodCastChannelDTO getListOfEpisodesFromChannel(PodCastChannelDTO selectedChannel);
}
