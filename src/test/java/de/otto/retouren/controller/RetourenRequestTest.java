package de.otto.retouren.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class RetourenRequestTest {

    @Test
    public void testToString() {
        RetourenRequest req = RetourenRequest.builder().customerId("c1").orderId("o1").build();
        assertThat(req.toString(), is("{\"customerId\":\"c1\",\"orderId\":\"o1\"}"));
    }

    @Test
    public void testFromString() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RetourenRequest req = objectMapper.readerFor(RetourenRequest.class).readValue("{\n \"customerId\": \"c1\",\n \"orderId\": \"o1\"\n}");
        assertThat(req.getCustomerId(), is("c1"));
    }
}
