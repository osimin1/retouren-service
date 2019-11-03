package de.otto.retouren.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import de.otto.retouren.Retoure;
import de.otto.retouren.controller.RetourenRequest;
import de.otto.retouren.data.RetoureRepo;

import java.util.Date;

public class RetourenService {

    private RetoureRepo retoureRepo;
    private LambdaLogger logger;


    public RetourenService(LambdaLogger logger) {
        this.logger = logger;
        retoureRepo = getRetoureRepo(logger);
    }

    RetoureRepo getRetoureRepo(LambdaLogger logger) {
        return new RetoureRepo(logger);
    }

    public boolean isSaveRetoureSuccessful(RetourenRequest retourenRequest) {
        Retoure retoure = Retoure.builder()
                .customerId(retourenRequest.getCustomerId())
                .orderId(retourenRequest.getOrderId())
                .returnedShipment(true)
                .creationDate(new Date())
                .build();
        return retoureRepo.save(retoure);
    }

    public boolean isDuplicateRetoure(RetourenRequest retourenRequest) {
        Retoure retoure = retoureRepo.loadRetoure(retourenRequest.getCustomerId(), retourenRequest.getOrderId());
        return retoure != null;
    }
}
