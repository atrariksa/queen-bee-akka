package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;
import com.queenbee.http.components.HttpResponseBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ErrorHandler extends AbstractErrorHandler implements CustomErrorHandler {

    @Override
    public CompletionStage<HttpResponse> getPageNotFound(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(404, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getBadRequest(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(400, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getUnAuthorized(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(401, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getForbidden(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(403, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getInternalServerError(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(500, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getCustomError(int status, String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(status, body, headers)
        );
        return httpResponseCompletionStage;
    }
}
