package de.lukaskoerfer.autoimplementation.processor.generator;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayDeque;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NamingStrategy {

    private static final String NAME_PREFIX = "Default";
    private static final String PACKAGE_TEMPLATE_PLACEHOLDER = "#";
    private static final String PACKAGE_SEPARATOR_REGEX = "\\.";

    public String determineTypeName(String definedName, String baseName) {
        return definedName != null ? definedName : NAME_PREFIX + baseName;
    }

    public String determinePackage(String template, String basePackage) {
        String pkg = template.replace(PACKAGE_TEMPLATE_PLACEHOLDER, basePackage);
        ArrayDeque<String> components = new ArrayDeque<>();
        for (String component : pkg.split(PACKAGE_SEPARATOR_REGEX)) {
            if (component.equals("-")) {
                components.pollLast();
            } else {
                components.add(component);
            }
        }
        return String.join(".", components);
    }

}
