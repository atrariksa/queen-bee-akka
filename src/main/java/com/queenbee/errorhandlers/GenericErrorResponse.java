package com.queenbee.errorhandlers;

import akka.http.javadsl.model.HttpResponse;

import java.util.concurrent.CompletionStage;

public interface GenericErrorResponse {
    CompletionStage<HttpResponse> getPageNotFound();
    CompletionStage<HttpResponse> getBadRequest();
    CompletionStage<HttpResponse> getUnAuthorized();
    CompletionStage<HttpResponse> getForbidden();
    CompletionStage<HttpResponse> getInternalServerError();
}
