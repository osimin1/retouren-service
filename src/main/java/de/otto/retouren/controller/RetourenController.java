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
        RetourenResponse retourenResponse = retourenService.saveRetoure(retourenRequest);
        if (retourenResponse.status.equals(RetourenResponse.Status.SAVED)) {
            return callServices(retourenResponse, logger);
        } else {
            return retourenResponse;
        }
    }

    RetourenResponse callServices(RetourenResponse retourenResponse, LambdaLogger logger) {
        try {
/*            call(KUNDENGUTSCHRIFTENURL,logger);
            call(LIEFERANTENBELASTUNGENURL,logger);*/
            return retourenResponse;
        } catch (Exception e) {
            return RetourenResponse.builder().message(e.getMessage()).status(RetourenResponse.Status.ERROR).build();
        }
    }

    private int call(String url, LambdaLogger logger) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        logger.log("fetching response from :: " + url);
        return con.getResponseCode();
    }

    RetourenService getRetourenService(LambdaLogger logger) {
        return new RetourenService(logger);
    }
}