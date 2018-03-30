package de.lukaskoerfer.implementation.processor.methods;

import de.lukaskoerfer.implementation.annotations.Implementation;

/**
 * Implements boolean methods
 */
public class BooleanMethodHandler extends ValueMethodHandler {

	public BooleanMethodHandler(Implementation implementation) {
		super(implementation);
	}

	@Override
	protected String getValue() {
		return Boolean.FALSE.toString();
	}

}
