package com.alvaromoran.podcastaccess.factories;

import com.alvaromoran.podcastaccess.data.dto.PodCastChannelDTO;
import com.alvaromoran.podcastaccess.data.json.PodCastChannel;
import org.w3c.dom.Document;

/**
 * Factory used to create podcasts channels and episodes information
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public interface PodCastsFactory {

    /**
     * Creates channel from the JSON received from the ITunes store
     * @param roughChannelInfo
     * @param paidChannels
     * @return
     */
    PodCastChannelDTO createChannel(PodCastChannel roughChannelInfo, boolean paidChannels);

    /**
     * Creates channel with episodes information from the XML received from the provider
     * @param channel
     * @param deserializedMessage
     * @return
     */
    PodCastChannelDTO createChannelWithEpisodes(PodCastChannelDTO channel, Document deserializedMessage);

    /**
     * Creates channel extended information from the XML received from the provider
     * @param channel
     * @param deserializedMessage
     * @return
     */
    PodCastChannelDTO createChannelExtended(PodCastChannelDTO channel, Document deserializedMessage);

    /**
     * Creates channel extended information with episodes from the XML received from the provider
     * @param channel
     * @param deserializedMessage
     * @return
     */
    PodCastChannelDTO createChannelExtendedAndEpisodes(PodCastChannelDTO channel, Document deserializedMessage);
}
