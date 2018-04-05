package com.abner.codebase.reporting.infra;

import java.util.Map;

public interface AttributeParser {
	<T> Map<String, Attribute<T, ?>> parse(Class<? extends T> domain);
}
