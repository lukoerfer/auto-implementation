package de.lukaskoerfer.implementation.processor.methods;

import java.util.Objects;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.Statement;

public class ObjectMethodHandler extends MethodHandler {

	public ObjectMethodHandler(Implementation implementation) {
		super(implementation);
	}

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return $L", Objects.toString(null));
	}

	@Override
	protected Statement getStatementType() {
		return implementation.objectMethods();
	}
	
}
