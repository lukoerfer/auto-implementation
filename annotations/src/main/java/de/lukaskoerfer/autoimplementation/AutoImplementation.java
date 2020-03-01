package de.lukaskoerfer.autoimplementation;

import java.lang.annotation.*;

/**
 * Marks an interface or abstract class for the ImplementationAnnotationProcessor
 * to generate a default implementation.
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.CLASS)
@Repeatable(AutoImplementations.class)
@Documented
public @interface AutoImplementation {

	/**
	 *
	 */
	Class<?> target() default void.class;

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
	String packageTemplate() default "#";
	
	/**
	 * Defines how to implement methods regardless of their return type
	 */
	Implementation all() default Implementation.SKIP;
	
	/**
	 * Defines how to implement methods with the return type void
	 */
	Implementation voids() default Implementation.SKIP;

	/**
	 * Defines how to implement methods without the return type void
	 */
	Implementation nonVoids() default Implementation.SKIP;

	/**
	 * Defines how to implement methods with a value-type (primitive) return type
	 */
	Implementation primitives() default Implementation.SKIP;
	
	/**
	 * Defines how to implement methods with a reference-type (object) return type
	 */
	Implementation objects() default Implementation.SKIP;
	
}
