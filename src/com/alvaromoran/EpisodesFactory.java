package com.alvaromoran;

import com.alvaromoran.constants.XmlFeedConstants;
import com.alvaromoran.data.AudioInformation;
import com.alvaromoran.data.SingleEpisode;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.logging.Level;
import java.util.logging.Logger;

class EpisodesFactory {

    private static final Logger LOGGER = Logger.getLogger(EpisodesFactory.class.getName());

    public static SingleEpisode createEpisodeFromXml(Element nodeInformation) {
        try {
            // Load information from node
            String title = parseTitle(nodeInformation);
            String subtitle = parseSubTitle(nodeInformation);
            String summary = parseSummary(nodeInformation);
            String duration = parseDuration(nodeInformation);
            String description = parseDescription(nodeInformation);
            String releaseDate = parseReleaseDate(nodeInformation);
            String keywords = parseKeywords(nodeInformation);
            AudioInformation audio = parseUrl(nodeInformation);
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
    }

    private static String parseTitle(Element nodeInformation) throws NullPointerException {
        String title = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_TITLE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            title = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_TITLE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
         } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_TITLE).item(0) != null) {
            title = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_TITLE).item(0).getTextContent();
        }
        return title;
    }

    private static AudioInformation parseUrl(Element nodeInformation) throws NumberFormatException {
        AudioInformation audioInformation;
        if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_ENCLOSURE).item(0) != null) {
            NamedNodeMap attributes = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_ENCLOSURE).item(0).getAttributes();
            if (attributes != null) {
                String url = attributes.getNamedItem(XmlFeedConstants.XML_ENCLOSURE_URL).getTextContent();
                int length = Integer.valueOf(attributes.getNamedItem(XmlFeedConstants.XML_ENCLOSURE_LENGTH).getTextContent());
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

    private static String parseSubTitle(Element nodeInformation) {
        String subtitle = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUBTITLE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            subtitle = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUBTITLE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUBTITLE).item(0) != null) {
            subtitle = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUBTITLE).item(0).getTextContent();
        }
        return subtitle;
    }

    private static String parseSummary(Element nodeInformation) {
        String summary = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUMMARY, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            summary = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SUMMARY, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUMMARY).item(0) != null) {
            summary = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SUMMARY).item(0).getTextContent();
        }
        return summary;
    }

    private static String parseDuration(Element nodeInformation) {
        String duration = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DURATION, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            duration = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DURATION, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DURATION).item(0) != null) {
            duration = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DURATION).item(0).getTextContent();
        }
        return duration;
    }

    private static String parseDescription(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DESCRIPTION, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_DESCRIPTION, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0).getTextContent();
        }
        return description;
    }

    private static String parseReleaseDate(Element nodeInformation) {
        String pubDate = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_PUBDATE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_PUBDATE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_PUBDATE).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_PUBDATE).item(0).getTextContent();
        }
        return pubDate;
    }

    private static String parseKeywords(Element nodeInformation) {
        String pubDate = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_KEYWORDS, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_KEYWORDS, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_KEYWORDS).item(0) != null) {
            pubDate = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_KEYWORDS).item(0).getTextContent();
        }
        return pubDate;
    }

    private static int parseSeason(Element nodeInformation) throws NumberFormatException {
        int season = -1;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SEASON, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            season = Integer.valueOf(nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_SEASON, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent());
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SEASON).item(0) != null) {
            season = Integer.valueOf(nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_SEASON).item(0).getTextContent());
        }
        return season;
    }

    private static int parseEpisodeNumber(Element nodeInformation) throws NumberFormatException {
        int episodeNumber = -1;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_EPISODE, XmlFeedConstants.XML_ITUNES_NS).item(0) != null) {
            episodeNumber = Integer.valueOf(nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITEM_EPISODE, XmlFeedConstants.XML_ITUNES_NS).item(0).getTextContent());
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_EPISODE).item(0) != null) {
            episodeNumber = Integer.valueOf(nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_EPISODE).item(0).getTextContent());
        }
        return episodeNumber;
    }
}
