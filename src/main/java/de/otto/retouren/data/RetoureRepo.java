package de.otto.retouren.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenResponse;

import java.util.HashMap;

public class RetoureRepo {
    private DynamoDBMapper dynamoDBMapper;
    private Regions REGION = Regions.EU_CENTRAL_1;
    private DynamoDBSaveExpression uniqueOrderIdSaveExpression;

    public RetoureRepo() {
        this.dynamoDBMapper = getDynamoDbClient();
        this.uniqueOrderIdSaveExpression = getUniqueOrderIdSaveExpression();
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

    public RetourenResponse.Status save(Retoure retoure) {
        try {
            dynamoDBMapper.save(retoure, uniqueOrderIdSaveExpression);
          return RetourenResponse.Status.SAVED;
        } catch (ConditionalCheckFailedException e) {
            return RetourenResponse.Status.IGNORED;
        }
    }
}
