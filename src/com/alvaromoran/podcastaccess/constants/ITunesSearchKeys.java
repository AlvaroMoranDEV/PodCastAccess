package com.alvaromoran.podcastaccess.constants;

/**
 * Main parameters allowed while performing the GET request
 * over the ITunes store
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public enum ITunesSearchKeys {

    /** ITunes Store main query parameters */
    TERM("term"),
    COUNTRY("country"),
    MEDIA("media"),
    ENTITY("entity"),
    CALLBACK("callback"),
    LIMIT("limit"),
    LANG("lang"),
    VERSION("version"),
    EXPLICIT("explicit");

    /** Query parameters will be treated as strings */
    private final String key;

    ITunesSearchKeys(final String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }


}
