package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RetourenControllerTest {

    @InjectMocks
    @Spy
    RetourenController retourenController = new RetourenController();

    @Mock
    Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatRetoureIsAccepted() {
        //       retourenController.handleRequest(RetourenRequest.builder().CustomerId("Hans1").OrderId("order1").build(),context);
 /*       retoureRequest.handleRequest("{\n" +
                "  \"CustomerId\":\"Hans1\",\n" +
                "  \"OrderId\":\"oder1\"\n" +
                "}",context);*/
    }


    @Test
    public void testToString() {
        RetourenRequest req = new RetourenRequest();
        req.setCustomerId("c1");
        req.setOrderId("o1");
        assertThat(req.toString(), is("{\"customerId\":\"c1\",\"orderId\":\"o1\"}"));
    }

    @Test
    public void testFromString() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RetourenRequest req = objectMapper.readerFor(RetourenRequest.class).readValue("{\n \"customerId\": \"c1\",\n \"orderId\": \"o1\"\n}");
        assertThat(req.getCustomerId(), is("c1"));
    }
}
