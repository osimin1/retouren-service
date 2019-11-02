package de.otto.retouren.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.controller.RetourenResponse;

import java.util.Date;
import java.util.HashMap;

public class RetourenService {

    private LambdaLogger logger;
    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;
    private DynamoDBSaveExpression uniqueOrderIdSaveExpression;

    public RetourenService(LambdaLogger logger) {
        this.logger = logger;
        dynamoDBMapper = getDynamoDbClient();
        uniqueOrderIdSaveExpression = getUniqueOrderIdSaveExpression();
    }

    public RetourenResponse saveRetoure(RetourenRequest retourenRequest) {
        Retoure retoure = Retoure.builder()
                .customerId(retourenRequest.getCustomerId())
                .orderId(retourenRequest.getOrderId())
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        String message;
        try {
            dynamoDBMapper.save(retoure, uniqueOrderIdSaveExpression);
            message = String.format("Retoure was saved: %s", retoure.toString());
        } catch (ConditionalCheckFailedException e) {
            message = String.format("Retoure is already known and send: %s", retoure.toString());
        }
        RetourenResponse retourenResponse = RetourenResponse.builder().message(message).build();
        logger.log(message);
        return retourenResponse;
    }

    DynamoDBSaveExpression getUniqueOrderIdSaveExpression() {
        DynamoDBSaveExpression saveExpr = new DynamoDBSaveExpression();
        HashMap<String, ExpectedAttributeValue> expectedCondition = new HashMap<>();
        expectedCondition.put("OrderId", new ExpectedAttributeValue(false));
        saveExpr.setExpected(expectedCondition);
        return saveExpr;
    }

    DynamoDBMapper getDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        return new DynamoDBMapper(client);
    }
}
