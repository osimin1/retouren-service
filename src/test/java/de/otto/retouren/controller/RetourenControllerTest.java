package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.service.RetourenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatRetourenIsEvaluated() {
        // given
        doReturn( retourenService )
                .when( retourenController )
                .getRetourenService( any( LambdaLogger.class ));
        when(context.getLogger()).thenReturn(logger);
        // when
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("order1").build();
        retourenController.handleRequest(retourenRequest, context);

        // then
        verify(retourenService).saveRetoure(retourenRequest);
    }
}
