package de.lukaskoerfer.autoplementations.processor.generation.statements;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.autoplementations.annotations.Action;
import de.lukaskoerfer.autoplementations.annotations.AutoImplementation;

/**
 * Implements methods with the return type void
 */
public class VoidStatementGenerator extends StatementGenerator {

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return");
	}

	@Override
	protected Action extractPrimaryAction(AutoImplementation definition) {
		return definition.voids();
	}

}
