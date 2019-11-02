package de.otto.retouren.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class RetoureRepoTest {

    @InjectMocks
    @Spy
    RetoureRepo retoureRepo = new RetoureRepo();

    @Mock
    DynamoDBMapper dynamoDBMapper;

    @Mock
    private DynamoDBSaveExpression uniqueOrderIdSaveExpression;

    @Test
    public void thatRetoureIsSaved(){
        // given
        Retoure retoure = Retoure.builder()
                .customerId("peter1")
                .orderId("order_peter_1")
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        // when
        RetourenResponse.Status result = retoureRepo.save(retoure);

        // then
        assertThat(result, is(RetourenResponse.Status.SAVED));
    }

    @Test
    public void thatRetoureWithKnownOrderIdIsNotSaved(){
        // given
        Retoure retoure = Retoure.builder()
                .customerId("peter1")
                .orderId("order_peter_1")
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        doThrow(ConditionalCheckFailedException.class)
                .when(dynamoDBMapper)
                .save(retoure, uniqueOrderIdSaveExpression);

        RetourenResponse.Status result = retoureRepo.save(retoure);
        assertThat(result, is(RetourenResponse.Status.IGNORED));
    }

}
