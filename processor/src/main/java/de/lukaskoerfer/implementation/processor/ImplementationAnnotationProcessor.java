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

/**
 * 
 */
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
	
	/**
	 * Processes a single annotated element (either interface or abstract class)
	 * @param target
	 */
	private void process(TypeElement target) {
		Stream.of(target.getAnnotationsByType(Implementation.class))
			.map(implementation -> generate(target, implementation))
			.forEach(this::write);
	}
	
	/**
	 * Generates a Java file for a single {@link Implementation} annotation
	 * @param target
	 * @param implementation
	 * @return
	 */
	private JavaFile generate(TypeElement target, Implementation implementation) {
		return ImplementationGenerator.builder()
			.elementUtils(processingEnv.getElementUtils())
			.typeUtils(processingEnv.getTypeUtils())
			.target(target)
			.implementation(implementation)
			.build()
			.generate();
	}
	
	/**
	 * Writes a Java file to the file system
	 * @param file
	 */
	@SneakyThrows(IOException.class)
	private void write(JavaFile file) {
		file.writeTo(processingEnv.getFiler());
	}

}
