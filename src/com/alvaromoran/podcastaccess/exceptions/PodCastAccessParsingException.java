package com.alvaromoran.podcastaccess.exceptions;

/**
 * Exception thrown when the parsing of information fails
 */
public class PodCastAccessParsingException extends RuntimeException {

    /**
     * Parsing error class constructor
     * @param message error message
     */
    public PodCastAccessParsingException(String message) {
        super(message);
    }
}
