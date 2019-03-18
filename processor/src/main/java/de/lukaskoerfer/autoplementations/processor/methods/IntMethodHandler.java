package de.lukaskoerfer.autoplementations.processor.methods;

import de.lukaskoerfer.autoplementations.annotations.Autoplementation;

/**
 * Implements integer (or byte, char, short or long) methods
 */
public class IntMethodHandler extends ValueMethodHandler {

	@Override
	protected String getDefaultReturnValue() {
		return Integer.toString(0);
	}

}
