# implementations
Java annotation processor to create default implementations for interfaces or abstract classes

## Motivation
Reduce boilerplate code (and modification effort) by automatically generating default implementations of interfaces or abstract classes with lots of methods (often required when using listener interfaces). Modern IDEs can generate these implementations, but it is still necessary to manage and, if required, to modify the occuring code files manually. Also, these files won't have any direct benefit beside saving boilerplate code at some other location, so why not just save that boilerplate code, too?

## Usage

Simply add the `Implementation` annotation on an interface or abstract class. It is possible to use the annotation multiple times on the same element. The annotation processor will create a new class for each provided annotation. Each new class will implement the annotated interface (or extend the annotated abstract class) by implementing each unimplemented method:

    @Implementation
    public interface MyInterface {
        // ...
    }
    
The annotation provides the following parameters to configure the resulting implementation class (see below for details on their impact):

* **name** - a `String`, defaults to an empty string
* **nameFormat** - on from the `NameFormat` enum, defaults to `NameFormat.DEFAULT`
* **nameParam** - a `String`, defaults to an empty string
* **packageName** - a `String`, defaults to `"%s"`
* **allMethods** - one from the `Statement` enum, defaults to `Statement.RETURN`
* **voidMethods** - one from the `Statement` enum, defaults to `Statement.DEFAULT`
* **valueMethods** - one from the `Statement` enum, defaults to `Statement.DEFAULT`
* **objectMethods** - one from the `Statement` enum, defaults to `Statement.DEFAULT`

### Class name

The first three parameters **name**, **nameFormat** and **nameParam** form the name of the generated implementation class.
You can use **name** to directly specify a name:

    @Implementation(name="MyInterfaceImplementation")
    public interface MyInterface { }
    
However, you may want to generate the class name dynamically based on the name of the annotated interface or class. Just use the **nameFormat** parameter to choose how the name should be generated. For specific choices of the **nameFormat** parameter, the **nameParam** parameter may provide additional flexibility. The different choices for the **nameFormat** parameter (from the `NameFormat` enum) are explained below:

#### DEFAULT (the default option)

Adds the suffix `Default` to the name of the annotated element:
