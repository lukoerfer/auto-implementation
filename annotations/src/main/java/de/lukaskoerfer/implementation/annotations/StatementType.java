package de.lukaskoerfer.implementation.annotations;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Provides statement types for method implementation
 */
public enum StatementType {
	
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
	 * Returns the first {@link StatementType} <b>not</b> equal to {@link DEFAULT}
	 * @param statements
	 * @return
	 */
	public static StatementType firstDefined(StatementType... statements) {
		return Stream.of(statements)
			.filter(Predicate.isEqual(DEFAULT).negate())
			.findFirst()
			.get();
	}
}
