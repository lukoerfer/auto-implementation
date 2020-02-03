package de.lukaskoerfer.autoplementations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bundles multiple {@link AutoImplementation} annotations
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AutoImplementations {
	
	AutoImplementation[] value() default {};
	
}
