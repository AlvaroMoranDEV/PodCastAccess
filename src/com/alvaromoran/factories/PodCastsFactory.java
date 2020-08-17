package com.alvaromoran.factories;

import com.alvaromoran.data.dto.PodCastChannelDTO;
import com.alvaromoran.data.json.PodCastChannel;
import org.w3c.dom.Document;

public interface PodCastsFactory {

    PodCastChannelDTO createChannel(PodCastChannel roughChannelInfo, boolean paidChannels);

    PodCastChannelDTO createChannelWithEpisodes(PodCastChannelDTO channel, Document deserializedMessage);

    PodCastChannelDTO createChannelExtended(PodCastChannelDTO channel, Document deserializedMessage);

    PodCastChannelDTO createChannelExtendedAndEpisodes(PodCastChannelDTO channel, Document deserializedMessage);
}
