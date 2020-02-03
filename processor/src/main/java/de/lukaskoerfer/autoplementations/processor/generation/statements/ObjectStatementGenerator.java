package de.lukaskoerfer.autoplementations.processor.generation.statements;

import java.util.Objects;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.autoplementations.annotations.Action;
import de.lukaskoerfer.autoplementations.annotations.AutoImplementation;

/**
 * Implements methods that return an object
 */
public class ObjectStatementGenerator extends StatementGenerator {

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return $L", Objects.toString(null));
	}

	@Override
	protected Action extractPrimaryAction(AutoImplementation definition) {
		return definition.objects();
	}
	
}
