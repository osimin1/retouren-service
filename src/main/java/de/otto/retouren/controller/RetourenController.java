package de.otto.retouren.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import de.otto.retouren.Retoure;

import java.util.Date;

public class RetourenController implements RequestHandler<String, RetourenResponse> {

    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;

    public RetourenResponse handleRequest(String retoureStringRequest, Context context) {
        this.initDynamoDbClient();
      //  LambdaLogger logger = context.getLogger();
      //  logger.log(retoureStringRequest);
        Gson g = new Gson();
        RetourenRequest retourenRequest = g.fromJson(retoureStringRequest, RetourenRequest.class);
        Retoure retoure = Retoure.builder().CustomerId(retourenRequest.getCustomerId()).OrderId(retourenRequest.getOrderId()).returnedShipment(true).CreationDate(new Date()).build();
        dynamoDBMapper.save(retoure);
        String message = String.format("Retoure was saved: %s", retoure.toString());
        //logger.log(message);
        RetourenResponse retourenResponse = RetourenResponse.builder().message(message).build();
        //logger.log(retourenResponse.toString());
        return retourenResponse;
    }

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        dynamoDBMapper = new DynamoDBMapper(client);

    }
}