package com.kis.analyzer;

import java.util.List;
import java.util.Set;

public interface Analyzer<T> {
    List<T> process(Set<T> originSet, Set<T> anotherSet);
}
