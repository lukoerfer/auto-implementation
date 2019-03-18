package de.lukaskoerfer.autoplementations.processor.methods;

import de.lukaskoerfer.autoplementations.annotations.Autoplementation;

/**
 * Implements boolean methods
 */
public class BooleanMethodHandler extends ValueMethodHandler {

	@Override
	protected String getDefaultReturnValue() {
		return Boolean.FALSE.toString();
	}

}
