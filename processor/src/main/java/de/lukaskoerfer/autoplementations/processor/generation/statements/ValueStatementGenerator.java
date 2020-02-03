package de.lukaskoerfer.autoplementations.processor.generation.statements;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.autoplementations.annotations.Action;
import de.lukaskoerfer.autoplementations.annotations.AutoImplementation;

/**
 * Base class for value type method handlers
 */
public abstract class ValueStatementGenerator extends StatementGenerator {

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return $L", getDefaultReturnValue());
	}

	protected abstract String getDefaultReturnValue();
	
	@Override
	protected final Action extractPrimaryAction(AutoImplementation definition) {
		return definition.values();
	}
	
}
