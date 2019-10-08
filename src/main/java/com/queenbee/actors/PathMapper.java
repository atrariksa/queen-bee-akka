package com.queenbee.actors;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.*;
import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.queenbee.QueenBee;
import com.queenbee.http.components.HttpHeaderBuilder;
import com.queenbee.http.components.HttpResponseBuilder;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PathMapper implements IPathMapper {
    private QueenBee app;

    public PathMapper(QueenBee app) {
        this.app = app;
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource =
                Http.get(system).bind(ConnectHttp.toHost("localhost", 9789));

        final Function<HttpRequest, HttpResponse> requestHandlerSync =
                new Function<HttpRequest, HttpResponse>() {
                    private final HttpResponse NOT_FOUND =
                            HttpResponse.create()
                                    .withStatus(404)
                                    .withEntity("Unknown resource!");


                    @Override
                    public HttpResponse apply(HttpRequest request) throws Exception {
                        Uri uri = request.getUri();
                        if (request.method() == HttpMethods.GET) {
                            if (uri.path().equals("/")) {
                                Thread.sleep(10000);
                                return
                                        HttpResponse.create()
                                                .withEntity(ContentTypes.TEXT_HTML_UTF8,
                                                        "<html><body>Hello world!</body></html>");
                            } else if (uri.path().equals("/hello")) {
                                String name = uri.query().get("name").orElse("Mister X");

                                return
                                        HttpResponse.create()
                                                .withEntity("Hello " + name + "!");
                            } else if (uri.path().equals("/ping")) {
                                return HttpResponse.create().withEntity("PONG!");
                            } else {
                                return NOT_FOUND;
                            }
                        } else {
                            return NOT_FOUND;
                        }
                    }
                };

        final Function<HttpRequest, CompletionStage<HttpResponse>> requestHandlerAsync =
                new Function<HttpRequest, CompletionStage<HttpResponse>>() {
                @Override
                public CompletionStage<HttpResponse> apply(HttpRequest httpRequest) throws Exception {
                    if (httpRequest.getUri().path().equals("/")) {
//                        Thread.sleep(10000);//this will be blocking
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-type", "text/plain");
//                        CompletionStage<HttpResponse> httpResponse =
//                                CompletableFuture.completedFuture(
//                                        HttpResponseBuilder.build(
//                                                StatusCodes.ACCEPTED.intValue(), "This Is Body", headers
//                                        )
//                                );
                        CompletionStage<HttpResponse> httpResponse =
                                CompletableFuture.supplyAsync(()-> {
                                    try {
                                        Thread.sleep(10000);//this will not blocking
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    return HttpResponseBuilder.build(
                                            StatusCodes.ACCEPTED.intValue(), "This Is Body", headers
                                    );
                                });
                        return httpResponse;
                    }
                    else {
                        return CompletableFuture.completedFuture(HttpResponse.create().withEntity("Not Found"));
                    }
                }
        };

        CompletionStage<ServerBinding> serverBindingFuture =
                serverSource.to(Sink.foreach(connection -> {
                    System.out.println("Accepted new connection from " + connection.remoteAddress());

//                    connection.handleWithSyncHandler(requestHandlerSync, materializer);
                    connection.handleWithAsyncHandler(requestHandlerAsync, materializer);
                    // this is equivalent to
                    //connection.handleWith(Flow.of(HttpRequest.class).map(requestHandler), materializer);
                })).run(materializer);
    }

    @Override
    public void mapAsyncHandler(String uriPath, Function<HttpRequest, CompletableFuture<HttpResponse>> handler) {

    }

    @Override
    public void mapSyncHandler(String uriPath, Function<HttpRequest, HttpResponse> handler) {

    }
}
