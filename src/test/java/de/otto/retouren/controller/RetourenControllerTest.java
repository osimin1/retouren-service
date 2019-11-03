package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.service.RetourenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetourenControllerTest {

    @InjectMocks
    @Spy
    RetourenController retourenController = new RetourenController();

    @Mock
    Context context;

    @Mock
    LambdaLogger logger;

    @Mock
    RetourenService retourenService;

    @Test
    public void thatRetourenIsEvaluatedAndSaVED() {
        // given
        doReturn(retourenService)
                .when(retourenController)
                .getRetourenService(any(LambdaLogger.class));
        when(retourenService.isSaveRetoureSuccessful(any())).thenReturn(true);
        when(context.getLogger()).thenReturn(logger);

        // when
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("order1").build();
        RetourenResponse result = retourenController.handleRequest(retourenRequest, context);

        // then
        verify(retourenService).isDuplicateRetoure(retourenRequest);
        verify(retourenService).isSaveRetoureSuccessful(retourenRequest);
        verify(retourenController).callCustomerCreditNote(logger);
        verify(retourenController).callVendorCharge(logger);
        assertThat(result.getStatus(), is(RetourenResponse.Status.SAVED));
        assertThat(result.getMessage(), containsString("Retoure was saved successful"));
    }

    @Test
    public void thatDuplicateRetourenIsHandledCorrect() {
        // given
        doReturn(retourenService)
                .when(retourenController)
                .getRetourenService(any(LambdaLogger.class));
        when(retourenService.isDuplicateRetoure(any())).thenReturn(true);
        when(context.getLogger()).thenReturn(logger);

        // when
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("order1").build();
        RetourenResponse result = retourenController.handleRequest(retourenRequest, context);

        // then
        verify(retourenService, never()).isSaveRetoureSuccessful(retourenRequest);
        verify(retourenController,never()).callCustomerCreditNote(logger);
        verify(retourenController,never()).callVendorCharge(logger);
        assertThat(result.getStatus(), is(RetourenResponse.Status.DUBLICATE));
        assertThat(result.getMessage(), containsString("Duplicate Retoure detected"));
    }

    @Test
    public void thatDynamoDBErrorIsHandledCorrect() {
        // given
        doReturn(retourenService)
                .when(retourenController)
                .getRetourenService(any(LambdaLogger.class));
        when(retourenService.isSaveRetoureSuccessful(any())).thenReturn(false);
        when(context.getLogger()).thenReturn(logger);

        // when
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("order1").build();
        RetourenResponse result = retourenController.handleRequest(retourenRequest, context);

        // then
        verify(retourenService).isSaveRetoureSuccessful(retourenRequest);
        verify(retourenController).callCustomerCreditNote(logger);
        verify(retourenController).callVendorCharge(logger);
        assertThat(result.getStatus(), is(RetourenResponse.Status.ERROR));
        assertThat(result.getMessage(), containsString("Retoure was NOT saved"));
    }
}
