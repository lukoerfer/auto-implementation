package de.lukaskoerfer.autoimplementation;

import java.lang.annotation.*;

/**
 * Bundles multiple {@link AutoImplementation} annotations
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface AutoImplementations {
	
	AutoImplementation[] value() default {};
	
}
