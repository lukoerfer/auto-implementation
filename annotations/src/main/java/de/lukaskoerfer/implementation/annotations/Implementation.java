package de.lukaskoerfer.implementation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(Implementations.class)
public @interface Implementation {

	String name() default "";
	
	NameFormat nameFormat() default NameFormat.DEFAULT;
	
	String nameParam() default "";
	
	String packageName() default "%s";
	
	Statement allMethods() default Statement.RETURN;
	
	Statement voidMethods() default Statement.DEFAULT;
	
	Statement valueMethods() default Statement.DEFAULT;
	
	Statement objectMethods() default Statement.DEFAULT;
	
}
