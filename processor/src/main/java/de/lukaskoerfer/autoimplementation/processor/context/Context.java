package de.lukaskoerfer.autoimplementation.processor.context;

import de.lukaskoerfer.autoimplementation.Implementation;
import lombok.Builder;
import lombok.Value;

import javax.lang.model.element.TypeElement;

@Value
@Builder
public class Context {

    TypeElement targetType;

    String definedName;

    String packageTemplate;

    ImplementationStrategy implementationStrategy;

}
