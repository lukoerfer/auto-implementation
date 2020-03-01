package de.lukaskoerfer.autoimplementation.processor.generator.type;

import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import de.lukaskoerfer.autoimplementation.processor.context.Context;
import de.lukaskoerfer.autoimplementation.processor.generator.methods.MethodGenerator;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TypeGenerator {

    private final MethodGenerator methodGenerator;

    public TypeSpec generateType(Context context) {
        String typeName = typeNames.determine(context);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName);
        addSupertype(typeBuilder, context);
        addModifiers(typeBuilder, context);
        addTypeParameters(typeBuilder, context);
        return typeBuilder.build();
    }

    private static void addSupertype(TypeSpec.Builder typeBuilder, Context context) {

    }

    private static void addModifiers(TypeSpec.Builder typeBuilder, Context context) {
        Modifier[] modifiers = context.getTargetType().getModifiers().stream()
            .filter(modifier -> modifier != Modifier.ABSTRACT)
            .toArray(Modifier[]::new);
        typeBuilder.addModifiers(modifiers);
    }

    private static void addTypeParameters(TypeSpec.Builder typeBuilder, Context context) {
        Collection<TypeVariableName> typeParameters = context.getTargetType().getTypeParameters().stream()
            .map(TypeVariableName::get)
            .collect(Collectors.toList());
        typeBuilder.addTypeVariables(typeParameters);
    }

}


