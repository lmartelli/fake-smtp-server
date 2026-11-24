package de.gessnerfl.fakesmtp.model.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.function.Predicate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonTypeName("and")
public record LogicalAnd(@NotEmpty List<FilterExpression> expressions) implements FilterExpression {
    @Override
    public <T> Predicate<T> toPredicate() {
        return x -> expressions.stream()
                .map(FilterExpression::toPredicate)
                .allMatch(p -> p.test(x));
    }
}
