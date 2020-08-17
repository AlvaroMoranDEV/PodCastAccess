package com.alvaromoran.exceptions;

/**
 * Exception thrown when a connection issue occurs
 */
public class PodCastAccessConnectionException extends RuntimeException {

    /**
     * Connection exception constructor
     * @param message message error
     */
    public PodCastAccessConnectionException(String message) {
        super(message);
    }
}
