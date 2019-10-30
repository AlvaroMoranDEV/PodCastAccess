# PodCastAccess
PodCastAccess is a library built in Java11 (in particular, 11.0.3)  that provides access to PodCast information through ITunes Store. This library has been built by using IntelliJ IDE. Feel free to use it and modify it according to your needs.

[TOCM]

This library is under the MIT license.

## Using the library

### Install
To install this library just import the PodCastAccess.jar file into your project as a typical library and you will be ready to go.

The compiled JAR is located in this repository in the following link:

[Compiled JAR V0.1](https://github.com/AlvaroMoranDEV/PodCastAccess/tree/master/out/artifacts/PodCastAccess_jar)

### Data Access Object
The library provides a public interface with the needed methods to access both channels and episodes information. The interface is called
```
PodCastsDAO
```

### Accessing PodCast channels
The information about PodCast channels is gathered from the ITunes Store. More information about its search API is addressed [here](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/). For now, this library provides the following search parameters:

- **term:** Generic term parameter
- **podcastAuthor:** Author of the podcast
- **artistTerm:** Artist of the podcast

To search by these parameters the following methods are provided in the interface:
```
List<ChannelInformation> updateTermSearchParameter(String term);
List<ChannelInformation>  updateArtistSearchParameter(String artist);
List<ChannelInformation>  updateAuthorSearchParameter(String author);
```
When updating one of these search parameters, the library automatically performs a query over the ITunes store (if it is configured to do so, see section *Additional configurations* for more information) and returns the list of results provided in form of a ChannelInformation object.

------------

### Accessing PodCast episodes and additional channel information
There are different ways of getting the list of episodes for a single channel:
- By providing the ChannelInformation oject that is wanted to be filled with the list of episodes.
- By providing the URL of the channels author where this information is located.

The advantages of using the first method is that, if you have already a selected channel that you want to populate with the list of episodes and more information provided by the contents author, you only have to provide this object to the library and it will fill it with all additional information. On the other hand, by providing only the URL of the contents author, only the list of related episodes is provided with no additional information whatsoever.

#### Using ChannelInformation object
The ChannelInformation class already contains a list of episodes that will be filled when using the following methods:
```
void getEnrichedChannelInformation(ChannelInformation selectedChannel, boolean getEpisodes);
void getEnrichedChannelInformation(ChannelInformation selectedChannel);
List<SingleEpisode> getListOfEpisodesFromChannel(ChannelInformation selectedChannel);
```
Each one of them are explained below:

1.  In the first call, by providing a ChannelInformation object (this object is created as a result of a search through channels, see section *Accessing PodCast channels* ), the object is automatically filled with the list of episodes and additional channel information, such as copyright, channel description, summary and so on. The flag **getEpisodes** enables the functionality to get the list of episodes (*true*) and the channels detailed information or only these detailed information (*false*).
```
void getEnrichedChannelInformation(ChannelInformation selectedChannel, boolean getEpisodes);
```

2. Second call is the same as the first one but with the flag **getEpisodes** automatically set to true, to populate the channel both with the episodes and the additional information.
```
void getEnrichedChannelInformation(ChannelInformation selectedChannel);
```

3. The third call returns the list of episodes related to a provided ChannelInformation object. In this case, the ChannelInformation object will not be populated with additional information or episodes (they are returned independently).
```
List<SingleEpisode> getListOfEpisodesFromChannel(ChannelInformation selectedChannel);
```


#### URL of the channel author or provider
If you only want to get the list of episodes of a podcast channel without the ChannelInformation object by using a particular URL or you only want to use the *feedUrl* property of the ChannelInformation class (more information about this in the following section) the following method of the interface will suit your needs:

```
List<SingleEpisode> getListOfEpisodesFromUrl(String url);
```

------------


### ChannelInformation class
In this section we will discuss the information provided by the ChannelInformation class. This class will be filled with information, both from the ITunes store API and the contents author:

##### Basic information from ITunes Store
- Name of the collection
- Categories related to the channel (Politics, History... the full list is provided by the [ITunes API](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/))
- Low image resolution
- High image resolution
- FeedURL, the URL to access the detailed information

##### Detailed information from channel provider
- Channel description
- Channel authors link
- Copyright
- Author
- Summary about the channel
- List of episodes

### SingleEpisode class
Each of the episodes provided by the library will contain the following information, if its provided by the channels author:
- Title of the episode
- Subtitle of the episode
- Summary
- Detailed description of the episode (usually its the same as the summary, but just in case...)
- Keywords related to the episode sepparated by ','
- Duration of the episode
- Release date
- Number of the episode and season
- Audio information, with the URL where the MP3 is located.

### Additional configurations
In this section we will discuss the different additional configurations accessed and the way to configured them.

1. **Channel results limit:** In case you only want the first few results provided by the ITunes API, you can configure the desired value between 1 and 200. By default its value is 50.
```
void setChannelResultsLimit(int number);
```
2. **Paid channels search:** Allows the search over paid channels too, or only in free channels. By default its configured to only search channels which their content is free (value *false*).
```
void setSearchPaidChannels(boolean searchPaidChannels);
```
3. **Autoquery channels:** This functionality, enabled by default, will try to search over the ITunes Search API each time a search term is updated, and will return the set of channels received. 
```
void setAutoQueryChannelsOption(boolean autoQuery);
```
	If it is disabled (value *false*) the search will only be performed when the following method is called:
```
List<ChannelInformation> executeQueryOnDemand();
```

## About the library

##### Current version
Version 0.1, currently under beta testing. Feel free to communicate any issue you found or additional features to be included!
##### Dependencies
Library **Gson** is used to parse the information from the ITunes Search API.
More information about this library is located in its own GitHub repository [here](https://github.com/google/gson).
##### Motivation
This library has been created by @AlvaroMoranDEV with the purpose of creating a full PodCast Android application free and without ads (Im tired of these).
