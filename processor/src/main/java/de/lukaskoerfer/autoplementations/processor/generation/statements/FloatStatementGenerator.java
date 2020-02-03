package de.lukaskoerfer.autoplementations.processor.generation.statements;


/**
 * Implements float or double methods
 */
public class FloatStatementGenerator extends ValueStatementGenerator {

	@Override
	protected String getDefaultReturnValue() {
		return Float.toString(0);
	}
	
}
