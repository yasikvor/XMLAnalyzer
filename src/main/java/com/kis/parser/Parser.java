package com.kis.parser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface Parser<T> {
    Set<T> parse(File file, boolean isOrigin) throws IOException;
}
