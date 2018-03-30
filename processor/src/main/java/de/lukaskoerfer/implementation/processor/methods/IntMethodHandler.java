package de.lukaskoerfer.implementation.processor.methods;

import de.lukaskoerfer.implementation.annotations.Implementation;

/**
 * Implements integer (or byte, char, short or long) methods
 */
public class IntMethodHandler extends ValueMethodHandler {

	public IntMethodHandler(Implementation implementation) {
		super(implementation);
	}

	@Override
	protected String getValue() {
		return Integer.toString(0);
	}

}
