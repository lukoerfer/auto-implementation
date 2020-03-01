package de.lukaskoerfer.autoimplementation.processor.generator;

import de.lukaskoerfer.autoimplementation.processor.context.Context;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.lang.model.util.Elements;
import java.util.ArrayDeque;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ImplementationPackages {

    private final Elements elements;

    public String determine(Context context) {
        String base = elements.getPackageOf(context.getTargetType()).getQualifiedName().toString();
        String pkg = context.getPackageTemplate().replace("#", base);
        ArrayDeque<String> components = new ArrayDeque<>();
        for (String component : pkg.split("\\.")) {
            if (component.equals("-")) {
                components.pollLast();
            } else {
                components.add(component);
            }
        }
        return String.join(".", components);
    }

}
