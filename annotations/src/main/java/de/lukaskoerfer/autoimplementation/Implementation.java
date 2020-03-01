package de.lukaskoerfer.autoimplementation;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 */
public enum Implementation {

	/**
	 *
	 */
	SKIP,
	/**
	 * Represents a method implementation that returns the default value for the return type of the method
	 */
	RETURN,
	/**
	 * Represents a method implementation that throws an {@link UnsupportedOperationException}
	 */
	THROW

}
