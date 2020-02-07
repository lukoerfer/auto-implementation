package de.lukaskoerfer.autoplementations.annotations;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Provides statement types for method implementation
 */
public enum Action {
	
	/**
	 *
	 */
	UNDEFINED,
	/**
	 * Represents a statement that returns the default value for the return type of the method
	 */
	RETURN,
	/**
	 * Represents a statement that throws an {@link UnsupportedOperationException}
	 */
	THROW;

}
