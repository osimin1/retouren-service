package de.otto.retouren.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.otto.retouren.Retoure;

import java.util.Date;

public class RetourenController implements RequestHandler<RetourenRequest, RetourenResponse> {

    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;

    public RetourenResponse handleRequest(RetourenRequest retourenRequest, Context context) {
        this.initDynamoDbClient();
        LambdaLogger logger = context.getLogger();
        logger.log(retourenRequest.toString());
 /*       Gson g = new Gson();
        RetourenRequest retourenRequest = g.fromJson(retoureStringRequest, RetourenRequest.class);*/
        Retoure retoure = Retoure.builder().CustomerId(retourenRequest.getCustomerId()).OrderId(retourenRequest.getOrderId()).returnedShipment(true).CreationDate(new Date()).build();
        dynamoDBMapper.save(retoure);
        String message = String.format("Retoure was saved: %s", retoure.toString());
        logger.log(message);
        RetourenResponse retourenResponse = RetourenResponse.builder().message(message).build();
        logger.log(retourenResponse.toString());
        return retourenResponse;
    }

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        dynamoDBMapper = new DynamoDBMapper(client);

    }
}