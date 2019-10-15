package com.queenbee.actors;

import akka.http.javadsl.model.*;
import akka.japi.function.Function;
import com.queenbee.errorhandlers.ErrorHandler;
import com.queenbee.http.components.HttpResponseBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PathMapper {
    private ErrorHandler errorHandler;
    private ConfigValue endpoints;
    public PathMapper(ErrorHandler errorHandler, Config queenBeeConfig) {
        this.errorHandler = errorHandler;
        this.endpoints = queenBeeConfig.getValue("endpoints");
    }

    public Function<HttpRequest, CompletionStage<HttpResponse>> mapAsyncHandler() {
        final Function<HttpRequest, CompletionStage<HttpResponse>> requestHandlerAsync =
             httpRequest -> {
                 if (httpRequest.getUri().path().equals("/async")) {
                     HashMap<String, String> headers = new HashMap<>();
                     headers.put("Content-type", "text/plain");
                     CompletionStage<HttpResponse> httpResponse =
                             CompletableFuture.supplyAsync(() -> {
                                 try {
                                     Thread.sleep(10000);//this will not blocking
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }
                                 return HttpResponseBuilder.build(
                                         StatusCodes.ACCEPTED.intValue(), "This Is Body Async", headers
                                 );
                             });
                     return httpResponse;
                 } else {
                     return errorHandler.getPageNotFound();
                 }
            };
        return requestHandlerAsync;
    }
}