package com.queenbee.errorhandlers;
import akka.http.javadsl.model.StatusCode;
import akka.http.javadsl.server.Route;
import akka.http.scaladsl.model.StatusCodes;
import com.queenbee.QueenBee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public abstract class AbstractErrorHandler implements GenericErrorResponse {

    private final QueenBee app;

    public AbstractErrorHandler(QueenBee app) {
        this.app = app;
    }

    @Override
    public Route getPageNotFound() {
        StatusCode statusCode = new StatusCodes.ClientError(404, "Page Not Found","Page Not Found");
        CompletionStage<StatusCode> statusCodeCompletableFuture = CompletableFuture.completedFuture(statusCode);
        return app.completeWithFutureStatus(statusCodeCompletableFuture);
    }

    @Override
    public Route getBadRequest() {
        StatusCode statusCode = new StatusCodes.ClientError(400, "Bad Request","Bad Request");
        CompletionStage<StatusCode> statusCodeCompletableFuture = CompletableFuture.completedFuture(statusCode);
        return app.completeWithFutureStatus(statusCodeCompletableFuture);
    }

    @Override
    public Route getUnAuthorized() {
        StatusCode statusCode = new StatusCodes.ClientError(401, "UnAuthorized","UnAuthorized");
        CompletionStage<StatusCode> statusCodeCompletableFuture = CompletableFuture.completedFuture(statusCode);
        return app.completeWithFutureStatus(statusCodeCompletableFuture);
    }

    @Override
    public Route getForbidden() {
        StatusCode statusCode = new StatusCodes.ClientError(403, "Forbidden","Forbidden");
        CompletionStage<StatusCode> statusCodeCompletableFuture = CompletableFuture.completedFuture(statusCode);
        return app.completeWithFutureStatus(statusCodeCompletableFuture);
    }

    @Override
    public Route getInternalServerError() {
        StatusCode statusCode = new StatusCodes.ClientError(500, "Internal Server Error","Internal Server Error");
        CompletionStage<StatusCode> statusCodeCompletableFuture = CompletableFuture.completedFuture(statusCode);
        return app.completeWithFutureStatus(statusCodeCompletableFuture);
    }
}
