package com.alvaromoran.podcastaccess;

import com.alvaromoran.podcastaccess.exceptions.PodCastAccessConnectionException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public String performGetRequest(String url) throws PodCastAccessConnectionException {
        // String buffer
        StringBuilder bufferedAnswer = new StringBuilder();
        try {
            // Connection open
            openConnection(url);
            // Check connection correct
            if (this.performingConnection) {
                // Line buff
                String readLine;
                LOGGER.log(Level.FINE, "Performing HTTP GET REST request over {}", url);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
                while ((readLine = bufferedReader.readLine()) != null) {
                    bufferedAnswer.append(readLine);
                }
            } else {
                LOGGER.log(Level.WARNING, "Attempting to perform a GET request without specified URL");
            }
        } catch (IOException e) {
            // Error management
            String msg = "Connection error performing connection over: " + url;
            LOGGER.log(Level.SEVERE, msg);
            throw new PodCastAccessConnectionException(msg);
        } finally {
            // Close the connection once the operation has ended
            closeConnection();
        }
        return bufferedAnswer.toString();
    }

    /**
     * Method to open a connection over a given URL
     * @param url url to be accessed
     * @throws IOException exception throw while trying to open the connection
     */
    private void openConnection(String url) throws IOException {
        URL urlObject = new URL(url);
        this.performingConnection = true;
        this.connection = (HttpURLConnection) urlObject.openConnection();
        // Manage redirections
        this.connection.setInstanceFollowRedirects(true);
        LOGGER.log(Level.FINE, "Opening connection over URL: " + url);
        manageRedirections();
    }

    /**
     * Manages redirection between different URL if needed according to the response code
     * @throws IOException exception throw while trying to open the connection
     */
    private void manageRedirections() throws IOException {
        if (isRedirected(this.connection.getResponseCode())) {
            String newUrl = this.connection.getHeaderField("Location");
            LOGGER.log(Level.FINE, "Performing redirection to new URL: " + newUrl);
            closeConnection();
            openConnection(newUrl);
        }
    }

    /**
     * Checks if the connection needs to be redirected according to the answer
     * @param response answer received when establishing connection
     * @return <code>true</code> if the connection needs to be redirected
     */
    private boolean isRedirected(int response) {
        if (response != HttpURLConnection.HTTP_OK) {
            if (response == HttpURLConnection.HTTP_MOVED_TEMP
                    || response == HttpURLConnection.HTTP_MOVED_PERM
                    || response == HttpURLConnection.HTTP_SEE_OTHER) {
                // Redirected
                LOGGER.log(Level.FINE, "Received code to perform redirection: " + response);
                return true;
            } else {
                // No redirected - error maybe
                return false;
            }
        } else {
            // Connection OK
            return false;
        }
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
