# AutoImplementation
Java annotation processor to automatically create default implementations for interfaces or abstract classes

## Motivation
<!--
Modern IDEs can generate these implementations, but it is still necessary to manage and to modify the resulting code files manually.
-->

## Installation
<!--
The annotation processor is available via [JCenter](https://bintray.com/lukoerfer/maven-release-remote/de.lukaskoerfer.implementations).
Just add the following lines to the `dependencies` block of your `build.gradle` file:

``` gradle
implementation 'de.lukaskoerfer.implementations:annotations:0.1'
annotationProcessor 'de.lukaskoerfer.implementations:processor:0.1'
```

For the usage with an IDE, we suggest using the [gradle-apt-plugin](https://github.com/tbroyer/gradle-apt-plugin).

You may also download the `.jar` files manually, both for the [annotations](https://bintray.com/lukoerfer/maven-release-remote/download_file?file_path=de%2Flukaskoerfer%2Fimplementations%2Fannotations%2F0.1%2Fannotations-0.1.jar) and the [processor](https://bintray.com/lukoerfer/maven-release-remote/download_file?file_path=de%2Flukaskoerfer%2Fimplementations%2Fprocessor%2F0.1%2Fprocessor-0.1.jar).
-->

## Usage
Simply add the annotation `Implementation` on an interface or abstract class.
The generated class will implement the annotated interface (or extend the annotated abstract class) by implementing each unimplemented method:

``` java
@AutoImplementation
public interface Listener {
    boolean isActive();
    void onShow();
    void onHide();
}
```



``` java
public class DefaultListener implements Listener {
    public boolean isActive() {
        return false;
    }
    public void onShow() {
        return;
    }
    public void onHide() {
        return;
    }
}
```



### Target package

By default, the implementation class will be put into the same package as the annotated element. Using the **pkg** parameter, you can modify the target package.
Either specify an absolute package name or use `#` as placeholder for the package name of the annotated element (for relative package names):

``` java
@AutoImplementation(pkg = "#.implementation")
// "com.example.interfaces" => "com.example.interfaces.implementation"
```

You can use hyphens (`-`) as package components to walk back in the package tree:

``` java
@AutoImplementation(pkg = "#.-.implementation")
// "com.example.interfaces" => "com.example.implementation"
```

### Method implementation

The annotation processor can implement a method in two different ways:
   
* Return a return-type specific default value (`false` for booleans, `0` for integers, `0.0` for floats, `null` for objects)
* Throw an `UnsupportedOperationException`

Depending on the use case, one can decide which methods should be used to implement the interface or abstract class by using the **all** parameter on the annotation:

``` java
@AutoImplementation(all = StatementType.THROW)
```

The example above will cause any method on the implemented class to throw an `UnsupportedOperationException` when invoked.

To handle methods with different return-types differently, one can use the parameters **voids**, **values** and **objects**. The setting on each the those parameters will overwrite the setting on the **all** parameters if it differs from `StatementType.Default`:

``` java
@AutoImplementation(objects = StatementType.THROW)
```

The example above will generate a class implementation, that will throw an `UnsupportedOperationException` for each method with a non-primitive (and non-void) return-type.

## License
The software is licensed under the [MIT License](LICENSE).
