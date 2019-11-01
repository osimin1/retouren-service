package de.otto.retouren;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@DynamoDBTable(tableName="Retoure")
@Builder
@Data
public class Retoure {
    @DynamoDBHashKey(attributeName="CustomerId")
    private String CustomerID;
    @DynamoDBRangeKey(attributeName="SongTitle")
    private String OrderId;
    @DynamoDBAttribute(attributeName = "ReturnedShipment")
    private boolean returnedShipment;
    @DynamoDBAttribute(attributeName = "CreationDate")
    private Date CreationDate;
}
