package de.lukaskoerfer.implementation.processor.methods;

import javax.lang.model.type.TypeKind;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.StatementType;
import lombok.RequiredArgsConstructor;

/**
 * Base class for method implementation
 */
@RequiredArgsConstructor
public abstract class MethodHandler {
	
	protected final Implementation implementation;
	
	/**
	 * Creates a method statement
	 * @return A Java code block containing a statement
	 */
	public final CodeBlock createStatement() {
		if (getStatementType() == StatementType.RETURN) {
			return createReturnStatement();
		} else {
			return CodeBlock.of("throw new $T()", UnsupportedOperationException.class);
		}
	}
	
	private final StatementType getStatementType() {
		return StatementType.firstDefined(getChosenStatementType(), implementation.allMethods(), StatementType.RETURN);
	}
	
	/**
	 * Extracts the statement type from the method type
	 * @return A statement type
	 */
	protected abstract StatementType getChosenStatementType();
	
	/**
	 * Creates a method return statement
	 * @return A Java code block containing a statement
	 */
	protected abstract CodeBlock createReturnStatement();
	
	/**
	 * Creates a method handler based on a given implementation policy and a specific type
	 * @param implementation A implementation annotation
	 * @param kind A type kind
	 * @return A method handler
	 * @throws IllegalArgumentException for an unsupported type kind
	 */
	public static MethodHandler create(Implementation implementation, TypeKind kind) {
		switch (kind) {
		case VOID:
			return new VoidMethodHandler(implementation);
		case BOOLEAN:
			return new BooleanMethodHandler(implementation);
		case BYTE:
		case CHAR:
		case SHORT:
		case INT:
		case LONG:
			return new IntMethodHandler(implementation);
		case FLOAT:
		case DOUBLE:
			return new FloatMethodHandler(implementation);
		case ARRAY:
		case DECLARED:
		case NULL:
		case TYPEVAR:
		case UNION:
		case WILDCARD:
			return new ObjectMethodHandler(implementation);
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
