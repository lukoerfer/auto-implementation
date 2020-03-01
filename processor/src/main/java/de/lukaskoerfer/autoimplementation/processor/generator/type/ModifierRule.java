package de.lukaskoerfer.autoimplementation.processor.generator.type;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ModifierRule {

    public Set<Modifier> extractModifiers(Set<Modifier> baseModifiers, boolean mustBeAbstract) {
        Set<Modifier> modifiers = new HashSet<>(baseModifiers);
        if (!mustBeAbstract) {
            modifiers.remove(Modifier.ABSTRACT);
        }
        return modifiers;
    }

}
