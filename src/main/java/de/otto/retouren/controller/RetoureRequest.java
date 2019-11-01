package de.otto.retouren.controller;


public class RetoureRequest {
    @Override
    public String toString() {
        return "RetoureRequest{" +
                "CustomerID='" + CustomerID + '\'' +
                ", OrderId='" + OrderId + '\'' +
                '}';
    }

    private String CustomerID;
    private String OrderId;

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
