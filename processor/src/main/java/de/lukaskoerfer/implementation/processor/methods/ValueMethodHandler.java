package de.lukaskoerfer.implementation.processor.methods;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.StatementType;

/**
 * Base class for value type method handlers
 */
public abstract class ValueMethodHandler extends MethodHandler {

	public ValueMethodHandler(Implementation implementation) {
		super(implementation);
	}

	protected abstract String getValue();

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return $L", getValue());
	}
	
	@Override
	protected StatementType getChosenStatementType() {
		return implementation.valueMethods();
	}
	
}
