package com.queenbee.utils;

import org.jsoup.Jsoup;

public class InputSanitizer {
    public static boolean hasHtml(String html) {
        return !(Jsoup.parse(html).text()).equals(html);
    }
    public static boolean isCleanText(String html) {
        return (Jsoup.parse(html).text()).equals(html);
    }
    public static void main(String[] args) {
        String htmlText = "var Hello = 'text'";
        System.out.println(htmlText + " isCleanText: " + isCleanText(htmlText));
        String htmlText2 = "Hello";
        System.out.println(htmlText2 + " isCleanText: " + isCleanText(htmlText2));
    }
}
