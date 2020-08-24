package com.alvaromoran.podcastaccess.constants;

/**
 * Constants used to parse generic XML received from each PodCast provider
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
public class XmlFeedConstants {

    /** Channel element */
    public static final String XML_CHANNEL = "channel";
    public static final String XML_CHANNEL_LINK = "link";
    public static final String XML_CHANNEL_COPYRIGHT = "copyright";
    public static final String XML_CHANNEL_SUMMARY = "summary";
    public static final String XML_CHANNEL_AUTHOR = "author";

    /** Item element */
    public static final String XML_ITEM_TAG = "item";

    /** Title element */
    public static final String XML_ITEM_TITLE = "title";

    /** Subtitle element */
    public static final String XML_ITEM_SUBTITLE = "subtitle";

    /** Description element */
    public static final String XML_ITEM_DESCRIPTION = "description";

    /** Duration element */
    public static final String XML_ITEM_DURATION = "duration";

    /** Summary element */
    public static final String XML_ITEM_SUMMARY = "summary";

    /** Enclosure element */
    public static final String XML_ITEM_ENCLOSURE = "enclosure";

    /** Publication date element */
    public static final String XML_ITEM_PUBDATE = "pubDate";

    /** Set of keywords element */
    public static final String XML_ITEM_KEYWORDS = "keywords";

    /** Episode number element */
    public static final String XML_ITEM_EPISODE = "episode";

    /** Season number element */
    public static final String XML_ITEM_SEASON = "season";

    /** Image element*/
    public static final String XML_ITEM_IMAGE = "image";

    /** Enclosure element */
    public static final String XML_ENCLOSURE_URL = "url";

    /** Audio length element */
    public static final String XML_ENCLOSURE_LENGTH = "length";

    /** Audio type element */
    public static final String XML_ENCLOSURE_TYPE = "type";

    /** Image reference element */
    public static final String XML_IMAGE_HREF = "href";

    /** ITunes namespace */
    public static final String XML_ITUNES_NS = "http://www.itunes.com/dtds/podcast-1.0.dtd";
}
