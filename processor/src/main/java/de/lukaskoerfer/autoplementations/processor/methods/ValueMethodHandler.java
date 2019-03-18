package de.lukaskoerfer.autoplementations.processor.methods;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.autoplementations.annotations.Action;
import de.lukaskoerfer.autoplementations.annotations.Autoplementation;

/**
 * Base class for value type method handlers
 */
public abstract class ValueMethodHandler extends MethodHandler {

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return $L", getDefaultReturnValue());
	}

	protected abstract String getDefaultReturnValue();
	
	@Override
	protected final Action extractPrimaryAction(Autoplementation definition) {
		return definition.values();
	}
	
}
