package de.lukaskoerfer.implementation.processor;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.Generated;
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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.processor.methods.MethodHandler;
import lombok.Builder;

@Builder
public class ImplementationGenerator {
	
	private final Elements elementUtils;
	private final Types typeUtils;
	
	private final Implementation implementation;
	private final TypeElement target;
	
	public JavaFile generate() {
		TypeSpec.Builder builder = TypeSpec.classBuilder(getName())
			.addAnnotation(createGeneratedAnnotation())
			.addModifiers(getModifiers(target))
			.addTypeVariables(getTypeParameters(target))
			.addMethods(createMethodSpecs());
		TypeName sourceType = TypeName.get(target.asType());
		if (target.getKind().isClass()) {
			builder.superclass(sourceType);
		} else if (target.getKind().isInterface()) {
			builder.addSuperinterface(sourceType);
		}
		TypeSpec targetType = builder.build();
		return JavaFile.builder(getPackageName(), targetType).build();
	}
	
	private String getName() {
		String sourceName = target.getSimpleName().toString();
		String targetName = implementation.name();
		if (targetName.isEmpty()) {
			targetName = implementation.nameFormat().generateName(sourceName, implementation.nameParam());
		}
		return targetName;
	}
	
	private String getPackageName() {
		String sourcePackage = elementUtils.getPackageOf(target).getQualifiedName().toString();
		String targetPackage = String.format(implementation.packageName(), sourcePackage);
		ArrayDeque<String> components = new ArrayDeque<>();
		for (String component : targetPackage.split("\\.")) {
			if (component.equals("-")) {
				components.pollLast();
			} else {
				components.add(component);
			}
		}
		targetPackage = components.stream().collect(Collectors.joining("."));
		return targetPackage;
	}
	
	private Collection<MethodSpec> createMethodSpecs() {
		return ElementFilter.methodsIn(elementUtils.getAllMembers(target)).stream()
			.filter(method -> method.getModifiers().contains(Modifier.ABSTRACT))
			.map(this::createMethodSpec)
			.collect(Collectors.toList());
	}
	
	private MethodSpec createMethodSpec(ExecutableElement method) {
		DeclaredType enclosingType = (DeclaredType) target.asType();
		return MethodSpec.overriding(method, enclosingType, typeUtils)
			.addStatement(buildStatement(method.getReturnType()))
			.build();
	}
	
	private Collection<TypeVariableName> getTypeParameters(Parameterizable element) {
		return element.getTypeParameters().stream()
			.map(TypeVariableName::get)
			.collect(Collectors.toList());
	}
	
	private Modifier[] getModifiers(Element element) {
		return element.getModifiers().stream()
			.filter(modifier -> modifier != Modifier.ABSTRACT)
			.toArray(Modifier[]::new);
	}
	
	private AnnotationSpec createGeneratedAnnotation() {
		return AnnotationSpec
			.builder(Generated.class)
			.addMember("value", "$S", ImplementationGenerator.class.getName())
			// .addMember("date", "$S", LocalDate.now().format(DateTimeFormatter.ISO_DATE_TIME))
			.build();
	}
	
	private CodeBlock buildStatement(TypeMirror returnType) {
		TypeKind typeKind = returnType.getKind();
		return MethodHandler.create(implementation, typeKind).createStatement();
	}
	
}
