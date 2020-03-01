package de.lukaskoerfer.autoimplementation;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

import java.lang.annotation.Annotation;

@Value
@Builder
@Accessors(fluent = true)
@SuppressWarnings("ClassExplicitlyAnnotation")
public class AutoImplementationMock implements AutoImplementation {

    @Builder.Default
    Class<?> target = void.class;

    @Builder.Default
    String name = "";

    @Builder.Default
    String pkg = "#";

    @Builder.Default
    Implementation all = Implementation.UNDEFINED;

    @Builder.Default
    Implementation voids = Implementation.UNDEFINED;

    @Builder.Default
    Implementation values = Implementation.UNDEFINED;

    @Builder.Default
    Implementation objects = Implementation.UNDEFINED;

    @Override
    public Class<? extends Annotation> annotationType() {
        return AutoImplementation.class;
    }
}
