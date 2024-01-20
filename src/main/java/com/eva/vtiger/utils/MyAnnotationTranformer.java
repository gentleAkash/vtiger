package com.eva.vtiger.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITest;
import org.testng.annotations.ITestAnnotation;

public class MyAnnotationTranformer implements IAnnotationTransformer {
 
	//javaxmail	  
	
	@Override
	public void transform(final ITestAnnotation annotation, final Class testClass, final Constructor testConstructor,
			final Method testMethod) {

		annotation.setRetryAnalyzer(RetryFailedTesCase.class);
	}
}
