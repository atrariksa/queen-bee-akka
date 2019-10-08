package com.queenbee.errorhandlers;

import akka.http.javadsl.server.Route;

import java.util.Map;

public interface CustomErrorHandler {
    Route getPageNotFound(String body, Map<String, String> headers);
    Route getBadRequest(String body, Map<String, String> headers);
    Route getUnAuthorized(String body, Map<String, String> headers);
    Route getForbidden(String body, Map<String, String> headers);
    Route getInternalServerError(String body, Map<String, String> headers);
    Route getCustomError(int status, String body, Map<String, String> headers);
}
