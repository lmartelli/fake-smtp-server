package de.gessnerfl.fakesmtp.model.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;

import java.util.function.Predicate;

import static de.gessnerfl.fakesmtp.model.query.ExpressionValueHelper.convertDateIfApplicable;
import static de.gessnerfl.fakesmtp.model.query.PropertyUtils.getProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonTypeName("less_than")
public record LessThanExpression(@NotEmpty String property, @NotEmpty Object value) implements FilterExpression {
    @Override
    @SuppressWarnings("rawtypes")
    public <T> Predicate<T> toPredicate() {
        return x -> {
            Comparable target = getProperty(x, property, Comparable.class);
            return target.compareTo(convertDateIfApplicable(target, value)) < 0;
        };
    }
}
