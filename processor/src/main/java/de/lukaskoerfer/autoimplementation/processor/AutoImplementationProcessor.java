package de.lukaskoerfer.autoimplementation.processor;

import com.google.auto.service.AutoService;
import de.lukaskoerfer.autoimplementation.AutoImplementation;
import de.lukaskoerfer.autoimplementation.AutoImplementations;
import de.lukaskoerfer.autoimplementation.processor.context.ContextFactory;
import de.lukaskoerfer.autoimplementation.processor.generator.ImplementationGenerator;
import de.lukaskoerfer.autoimplementation.processor.utility.JavaFileWriter;
import lombok.AccessLevel;
import lombok.Setter;

import javax.annotation.processing.*;
import javax.inject.Inject;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 */
@AutoService(javax.annotation.processing.Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AutoImplementationProcessor extends AbstractProcessor {

	private static final Set<Class<? extends Annotation>> ANNOTATIONS =
		Set.of(AutoImplementation.class, AutoImplementations.class);

	@Setter(value = AccessLevel.PACKAGE, onMethod = @__(@Inject))
	private ContextFactory contextFactory;

	@Setter(value = AccessLevel.PACKAGE, onMethod = @__(@Inject))
	private ImplementationGenerator implementationGenerator;

	@Setter(value = AccessLevel.PACKAGE, onMethod = @__(@Inject))
	private JavaFileWriter fileWriter;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		DaggerProcessorComponent.builder()
			.processingEnvironment(processingEnv)
			.build()
			.setup(this);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		roundEnv.getElementsAnnotatedWithAny(ANNOTATIONS).stream()
			.flatMap(contextFactory::process)
			.map(implementationGenerator::generate)
			.forEach(fileWriter::write);
		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return ANNOTATIONS.stream().map(Class::getCanonicalName).collect(Collectors.toSet());
	}

}
