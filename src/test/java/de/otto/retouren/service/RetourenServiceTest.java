package de.otto.retouren.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.controller.RetourenResponse;
import de.otto.retouren.data.RetoureRepo;
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
import static org.mockito.Mockito.when;

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
    RetoureRepo retoureRepo;

    @Test
    public void thatUnknownRetoureHasCorrectResponse() {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("1").build();
        when(retoureRepo.save(any())).thenReturn(RetourenResponse.Status.SAVED);

        //when
        RetourenResponse result = retourenService.saveRetoure(retourenRequest);

        //then
        assertThat(result.getMessage(), containsString("Retoure was saved: Retoure(customerId=Hans1, orderId=1, returnedShipment=true"));
        assertThat(result.getStatus(),is(RetourenResponse.Status.SAVED));
    }

    @Test
    public void thatKnownRetoureHasCorrectResponse() {
        // given
        RetourenRequest retourenRequest = RetourenRequest.builder().customerId("Hans1").orderId("1").build();
        when(retoureRepo.save(any())).thenReturn(RetourenResponse.Status.IGNORED);

        //when
        RetourenResponse result = retourenService.saveRetoure(retourenRequest);

        //then
        assertThat(result.getMessage(), containsString("Retoure is already known and send: Retoure(customerId=Hans1, orderId=1, returnedShipment=true"));
        assertThat(result.getStatus(),is(RetourenResponse.Status.IGNORED));
    }

}
