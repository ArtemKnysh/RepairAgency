package com.epam.rd.java.basic.repairagency.web.command;

import java.util.Objects;

public class UrlPatternAndMethod {

    private final String urlPattern;
    private final Method method;

    public UrlPatternAndMethod(String urlPattern, Method method) {
        this.urlPattern = urlPattern;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlPatternAndMethod that = (UrlPatternAndMethod) o;
        return Objects.equals(urlPattern, that.urlPattern) && method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlPattern, method);
    }

    @Override
    public String toString() {
        return "UrlPatternAndMethod [" +
                "urlPattern='" + urlPattern + '\'' +
                ", method=" + method +
                ']';
    }
}
