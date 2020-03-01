package de.lukaskoerfer.autoimplementation.processor.context;

import de.lukaskoerfer.autoimplementation.AutoImplementation;
import de.lukaskoerfer.autoimplementation.Implementation;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.Arrays;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ImplementationStrategyFactory {

    public ImplementationStrategy create(AutoImplementation definition) {
        return ImplementationStrategy.builder()
            .forVoidMethods(evaluate(definition.voids(), definition.all()))
            .forPrimitiveMethods(evaluate(definition.primitives(), definition.nonVoids(), definition.all()))
            .forObjectMethods(evaluate(definition.objects(), definition.nonVoids(), definition.all()))
            .build();
    }

    private static Implementation evaluate(Implementation... priorities) {
        return Arrays.stream(priorities)
            .filter(priority -> priority != Implementation.SKIP)
            .findFirst().orElse(Implementation.SKIP);
    }

}
