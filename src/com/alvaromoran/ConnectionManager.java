package com.alvaromoran;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    private HttpURLConnection connection;

    private boolean performingConnection = false;

    String performGetRequest(String url) {
        String result = null;
        try {
            openConnection(url);
            if (this.performingConnection) {
                this.connection.setRequestMethod("GET");
                LOGGER.log(Level.FINE, "Performing HTTP GET REST request");
                InputStream in = new BufferedInputStream(this.connection.getInputStream());
                result = new BufferedReader(new InputStreamReader(in))
                        .lines().collect(Collectors.joining("\n"));
            } else {
                LOGGER.log(Level.WARNING, "Attempting to perform a GET request without specified URL");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Connection error performing connection over: " + url);
        } finally {
            closeConnection();
        }
        return result;
    }

    private void openConnection(String url) throws IOException {
        URL urlObject = new URL(url);
        this.connection = (HttpURLConnection) urlObject.openConnection();
        this.performingConnection = true;
        LOGGER.log(Level.FINE, "Opening connection over URL: " + url);
    }

    private void closeConnection() {
        this.connection.disconnect();
        this.performingConnection = false;
        LOGGER.log(Level.FINE, "Closing connection");
    }
}
