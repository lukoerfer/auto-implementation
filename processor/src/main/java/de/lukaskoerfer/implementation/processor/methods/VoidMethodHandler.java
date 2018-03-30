package de.lukaskoerfer.implementation.processor.methods;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.StatementType;

/**
 * Implements methods with the return type void
 */
public class VoidMethodHandler extends MethodHandler {

	public VoidMethodHandler(Implementation implementation) {
		super(implementation);
	}

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return");
	}

	@Override
	protected StatementType getChosenStatementType() {
		return implementation.voidMethods();
	}

}
