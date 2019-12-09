package com.alvaromoran.data;

/**
 * Episode information - Only the title and the audio information are mandatory
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class SingleEpisode {

    /** Episode title */
    private String title;

    /** Episode subtitle */
    private String subTitle;

    /** Episode summary */
    private String summary;

    /** Episode description */
    private String description;

    /** Episode keywords */
    private String keywords;

    /** Episode duration */
    private String episodeDuration;

    /** Episode release date */
    private String releaseDate;

    /** Episode season */
    private int season;

    /** Episode number */
    private int episode;

    /** Episode audio information */
    private AudioInformation audioInformation;

    /** Episode image reference */
    private String imageUrl;

    /**
     * Constructor of the class with the mandatory fields
     * @param title episode title
     * @param audioInformation episode audio information
     */
    public SingleEpisode(String title, AudioInformation audioInformation) {
        this.title = title;
        this.audioInformation = audioInformation;
    }

//region Getters and Setters

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public AudioInformation getAudioInformation() {
        return audioInformation;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getEpisodeDuration() {
        return episodeDuration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setEpisodeDuration(String episodeDuration) {
        this.episodeDuration = episodeDuration;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
//endregion
}
