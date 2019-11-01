package de.otto.retouren.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.otto.retouren.Retoure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class RetourenController implements RequestHandler<RetoureRequest, RetoureResponse> {

    private static String URL = "http://dummy.restapiexample.com/api/v1/retouren";
    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;

    public RetoureResponse handleRequest(RetoureRequest retoureRequest, Context context) {
        this.initDynamoDbClient();
        LambdaLogger logger = context.getLogger();
        logger.log(retoureRequest.toString());
        Retoure retoure = Retoure.builder().CustomerID(retoureRequest.getCustomerID()).OrderId(retoureRequest.getOrderId()).returnedShipment(true).CreationDate(new Date()).build();
        dynamoDBMapper.save(retoure);
        String message = String.format("Retoure was saved: %s", retoure.toString());
        logger.log(message);
        RetoureResponse retoureResponse = RetoureResponse.builder().message(message).build();
        logger.log(retoureResponse.toString());
        return retoureResponse;
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

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        dynamoDBMapper = new DynamoDBMapper(client);

    }
}