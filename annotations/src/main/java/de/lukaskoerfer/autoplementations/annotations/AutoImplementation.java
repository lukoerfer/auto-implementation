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
	 * Sets the name of the implementation directly
	 */
	String name() default "";
	
	/**
	 * Sets a format for class name generation
	 */
	Format format() default Format.DEFAULT;
	
	/**
	 * Sets an additional parameter, which may be used by the name generator
	 */
	String param() default "";
	
	/**
	 * Sets the package name of the implementation
	 * <br><br>
	 * It is possible to specify a relative package name by using '%s' as a placeholder for the base package name.
	 * To go back in the package hierarchy, use '-' as package component.
	 */
	String namespace() default "%s";
	
	/**
	 * Sets the type of statement to implement all methods regardless of the return type
	 */
	Action all() default Action.RETURN;
	
	/**
	 * Sets the type of statement to implement methods with the return type void
	 */
	Action voids() default Action.DEFAULT;
	
	/**
	 * Sets the type of statement to implement methods with a value-type (primitive) return type
	 */
	Action values() default Action.DEFAULT;
	
	/**
	 * Sets the type of statement to implement methods with a reference-type (object) return type
	 */
	Action objects() default Action.DEFAULT;
	
}
