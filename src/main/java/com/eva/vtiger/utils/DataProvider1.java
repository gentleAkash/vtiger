package com.eva.vtiger.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvider1 {
	
	@Test(dataProvider = "loginTestData")
	public void TestLogin(String userNmae, String password) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(option);
		driver.get("http://localhost:8888");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(userNmae);

		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@name='Login']")).click();
		Thread.sleep(3000);
		
		driver.quit();
	}

	@DataProvider(name = "loginTestData")
	public Object[][] LOginData() {

		Object[][] data = new Object[2][2];
		data[0][0] = "admin";
		data[0][1] = "user";

		data[1][0] = "admin";
		data[1][1] = "user";

		return data;

	}

}
