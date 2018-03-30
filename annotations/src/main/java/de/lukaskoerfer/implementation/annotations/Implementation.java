package de.lukaskoerfer.implementation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an interface or abstract class for the ImplementationAnnotationProcessor
 * to generate a default implementation.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(Implementations.class)
public @interface Implementation {

	/**
	 * Sets the name of the implementation directly
	 */
	String name() default "";
	
	/**
	 * Sets a format for class name generation
	 */
	NameFormat nameFormat() default NameFormat.DEFAULT;
	
	/**
	 * Sets an additional parameter for some name formats
	 */
	String nameParam() default "";
	
	/**
	 * Sets the package name of the implementation
	 * <br><br>
	 * It is possible to specify a relative package name by using '%s' as a placeholder for the base package name.
	 * To go back in the package hierarchy, use '-' as package component.
	 */
	String packageName() default "%s";
	
	/**
	 * Sets the type of statement to implement all methods regardless of the return type
	 */
	StatementType allMethods() default StatementType.RETURN;
	
	/**
	 * Sets the type of statement to implement methods with the return type void
	 */
	StatementType voidMethods() default StatementType.DEFAULT;
	
	/**
	 * Sets the type of statement to implement methods with a value-type (primitive) return type
	 */
	StatementType valueMethods() default StatementType.DEFAULT;
	
	/**
	 * Sets the type of statement to implement methods with a reference-type (object) return type
	 */
	StatementType objectMethods() default StatementType.DEFAULT;
	
}
