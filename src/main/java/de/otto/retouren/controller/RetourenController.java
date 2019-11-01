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

public class RetourenController implements RequestHandler<RetoureRequest, RetoureResponse> {

    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;

    public RetoureResponse handleRequest(RetoureRequest retoureRequest, Context context) {
        this.initDynamoDbClient();
        LambdaLogger logger = context.getLogger();
        logger.log(retoureRequest.toString());
        Retoure retoure = Retoure.builder().CustomerId(retoureRequest.getCustomerID()).OrderId(retoureRequest.getOrderId()).returnedShipment(true).CreationDate(new Date()).build();
        dynamoDBMapper.save(retoure);
        String message = String.format("Retoure was saved: %s", retoure.toString());
        logger.log(message);
        RetoureResponse retoureResponse = RetoureResponse.builder().message(message).build();
        logger.log(retoureResponse.toString());
        return retoureResponse;
    }

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        dynamoDBMapper = new DynamoDBMapper(client);

    }
}