package com.alvaromoran.data.dto;

/**
 * Episode information - Only the title and the audio information are mandatory
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class EpisodeDTO {

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
    private AudioInformationDTO audioInformationDTO;

    /** Episode image reference */
    private String imageUrl;

    /**
     * Constructor of the class with the mandatory fields
     * @param title episode title
     * @param audioInformationDTO episode audio information
     */
    public EpisodeDTO(String title, AudioInformationDTO audioInformationDTO) {
        this.title = title;
        this.audioInformationDTO = audioInformationDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEpisodeDuration() {
        return episodeDuration;
    }

    public void setEpisodeDuration(String episodeDuration) {
        this.episodeDuration = episodeDuration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public AudioInformationDTO getAudioInformationDTO() {
        return audioInformationDTO;
    }

    public void setAudioInformationDTO(AudioInformationDTO audioInformationDTO) {
        this.audioInformationDTO = audioInformationDTO;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
