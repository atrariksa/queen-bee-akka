package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import com.queenbee.http.components.HttpResponseBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ErrorHandler extends AbstractErrorHandler implements CustomErrorHandler {

    @Override
    public CompletionStage<HttpResponse> getPageNotFound(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(StatusCodes.NOT_FOUND, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getBadRequest(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(StatusCodes.BAD_REQUEST, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getUnAuthorized(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(StatusCodes.UNAUTHORIZED, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getForbidden(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(StatusCodes.FORBIDDEN, body, headers)
        );
        return httpResponseCompletionStage;
    }

    @Override
    public CompletionStage<HttpResponse> getInternalServerError(String body, Map<String, String> headers) {
        CompletionStage<HttpResponse> httpResponseCompletionStage = CompletableFuture.supplyAsync(
                ()-> HttpResponseBuilder.build(StatusCodes.INTERNAL_SERVER_ERROR, body, headers)
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
