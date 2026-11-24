package de.gessnerfl.fakesmtp.model.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;

import java.util.function.Predicate;

import static de.gessnerfl.fakesmtp.model.query.PropertyUtils.getProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonTypeName("is_null")
public record IsNullExpression(@NotEmpty String property) implements FilterExpression {
	@Override
	public <T> Predicate<T> toPredicate() {
		return x -> getProperty(x, property) == null;
	}
}
