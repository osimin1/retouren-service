package de.otto.retouren.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;

@Builder
public class RetourenRequest {
    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    private String CustomerId;
    private String OrderId;

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
