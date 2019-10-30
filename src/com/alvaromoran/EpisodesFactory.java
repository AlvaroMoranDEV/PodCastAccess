package com.alvaromoran;

import com.alvaromoran.constants.XmlFeedConstants;
import com.alvaromoran.data.AudioInformation;
import com.alvaromoran.data.SingleEpisode;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to create the SingleEpisode object based on a factory pattern
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
class EpisodesFactory {

    /** Logger of the class */
    private static final Logger LOGGER = Logger.getLogger(EpisodesFactory.class.getName());

    /**
     * Gets the full list of parsed episodes form a channel based on the returned XML
     * @param deserializedMessage document that contains the received XML
     * @return list of channel episodes
     */
    static List<SingleEpisode> getParsedListOfEpisodesFromDocument(Document deserializedMessage) {
        // Look for the items of the deserialized message
        List<SingleEpisode> parsedEpisodes = new ArrayList<>();
        if (deserializedMessage != null) {
            NodeList itemList = deserializedMessage.getElementsByTagName(XmlFeedConstants.XML_ITEM_TAG);
            for (int index = 0; index < itemList.getLength(); index++) {
                Node item = itemList.item(index);
                // Once the items are found, the method iterate through them to parse them into SingleEpisode elements
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element episodeToParse = (Element) item;
                    SingleEpisode episode = EpisodesFactory.createEpisodeFromElement(episodeToParse);
                    if (episode != null) {
                        parsedEpisodes.add(episode);
                    }
                }
            }
        }
        return parsedEpisodes;
    }

    /**
     * Creates the SingleEpisode object based on the item node of the XML gathered from the channel provider
     * @param nodeInformation  item node of the XML
     * @return well-formed SingleEpisode class if possible
     */
    private static SingleEpisode createEpisodeFromElement(Element nodeInformation) {
        if (nodeInformation != null) {
            try {
                // Load information from node
                String title = parseTitle(nodeInformation);
                String subtitle = parseSubTitle(nodeInformation);
                String summary = parseSummary(nodeInformation);
                String duration = parseDuration(nodeInformation);
                String description = parseDescription(nodeInformation);
                String releaseDate = parseReleaseDate(nodeInformation);
                String keywords = parseKeywords(nodeInformation);
                AudioInformation audio = parseAudioInformation(nodeInformation);
                int season = parseSeason(nodeInformation);
                int episodeNumber = parseEpisodeNumber(nodeInformation);
                // Minimum information loaded -  episode is created
                if (title != null && audio != null)  {
                    SingleEpisode episode = new SingleEpisode(title, audio);
                    episode.setSummary(summary);
                    episode.setSubTitle(subtitle);
                    episode.setEpisodeDuration(duration);
                    episode.setDescription(description);
                    episode.setReleaseDate(releaseDate);
                    episode.setKeywords(keywords);
                    episode.setSeason(season);
                    episode.setEpisode(episodeNumber);
                    return episode;
                    // Otherwise, the episode is discarded
                } else {
                    LOGGER.log(Level.WARNING, "Title or audio information not found for episode - Item will be discarded");
                    return null;
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Error while trying to correctly parse episode information - Item will be discarded");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Gets the title information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed title
     */
    private static String parseTitle(Element nodeInformation) throws NullPointerException {
        String title = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_TITLE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            title = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_TITLE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
         } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_TITLE).item(0) != null) {
            title = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_TITLE).item(0).getTextContent();
        }
        return title;
    }

    /**
     * Gets the audio information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed AudioInformation object with the URL of the audio, its format and length
     */
    private static AudioInformation parseAudioInformation(Element nodeInformation) throws NumberFormatException {
        if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_ENCLOSURE).item(0) != null) {
            NamedNodeMap attributes = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_ENCLOSURE).item(0).getAttributes();
            if (attributes != null) {
                String url = attributes.getNamedItem(XmlFeedConstants.XML_ENCLOSURE_URL).getTextContent();
                int length = Integer.parseInt(attributes.getNamedItem(XmlFeedConstants.XML_ENCLOSURE_LENGTH).getTextContent());
                String type = attributes.getNamedItem(XmlFeedConstants.XML_ENCLOSURE_TYPE).getTextContent();
                if (url != null) {
                    return new AudioInformation(url, type, length);
                } else {
                    LOGGER.log(Level.WARNING, "Received item with null url - Item will be discarded");
                    return null;
                }
            } else {
                LOGGER.log(Level.WARNING, "Unable to parse enclosure - Item will be discarded");
                return null;
            }
        } else {
            LOGGER.log(Level.WARNING, "Received item with null enclosure - Item will be discarded");
            return null;
        }
    }

    /**
     * Gets the subtitle information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed subtitle
     */
    private static String parseSubTitle(Element nodeInformation) {
        String subtitle = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUBTITLE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            subtitle = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUBTITLE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUBTITLE).item(0) != null) {
            subtitle = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUBTITLE).item(0).getTextContent();
        }
        return subtitle;
    }

    /**
     * Gets the summary information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed summary
     */
    private static String parseSummary(Element nodeInformation) {
        String summary = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUMMARY, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            summary = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUMMARY, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUMMARY).item(0) != null) {
            summary = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUMMARY).item(0).getTextContent();
        }
        return summary;
    }

    /**
     * Gets the duration information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed duration
     */
    private static String parseDuration(Element nodeInformation) {
        String duration = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DURATION, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            duration = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DURATION, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DURATION).item(0) != null) {
            duration = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DURATION).item(0).getTextContent();
        }
        return duration;
    }

    /**
     * Gets the copyright information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed copyright
     */
    private static String parseDescription(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DESCRIPTION, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DESCRIPTION, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0).getTextContent();
        }
        return description;
    }

    /**
     * Gets the release date information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed release date (string format, not date)
     */
    private static String parseReleaseDate(Element nodeInformation) {
        String pubDate = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_PUBDATE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_PUBDATE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_PUBDATE).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_PUBDATE).item(0).getTextContent();
        }
        return pubDate;
    }

    /**
     * Gets the keywords information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed keywords
     */
    private static String parseKeywords(Element nodeInformation) {
        String pubDate = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_KEYWORDS, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_KEYWORDS, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_KEYWORDS).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_KEYWORDS).item(0).getTextContent();
        }
        return pubDate;
    }

    /**
     * Gets the season information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed season
     */
    private static int parseSeason(Element nodeInformation) throws NumberFormatException {
        int season = -1;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SEASON, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            season = Integer.parseInt(nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SEASON, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent());
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SEASON).item(0) != null) {
            season = Integer.parseInt(nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SEASON).item(0).getTextContent());
        }
        return season;
    }

    /**
     * Gets the episode number information from the item element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed episode number
     */
    private static int parseEpisodeNumber(Element nodeInformation) throws NumberFormatException {
        int episodeNumber = -1;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_EPISODE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            episodeNumber = Integer.parseInt(nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_EPISODE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent());
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_EPISODE).item(0) != null) {
            episodeNumber = Integer.parseInt(nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_EPISODE).item(0).getTextContent());
        }
        return episodeNumber;
    }
}
