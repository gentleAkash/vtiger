package com.eva.vtiger.base;

import java.lang.reflect.Method;

import org.testng.ITestResult;
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
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.eva.vtiger.pages.common.CommonReusableCodes;
import com.eva.vtiger.utils.WebUtil;

import lombok.Getter;

@Getter
public class BaseTest {

	private ExtentReports extent;
	private WebUtil webUtil = WebUtil.getObject();

	// Data Management

	protected CommonReusableCodes reuseCode;

	@BeforeGroups(groups = "Smoke")

	public void testGroups() {
//		webUtil= WebUtil.getObject();
//		webUtil.launchBrowser("chrome");
//		webUtil.goToHitUrl("http://localhost:8888");
//		reuseCode=new CommonReusableCodes(webUtil); 
//		reuseCode.login("admin", "admin");
	}
	////////////// ExtentReports////////////////////

	@BeforeSuite(groups = "Smoke")
	public void beforeSuite() {
		System.out.println("Extent-Report Initialization");
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output//ExtentReports//VtigerExtentReport.html");
		extent.attachReporter(spark);
		System.out.println("BeforeSuite");

	}

	@BeforeTest(groups = "Smoke")
	public void beforeTestTag() {
		System.out.println("Connect To The Data Base");
	}

	@Parameters({ "browser" })

	@BeforeClass(groups = { "Smoke" })
	public void beforeTestCases(String browserName) {
		// webUtil.launchBrowser(browserName);
		webUtil.launchBrowser("chrome");
		webUtil.goToHitUrl("http://localhost:8888");
	}

	@Parameters({ "userName", "password" })
	@BeforeMethod(groups = { "Smoke" })
	public void beforeMethod(String userName, String password, Method mt) {

		ExtentTest extTest = extent.createTest(mt.getName());
		webUtil.setExtentTestObject(extTest);
		reuseCode = new CommonReusableCodes(webUtil);
		// reuseCode.login("admin", "user");
		reuseCode.login(userName, password);

		System.out.println("login page successfully");
	}

	@Test(groups = "Test")
	public void test() {
		System.out.println("test");

	}

	@AfterMethod(groups = "Smoke")
	public void afterMethod(ITestResult result, Method mt) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String snapshotPath = webUtil.takeSnapshot(mt.getName());
			webUtil.getExtentTestObject().addScreenCaptureFromPath(snapshotPath);

		}
		reuseCode.logOut();
		extent.flush();
	}

	@AfterClass
	public void afterClass() {
		webUtil.quit();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("Disconnect From The Data Base");
	}

	@AfterSuite(groups = "Smoke")
	public void afterSuite() {
		System.out.println("Extent-Report Finalization");
		extent.flush();
	}
}
