package com.queenbee.actors;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.function.Function;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.CompletableFuture;

public interface IPathMapper {
    void mapAsyncHandler(JsonNode endpoints, Function<HttpRequest, CompletableFuture<HttpResponse>> handler);
}
