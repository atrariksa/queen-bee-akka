package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCode;
import akka.http.scaladsl.model.StatusCodes;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public abstract class AbstractErrorHandler implements GenericErrorResponse {

    @Override
    public CompletionStage<HttpResponse> getPageNotFound() {
        StatusCode statusCode = new StatusCodes.ClientError(404, "Page Not Found","Page Not Found");
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(statusCode)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getBadRequest() {
        StatusCode statusCode = new StatusCodes.ClientError(400, "Bad Request","Bad Request");
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(statusCode)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getUnAuthorized() {
        StatusCode statusCode = new StatusCodes.ClientError(401, "UnAuthorized","UnAuthorized");
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(statusCode)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getForbidden() {
        StatusCode statusCode = new StatusCodes.ClientError(403, "Forbidden","Forbidden");
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(statusCode)
        );
        return httpResponse;
    }

    @Override
    public CompletionStage<HttpResponse> getInternalServerError() {
        StatusCode statusCode = new StatusCodes.ClientError(500, "Internal Server Error","Internal Server Error");
        CompletionStage<HttpResponse> httpResponse = CompletableFuture.supplyAsync(
                ()->HttpResponse.create().withStatus(statusCode)
        );
        return httpResponse;
    }
}
