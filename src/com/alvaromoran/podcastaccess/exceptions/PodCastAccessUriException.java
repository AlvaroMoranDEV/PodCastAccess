package com.alvaromoran.podcastaccess.exceptions;

/**
 * Exception thrown when generating URI to access the ITunes Store
 */
public class PodCastAccessUriException extends RuntimeException {

    /**
     * URI generation constructor error
     * @param message error message
     */
    public PodCastAccessUriException(String message) {
        super(message);
    }
}
