package de.lukaskoerfer.implementation.processor.methods;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.Statement;

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
	protected Statement getStatementType() {
		return implementation.valueMethods();
	}
	
}
