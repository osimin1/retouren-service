package de.otto.retouren.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.data.RetoureRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RetourenServiceTest {

    private static final String CUSTOMER_ID = "Hans1";
    private static final String ORDER_ID = "1";
    @Mock
    LambdaLogger lambdaLogger;

    @InjectMocks
    @Spy
    RetourenService retourenService = new RetourenService(lambdaLogger);

    @Mock
    RetoureRepo retoureRepo;

    @Test
    public void thatUnknownRetoureIsSaved() {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId(CUSTOMER_ID).orderId(ORDER_ID).build();
        when(retoureRepo.save(any())).thenReturn(true);

        //when
        boolean result = retourenService.isSaveRetoureSuccessful(retourenRequest);

        //then
        assertThat(result, is(true));
    }

    @Test
    public void thatKnownRetoureIsNOTSaved()
    {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId(CUSTOMER_ID).orderId(ORDER_ID).build();
        when(retoureRepo.save(any())).thenReturn(false);

        //when
        boolean result = retourenService.isSaveRetoureSuccessful(retourenRequest);

        //then
        assertThat(result, is(false));
    }

    @Test
    public void thatDuplicateIsFound()
    {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId(CUSTOMER_ID).orderId(ORDER_ID).build();
        Retoure retoure= Retoure.builder().customerId(CUSTOMER_ID).orderId(ORDER_ID).build();
        doReturn(retoure).
                when(retoureRepo).loadRetoure(CUSTOMER_ID, ORDER_ID);

        //when
        boolean result = retourenService.isDuplicateRetoure(retourenRequest);

        //then
        assertThat(result, is(true));
    }

    @Test
    public void thatDuplicateIsNOTFound()
    {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId(CUSTOMER_ID).orderId(ORDER_ID).build();
        Retoure retoure= Retoure.builder().customerId(CUSTOMER_ID).orderId(ORDER_ID).build();
        doReturn(null).
                when(retoureRepo).loadRetoure(CUSTOMER_ID, ORDER_ID);

        //when
        boolean result = retourenService.isDuplicateRetoure(retourenRequest);

        //then
        assertThat(result, is(false));
    }

}
