package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class RetourenControllerTest {
    RetourenController retoureRequest = new RetourenController();

    @Mock
    Context context;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatRetoureIsAccepted(){
       // retoureRequest.handleRequest(RetoureRequest.builder().CustomerID("Hans1").OrderId("order1").build(),context);
    }
}