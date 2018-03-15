package test;

import java.util.Properties;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.Statement;

@Implementation(name="Test1", allMethods=Statement.THROW)
@Implementation(name="Test3")
@Implementation(name="Test4")
public interface MyProperties<T> {

	void test();
	
	T get(Properties... properties);

}
