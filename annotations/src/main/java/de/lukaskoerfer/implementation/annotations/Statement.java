package de.lukaskoerfer.implementation.annotations;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Statement {
	
	DEFAULT,
	RETURN,
	THROW;
	
	public static Statement firstDefined(Statement... statements) {
		return Stream.of(statements)
			.filter(Predicate.isEqual(DEFAULT).negate())
			.findFirst()
			.get();
	}
}
