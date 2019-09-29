package com.alvaromoran.constants;

public enum ITunesSpecificPodCastKeys {

    /** Specific keys to search PodCasts over the ITunes Store DB */
    SEARCH_TERM_PODCAST("podcast"),
    SEARCH_TERM_PODCAST_AUTHOR("podcastAuthor"),
    SEARCH_ATTRIBUTE_PODCAST_TITLE("titleTerm"),
    SEARCH_ATTRIBUTE_PODCAST_LANGUAGE("languageTerm"),
    SEARCH_ATTRIBUTE_PODCAST_AUTHOR("authorTerm"),
    SEARCH_ATTRIBUTE_PODCAST_GENRE("genreIndex"),
    SEARCH_ATTRIBUTE_PODCAST_ARTIST("artistTerm"),
    SEARCH_ATTRIBUTE_PODCAST_RATING("ratingIndex"),
    SEARCH_ATTRIBUTE_PODCAST_KEYWORDS("keywordsTerm"),
    SEARCH_ATTRIBUTE_PODCAST_DESCRIPTION("descriptionTerm");

    /** Query parameters will be treated as strings */
    private final String key;

    ITunesSpecificPodCastKeys(final String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }

}
