package de.lukaskoerfer.autoplementations.annotations;

import java.util.function.BiFunction;

import lombok.RequiredArgsConstructor;

/**
 * Provides formats for the generation of a class name
 */
@RequiredArgsConstructor
public enum Format {

	/**
	 * Adds the prefix 'Default' to the base name
	 */
	DEFAULT((name, param) -> "Default" + name, null),
	/**
	 * Adds the suffix 'Adapter' to the base name
	 */
	ADAPTER((name, param) -> name + "Adapter", null),
	/**
	 * Adds the suffix 'Impl' to the base name
	 */
	IMPL((name, param) -> name + "Impl", null),
	/**
	 * Toggles the prefix 'Abstract' to / from the base name
	 */
	ABSTRACT((name, param) -> togglePrefix(name, "Abstract"), null),
	/**
	 * Adds a custom prefix to the base name
	 */
	ADD_PREFIX((name, param) -> param + name, "Default"),
	/**
	 * Adds a custom suffix to the base name
	 */
	ADD_SUFFIX((name, param) -> name + param, "Adapter"),
	/**
	 * Removes a custom prefix from the base name
	 */
	REMOVE_PREFIX((name, param) -> removePrefix(name, param), "Abstract"),
	/**
	 * Generates a name by applying a custom String format with the base name as argument
	 */
	CUSTOM((name, param) -> String.format(param, name), "Default%s");
	
	private final BiFunction<String, String, String> function;
	private final String defaultParam;
	
	/**
	 * Generates a class name according to this name format
	 * @param name A base class name
	 * @param param A custom name parameter
	 */
	public String apply(String name, String param) {
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
