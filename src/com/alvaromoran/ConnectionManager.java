package com.alvaromoran;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class used to manage the connections over the different web information providers, perform
 * GET requests over them and parse the returned body information into a string.
 *
 * @author AlvaroMoranDEV
 * @version 0.1
 */
class ConnectionManager {

    /** Class logger */
    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    /** Object to manage the web connection */
    private HttpURLConnection connection;

    /** Flag to check if the class is currently performing a valid request operation*/
    private boolean performingConnection = false;

    /**
     * Method used to perform a GET request over a given URL and parse the incoming information into a String
     * @param url url to be accessed
     * @return string returned as an answer
     */
    public String performGetRequest(String url) {
        String result = null;
        try {
            // Connection open
            openConnection(url);
            if (this.performingConnection) {
                this.connection.setRequestMethod("GET");
                LOGGER.log(Level.FINE, "Performing HTTP GET REST request over {}", url);
                // Buffering answer
                InputStream in = new BufferedInputStream(this.connection.getInputStream());
                result = new BufferedReader(new InputStreamReader(in))
                        .lines().collect(Collectors.joining("\n"));
            } else {
                LOGGER.log(Level.WARNING, "Attempting to perform a GET request without specified URL");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Connection error performing connection over: " + url);
        } finally {
            // Close the connection once the operation has ended
            closeConnection();
        }
        return result;
    }

    /**
     * Method to open a connection over a given URL
     * @param url url to be accessed
     * @throws IOException exception throw while trying to open the connection
     */
    private void openConnection(String url) throws IOException {
        URL urlObject = new URL(url);
        this.connection = (HttpURLConnection) urlObject.openConnection();
        this.performingConnection = true;
        LOGGER.log(Level.FINE, "Opening connection over URL: " + url);
    }

    /**
     * Method to close the connection
     */
    private void closeConnection() {
        this.connection.disconnect();
        this.performingConnection = false;
        LOGGER.log(Level.FINE, "Closing connection");
    }
}
