package com.alvaromoran;

import com.alvaromoran.constants.XmlFeedConstants;
import com.alvaromoran.data.ChannelInformation;
import com.alvaromoran.data.PodCastChannelDTO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to create the ChannelInformation object based on a factory pattern
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
class ChannelsFactory {

    /** Logger of the class */
    private static final Logger LOGGER = Logger.getLogger(ChannelsFactory.class.getName());

    /** Flag to take into consideration paid channels or not - By default only free channels are allowed */
    private static boolean createPaidChannels = false;

    /**
     * Creates ChannelInformation object based on the PodCastChannelDTO that has been directly parsed from the JSON
     * @param roughChannelInfo rough channel information parsed from the JSON
     * @return well-formed ChannelInformation
     */
    static ChannelInformation createChannelFromDTO(PodCastChannelDTO roughChannelInfo) {
        if (isValidRoughInformation(roughChannelInfo)) {
            ChannelInformation newChannel = new ChannelInformation(roughChannelInfo.collectionName);
            // Fill other information
            newChannel.setImageUrlLow(roughChannelInfo.artworkUrl100);
            newChannel.setImageUrlHigh(roughChannelInfo.artworkUrl600);
            newChannel.setFeedUrl(roughChannelInfo.feedUrl);
            newChannel.setCategories(roughChannelInfo.genres);
            newChannel.setAuthor(roughChannelInfo.artistName);
            return newChannel;
        } else {
            return null;
        }
    }

    /**
     * Updates the ChannelInformation object passed as argument, based on the Document parsed directly from the channels
     * provider.
     * @param deserializedMessage XML parsed document from the channels provider
     * @param channelInformation base channel information to be updated
     */
    static void enrichChannelFromAuthorsInformation(Document deserializedMessage, ChannelInformation channelInformation) {
        Element channelElement = getChannelElement(deserializedMessage);
        if (channelElement != null) {
            channelInformation.setDescription(getDescription(channelElement));
            channelInformation.setLink(getLink(channelElement));
            channelInformation.setCopyright(getCopyright(channelElement));
            channelInformation.setAuthor(getAuthor(channelElement));
            channelInformation.setSummary(getSummary(channelElement));
        }
    }

    /**
     * CHanges the flag to take into consideration paid channels or not - By default only free channels are allowed
     * If <code>true</code> paid and free channels will be taken into consideration
     * If <code>false</code> only free channels will be taken into consideration
     * @param newValue new value for the flag
     */
    static void considerPaidChannels(boolean newValue) {
        ChannelsFactory.createPaidChannels = newValue;
    }

    /**
     * Check if the information provided by the DTO is valid and enough to get more channel information or not
     * @param roughChannelInfo base channel information gathered from the JSON
     * @return <code>true</code> if the JSON information provided is valid
     *         <code>false</code> if the JSON information provided is invalid
     */
    private static boolean isValidRoughInformation(PodCastChannelDTO roughChannelInfo) {
        // At least the channel name and the feed URL is needed
        if (roughChannelInfo.collectionName != null && roughChannelInfo.artistName != null && roughChannelInfo.feedUrl != null) {
            // Check if paid channels are taken into consideration or not
            if (!ChannelsFactory.createPaidChannels && roughChannelInfo.collectionPrice == 0.0) {
                return true;
            } else if (!ChannelsFactory.createPaidChannels && roughChannelInfo.collectionPrice != 0.0) {
                LOGGER.log(Level.FINE, "Ignoring channel since its a paid channel and the configuration is only" +
                        "set up for free channels");
                return false;
            } else {
             return true;
            }
        } else {
            LOGGER.log(Level.FINE, "Ignoring channel since it does not have the basic information filled");
            return false;
        }
    }

    /**
     * Gets the channel element-node from the full deserialized XML gathered from the channels provider
     * @param deserializedMessage Document with the XML gathered from the channels provider
     * @return Element node with the channel detailed information
     */
    private static Element getChannelElement(Document deserializedMessage) {
        NodeList itemList = deserializedMessage.getElementsByTagName(XmlFeedConstants.XML_CHANNEL);
        // One channel node is mandatory
        if (itemList != null) {
            Node item = itemList.item(0);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) item;
            } else {
                LOGGER.log(Level.WARNING, "Channel information not found in the returned message");
                return null;
            }
        } else {
            LOGGER.log(Level.WARNING, "Channel information not found in the returned message");
            return null;
        }
    }

    /**
     * Gets the copyright information from the channel element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed copyright
     */
    private static String getCopyright(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_COPYRIGHT).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_COPYRIGHT).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_COPYRIGHT).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_COPYRIGHT).item(0).getTextContent();
        }
        return description;
    }

    /**
     * Gets the description information from the channel element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed description
     */
    private static String getDescription(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_ITEM_DESCRIPTION).item(0).getTextContent();
        }
        return description;
    }

    /**
     * Gets the link information from the channel element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed link
     */
    private static String getLink(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_LINK).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_LINK).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_LINK).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_LINK).item(0).getTextContent();
        }
        return description;
    }

    /**
     * Gets the author information from the channel element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed author
     */
    private static String getAuthor(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_AUTHOR).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_AUTHOR).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_AUTHOR).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_AUTHOR).item(0).getTextContent();
        }
        return description;
    }

    /**
     * Gets the summary information from the channel element node
     * @param nodeInformation channel node of the XML gathered from the channels provider
     * @return parsed summary
     */
    private static String getSummary(Element nodeInformation) {
        String description = null;
        if (nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_SUMMARY).item(0) != null) {
            description = nodeInformation.getElementsByTagNameNS(XmlFeedConstants.XML_ITUNES_NS, XmlFeedConstants.XML_CHANNEL_SUMMARY).item(0).getTextContent();
        } else if (nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_SUMMARY).item(0) != null) {
            description = nodeInformation.getElementsByTagName(XmlFeedConstants.XML_CHANNEL_SUMMARY).item(0).getTextContent();
        }
        return description;
    }
}
