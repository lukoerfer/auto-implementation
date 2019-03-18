package de.lukaskoerfer.autoplementations.annotations;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Provides statement types for method implementation
 */
public enum Action {
	
	/**
	 * Default option, does not represent any statement
	 */
	DEFAULT,
	/**
	 * Represents a statement that returns the default value for the return type of the method
	 */
	RETURN,
	/**
	 * Represents a statement that throws an {@link UnsupportedOperationException}
	 */
	THROW;
	
	/**
	 * Returns the first {@link Action} <b>not</b> equal to Action.Default
	 * @param actions
	 * @return
	 */
	public static Action firstNonDefault(Action... actions) {
		return Stream.of(actions)
			.filter(Predicate.isEqual(DEFAULT).negate())
			.findFirst()
			.get();
	}
}
