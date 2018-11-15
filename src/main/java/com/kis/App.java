package com.kis;

import com.kis.analyzer.Analyzer;
import com.kis.analyzer.HTMLAnalyzer;
import com.kis.parser.Parser;
import org.jsoup.nodes.Element;
import com.kis.parser.HTMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class App {

    private static Parser<Element> parser = new HTMLParser();
    private static Analyzer<Element> analyzer = new HTMLAnalyzer();

    public static void main(String[] args) throws Exception {
        if (args[0] == null || args[1] == null) {
            throw new IllegalArgumentException("Argument are empty");
        }
        Set<Element> source = parser.parse(new File(args[0]), true);
        Set<Element> target = parser.parse(new File(args[1]), false);
        Collection<Element> elements = analyzer.process(source, target);
        for (Element element : elements) {
            System.out.println(writeParents(element));
        }
    }

    private static String writeParents(Element element) {
        StringBuilder sb = new StringBuilder();
        for (Element parent : reversedList(element.parents())) {
           appendElement(sb, parent);
        }
        appendElement(sb, element);
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }

    private static void appendElement(StringBuilder sb, Element element) {
        sb.append(element.nodeName());
        if (!element.className().isEmpty()) {
            sb.append("[")
                .append(element.className())
                .append("]");
        }
        sb.append(" > ");
    }

    private static List<Element> reversedList(List<Element> list) {
        List<Element> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}
