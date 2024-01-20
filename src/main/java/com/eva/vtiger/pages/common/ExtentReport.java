package com.eva.vtiger.pages.common;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

public class ExtentReport {

	public static void main(String[] args) {

		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/VtigerExtentReportab.html");
		extent.attachReporter(spark);

		ExtentTest extTest = extent.createTest("Testcase1");
		extTest.log(Status.PASS, "Validation is passed");

		ExtentTest extTest2 = extent.createTest("Testcase2");
		extTest2.log(Status.INFO, "user logged in");

		System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver.exe");
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8888/");

		TakesScreenshot tss = (TakesScreenshot) driver;
		File snapshotSourceFileObj = tss.getScreenshotAs(OutputType.FILE);
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy hh_mm_ss a");
		String timeStamp = df.format(new Date());
		File snapshotDestinationFileObj = new File("test-output\\" + "sdhasbdhv" + timeStamp + ".png");
		try {
			
			Files.copy(snapshotSourceFileObj, snapshotDestinationFileObj);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExtentTest extTest3 = extent.createTest("Testcase3");
		extTest3.log(Status.FAIL, "it is failing");
		extTest3.log(Status.PASS, "it is passing");
		extTest3.log(Status.INFO, "it is info");
		extTest3.log(Status.PASS, "it is Passing");

		extTest3.addScreenCaptureFromPath(snapshotDestinationFileObj.getAbsolutePath());

		extent.flush();

	}

}
