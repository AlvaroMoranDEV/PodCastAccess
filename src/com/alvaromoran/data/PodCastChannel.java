package com.alvaromoran.data;

import java.util.Collection;
import java.util.Date;

/**
 * Set of all the data provided by the ITunes store in its JSON answers
 */
public class PodCastChannel {

    public String wrapperType;
    public String kind;
    public int artistId;
    public int collectionId;
    public int trackId;
    public String artistName;
    public String collectionName;
    public String trackName;
    public String collectionCensoredName;
    public String trackCensoredName;
    public String artistViewUrl;
    public String collectionViewUrl;
    public String feedUrl;
    public String trackViewUrl;
    public String artworkUrl30;
    public String artworkUrl60;
    public String artworkUrl100;
    public double collectionPrice;
    public double trackPrice;
    public double trackRentalPrice;
    public double collectionHdPrice;
    public double trackHdPrice;
    public double trackHdRentalPrice;
    public Date releaseDate;
    public String collectionExplicitness;
    public String trackExplicitness;
    public int trackCount;
    public String country;
    public String currency;
    public String primaryGenreName;
    public String artworkUrl600;
    public Collection<String> genreIds;
    public Collection<String> genres;
    public String previewUrl;
    public int discCount;
    public int discNumber;
    public int trackNumber;
    public int trackTimeMillis;
    public boolean isStreamable;

}

