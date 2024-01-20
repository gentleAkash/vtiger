package com.eva.vtiger.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//  IRetryAnalyzer     ITestListener  IReporter



public class RetryFailedTesCase implements  IRetryAnalyzer {
	
	private static final int maxTry = 2;
	private int count = 0;

	public boolean retry(ITestResult iTestResult) {
		  if (iTestResult.isSuccess()==false) {
			  if(this.count<maxTry) {
				  this.count++;
             
             return true;
		  }      
		  }
              return false;
}
}

//	public boolean retry(ITestResult result) {
//
//	}
//
//	@Test(retryAnalyzer = Retry.class)
//	public void testGenX() {
//
//		// ListenerTest Pass
//		Assert.assertEquals("sandeep", "sandeep");
//	}
//
//	@Test(retryAnalyzer = Retry.class)
//	public void testGenY() {
//
//		// ListenerTest fails
//		Assert.assertEquals("hello", "World");
//	}

