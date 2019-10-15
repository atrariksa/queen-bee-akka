package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public abstract class AbstractErrorHandler implements GenericErrorResponse {

    @Override
    public CompletionStage<HttpResponse> getPageNotFound() {
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(StatusCodes.NOT_FOUND)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getBadRequest() {
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(StatusCodes.BAD_REQUEST)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getUnAuthorized() {
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(StatusCodes.UNAUTHORIZED)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getForbidden() {
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(StatusCodes.FORBIDDEN)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getInternalServerError() {
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(StatusCodes.INTERNAL_SERVER_ERROR)
        );
        return httpResponse;
    }
}
