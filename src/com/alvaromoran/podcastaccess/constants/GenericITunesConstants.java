package com.alvaromoran.podcastaccess.constants;

/**
 * Constants used to create the API REST GET request over the ITunes store
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */

public class GenericITunesConstants {

    /** Separator between main URL and search parameters */
    public static final String ITUNES_QUERY_CHARACTER = "?";

    /** Separator between query URI terms */
    public static final String ITUNES_PARAMETER_SEPARATION = "&";

    /** Separator between query URI parameter and its value */
    public static final String ITUNES_PARAMETER_EQUAL = "=";

    /** Main URL of the ITunes Store REST server*/
    public static final String ITUNES_AUTHORITY = "https://itunes.apple.com";

    /** Path to access the search service of the store*/
    public static final String ITUNES_SEARCH_URI = "/search";

}
