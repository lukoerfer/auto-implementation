package de.lukaskoerfer.autoimplementation.processor.generator.methods;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import de.lukaskoerfer.autoimplementation.Implementation;
import de.lukaskoerfer.autoimplementation.processor.context.Context;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static javax.lang.model.type.TypeKind.*;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MethodGenerator {

    private final Types types;

    public Optional<MethodSpec> generateMethod(Context context, ExecutableElement baseMethod) {
        TypeKind returnType = baseMethod.getReturnType().getKind();
        Implementation implementation = getByReturnType(context, returnType);
        if (implementation == Implementation.SKIP) {
            return Optional.empty();
        } else {
            DeclaredType enclosingType = (DeclaredType) context.getTargetType().asType();
            CodeBlock methodBody;
            switch (implementation) {
                case RETURN:
                    methodBody = createReturnImplementation(returnType);
                    break;
                case THROW:
                    methodBody = CodeBlock.of("throw new $T()", UnsupportedOperationException.class);
                    break;
                default:
                    throw new IllegalStateException();
            }
            MethodSpec method = MethodSpec.overriding(baseMethod, enclosingType, types)
                .addStatement(methodBody)
                .build();
            return Optional.of(method);
        }
    }

    private Implementation getByReturnType(Context context, TypeKind returnType) {
        if (returnType == VOID) {
            return context.getImplementationStrategy().forVoidMethods();
        } else if (returnType.isPrimitive()) {
            return context.getImplementationStrategy().forPrimitiveMethods();
        } else {
            return context.getImplementationStrategy().forObjectMethods();
        }
    }

    private CodeBlock createReturnImplementation(TypeKind returnType) {
        if (returnType == VOID) {
            return CodeBlock.of("return");
        } else if (returnType == BOOLEAN) {
            return CodeBlock.of("return $L", Boolean.FALSE.toString());
        } else if (Arrays.asList(BYTE, CHAR, SHORT, INT, LONG).contains(returnType)) {
            return CodeBlock.of("return $L", Integer.toString(0));
        } else if (Arrays.asList(FLOAT, DOUBLE).contains(returnType)) {
            return CodeBlock.of("return $L", Float.toString(0));
        } else {
            return CodeBlock.of("return $L", Objects.toString(null));
        }
    }

}
