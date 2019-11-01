package de.otto.retouren;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;

import java.util.Date;

@Builder
@DynamoDBTable(tableName = "Retoure")
public class Retoure {
    private String CustomerId;
    private String OrderId;
    private boolean returnedShipment;
    private Date CreationDate;

    @DynamoDBHashKey(attributeName = "CustomerId")
    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    @DynamoDBRangeKey(attributeName = "OrderId")
    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    @DynamoDBAttribute(attributeName = "ReturnedShipment")
    public boolean isReturnedShipment() {
        return returnedShipment;
    }

    public void setReturnedShipment(boolean returnedShipment) {
        this.returnedShipment = returnedShipment;
    }

    @DynamoDBAttribute(attributeName = "CreationDate")
    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }
}
