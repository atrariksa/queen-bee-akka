package com.queenbee.actors;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.function.Function;

import java.util.concurrent.CompletableFuture;

public interface IPathMapper {
    void mapAsyncHandler(String uriPath, Function<HttpRequest, CompletableFuture<HttpResponse>> handler);
    void mapSyncHandler(String uriPath, Function<HttpRequest, HttpResponse> handler);
}
