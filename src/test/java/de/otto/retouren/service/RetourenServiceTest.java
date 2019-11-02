package de.otto.retouren.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.controller.RetourenResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @Mock
    DynamoDBMapper dynamoDBMapper;

    @Mock
    DynamoDBSaveExpression uniqueOrderIdSaveExpression;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void thatRetoureIsSaved(){
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("1").build();

        //when
        RetourenResponse result = retourenService.saveRetoure(retourenRequest);

        //then
        assertThat(result.getMessage(),containsString("Retoure was saved: Retoure(customerId=Hans1, orderId=1, returnedShipment=true"));
    }
}
