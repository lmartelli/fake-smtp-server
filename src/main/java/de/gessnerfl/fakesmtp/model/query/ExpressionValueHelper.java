package de.gessnerfl.fakesmtp.model.query;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ExpressionValueHelper {
    private ExpressionValueHelper(){}

    public static Object convertDateIfApplicable(Object instance, String path, Object value) {
        Object target = PropertyUtils.getProperty(instance, path);
        return target != null && target.getClass().isAssignableFrom(ZonedDateTime.class) ? parseDate(value) : value;
    }

    public static Object convertDateIfApplicable(Object target, Object value) {
        return target != null && target.getClass().isAssignableFrom(ZonedDateTime.class) ? parseDate(value) : value;
    }

    private  static Object parseDate(Object value) {
        String dateString = value.toString();
        return ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
