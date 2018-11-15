package com.kis.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HTMLParser implements Parser<Element> {
    public Set<Element> parse(File file, boolean isOrigin) throws IOException {
        Predicate<Element> predicate = isOrigin
            ? getAnotherPredicate()
            : getOriginPredicate();
        Document document = Jsoup.parse(file, StandardCharsets.UTF_8.name());
        return document.getAllElements().stream()
            .filter(predicate)
            .collect(Collectors.toSet());
    }



    private Predicate<Element> getOriginPredicate() {
        return element -> {
            String attrClass = element.attr("class");
            String attrTitle = element.attr("title");
            return (attrClass != null && attrClass.equals("btn btn-success"))
                || (attrTitle != null && attrTitle.equals("Make-Button"));
        };
    }

    private Predicate<Element> getAnotherPredicate() {
        return element -> element.id().equals("make-everything-ok-button");
    }
}
