package com.alvaromoran;

import com.alvaromoran.data.EnrichedChannel;
import com.alvaromoran.data.SingleEpisode;
import com.alvaromoran.data.PodCastChannelDTO;

import java.util.List;

public interface PodCastsDAO {

    List<PodCastChannelDTO> updateTermSearchParameter(String term);

    List<PodCastChannelDTO>  updateArtistSearchParameter(String artist);

    List<PodCastChannelDTO>  updateAuthorSearchParameter(String author);

    void setResultsLimit(int number);

    void setAutoQueryChannelsOption(boolean autoQuery);

    List<PodCastChannelDTO> executeQueryOnDemand();

    EnrichedChannel getEnrichedChannelInformation(PodCastChannelDTO selectedChannel);

    List<SingleEpisode> getListOfEpisodesFromChannel(PodCastChannelDTO selectedChannel);

    List<SingleEpisode> getListOfEpisodesFromUrl(String url);



}
