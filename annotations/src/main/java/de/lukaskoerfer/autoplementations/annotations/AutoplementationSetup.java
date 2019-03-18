package de.lukaskoerfer.autoplementations.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PACKAGE)
@Documented
public @interface AutoplementationSetup {

    /**
     * Sets a format for class name generation
     */
    Format format() default Format.DEFAULT;

    /**
     * Sets an additional parameter, which may be used by the name generator
     */
    String param() default "";

    /**
     * Sets the namespace of the implementation
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
