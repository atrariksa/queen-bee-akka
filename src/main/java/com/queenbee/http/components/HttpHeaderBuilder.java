package com.queenbee.http.components;

import akka.http.javadsl.model.HttpHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHeaderBuilder {
    public static List<HttpHeader> build(Map<String, String> headers) {
        List<HttpHeader> headerList = new ArrayList<>();
        headers.forEach((key, value) -> {headerList.add(HttpHeader.parse(key, value));});
        return headerList;
    }
}
