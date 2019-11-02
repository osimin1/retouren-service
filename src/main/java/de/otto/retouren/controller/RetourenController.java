package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.otto.retouren.service.RetourenService;

public class RetourenController implements RequestHandler<RetourenRequest, RetourenResponse> {

    public RetourenResponse handleRequest(RetourenRequest retourenRequest, Context context) {
        LambdaLogger logger = context.getLogger();
        RetourenService retourenService = new RetourenService(logger);
        logger.log(retourenRequest.toString());
        return retourenService.saveRetoure(retourenRequest);
    }
}