package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RetourenController implements RequestHandler<Map<String, Object>, String> {

    private static String URL = "http://dummy.restapiexample.com/api/v1/retouren";

    @Override
    public String handleRequest(Map<String, Object> inputStream, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log(inputStream.toString());

        String responseObject;
        try {
            responseObject = fetchResponseFromUrl(URL, logger);
        } catch (IOException e) {
            logger.log(e.getMessage());
            responseObject = e.getMessage();
        }
        logger.log("sending response");
        logger.log(responseObject);
        return responseObject;
    }

    private String fetchResponseFromUrl(String url, LambdaLogger logger) throws IOException {
        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        logger.log("fetching response from :: " + url);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            )) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            logger.log("response from :: " + url);
            logger.log(response.toString());

            return response.toString();
        } else {
            logger.log("unable to get response :: ");
            return con.getResponseMessage();
        }
    }

}