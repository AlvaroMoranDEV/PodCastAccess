package com.alvaromoran.podcastaccess.data.dto;

/**
 * Class that stores basic audio information of the PodCast episode
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class AudioInformationDTO {

    /** URL where the audio is located */
    private String audioUrl;

    /** Format of the audio */
    private String audioFormat;

    /** Length of the audio*/
    private int audioLength;

    /**
     * Constructor of the class with basic audio information
     * @param audioUrl url where the audio is located
     * @param audioFormat format of the audio
     * @param audioLength length of the audio
     */
    public AudioInformationDTO(String audioUrl, String audioFormat, int audioLength) {
        this.audioFormat = audioFormat;
        this.audioLength = audioLength;
        this.audioUrl = audioUrl;
    }

//region Getters and Setters

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getAudioFormat() {
        return audioFormat;
    }

    public int getAudioLength() {
        return audioLength;
    }

//endregion
}
