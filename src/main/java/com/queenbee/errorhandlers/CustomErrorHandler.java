package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;

import java.util.Map;
import java.util.concurrent.CompletionStage;

public interface CustomErrorHandler {
    CompletionStage<HttpResponse> getPageNotFound(String body, Map<String, String> headers);
    CompletionStage<HttpResponse> getBadRequest(String body, Map<String, String> headers);
    CompletionStage<HttpResponse> getUnAuthorized(String body, Map<String, String> headers);
    CompletionStage<HttpResponse> getForbidden(String body, Map<String, String> headers);
    CompletionStage<HttpResponse> getInternalServerError(String body, Map<String, String> headers);
    CompletionStage<HttpResponse> getCustomError(int status, String body, Map<String, String> headers);
}
