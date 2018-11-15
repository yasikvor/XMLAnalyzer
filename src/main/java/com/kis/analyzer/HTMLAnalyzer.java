package com.kis.analyzer;

import javafx.util.Pair;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class HTMLAnalyzer implements Analyzer<Element>{

    private static final String CLASS = "class";
    private static final String TITLE = "title";
    private static final String HREF = "href";
    private static final String ON_CLICK = "onclick";

    public List<Element> process(Set<Element> originSet, Set<Element> anotherSet) {
        List<Element> result = new ArrayList<>();
        for (Element origin : originSet) {
            List<Pair<Integer, Element>> originResult = new ArrayList<>();
            for (Element another : anotherSet) {
                int compatibility = compare(origin, another);
                originResult.add(new Pair<>(compatibility, another));
            }
            Element theMost = originResult.stream()
                .max(Comparator.comparingInt(Pair::getKey))
                .map(Pair::getValue)
                .orElse(null);
            result.add(theMost);
        }
        return result;
    }

    private int compare(Element origin, Element another) {
        int compatibility = 0;
        compatibility += compareAttr(origin, another, CLASS);
        compatibility += compareAttr(origin, another, TITLE);
        compatibility += compareAttr(origin, another, HREF);
        compatibility += compareAttr(origin, another, ON_CLICK);
        return compatibility;
    }

    private int compareAttr(Element origin, Element another, String attrName) {
        return (origin.attr(attrName).equalsIgnoreCase(another.attr(attrName)))
            ? 1
            : 0;
    }
}
