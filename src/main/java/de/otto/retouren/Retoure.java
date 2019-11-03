package de.otto.retouren;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.Date;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Retoure")
@Setter
public class Retoure {
    @NonNull
    private String customerId;
    @NonNull
    private String orderId;
    private boolean returnedShipment;
    private Date creationDate;

    @DynamoDBHashKey(attributeName = "CustomerId")
    public String getCustomerId() {
        return customerId;
    }

    @DynamoDBRangeKey(attributeName = "OrderId")
    public String getOrderId() {
        return orderId;
    }

    @DynamoDBAttribute(attributeName = "ReturnedShipment")
    public boolean isReturnedShipment() {
        return returnedShipment;
    }

    @DynamoDBAttribute(attributeName = "CreationDate")
    public Date getCreationDate() {
        return creationDate;
    }

}
