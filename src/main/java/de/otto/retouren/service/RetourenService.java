package de.otto.retouren.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.controller.RetourenResponse;
import de.otto.retouren.data.RetoureRepo;

import java.util.Date;

public class RetourenService {

    private RetoureRepo retoureRepo;
    private LambdaLogger logger;


    public RetourenService(LambdaLogger logger) {
        this.logger = logger;
        retoureRepo = getRetoureRepo();
    }

    RetoureRepo getRetoureRepo() {
        return new RetoureRepo();
    }

    public RetourenResponse saveRetoure(RetourenRequest retourenRequest) {
        Retoure retoure = Retoure.builder()
                .customerId(retourenRequest.getCustomerId())
                .orderId(retourenRequest.getOrderId())
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        String message;
        RetourenResponse.Status status = retoureRepo.save(retoure);
        if (status.equals(RetourenResponse.Status.SAVED)) {
            message = String.format("Retoure was saved: %s", retoure.toString());
        } else {
            message = String.format("Retoure is already known and send: %s", retoure.toString());
        }
        logger.log(message);
        return RetourenResponse.builder()
                .message(message)
                .status(status)
                .build();
    }
}
