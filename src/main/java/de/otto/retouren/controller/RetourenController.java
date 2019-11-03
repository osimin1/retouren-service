package de.otto.retouren.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.otto.retouren.service.RetourenService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetourenController implements RequestHandler<RetourenRequest, RetourenResponse> {

    private static final String KUNDENGUTSCHRIFTENURL = "https://kundengutschriften.de";
    private static final String LIEFERANTENBELASTUNGENURL = "https://lieferantenbelastungen.de";

    public RetourenResponse handleRequest(RetourenRequest retourenRequest, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(retourenRequest.toString());
        return getRetourenResponse(retourenRequest, logger);
    }

    private RetourenResponse getRetourenResponse(RetourenRequest retourenRequest, LambdaLogger logger) {
        RetourenService retourenService = getRetourenService(logger);
        if (retourenService.isDuplicateRetoure(retourenRequest)) {
            return RetourenResponse.builder().message("Duplicate Retoure detected").status(RetourenResponse.Status.DUBLICATE).build();
        }
        if (callCustomerCreditNote(logger) != 200) {
            return RetourenResponse.builder().message("CustomerCreditNote-Service call was not successful").status(RetourenResponse.Status.ERROR).build();
        }
        if (callVendorCharge(logger) != 200) {
            return RetourenResponse.builder().message("VendorCharge-Service call was not successful").status(RetourenResponse.Status.ERROR).build();
        }
        if (retourenService.isSaveRetoureSuccessful(retourenRequest)) {
            return RetourenResponse.builder().message("Retoure was saved successful").status(RetourenResponse.Status.SAVED).build();
        }
        return RetourenResponse.builder().message("Retoure was NOT saved").status(RetourenResponse.Status.ERROR).build();
    }

    int callCustomerCreditNote(LambdaLogger logger) {
        try {
            /*           return call(KUNDENGUTSCHRIFTENURL,logger);*/
            return 200;
        } catch (Exception e) {
            return 500;
        }
    }

    int callVendorCharge(LambdaLogger logger) {
        try {
            /*           return call(KUNDENGUTSCHRIFTENURL,logger);*/
            return 200;
        } catch (Exception e) {
            return 500;
        }
    }

    private int call(String url, LambdaLogger logger) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        logger.log("fetching response from :: " + url);
        return con.getResponseCode();
    }

    RetourenService getRetourenService(LambdaLogger logger) {
        return new RetourenService(logger);
    }
}