package de.lukaskoerfer.autoplementations.processor.methods;

import java.util.Objects;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.autoplementations.annotations.Action;
import de.lukaskoerfer.autoplementations.annotations.Autoplementation;

/**
 * Implements methods that return an object
 */
public class ObjectMethodHandler extends MethodHandler {

	@Override
	public CodeBlock createReturnStatement() {
		return CodeBlock.of("return $L", Objects.toString(null));
	}

	@Override
	protected Action extractPrimaryAction(Autoplementation definition) {
		return definition.objects();
	}
	
}
