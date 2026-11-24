package de.gessnerfl.fakesmtp.model.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.function.Predicate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonTypeName("not")
public record Negation(@NotNull FilterExpression expression) implements FilterExpression {
    @Override
    public <T> Predicate<T> toPredicate() {
        return expression.<T>toPredicate().negate();
    }
}
