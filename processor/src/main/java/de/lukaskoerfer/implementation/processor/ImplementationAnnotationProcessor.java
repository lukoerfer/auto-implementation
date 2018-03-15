package de.lukaskoerfer.implementation.processor;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.annotations.Implementations;
import lombok.SneakyThrows;

@AutoService(Processor.class)
public class ImplementationAnnotationProcessor extends AbstractProcessor {

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Stream.of(Implementation.class, Implementations.class)
			.map(Class::getCanonicalName)
			.collect(Collectors.toSet());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_8;
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		annotations.stream()
			.map(roundEnv::getElementsAnnotatedWith)
			.flatMap(Set::stream)
			.map(TypeElement.class::cast)
			.forEach(this::process);
		return true;
	}
	
	private void process(TypeElement source) {
		Stream.of(source.getAnnotationsByType(Implementation.class))
			.map(implementation -> generate(source, implementation))
			.forEach(this::write);
	}
	
	private JavaFile generate(TypeElement source, Implementation implementation) {
		return ImplementationGenerator.builder()
			.elements(processingEnv.getElementUtils())
			.source(source)
			.implementation(implementation)
			.build()
			.generate();
	}
	
	@SneakyThrows(IOException.class)
	private void write(JavaFile file) {
		file.writeTo(processingEnv.getFiler());
	}

}
