package de.lukaskoerfer.autoplementations.processor.generation.statements;

/**
 * Implements boolean methods
 */
public class BooleanStatementGenerator extends ValueStatementGenerator {

	@Override
	protected String getDefaultReturnValue() {
		return Boolean.FALSE.toString();
	}

}
