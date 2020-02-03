package de.lukaskoerfer.autoplementations.processor.generation.statements;

/**
 * Implements integer (or byte, char, short or long) methods
 */
public class IntStatementGenerator extends ValueStatementGenerator {

	@Override
	protected String getDefaultReturnValue() {
		return Integer.toString(0);
	}

}
