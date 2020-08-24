package com.alvaromoran.podcastaccess.data.dto;

import java.util.Collection;

/**
 * Channel information provided for the particular PodCast channel and ITunes store.
 * This class contains all episodes of the related PodCast and some detailed channel information
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class PodCastChannelDTO {

    // BASIC INFORMATION GET FROM THE ITUNES STORE

    /** Name of the collection */
    private String collection;

    /** List of categories related to the channel */
    private Collection<String> categories;

    /** Low resolution image URL (100px * 100px) */
    private String imageUrlLow;

    /** High resolution image URL (600px * 600px) */
    private String imageUrlHigh;

    /** Link related to the author, episodes and some other enriched info */
    private String feedUrl;

    // ENRICHED INFORMATION GATHERED FROM THE AUTHOR

    /** Description of the channel */
    private String description;

    /** Link with more information about the PodCast */
    private String link;

    /** Copyright information */
    private String copyright;

    /** Author of the channel */
    private String author;

    /** Channel summary */
    private String summary;

    /** Collection of episodes */
    private Collection<EpisodeDTO> episodes;

    /**
     * Constructor of the class with whole channel information
     * @param collection name of the collection
     */
    public PodCastChannelDTO(String collection) {
        this.collection = collection;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public String getImageUrlLow() {
        return imageUrlLow;
    }

    public void setImageUrlLow(String imageUrlLow) {
        this.imageUrlLow = imageUrlLow;
    }

    public String getImageUrlHigh() {
        return imageUrlHigh;
    }

    public void setImageUrlHigh(String imageUrlHigh) {
        this.imageUrlHigh = imageUrlHigh;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Collection<EpisodeDTO> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Collection<EpisodeDTO> episodes) {
        this.episodes = episodes;
    }
}
