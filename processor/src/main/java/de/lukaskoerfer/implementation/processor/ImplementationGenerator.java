package de.lukaskoerfer.implementation.processor;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Parameterizable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import de.lukaskoerfer.implementation.annotations.Implementation;
import de.lukaskoerfer.implementation.processor.methods.MethodHandler;
import lombok.Builder;

@Builder
public class ImplementationGenerator {
	
	private final Elements elements;
	
	private final Implementation implementation;
	private final TypeElement source;
	
	public JavaFile generate() {
		TypeSpec.Builder builder = TypeSpec.classBuilder(getName())
			.addAnnotation(createGeneratedAnnotation())
			.addModifiers(getModifiers(source))
			.addTypeVariables(getTypeParameters(source))
			.addMethods(createMethodSpecs());
		TypeName sourceType = TypeName.get(source.asType());
		if (source.getKind().isClass()) {
			builder.superclass(sourceType);
		} else if (source.getKind().isInterface()) {
			builder.addSuperinterface(sourceType);
		}
		TypeSpec targetType = builder.build();
		return JavaFile.builder(getPackageName(), targetType).build();
	}
	
	private String getName() {
		String sourceName = source.getSimpleName().toString();
		String targetName = implementation.name();
		if (targetName.isEmpty()) {
			targetName = implementation.nameFormat().generateName(sourceName, implementation.nameParam());
		}
		return targetName;
	}
	
	private String getPackageName() {
		String sourcePackage = elements.getPackageOf(source).getQualifiedName().toString();
		String targetPackage = String.format(implementation.packageName(), sourcePackage);
		ArrayDeque<String> components = new ArrayDeque<>();
		Stream.of(targetPackage.split("\\.")).forEachOrdered(component -> {
			if (component.equals("-")) {
				components.pollLast();
			} else {
				components.add(component);
			}
		});
		targetPackage = components.stream().collect(Collectors.joining("."));
		return targetPackage;
	}
	
	private Collection<MethodSpec> createMethodSpecs() {
		return ElementFilter.methodsIn(elements.getAllMembers(source)).stream()
			.filter(method -> method.getModifiers().contains(Modifier.ABSTRACT))
			.map(this::createMethodSpec)
			.collect(Collectors.toList());
	}
	
	private MethodSpec createMethodSpec(ExecutableElement method) {
		return MethodSpec.methodBuilder(method.getSimpleName().toString())
			.addAnnotation(Override.class)
			.addModifiers(getModifiers(method))
			.returns(TypeName.get(method.getReturnType()))
			.addTypeVariables(getTypeParameters(method))
			.addParameters(createParameterSpecs(method))
			.varargs(method.isVarArgs())
			.addStatement(buildStatement(method.getReturnType()))
			.build();
	}
	
	private Collection<ParameterSpec> createParameterSpecs(ExecutableElement method) {
		return method.getParameters().stream()
			.map(ParameterSpec::get)
			.collect(Collectors.toList());
	}
	
	private Collection<TypeVariableName> getTypeParameters(Parameterizable element) {
		return element.getTypeParameters().stream()
			.map(TypeVariableName::get)
			.collect(Collectors.toList());
	}
	
	private Modifier[] getModifiers(Element element) {
		return element.getModifiers().stream()
			.filter(Predicate.isEqual(Modifier.ABSTRACT).negate())
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
