package com.eva.vtiger.base;

import org.testng.annotations.Test;

public class TestingTestng {

	//// @BeforeTest

	@Test(priority = 1)
	public void test1() {

		System.out.println("marketing click - account verification");
	}

	@Test()
	public void test2() {
		System.out.println("test2 testcase running");

	}

}
