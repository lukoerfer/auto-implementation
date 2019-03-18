package de.lukaskoerfer.autoplementations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bundles multiple {@link Autoplementation} annotations
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Autoplementations {
	
	Autoplementation[] value() default {};
	
}
