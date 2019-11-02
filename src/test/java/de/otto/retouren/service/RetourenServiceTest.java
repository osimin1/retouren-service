package de.otto.retouren.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.controller.RetourenRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RetourenServiceTest {

    LambdaLogger lambdaLogger= new LambdaLogger() {
        @Override
        public void log(String s) {

        }

        @Override
        public void log(byte[] bytes) {

        }
    };


    @InjectMocks
    @Spy
    RetourenService retourenService = new RetourenService(lambdaLogger);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void thatOrderIsSaved(){
        RetourenRequest retoureRequest = RetourenRequest.builder().customerId("Hans1").orderId("1").build();
        retourenService.saveRetoure(retoureRequest);
    }
}
