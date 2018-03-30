package de.lukaskoerfer.implementation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bundles multiple {@link Implementation} annotations
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Implementations {
	
	Implementation[] value() default {};
	
}
