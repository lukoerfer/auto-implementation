package de.lukaskoerfer.autoimplementation.processor.context;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.lang.model.element.Element;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ContextFactory {

    private final ImplementationStrategyFactory implementationStrategyFactory;

    public Stream<Context> process(Element element) {

    }

}
