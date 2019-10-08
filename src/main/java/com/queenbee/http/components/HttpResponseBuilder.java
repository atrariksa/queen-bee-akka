package com.queenbee.http.components;

import akka.http.javadsl.model.HttpHeader;
import akka.http.javadsl.model.HttpResponse;

import java.util.ArrayList;
import java.util.Map;

public class HttpResponseBuilder {
    public static HttpResponse build(int status, String body, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponse.create()
                .withStatus(status)
                .withHeaders(HttpHeaderBuilder.build(headers))
                .withEntity(body);
        return httpResponse;
    }
    public static HttpResponse build(int status, String body, HttpHeader httpHeader) {
        HttpResponse httpResponse = HttpResponse.create()
                .withStatus(status)
                .withEntity(body)
                .addHeader(httpHeader);
        return httpResponse;
    }
    public static HttpResponse build(int status, Map<String, String> headers) {
        HttpResponse httpResponse = HttpResponse.create()
                .withStatus(status)
                .withHeaders(HttpHeaderBuilder.build(headers));
        return httpResponse;
    }
    public static HttpResponse build(int status) {
        HttpResponse httpResponse = HttpResponse.create().withStatus(status);
        return httpResponse;
    }
}
