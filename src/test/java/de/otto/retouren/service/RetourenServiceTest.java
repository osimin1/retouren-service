package de.otto.retouren.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.controller.RetourenResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetourenServiceTest {

    LambdaLogger lambdaLogger = new LambdaLogger() {
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatRetoureIsSaved() {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("1").build();
        ArgumentCaptor<Retoure> retoureArgumentCaptor = ArgumentCaptor.forClass(Retoure.class);
        ArgumentCaptor<DynamoDBSaveExpression> dynamoDBSaveExpressionArgumentCaptor = ArgumentCaptor.forClass(DynamoDBSaveExpression.class);

        //when
        RetourenResponse result = retourenService.saveRetoure(retourenRequest);

        //then
        verify(dynamoDBMapper).save(retoureArgumentCaptor.capture(), dynamoDBSaveExpressionArgumentCaptor.capture());
        assertThat(retoureArgumentCaptor.getValue().getCustomerId(), is(retourenRequest.getCustomerId()));
        assertThat(retoureArgumentCaptor.getValue().getOrderId(), is(retourenRequest.getOrderId()));
        assertThat(result.getMessage(), containsString("Retoure was saved: Retoure(customerId=Hans1, orderId=1, returnedShipment=true"));
    }

    @Test
    public void thatRetoureWithKnownOrderIdIsNotSaved() {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("1").build();
        ArgumentCaptor<Retoure> retoureArgumentCaptor = ArgumentCaptor.forClass(Retoure.class);
        ArgumentCaptor<DynamoDBSaveExpression> dynamoDBSaveExpressionArgumentCaptor = ArgumentCaptor.forClass(DynamoDBSaveExpression.class);
        doThrow(ConditionalCheckFailedException.class)
                .when(dynamoDBMapper)
                .save(retoureArgumentCaptor.capture(), dynamoDBSaveExpressionArgumentCaptor.capture());

        //when
        RetourenResponse result = retourenService.saveRetoure(retourenRequest);

        //then
        assertThat(retoureArgumentCaptor.getValue().getCustomerId(), is(retourenRequest.getCustomerId()));
        assertThat(retoureArgumentCaptor.getValue().getOrderId(), is(retourenRequest.getOrderId()));
        assertThat(result.getMessage(), containsString("Retoure is already known and send: Retoure(customerId=Hans1, orderId=1, returnedShipment=true"));
    }

}
