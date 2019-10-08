package com.queenbee.errorhandlers;

import akka.http.javadsl.server.Route;

public interface GenericErrorResponse {
    Route getPageNotFound();
    Route getBadRequest();
    Route getUnAuthorized();
    Route getForbidden();
    Route getInternalServerError();
}
