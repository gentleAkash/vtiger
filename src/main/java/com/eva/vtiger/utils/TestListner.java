package com.eva.vtiger.utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListner implements ITestListener {

	@Override
	public void onTestStart(ITestResult args0) {
		System.out.println("reporting line");
	}

	@Override
	public void onTestSuccess(ITestResult args0) {

	}

	@Override
	public void onTestFailure(ITestResult args0) {

	}

	@Override
	public void onTestSkipped(ITestResult args0) {

	}

	@Override

	public void onTestFailedButWithinSuccessPercentage(ITestResult args0) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult args0) {
	}

	public void onStart(ITestResult args0) {

	}

	public void onFinish(ITestResult args0) {

	}

}