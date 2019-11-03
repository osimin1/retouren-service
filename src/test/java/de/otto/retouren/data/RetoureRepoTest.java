package de.otto.retouren.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetoureRepoTest {

    private static final String CUSTOMER_ID = "peter1";
    private static final String ORDER_ID = "order_peter_1";
    @Mock
    LambdaLogger logger;

    @InjectMocks
    @Spy
    RetoureRepo retoureRepo = new RetoureRepo(logger);

    @Mock
    DynamoDBMapper dynamoDBMapper;

    @Mock
    private DynamoDBSaveExpression uniqueOrderIdSaveExpression;

    @Test
    public void thatRetoureIsSaved() {
        // given
        Retoure retoure = Retoure.builder()
                .customerId(CUSTOMER_ID)
                .orderId(ORDER_ID)
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        // when
        boolean result = retoureRepo.save(retoure);

        // then
        assertThat(result, is(true));
    }

    @Test
    public void thatRetoureWithKnownOrderIdIsNotSaved() {
        // given
        Retoure retoure = Retoure.builder()
                .customerId(CUSTOMER_ID)
                .orderId(ORDER_ID)
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        doThrow(ConditionalCheckFailedException.class)
                .when(dynamoDBMapper)
                .save(retoure, uniqueOrderIdSaveExpression);

        // when
        boolean result = retoureRepo.save(retoure);

        // then
        assertThat(result, is(false));
    }

    @Test
    public void thatRetoureIsLoaded() {
        // given
        Retoure retoure = Retoure.builder()
                .customerId(CUSTOMER_ID)
                .orderId(ORDER_ID)
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        doReturn(retoure).
                when(dynamoDBMapper).load(Retoure.class, CUSTOMER_ID, ORDER_ID);
        // when
        Retoure result = retoureRepo.loadRetoure(CUSTOMER_ID, ORDER_ID);

        // then
        assertThat(result, is(equalTo(retoure)));
    }

    @Test
    public void thatRetoureIsNOTFound() {
        // given
        Retoure retoure = Retoure.builder()
                .customerId(CUSTOMER_ID)
                .orderId(ORDER_ID)
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        doReturn(null).
                when(dynamoDBMapper).load(Retoure.class, CUSTOMER_ID, ORDER_ID);

        // when
        Retoure result = retoureRepo.loadRetoure(CUSTOMER_ID, ORDER_ID);

        // then
        assertThat(result, is(nullValue()));
    }

}
