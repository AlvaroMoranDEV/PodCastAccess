package com.alvaromoran.constants;

/**
 * Genre-related parameters allowed while performing the GET request
 * over the ITunes store
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public enum ITunesGenreKeys {

    /** Set of different genres to categorize and episode / channel on the ITunes Store */
    SEARCH_GENRE_ARTS("Arts"),
    SEARCH_GENRE_COMEDY("Comedy"),
    SEARCH_GENRE_EDUCATION("Education"),
    SEARCH_GENRE_KIDS("Kids & Family"),
    SEARCH_GENRE_HEALTH("Health"),
    SEARCH_GENRE_TV("TV & Film"),
    SEARCH_GENRE_MUSIC("Music"),
    SEARCH_GENRE_NEWS("News & Politics"),
    SEARCH_GENRE_RELIGION("Religion & Spirituality"),
    SEARCH_GENRE_SCIENCE("Science & Medicine"),
    SEARCH_GENRE_SPORTS("Sports & Recreation"),
    SEARCH_GENRE_TECHNOLOGY("Technology"),
    SEARCH_GENRE_BUSINESS("Business"),
    SEARCH_GENRE_GAMES("Games & Hobbies"),
    SEARCH_GENRE_CULTURE("Society & Culture"),
    SEARCH_GENRE_GOVERNMENT("Government & Organizations");

    /** This terms will be treated as strings*/
    private final String key;

    ITunesGenreKeys(final String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
