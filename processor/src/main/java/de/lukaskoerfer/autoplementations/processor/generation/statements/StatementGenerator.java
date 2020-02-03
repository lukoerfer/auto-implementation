package de.lukaskoerfer.autoplementations.processor.generation.statements;

import javax.lang.model.type.TypeKind;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.autoplementations.annotations.Action;
import de.lukaskoerfer.autoplementations.annotations.AutoImplementation;
import lombok.RequiredArgsConstructor;

/**
 * Base class for method definition
 */
@RequiredArgsConstructor
public abstract class StatementGenerator {
	
	/**
	 * Creates a method statement
	 * @return A Java code block containing a statement
	 */
	public final CodeBlock generateBody(AutoImplementation definition) {
		if (extractAction(definition) == Action.RETURN) {
			return createReturnStatement();
		} else {
			return CodeBlock.of("throw new $T()", UnsupportedOperationException.class);
		}
	}
	
	private Action extractAction(AutoImplementation definition) {
		return Action.firstNonDefault(extractPrimaryAction(definition), definition.all(), Action.RETURN);
	}
	
	/**
	 * Extracts the statement type from the method type
	 * @return A statement type
	 */
	protected abstract Action extractPrimaryAction(AutoImplementation definition);
	
	/**
	 * Creates a method return statement
	 * @return A Java code block containing a statement
	 */
	protected abstract CodeBlock createReturnStatement();
	
	/**
	 * Creates a method handler based on a given definition policy and a specific type
	 * @param kind A type kind
	 * @return A method handler
	 * @throws IllegalArgumentException for an unsupported type kind
	 */
	public static StatementGenerator forMethodReturnType(TypeKind kind) {
		switch (kind) {
		case VOID:
			return new VoidStatementGenerator();
		case BOOLEAN:
			return new BooleanStatementGenerator();
		case BYTE:
		case CHAR:
		case SHORT:
		case INT:
		case LONG:
			return new IntStatementGenerator();
		case FLOAT:
		case DOUBLE:
			return new FloatStatementGenerator();
		case ARRAY:
		case DECLARED:
		case NULL:
		case TYPEVAR:
		case UNION:
		case WILDCARD:
			return new ObjectStatementGenerator();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
