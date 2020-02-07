package de.lukaskoerfer.autoplementations.processor;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import de.lukaskoerfer.autoplementations.annotations.AutoImplementation;
import de.lukaskoerfer.autoplementations.annotations.AutoImplementations;
import de.lukaskoerfer.autoplementations.processor.generation.AutoImplementationGenerator;
import lombok.SneakyThrows;

/**
 * 
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AutoImplementationProcessor extends AbstractProcessor {

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Stream.of(AutoImplementation.class, AutoImplementations.class)
			.map(Class::getCanonicalName).collect(Collectors.toSet());
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Stream.of(AutoImplementation.class, AutoImplementations.class)
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
		Stream.of(target.getAnnotationsByType(AutoImplementation.class))
			.map(definition -> generate(target, definition))
			.forEach(this::write);
	}
	
	/**
	 * Generates a Java file for a single {@link AutoImplementation} annotation
	 * @param target
	 * @param definition
	 * @return
	 */
	private JavaFile generate(TypeElement target, AutoImplementation definition) {
		return AutoImplementationGenerator.builder()
			.elementUtils(processingEnv.getElementUtils())
			.typeUtils(processingEnv.getTypeUtils())
			.target(target)
			.definition(definition)
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
