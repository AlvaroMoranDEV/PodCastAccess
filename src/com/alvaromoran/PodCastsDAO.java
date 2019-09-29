package com.alvaromoran;

import com.alvaromoran.data.EnrichedChannel;
import com.alvaromoran.data.PodCastChannel;

import java.util.List;

public interface PodCastsDAO {

    List<PodCastChannel> updateTermSearchParameter(String term);

    List<PodCastChannel>  updateArtistSearchParameter(String artist);

    List<PodCastChannel>  updateAuthorSearchParameter(String author);

    void setResultsLimit(int number);

    void setAutoQueryChannelsOption(boolean autoQuery);

    List<PodCastChannel> executeQueryOnDemand();

    EnrichedChannel getEnrichedChannelInformation(PodCastChannel selectedChannel);



}
