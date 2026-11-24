package de.gessnerfl.fakesmtp.model.query;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class PropertyUtils {
	public static Object getProperty(Object instance, String path) {
		try {
			var value = org.apache.commons.beanutils.PropertyUtils.getProperty(instance, path);
			if (value instanceof Optional<?> opt)
				value = opt.orElse(null);
			return value;
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T getProperty(Object instance, String path, Class<T> type) {
		return type.cast(getProperty(instance, path));
	}
}
