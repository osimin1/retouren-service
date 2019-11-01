package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Before;import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void thatRetoureIsAccepted(){
 //       retourenController.handleRequest(RetourenRequest.builder().CustomerId("Hans1").OrderId("order1").build(),context);
 /*       retoureRequest.handleRequest("{\n" +
                "  \"CustomerId\":\"Hans1\",\n" +
                "  \"OrderId\":\"oder1\"\n" +
                "}",context);*/
    }
}