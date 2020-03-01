package de.lukaskoerfer.autoimplementation.processor.utility;

import com.squareup.javapoet.JavaFile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.annotation.processing.Filer;
import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JavaFileWriter {

    private final Filer filer;

    @SneakyThrows
    public void write(JavaFile file) {
        file.writeTo(filer);
    }

}
