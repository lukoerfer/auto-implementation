package de.lukaskoerfer.autoplementations.processor.methods;


import de.lukaskoerfer.autoplementations.annotations.Autoplementation;

/**
 * Implements float or double methods
 */
public class FloatMethodHandler extends ValueMethodHandler {

	@Override
	protected String getDefaultReturnValue() {
		return Float.toString(0);
	}
	
}
