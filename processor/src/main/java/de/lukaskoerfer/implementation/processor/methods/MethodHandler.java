package de.lukaskoerfer.implementation.processor.methods;

import javax.lang.model.type.TypeKind;

import com.squareup.javapoet.CodeBlock;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.Statement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MethodHandler {
	
	protected final Implementation implementation;
	
	public CodeBlock createStatement() {
		if (Statement.firstDefined(getStatementType(), implementation.allMethods(), Statement.RETURN) == Statement.RETURN) {
			return createReturnStatement();
		} else {
			return CodeBlock.of("throw new $T()", UnsupportedOperationException.class);
		}
	}
	
	protected abstract CodeBlock createReturnStatement();
	
	protected abstract Statement getStatementType();
	
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
