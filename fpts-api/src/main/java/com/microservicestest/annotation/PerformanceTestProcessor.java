package com.microservicestest.annotation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import com.microservicetest.model.TestSpec;

/**
 * This is the class that will validate the methods annotated with the
 * {@link PerformanceTest}. <br>
 * The methods with this annotation need to have a return type of
 * {@link TestSpec} with a generic type set up.
 *
 *
 * @author andre
 *
 */
public class PerformanceTestProcessor extends AbstractProcessor {

	private static final String CLASS_REGEX = "(TestSpec)(<.+>)";
	private static final String CLASS_NAME = "TestSpec";

	public PerformanceTestProcessor() {

	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		Messager messager = this.processingEnv.getMessager();
		Pattern classPattern = Pattern.compile(CLASS_REGEX);
		for (TypeElement typeElement : annotations) {
			for (Element element : env.getElementsAnnotatedWith(typeElement)) {
				try {
					PerformanceTest annotation = element.getAnnotation(PerformanceTest.class);
					TypeMirror typeMirror = element.asType();
					Matcher m = classPattern.matcher(typeMirror.toString());
					if (m.find()) {
						String className = m.group(1);
						String genericClass = m.group(2);
						if (!className.equals(CLASS_NAME)) {
							messager.printMessage(Diagnostic.Kind.ERROR,
									"Returning type on method must be: " + CLASS_NAME);
						}
						if (genericClass == null) {
							messager.printMessage(Diagnostic.Kind.ERROR,
									"Returning type must have the generic class. Please set: TestSpec<Class> as the returning type");
						}
					} else {
						messager.printMessage(Diagnostic.Kind.ERROR,
								"Returning type must have the generic class. Please set: TestSpec<Class> as the returning type");
					}

				} catch (Exception e) {
					messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new HashSet<String>(Arrays.asList(PerformanceTest.class.getName()));
	}

}
