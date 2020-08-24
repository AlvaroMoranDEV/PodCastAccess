package com.alvaromoran.podcastaccess.factories;

import com.alvaromoran.podcastaccess.data.dto.EpisodeDTO;
import com.alvaromoran.podcastaccess.data.dto.PodCastChannelDTO;
import com.alvaromoran.podcastaccess.data.json.PodCastChannel;
import org.w3c.dom.Document;

import java.util.List;

/**
 * PodCastsFactory interface implementation
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class PodCastsFactoryImpl implements PodCastsFactory {

    /**
     * Constructor
     */
    public PodCastsFactoryImpl() {
        // empty
    }

    @Override
    public PodCastChannelDTO createChannel(PodCastChannel roughChannelInfo, boolean paidChannels) {
        return ChannelsFactory.createChannelFromEntity(roughChannelInfo, paidChannels);
    }

    @Override
    public PodCastChannelDTO createChannelWithEpisodes(PodCastChannelDTO channel, Document deserializedMessage) {
        List<EpisodeDTO> episodesList = EpisodesFactory.getParsedListOfEpisodesFromDocument(deserializedMessage);
        channel.setEpisodes(episodesList);
        return channel;
    }

    @Override
    public PodCastChannelDTO createChannelExtended(PodCastChannelDTO channel, Document deserializedMessage) {
        ChannelsFactory.enrichChannelFromAuthorsInformation(deserializedMessage, channel);
        return channel;
    }

    @Override
    public PodCastChannelDTO createChannelExtendedAndEpisodes(PodCastChannelDTO channel, Document deserializedMessage) {
        channel = this.createChannelExtended(channel, deserializedMessage);
        channel = this.createChannelWithEpisodes(channel, deserializedMessage);
        return channel;
    }
}
