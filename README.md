# Autoplementations
Java annotation processor to automatically create default implementations for interfaces or abstract classes

## Motivation
Simply reduce boilerplate code (and modification effort) by automatically generating default implementations of interfaces and abstract classes with lots of methods (often required when using listener interfaces)! Modern IDEs can generate these implementations, but it is still necessary to manage and to modify the resulting code files manually. Also, such files usually  don't have any direct benefit beside saving boilerplate code at some other location, so why not just save that boilerplate code, too?

## Download
The *autoplementations* annotation processor is available via [JCenter](https://bintray.com/lukoerfer/maven-release-remote/de.lukaskoerfer.implementations). Just add the following lines to the `dependencies` block of your `build.gradle` file:

``` gradle
implementation 'de.lukaskoerfer.implementations:annotations:0.1'
annotationProcessor 'de.lukaskoerfer.implementations:processor:0.1'
```

For the usage with an IDE, we suggest using the [gradle-apt-plugin](https://github.com/tbroyer/gradle-apt-plugin).

You may also download the `.jar` files manually, both for the [annotations](https://bintray.com/lukoerfer/maven-release-remote/download_file?file_path=de%2Flukaskoerfer%2Fimplementations%2Fannotations%2F0.1%2Fannotations-0.1.jar) and the [processor](https://bintray.com/lukoerfer/maven-release-remote/download_file?file_path=de%2Flukaskoerfer%2Fimplementations%2Fprocessor%2F0.1%2Fprocessor-0.1.jar).

## Usage

Simply add the `Autoplementation` annotation on an interface or abstract class. The generated class will implement the annotated interface (or extend the annotated abstract class) by implementing each unimplemented method:

``` java
@Autoplementation
public interface Listener {
    boolean isActive();
    void onShow();
    void onHide();
}
```
    
Generated code:

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
    
The annotation provides several parameters to modify the name, the package and implementation details for the generated class. For detailed information check out [the wiki](https://github.com/lukoerfer/implementations/wiki/Usage).

## License

The software is licensed under the [MIT License](https://github.com/lukoerfer/implementations/blob/master/LICENSE).
