package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import com.queenbee.QueenBee;
import com.queenbee.http.components.HttpResponseBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ErrorHandler extends AbstractErrorHandler implements CustomErrorHandler {
    private QueenBee app;
    public ErrorHandler(QueenBee app) {
        super(app);
        this.app = app;
    }

    @Override
    public Route getPageNotFound(String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponseBuilder.build(404, body, headers);
        CompletionStage<HttpResponse> futureHttpResponse = CompletableFuture.completedFuture(httpResponse);
        return app.completeWithFuture(futureHttpResponse);
    }

    @Override
    public Route getBadRequest(String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponseBuilder.build(400, body, headers);
        CompletionStage<HttpResponse> futureHttpResponse = CompletableFuture.completedFuture(httpResponse);
        return app.completeWithFuture(futureHttpResponse);
    }

    @Override
    public Route getUnAuthorized(String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponseBuilder.build(401, body, headers);
        CompletionStage<HttpResponse> futureHttpResponse = CompletableFuture.completedFuture(httpResponse);
        return app.completeWithFuture(futureHttpResponse);
    }

    @Override
    public Route getForbidden(String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponseBuilder.build(403, body, headers);
        CompletionStage<HttpResponse> futureHttpResponse = CompletableFuture.completedFuture(httpResponse);
        return app.completeWithFuture(futureHttpResponse);
    }

    @Override
    public Route getInternalServerError(String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponseBuilder.build(500, body, headers);
        CompletionStage<HttpResponse> futureHttpResponse = CompletableFuture.completedFuture(httpResponse);
        return app.completeWithFuture(futureHttpResponse);
    }

    @Override
    public Route getCustomError(int status, String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponseBuilder.build(status, body, headers);
        CompletionStage<HttpResponse> futureHttpResponse = CompletableFuture.completedFuture(httpResponse);
        return app.completeWithFuture(futureHttpResponse);
    }
}
