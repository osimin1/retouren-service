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
        RetourenService retourenService = getRetourenService(logger);
        logger.log(retourenRequest.toString());
        if (callCustomerCreditNote(logger) != 200) {
            return RetourenResponse.builder().message("Kundengutschriften-Service konnte nicht erfolgreich aufgerufen werden").status(RetourenResponse.Status.ERROR).build();
        }
        if (callVendorChare(logger) != 200) {
            return RetourenResponse.builder().message("Lieferantenbelastungen-Service konnte nicht erfolgreich aufgerufen werden").status(RetourenResponse.Status.ERROR).build();
        }
        return retourenService.saveRetoure(retourenRequest);
    }

    int callCustomerCreditNote(LambdaLogger logger) {
        try {
            /*           return call(KUNDENGUTSCHRIFTENURL,logger);*/
            return 200;
        } catch (Exception e) {
            return 500;
        }
    }

    int callVendorChare(LambdaLogger logger) {
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