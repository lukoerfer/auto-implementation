package de.lukaskoerfer.implementation.annotations;

import java.util.function.BiFunction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NameFormat {

	// Standard name formats
	DEFAULT((name, param) -> "Default" + name, null),
	ADAPTER((name, param) -> name + "Adapter", null),
	IMPL((name, param) -> name + "Impl", null),
	ABSTRACT((name, param) -> togglePrefix(name, "Abstract"), null),
	// Customizable name formats
	ADD_PREFIX((name, param) -> param + name, "Default"),
	ADD_SUFFIX((name, param) -> name + param, "Adapter"),
	REMOVE_PREFIX((name, param) -> removePrefix(name, param), "Abstract"),
	CUSTOM((name, param) -> String.format(param, name), "Default%s");
	
	private final BiFunction<String, String, String> function;
	private final String defaultParam;
	
	public String generateName(String name, String param) {
		if (param.isEmpty()) param = defaultParam;
		return function.apply(name, param);
	}
	
	private static String togglePrefix(String value, String prefix) {
		return value.startsWith(prefix) ? value.substring(prefix.length()) : "Abstract" + value;
	}
	
	private static String removePrefix(String value, String prefix) {
		if (value.startsWith(prefix)) {
			return value.substring(prefix.length());
		} else {
			throw new IllegalArgumentException("Interface name does not start with prefix " + prefix);
		}
	}
	
}
