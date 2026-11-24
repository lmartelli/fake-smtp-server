package de.gessnerfl.fakesmtp.model.query;

import java.util.function.Predicate;

public class SearchSpecification<T> {

    private final transient SearchRequest request;

    public SearchSpecification(SearchRequest request) {
        this.request = request;
    }

    public Predicate<T> toPredicate() {
        return request.getFilter().map(filterExpression -> filterExpression.<T>toPredicate()).orElse(_ -> true);
    }
}
