package de.lukaskoerfer.autoplementations.processor.generation;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Parameterizable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import de.lukaskoerfer.autoplementations.annotations.AutoImplementation;
import de.lukaskoerfer.autoplementations.processor.generation.statements.StatementGenerator;
import lombok.Builder;

@Builder
public class AutoImplementationGenerator {
	
	private final Elements elementUtils;
	private final Types typeUtils;
	
	private final AutoImplementation definition;
	private final TypeElement target;
	
	public JavaFile generate() {
		TypeSpec.Builder builder = TypeSpec.classBuilder(generateName())
			// .addAnnotation(buildGeneratedAnnotation())
			.addModifiers(extractModifiers(target))
			.addTypeVariables(extractTypeParameters(target))
			.addMethods(generateMethods());
		TypeName sourceType = TypeName.get(target.asType());
		if (target.getKind().isClass()) {
			builder.superclass(sourceType);
		} else if (target.getKind().isInterface()) {
			builder.addSuperinterface(sourceType);
		}
		TypeSpec targetType = builder.build();
		return JavaFile.builder(generateNamespace(), targetType).build();
	}
	
	private String generateName() {
		String sourceName = target.getSimpleName().toString();
		String targetName = definition.name();
		if (targetName.isEmpty()) {
			targetName = definition.format().apply(sourceName, definition.param());
		}
		return targetName;
	}
	
	private String generateNamespace() {
		String sourcePackage = elementUtils.getPackageOf(target).getQualifiedName().toString();
		String targetPackage = String.format(definition.namespace(), sourcePackage);
		ArrayDeque<String> components = new ArrayDeque<>();
		for (String component : targetPackage.split("\\.")) {
			if (component.equals("-")) {
				components.pollLast();
			} else {
				components.add(component);
			}
		}
		targetPackage = String.join(".", components);
		return targetPackage;
	}
	
	private Collection<MethodSpec> generateMethods() {
		return ElementFilter.methodsIn(elementUtils.getAllMembers(target)).stream()
			.filter(method -> method.getModifiers().contains(Modifier.ABSTRACT))
			.map(this::generateMethod)
			.collect(Collectors.toList());
	}
	
	private MethodSpec generateMethod(ExecutableElement method) {
		DeclaredType enclosingType = (DeclaredType) target.asType();
		return MethodSpec.overriding(method, enclosingType, typeUtils)
			.addStatement(generateStatement(method.getReturnType()))
			.build();
	}
	
	private Collection<TypeVariableName> extractTypeParameters(Parameterizable element) {
		return element.getTypeParameters().stream()
			.map(TypeVariableName::get)
			.collect(Collectors.toList());
	}
	
	private static Modifier[] extractModifiers(Element element) {
		return element.getModifiers().stream()
			.filter(modifier -> modifier != Modifier.ABSTRACT)
			.toArray(Modifier[]::new);
	}

	/*
	private static AnnotationSpec buildGeneratedAnnotation() {
		return AnnotationSpec
			.builder(Generated.class)
			.addMember("value", "$S", AutoImplementationGenerator.class.getName())
			// .addMember("date", "$S", LocalDate.now().format(DateTimeFormatter.ISO_DATE_TIME))
			.build();
	}
	 */
	
	private CodeBlock generateStatement(TypeMirror returnType) {
		return StatementGenerator.forMethodReturnType(returnType.getKind())
			.generateBody(definition);
	}
	
}
