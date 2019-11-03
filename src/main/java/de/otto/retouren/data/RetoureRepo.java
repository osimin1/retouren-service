package de.otto.retouren.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;

import java.util.HashMap;

public class RetoureRepo {
    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;
    private DynamoDBSaveExpression uniqueOrderIdSaveExpression;
    private LambdaLogger logger;

    public RetoureRepo(LambdaLogger logger) {
        this.logger = logger;
        this.dynamoDBMapper = getDynamoDbClient();
        this.uniqueOrderIdSaveExpression = getUniqueOrderIdSaveExpression();
    }

    private DynamoDBSaveExpression getUniqueOrderIdSaveExpression() {
        DynamoDBSaveExpression saveExpr = new DynamoDBSaveExpression();
        HashMap<String, ExpectedAttributeValue> expectedCondition = new HashMap<>();
        expectedCondition.put("OrderId", new ExpectedAttributeValue(false));
        saveExpr.setExpected(expectedCondition);
        return saveExpr;
    }

    private DynamoDBMapper getDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        return new DynamoDBMapper(client);
    }

    public boolean save(Retoure retoure) {
        try {
            dynamoDBMapper.save(retoure, uniqueOrderIdSaveExpression);
            logger.log(String.format("Retoure was saved: %s", retoure.toString()));
            return true;
        } catch (ConditionalCheckFailedException e) {
            logger.log(String.format("Retoure is already known: %s", retoure.toString()));
            return false;
        }
    }

    public Retoure loadRetoure(String customerId, String orderId) {
        return dynamoDBMapper.load(Retoure.class, customerId, orderId);
    }
}
