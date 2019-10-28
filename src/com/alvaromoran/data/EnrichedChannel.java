package com.alvaromoran.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Enriched channel information provided for the particular PodCast channel once the
 * feedURL is provided by the ITunes store. This class contains all episodes of the related
 * PodCast and some detailed channel information
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class EnrichedChannel {

    private String name;

    private String description;

    private String link;

    private String imageUrlLow;

    private String imageUrlHigh;

    private String copyRight;

    private String author;

    private String summary;

    private Collection<String> categories;

    /** Channel information */
    private PodCastChannelDTO channelInfoFromItunes;

    /** Collection of episodes */
    private Collection<SingleEpisode> episodes;

    /**
     * Constructor of the class with basic channel information
     * @param podCastChannel basic channel information
     */
    public EnrichedChannel(PodCastChannelDTO podCastChannel) {
        this.channelInfoFromItunes = podCastChannel;
    }

//region Getters and Setters

    public PodCastChannelDTO getChannelInfoFromItunes() {
        return this.channelInfoFromItunes;
    }

    public void addSingleEpisode(SingleEpisode episode) {
        if (this.episodes == null) {
            this.episodes = new ArrayList<>();
        }
        this.episodes.add(episode);
    }

    public void addEpisodes(Collection<SingleEpisode> episodes) {
        this.episodes = episodes;
    }

    public Collection<SingleEpisode> getEpisodes() {
        return this.episodes;
    }

//endregion
}
