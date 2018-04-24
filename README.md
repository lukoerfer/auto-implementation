# implementations
Java annotation processor to create default implementations for interfaces or abstract classes

## Motivation
Reduce boilerplate code (and modification effort) by automatically generating default implementations of interfaces or abstract classes with lots of methods (often required when using listener interfaces). Modern IDEs can generate these implementations, but it is still necessary to manage and, if required, to modify the occuring code files manually. Also, these files won't have any direct benefit beside saving boilerplate code at some other location, so why not just save that boilerplate code, too?

## Usage

Simply add the `Implementation` annotation on an interface or abstract class. It is possible to use the annotation multiple times on the same element. The annotation processor will create a new class for each provided annotation. Each new class will implement the annotated interface (or extend the annotated abstract class) by implementing each unimplemented method:

    @Implementation
    public interface Listener {
        boolean isActive();
        void onShow();
        void onHide();
    }
    
Generated code:

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
    
The annotation provides several parameters to modify the name, package and method statements of the implementation class. Please check out [the wiki](https://github.com/lukoerfer/implementations/wiki/Usage) for details.

## License

The software is licensed under the [MIT License](https://github.com/lukoerfer/implementations/blob/master/LICENSE).
