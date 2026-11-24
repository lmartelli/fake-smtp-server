package de.gessnerfl.fakesmtp.model.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;
import java.util.function.Predicate;

import static de.gessnerfl.fakesmtp.model.query.ExpressionValueHelper.convertDateIfApplicable;
import static de.gessnerfl.fakesmtp.model.query.PropertyUtils.getProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonTypeName("equal")
public record EqualExpression(
		@NotEmpty String property,
		@NotEmpty Object value) implements FilterExpression {
	@Override
	public <T> Predicate<T> toPredicate() {
		return x -> {
			Object target = getProperty(x, property);
			return Objects.equals(target, convertDateIfApplicable(target, value));
		};
	}
}
