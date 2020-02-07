package de.lukaskoerfer.autoplementations.annotations;

import java.lang.annotation.*;

/**
 * Marks an interface or abstract class for the ImplementationAnnotationProcessor
 * to generate a default implementation.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(AutoImplementations.class)
@Documented
public @interface AutoImplementation {

	/**
	 * Sets the name of the implementation class
	 */
	String name() default "";
	
	/**
	 * Sets the package name of the implementation
	 * <br><br>
	 * It is possible to specify a relative package name by using '#' as a placeholder for the base package name.
	 * To go back in the package hierarchy, use '-' as package component.
	 */
	String pkg() default "#";
	
	/**
	 * Sets the type of statement to implement all methods regardless of the return type
	 */
	Action all() default Action.RETURN;
	
	/**
	 * Sets the type of statement to implement methods with the return type void
	 */
	Action voids() default Action.UNDEFINED;
	
	/**
	 * Sets the type of statement to implement methods with a value-type (primitive) return type
	 */
	Action values() default Action.UNDEFINED;
	
	/**
	 * Sets the type of statement to implement methods with a reference-type (object) return type
	 */
	Action objects() default Action.UNDEFINED;
	
}
