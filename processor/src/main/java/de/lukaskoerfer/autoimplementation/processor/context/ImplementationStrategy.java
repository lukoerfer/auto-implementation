package de.lukaskoerfer.autoimplementation.processor.context;

import de.lukaskoerfer.autoimplementation.Implementation;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Builder
@Accessors(fluent = true)
public class ImplementationStrategy {

    private final Implementation forVoidMethods;

    private final Implementation forPrimitiveMethods;

    private final Implementation forObjectMethods;

}
