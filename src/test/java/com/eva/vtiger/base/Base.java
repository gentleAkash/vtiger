package com.eva.vtiger.base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.eva.vtiger.pages.commonOr.CommonReusableCodesOr;
import com.eva.vtiger.utils.WebUtil;

import lombok.Getter;

@Getter
public class Base {

	private WebUtil webUtil;
	private CommonReusableCodesOr commnRuseCode;

	@BeforeGroups(groups = "Smoke")
	public void beforeGroups() {
		System.out.println("beforeGroups");
//		webUtil = WebUtil.getInstance();
//		webUtil.launchBrowser("chrome"); // driver is initialized from here,
//		webUtil.goToHitUrl("http://localhost:8888");
//		commnCode = new CommonCodes(webUtil);
//		commnCode.login();
	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Extent-Report Initialization");
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Connect To The Data Base");
	}

	@Parameters({"browserName"})
	@BeforeClass
	public void beforeTestCases(String browserName) {
	  System.out.println("before class- launch browser");	
	
	}

	@Parameters({"userName","password"})
	@BeforeMethod
	public void beforeMethod(String userName,String password) {
		System.out.println("before testcase - Login");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("after testcase - Logout");

	}

	@AfterClass
	public void afterClass() {
		System.out.println("after class - close");

	}

	@AfterTest
	public void afterTest() {
		System.out.println("Disconnect From The Data Base");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("Extent-Report Finalization");
	}

}
