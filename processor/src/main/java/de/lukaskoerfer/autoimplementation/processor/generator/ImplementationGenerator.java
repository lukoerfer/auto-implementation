package de.lukaskoerfer.autoimplementation.processor.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import de.lukaskoerfer.autoimplementation.processor.context.Context;
import de.lukaskoerfer.autoimplementation.processor.generator.type.TypeGenerator;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ImplementationGenerator {

    private final ImplementationPackages naming;

    private final TypeGenerator generator;

    public JavaFile generate(Context context) {
        String packageName = naming.determine(context);
        TypeSpec type = generator.generateType(context);
        return JavaFile.builder(packageName, type)
            .skipJavaLangImports(true)
            .build();
    }

}
