package com.eva.vtiger.utils;

import java.io.File;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;

/**
 * @author Akash pal
 */
@Getter
@Setter
public class WebUtil {
	private WebDriver driver;

	private WebUtil() {

	}

	private static WebUtil webUtil;
	private ExtentTest extTest;

	public void setExtentTestObject(ExtentTest extTest) {
		this.extTest = extTest;
	}

	public ExtentTest getExtentTestObject() {
		return this.extTest;
	}

	public static WebUtil getObject() {
		if (webUtil == null) {
			webUtil = new WebUtil();
		}
		return webUtil;
	}

	public void printMessage(String yourString) {
		System.out.println(yourString);

	}

	public void statusAndDetails(Status status, String message) {
		extTest.log(status, message);
	}
	public void openLoginPage() {
		goToHitUrl("http://localhost:8888");
		windowMaximize();
		myIplicitilyWait(60);
		statusAndDetails(Status.INFO, "Login Page opened successfully");
	}

	//////////// myThread \\\\\\\\\\

	public void myThread(long duration) {
		try {
			Thread.sleep(duration);
			statusAndDetails(Status.PASS, "we have given " + duration + " millies seconds to the element");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//////////// launchBrowser \\\\\\\\\\

	public void launchBrowser(String browserName) {
		try {

			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver.exe");
				ChromeOptions co = new ChromeOptions();
				co.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(co);
				printMessage("Chrome Browser is launched sucessfully");
			} else if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "Driver\\geckodriver.exe");
				FirefoxOptions fireOptn = new FirefoxOptions();
				fireOptn.addArguments("--remote-allow-origins=*");
				driver = new FirefoxDriver(fireOptn);
				printMessage("FireFox Browser is launched sucessfully");
			} else if (browserName.equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver", "Driver\\msedgedriver.exe");
				EdgeOptions edOptn = new EdgeOptions();
				edOptn.addArguments("--remote-allow-origins=*");
				driver = new EdgeDriver(edOptn);
				printMessage("Edge Browser is launched sucessfully");
			} else {
				printMessage("Unsupported browser choice");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			printMessage("Browser is not launched sucessfully");
		}
	}
	//////////// windowMaximize \\\\\\\\\\

	public void windowMaximize() {

		try {
			driver.manage().window().maximize();
			statusAndDetails(Status.INFO, "Browser is maximized");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Browser is not maximized");
		}
	}

	//////////// windowMinimize \\\\\\\\\\

	public void windowMinimize() {

		try {
			driver.manage().window().minimize();
			statusAndDetails(Status.INFO, "the window is minimized");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the window is not minimized");
		}
	}

	//////////// myIplicitilyWait \\\\\\\\\\

	public void myIplicitilyWait(long seconds) {

		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
			statusAndDetails(Status.INFO, "the maximum timeouts for waiting is " + seconds);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	//////////// openUrl \\\\\\\\\\

	/**
	 * @param url
	 */
	public void openUrl(String url) {
		try {
			driver.get(url);
			statusAndDetails(Status.PASS, "the given url :-" + url + " is launched sucessfully");
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to open the URL
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String script = "window.location.href='" + url + "';";
			jsExecutor.executeScript(script);
			statusAndDetails(Status.PASS, "the given url :-" + url + " is launched sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.PASS, "the given url :-" + url + " is not launched sucessfully");
			throw e;

		}
	}

	//////////// goToBackPage \\\\\\\\\\

	/**
	 * by using of this method we can go to back page from the current page...
	 * 
	 */
	public void goToBackPage() {
		try {
			driver.navigate().back();
			statusAndDetails(Status.INFO, "the Page has been backed sucessfully");
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code to go back
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.history.back();");
			statusAndDetails(Status.INFO, "the Page has been backed sucessfully");
		} catch (WebDriverException e) {

			// WebDriverException:-----WebDriverException can be thrown for various reasons,
			// such as invalid URL,
			// network issues, browser crashes, or any other unexpected
			// errors during the navigation.......
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Page has not been backed sucessfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Page has not been backed sucessfully");
			throw e;
		}
	}

	//////////// goToForwardPage \\\\\\\\\\
	/**
	 * by using of this method we can go to forword page from the current page...
	 * 
	 */

	public void goToForwardPage() {
		try {
			driver.navigate().forward();
			statusAndDetails(Status.INFO, "the Page has been forwarded sucessfully");

		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code to go forward
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.history.forward();");
			statusAndDetails(Status.INFO, "the Page has been forwarded sucessfully");
		} catch (WebDriverException e) {

			// WebDriverException:-----WebDriverException can be thrown for various reasons,
			// such as invalid URL,
			// network issues, browser crashes, or any other unexpected
			// errors during the navigation.......
			e.printStackTrace();
			statusAndDetails(Status.PASS, "the Page hasn't been forwarded sucessfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Page has not been forwarded sucessfully");
			throw e;
		}
	}

	//////////// goToRefeshPage \\\\\\\\\\
	/**
	 * by using of this method we can refresh to the current page...
	 * 
	 */

	public void goToRefreshPage() {
		try {
			driver.navigate().refresh();
			statusAndDetails(Status.INFO, "the Page has been refreshed sucessfully");
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code to refresh the page
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("location.reload();");
			statusAndDetails(Status.PASS, "the Page has been refreshed sucessfully");
		} catch (WebDriverException e) {

			// WebDriverException:-----WebDriverException can be thrown for various reasons,
			// such as invalid URL,
			// network issues, browser crashes, or any other unexpected
			// errors during the navigation.......
			e.printStackTrace();
			statusAndDetails(Status.PASS, "the Page hasn't been refreshed sucessfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Page  hasn't been refreshed sucessfully");
			throw e;
		}
	}

	//////////// goToHitUrl \\\\\\\\\\

	/**
	 * with the help of this method i.e goToHitUrl() we can hit any url in the
	 * browser.
	 * 
	 * @param url by using of this url we can hit any url to the browser
	 */
	public void goToHitUrl(String url) {
		try {
			driver.navigate().to(url);
			printMessage("the given url :- " + url + " is hit successfully");
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code to open the URL
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.location.href = '" + url + "';");
			printMessage("the given url :- " + url + " is hit successfully");
		} catch (WebDriverException e) {

			// WebDriverException:-----WebDriverException can be thrown for various reasons,
			// such as invalid URL,
			// network issues, browser crashes, or any other unexpected
			// errors during the navigation.......
			e.printStackTrace();
			printMessage("the given url :- " + url + " is not hit successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			printMessage("the given url :- " + url + " is not hit successfully");
			throw e;
		}
	}

	//////////// getPresentUrl \\\\\\\\\\

	/**
	 * this method is commonaly used to get current url of the current page.
	 * 
	 * @return this method is return String as url.
	 */
	public String getPresentUrl() {
		String pageUrl = null;
		try {
			pageUrl = driver.getCurrentUrl();
			statusAndDetails(Status.INFO, "find the current page url :-" + pageUrl);
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code and get the page URL
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String script = "return window.location.href;";
			pageUrl = (String) jsExecutor.executeScript(script);
			statusAndDetails(Status.PASS, "find the current page url :-" + pageUrl);
			return pageUrl;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Didn't find the current page url :-" + pageUrl);
			throw e;
		}
		return pageUrl;

	}

	//////////// getPageTitle \\\\\\\\\\

	/**
	 * with the help of this method we can easily find the title of the current
	 * page..
	 * 
	 * @return it returns string as a title of the current page
	 */
	public String getPageTitle() {
		String pageTitle = null;
		try {
			pageTitle = driver.getTitle();
			statusAndDetails(Status.INFO, "find the title of the page is :-" + pageTitle);
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code and get the page title
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String script = "return document.title;";
			pageTitle = (String) jsExecutor.executeScript(script);
			statusAndDetails(Status.INFO, "find the title of the page is :-" + pageTitle);
			return pageTitle;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Didn't find the title of the page is :-" + pageTitle);
		}
		return pageTitle;
	}

	//////////// getPageSourceCode \\\\\\\\\\

	public String getPageSourceCode() {
		String sourceCode = null;
		try {
			sourceCode = driver.getPageSource();
			statusAndDetails(Status.INFO, "find the source code of the page :-" + sourceCode);
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to execute JavaScript code and get the page source
			// code
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String script = "return document.documentElement.outerHTML;";
			sourceCode = (String) jsExecutor.executeScript(script);
			statusAndDetails(Status.INFO, "find the source code of the page :-" + sourceCode);
			return sourceCode;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Didn't find the source code of the page- " + sourceCode);
		}
		return sourceCode;
	}

	//////////// myClose \\\\\\\\\\

	public void myClose() {

		try {
			driver.close();
			statusAndDetails(Status.INFO, "the current window or tab is closed successfully");
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to close the current browser window
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.close();");
			statusAndDetails(Status.INFO, "the current window or tab is closed successfully");
		} catch (NoSuchWindowException e) {
			// NoSuchWindowException: If the current window or tab is already closed or does
			// not exist.
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the current window or tab is not closed successfully");
			throw e;
		} catch (WebDriverException e) {
			// WebDriverException can be caused by a variety of factors, such as invalid
			// driver parameters,
			// network errors, or unexpected JavaScript errors....
			e.printStackTrace();
			statusAndDetails(Status.PASS, "the current window or tab is not closed successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the current window or tab is not closed successfully");
			throw e;
		}
	}

	//////////// myQuit \\\\\\\\\\

	public void quit() {

		try {
			driver.quit();
			statusAndDetails(Status.INFO, "All the  window or tab is closed successfully");
		} catch (ElementNotInteractableException e) {
			// Use JavascriptExecutor to quit the entire WebDriver session
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.open('', '_self', ''); window.close();");
			statusAndDetails(Status.INFO, "All the  window or tab is closed successfully");
		} catch (NoSuchSessionException e) {

			// The NoSuchSessionException is an exception raised by Selenium WebDriver when
			// it tries to perform an operation on a session that does not exist...
			e.printStackTrace();
			statusAndDetails(Status.INFO, "All the  window or tab is not closed successfully");
			throw e;
		} catch (WebDriverException e) {
			// WebDriverException can be caused by a variety of factors, such as invalid
			// driver parameters,
			// network errors, or unexpected JavaScript errors....
			e.printStackTrace();
			statusAndDetails(Status.INFO, "All the  window or tab is not closed successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "All the  window or tab is not closed successfully");
			throw e;
		}
	}
	//////////// switchToWindowByTitle \\\\\\\\\\

	public void switchToWindowByTitle(String expectPageTitle) {

		// NoSuchWindowException: This exception occurs when you try to switch to a
		// window
		// using a window handle that does not exist or has been closed.......

		try {
			Set<String> setWindowHandles = driver.getWindowHandles();
			for (String handleValue : setWindowHandles) {
				driver.switchTo().window(handleValue);
				String actPageTitle = driver.getTitle();
				if (actPageTitle.equalsIgnoreCase(expectPageTitle)) {
					break;
				}
			}
			statusAndDetails(Status.PASS, "the Window is switched by title :- " + expectPageTitle + " successfully");
		} catch (StaleElementReferenceException e) {
			Set<String> setWindowHandles = driver.getWindowHandles();
			for (String handleValue : setWindowHandles) {
				driver.switchTo().window(handleValue);
				String actPageTitle = driver.getTitle();
				if (actPageTitle.equalsIgnoreCase(expectPageTitle)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is  switched by title :- " + expectPageTitle + " successfully");
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO,
					"the Window is not  switched by title :- " + expectPageTitle + " successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO,
					"the Window is not switched by title :- " + expectPageTitle + " successfully");
			throw e;

		}

	}

	//////////// switchToWindowByUrl \\\\\\\\\\

	public void switchToWindowByUrl(String expectPageUrl) {

		try {
			Set<String> setWindowHandles = driver.getWindowHandles();
			for (String handleValue : setWindowHandles) {
				driver.switchTo().window(handleValue);
				String actPageUrl = driver.getCurrentUrl();
				if (actPageUrl.equalsIgnoreCase(expectPageUrl)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is switched by title :- " + expectPageUrl + " successfully");
		} catch (StaleElementReferenceException e) {
			Set<String> setWindowHandles = driver.getWindowHandles();
			for (String handleValue : setWindowHandles) {
				driver.switchTo().window(handleValue);
				String actPageUrl = driver.getCurrentUrl();
				if (actPageUrl.equalsIgnoreCase(expectPageUrl)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is switched by title :- " + expectPageUrl + " successfully");
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Window is not switched by title :- " + expectPageUrl + " successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Window is not switched by title :- " + expectPageUrl + " successfully");
			throw e;
		}

	}

	//////////// switchToWindowByDynamicTitle \\\\\\\\\\

	public void switchToWindowByDynamicTitle(String dynamicTitle) {

		try {
			Set<String> handleValues = driver.getWindowHandles();
			for (String handleValue : handleValues) {
				driver.switchTo().window(handleValue);
				String actPageTitle = driver.getTitle();
				if (actPageTitle.contains(dynamicTitle)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is switched by title :- " + dynamicTitle + " successfully");
		} catch (StaleElementReferenceException e) {
			Set<String> handleValues = driver.getWindowHandles();
			for (String handleValue : handleValues) {
				driver.switchTo().window(handleValue);
				String actPageTitle = driver.getTitle();
				if (actPageTitle.contains(dynamicTitle)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is switched by title :- " + dynamicTitle + " successfully");
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Window is not switched by title :- " + dynamicTitle + " successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Window is not switched by title :- " + dynamicTitle + " successfully");
			throw e;
		}
	}

	//////////// switchToWindowByDynamicUrl \\\\\\\\\\

	public void switchToWindowByDynamicUrl(String dynamicUrl) {

		try {
			Set<String> handleValues = driver.getWindowHandles();
			for (String handleValue : handleValues) {
				driver.switchTo().window(handleValue);
				String actPageUrl = driver.getCurrentUrl();
				if (actPageUrl.contains(dynamicUrl)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is switched by title :- " + dynamicUrl + " successfully");
		} catch (StaleElementReferenceException e) {
			Set<String> handleValues = driver.getWindowHandles();
			for (String handleValue : handleValues) {
				driver.switchTo().window(handleValue);
				String actPageUrl = driver.getCurrentUrl();
				if (actPageUrl.contains(dynamicUrl)) {
					break;
				}
			}
			statusAndDetails(Status.INFO, "the Window is switched by title :- " + dynamicUrl + " successfully");
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Window is not switched by title :- " + dynamicUrl + " successfully");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the Window is not switched by title :- " + dynamicUrl + " successfully");
			throw e;
		}
	}

	//////////// switchToFrameByWebElement \\\\\\\\\\

	public void switchToFrameByWebElement(WebElement weFrame) {

		// NoSuchFrameException:--This exception occurs when you attempt to switch to a
		// frame
		// that does not exist or cannot be found.
		String elementName = weFrame.getAccessibleName();
		try {
			driver.switchTo().frame(weFrame);
			statusAndDetails(Status.INFO, "the window is switched into the frame " + elementName + "  suceessfully");
		} catch (NoSuchFrameException e) {
			driver.switchTo().frame(weFrame);
			statusAndDetails(Status.INFO, "the window is switched into the frame " + elementName + "  suceessfully");
		} catch (StaleElementReferenceException e) {

			driver.switchTo().frame(weFrame);
			statusAndDetails(Status.INFO, "the window is switched into the frame " + elementName + "  suceessfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO,
					"the window is not switched into the frame " + elementName + "  suceessfully");
			throw e;
		}
	}

	//////////// switchToFrameByIndex \\\\\\\\\\

	public void switchToFrameByIndex(String elementName, int frameIndex) {
		try {
			driver.switchTo().frame(frameIndex);
			statusAndDetails(Status.INFO, "the window is switched into the frame " + elementName + "  suceessfully");
		} catch (NoSuchFrameException e) {
			driver.switchTo().frame(frameIndex);
			statusAndDetails(Status.INFO, "the window is switched into the frame " + elementName + "  suceessfully");
		} catch (StaleElementReferenceException e) {
			driver.switchTo().frame(frameIndex);
			statusAndDetails(Status.INFO, "the window is switched into the frame " + elementName + "  suceessfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO,
					"the window is not switched into the frame " + elementName + "  suceessfully");
			throw e;
		}
	}

	//////////// switchToFrameByNameOrId \\\\\\\\\\

	public void switchToFrameByNameOrId(String NameOrId) {
		try {
			driver.switchTo().frame(NameOrId);
			statusAndDetails(Status.INFO, "the window is switched into the frame by Name or Id  suceessfully");
		} catch (NoSuchFrameException e) {
			driver.switchTo().frame(NameOrId);
			statusAndDetails(Status.INFO, "the window is switched into the frame by Name or Id  suceessfully");
		} catch (StaleElementReferenceException e) {
			driver.switchTo().frame(NameOrId);
			statusAndDetails(Status.INFO, "the window is switched into the frame by Name or Id  suceessfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the window is not switched into the frame by Name or Id  suceessfully");
			throw e;
		}
	}

	//////////// switchToParentFrame \\\\\\\\\\

	public void switchToParentFrame(String elementName) {
		try {
			driver.switchTo().parentFrame();
			statusAndDetails(Status.INFO, "the window is switched into the parentFrame suceessfully");
		} catch (StaleElementReferenceException e) {
			driver.switchTo().parentFrame();
			statusAndDetails(Status.INFO, "the window is switched into the parentFrame suceessfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the window is not switched into the parentFrame suceessfully");
			throw e;
		}
	}

	//////////// switchToMainWindow \\\\\\\\\\

	public void switchToMainWindow(String elementName) {
		try {
			driver.switchTo().defaultContent();
			statusAndDetails(Status.INFO, "the window is switched into the  Main Window suceessfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the window is not switched into the  Main Window suceessfully");
			throw e;
		}
	}

	//////////// popUpAccept \\\\\\\\\\
	/**
	 * with the help of this method we can easily accept the alert message.
	 */
	public void popUpAccept() {

		// NoAlertPresentException: This exception occurs when there is no alert present
		// on the web page
		// at the time you attempt to interact with it..............

		try {
			driver.switchTo().alert().accept();
			statusAndDetails(Status.INFO, "Pop up is accepted successfully");
		} catch (NoAlertPresentException e) {
			WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			statusAndDetails(Status.PASS, " Wait for the alert to be present (timeout for 30 seconds)");
			webWait.until(ExpectedConditions.alertIsPresent());
			statusAndDetails(Status.PASS, "Switch to the alert and perform operations if needed");
			driver.switchTo().alert().accept();
			statusAndDetails(Status.INFO, "Pop up is accepted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Pop up is not accepted successfully");
			throw e;
		}

	}

	//////////// popUpDismiss \\\\\\\\\\
	/**
	 * with the help of this method we can easily dismiss the alert message.
	 */
	public void popUpDismiss() {
		try {
			driver.switchTo().alert().dismiss();
			statusAndDetails(Status.INFO, "Pop up is dismissed successfully");
		} catch (NoAlertPresentException e) {
			WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			statusAndDetails(Status.PASS, " Wait for the alert to be present (timeout for 30 seconds)");
			webWait.until(ExpectedConditions.alertIsPresent());
			statusAndDetails(Status.PASS, "Switch to the alert and perform operations if needed");
			driver.switchTo().alert().dismiss();
			statusAndDetails(Status.PASS, "Pop up is dismissed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Pop up is not dismissed successfully");
			throw e;
		}
	}

	//////////// popUpSendKeys \\\\\\\\\\
	/**
	 * with the help of this method we can easily send value in input box of the
	 * alert to accept or dismiss the alert message.
	 */
	public void popUpSendKeys(String popUpKeyText) {
		try {
			driver.switchTo().alert().sendKeys(popUpKeyText);
			statusAndDetails(Status.INFO, "Pop up is  " + popUpKeyText + "  successfully");
		} catch (NoAlertPresentException e) {
			WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			statusAndDetails(Status.PASS, " Wait for the alert to be present (timeout for 30 seconds)");
			webWait.until(ExpectedConditions.alertIsPresent());
			statusAndDetails(Status.PASS, "Switch to the alert and perform operations if needed");
			driver.switchTo().alert().sendKeys(popUpKeyText);
			statusAndDetails(Status.INFO, "Pop up is  " + popUpKeyText + "  successfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Pop up is not " + popUpKeyText + "  successfully");
			throw e;
		}

	}

	//////////// popUpGetText \\\\\\\\\\

	/**
	 * with the help of this method we can easily get inner text of alert message.
	 */
	///////////////////////// popUpGetText////////////////////////

	public void popUpGetText() {
		try {
			String alertMsg = driver.switchTo().alert().getText();
			statusAndDetails(Status.INFO, "the text of Pop up is found successfully-" + alertMsg);
		} catch (NoAlertPresentException e) {
			WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			statusAndDetails(Status.PASS, " Wait for the alert to be present (timeout for 30 seconds)");
			webWait.until(ExpectedConditions.alertIsPresent());
			statusAndDetails(Status.PASS, "Switch to the alert and perform operations if needed");
			driver.switchTo().alert().getText();
			statusAndDetails(Status.PASS, "the text of Pop up is found successfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "the text of Pop up is not found successfully");
			throw e;
		}

	}

	//////////// myFindElement \\\\\\\\\\

	/**
	 * this method is the most commonaly used to find the element then we perform
	 * any action.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns the webElement
	 */

	//////////// myFindElements \\\\\\\\\\

	/**
	 * this method is used to get multiple weblement as a list of webElement
	 * 
	 * @param xpath it is used to find element which we have to perform operation.
	 * 
	 * @return it returns list of webElements
	 */
	public List<WebElement> myFindElements(String xpath) {
		List<WebElement> webLsts = null;

		try {
			webLsts = driver.findElements(By.xpath(xpath));
			statusAndDetails(Status.INFO, "The List of WebElements is found successfully");
		} catch (InvalidSelectorException e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "The List of WebElements is not found successfully");
			throw e;
		} catch (StaleElementReferenceException e) {
			webLsts = driver.findElements(By.xpath(xpath));
			statusAndDetails(Status.INFO, "The List of WebElements is found successfully");
		} catch (ElementClickInterceptedException e) {
			/**
			 * ElementClickInterceptedException :- it is the child class of
			 * ElementNotInteractableException this type exception occures when the element
			 * is hidden so we can use to overcome this exception then we can use java
			 * script because JavasriptExecutorenters any value or action whether any
			 * element hidden or not hidden in UI.........
			 */
			e.printStackTrace();
			statusAndDetails(Status.INFO, "The List of WebElements is not found successfully");
			throw e;
		} catch (NullPointerException e) {
			webLsts = driver.findElements(By.xpath(xpath));
			statusAndDetails(Status.INFO, "The List of WebElements is  found successfully");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "The List of WebElements is not found successfully");
			throw e;
		}

		return webLsts;

	}

	//////////// getTableRowCount \\\\\\\\\\
	/**
	 * this method is used to obtain row count of the table as a int.
	 * 
	 * @param tableXpath with the help table xpath we can find those element that
	 *                   comes under the table as multiple column list name
	 * 
	 */
	public int getTableRowCount(String tableXpath, String elementName) {

		List<WebElement> webList = myFindElements(tableXpath + "//tr");
		int countRow = -1;
		if (webList.isEmpty() == false) {
			try {
				countRow = webList.size();
				statusAndDetails(Status.PASS, "total row count of leads table is =" + countRow);
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.FAIL, "total row count of leads table is not counted");
				throw e;
			}

		} else {
			statusAndDetails(Status.INFO, "List is empty there is no any element");
		}
		return countRow;

	}

	//////////// getTableColumnCount \\\\\\\\\\
	/**
	 * this method is used to obtain column count of the table as a int.
	 * 
	 * @param tableXpath with the help table xpath we can find those element that
	 *                   comes under the table as multiple column list name
	 * 
	 */
	public int getTableColumnHeaderCount(String tableXpath, String elementName) {

		List<WebElement> webListColumns = myFindElements(tableXpath + "//tr[1]//td");
		int countColumn = -1;
		if (webListColumns.isEmpty() == false) {
			try {
				countColumn = webListColumns.size();
				statusAndDetails(Status.PASS,
						"total column count of " + elementName + " leads table is =" + countColumn);
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.FAIL, "total column count of " + elementName + " leads table is not count");
				throw e;
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there is no any element");
		}
		return countColumn;
	}

	//////////// getTableColumnNamesList \\\\\\\\\\

	/**
	 * this method is used to obtain column name of table as a list
	 * 
	 * @param tableXpath  with the help table xpath we can find those element that
	 *                    comes under the table as multiple column list name
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it will return list of webElements
	 */
	public List<String> getTableColumnNamesList(String tableXpath, String elementName) {

		List<WebElement> weListColumns = myFindElements(tableXpath);
		String columnName = "";
		List<String> listColumnNames = new ArrayList<>();
		if (weListColumns.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 0; i < weListColumns.size(); i++) {
					WebElement weColumnHeader = weListColumns.get(i);
					columnName = weColumnHeader.getText();
					listColumnNames.add(columnName);
				}
				statusAndDetails(Status.INFO, "the list of column name is found from the table successfully");

			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "the list of column name is not found from the table successfully");
				throw e;
			}

		} else {
			statusAndDetails(Status.INFO, "List is empty there is no any element");
		}
		return listColumnNames;
	}
	//////////// getColumnHeaderNumberByColumnName \\\\\\\\\\

	/**
	 * @param tableXpath
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public int getColumnHeaderNumberByColumnName(String tableXpath, String tableName, String columnName) {
		List<WebElement> columnNamesList = myFindElements(tableXpath + "//tr[1]//td");
		int columnNumber = -1;
		if (columnNamesList.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 0; i < columnNamesList.size(); i++) {
					WebElement webColumnNames = columnNamesList.get(i);
					String actcolumnName = webColumnNames.getText();
					if (actcolumnName.equalsIgnoreCase(columnName)) {
						columnNumber = i + 1;
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"column header number of the list is found by column name so column header number="
								+ columnNumber);

			} catch (StaleElementReferenceException e) {
				columnNamesList = myFindElements(tableXpath + "//tr[1]//td");
				for (int i = 0; i < columnNamesList.size(); i++) {
					WebElement webColumnNames = columnNamesList.get(i);
					String actcolumnName = webColumnNames.getText();
					if (actcolumnName.equalsIgnoreCase(columnName)) {
						columnNumber = i + 1;
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"column header number of the list is found by column name so column header number="
								+ columnNumber);
			} catch (ElementNotInteractableException e) {
				columnNamesList = myFindElements(tableXpath + "//tr[1]//td");
				// Use JavascriptExecutor to execute JavaScript code and get the inner text
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				for (int i = 0; i < columnNamesList.size(); i++) {
					WebElement webColumnNames = columnNamesList.get(i);
					String script = "return arguments[0].innerText;";
					String actcolumnName = (String) jsExecutor.executeScript(script, webColumnNames);
					if (actcolumnName.equalsIgnoreCase(columnName)) {
						columnNumber = i + 1;
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"column header number of the list is found by column name so column header number="
								+ columnNumber);
				return columnNumber;
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO,
						"column header number of the list is not found by column name successfully");
			}
		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");
		}
		return columnNumber;
	}

	//////////// getColumnHeaderNameByColumnNumber \\\\\\\\\\

	/**
	 * @param tableXpath
	 * @param tableName
	 * @param number
	 * @return
	 */
	public String getColumnHeaderNameByColumnNumber(String tableXpath, String tableName, int number) {
		List<WebElement> columnNamesList = myFindElements(tableXpath + "//tr[1]//td");
		String columnHeaderName = "";
		if (columnNamesList.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements successfully");
			try {
				for (int i = 0; i < columnNamesList.size(); i++) {
					if (i == number - 1) {
						columnHeaderName = columnNamesList.get(i).getText();
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"Column Header Name of the list is found by column number hence Column Header Name ="
								+ columnHeaderName);
			} catch (StaleElementReferenceException e) {
				columnNamesList = myFindElements(tableXpath + "//tr[1]//td");
				for (int i = 0; i < columnNamesList.size(); i++) {
					if (i == number - 1) {
						columnHeaderName = columnNamesList.get(i).getText();
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"Column Header Name of the list is found by column number hence Column Header Name ="
								+ columnHeaderName);
				return columnHeaderName;
			} catch (ElementNotInteractableException e) {
				columnNamesList = myFindElements(tableXpath + "//tr[1]//td");
				// Use JavascriptExecutor to execute JavaScript code and get the inner text
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				for (int i = 0; i < columnNamesList.size(); i++) {
					if (i == number - 1) {
						WebElement webcolumnHeaderName = columnNamesList.get(i);
						String script = "return arguments[0].innerText;";
						columnHeaderName = (String) jsExecutor.executeScript(script, webcolumnHeaderName);
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"Column Header Name of the list is found by column number hence Column Header Name ="
								+ columnHeaderName);

				return columnHeaderName;
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO,
						"Column Header Name of the list is not found by column number successfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");
		}
		return columnHeaderName;
	}

	//////////// getColumnNameByColumnNumberOfHeader \\\\\\\\\\

	/**
	 * @param tableXpath
	 * 
	 * @param tableName
	 * 
	 * @param number
	 * 
	 * @return
	 */
	public String getColumnNameByColumnNumberOfHeader(String tableXpath, String tableName, int number) {

		List<WebElement> columnNamesList = myFindElements(tableXpath + "//tr[" + number + "]//td");
		String columnName = "";
		if (columnNamesList.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 0; i < columnNamesList.size(); i++) {
					if (i == number - 1) {
						columnName = columnNamesList.get(i).getText();
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"Column Name of the list is found by column number hence Column Name =" + columnName);
			} catch (StaleElementReferenceException e) {
				columnNamesList = myFindElements(tableXpath + "//tr[" + number + "]//td");
				for (int i = 0; i < columnNamesList.size(); i++) {
					if (i == number - 1) {
						columnName = columnNamesList.get(i).getText();
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"Column Name of the list is found by column number hence Column Name =" + columnName);
				return columnName;
			} catch (ElementNotInteractableException e) {
				columnNamesList = myFindElements(tableXpath + "//tr[" + number + "]//td");
				// Use JavascriptExecutor to execute JavaScript code and get the inner text
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				for (int i = 0; i < columnNamesList.size(); i++) {
					if (i == number - 1) {
						WebElement webcolumnHeaderName = columnNamesList.get(i);
						String script = "return arguments[0].innerText;";
						columnName = (String) jsExecutor.executeScript(script, webcolumnHeaderName);
						break;
					}
				}
				statusAndDetails(Status.INFO,
						"Column Name of the list is found by column number hence Column Name =" + columnName);
				return columnName;
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO,
						"Column Header Name of the list is not found by column number sucessfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");
		}
		return columnName;
	}
	//////////// getRowDetailsByRowNummber \\\\\\\\\\

	/**
	 * @param tableXpath
	 * @param tableName
	 * @param rowNumber
	 * @return
	 */
	public List<String> getRowDetailsByRowNummber(String tableXpath, String tableName, int rowNumber) {

		List<WebElement> rowDetailsList = myFindElements(tableXpath + "//tr[" + (rowNumber + 1) + "]//td");
		List<String> strList = new ArrayList<>();
		if (rowDetailsList.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 0; i < rowDetailsList.size(); i++) {
					String rowDetail = rowDetailsList.get(i).getText();
					strList.add(rowDetail);
				}
				statusAndDetails(Status.INFO, "Get row details of the list is found by row number sucessfully");
			} catch (StaleElementReferenceException e) {
				rowDetailsList = myFindElements(tableXpath + "//tr[" + (rowNumber + 1) + "]//td");
				for (int i = 0; i < rowDetailsList.size(); i++) {
					String rowDetail = rowDetailsList.get(i).getText();
					strList.add(rowDetail);
				}
				statusAndDetails(Status.INFO, "Get row details of the list is found by row number sucessfully");
				return strList;
			} catch (ElementNotInteractableException e) {
				rowDetailsList = myFindElements(tableXpath + "//tr[" + (rowNumber + 1) + "]//td");
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				for (int i = 0; i < rowDetailsList.size(); i++) {
					WebElement webRowDetail = rowDetailsList.get(i);
					String script = "return arguments[0].innerText;";
					String rowDetail = (String) jsExecutor.executeScript(script, webRowDetail);
					strList.add(rowDetail);
				}
				statusAndDetails(Status.INFO, "Get row details of the list is found by row number sucessfully");
				return strList;
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "Get row details of the list is not found by row number sucessfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");
		}
		return strList;
	}

	//////////// getRowDetailsByRowNummber \\\\\\\\\\

	/**
	 * @param tableXpath
	 * 
	 * @param tableName
	 * 
	 * @param columnNumber
	 * 
	 * @return
	 */
	public List<String> getColumnDataListByColumnNumber(String tableXpath, String tableName, int columnNumber) {

		List<WebElement> columnsList = myFindElements(tableXpath + "//tr//td[" + columnNumber + "]");
		List<String> columnNameList = new ArrayList<>();
		if (columnsList.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 1; i < columnsList.size(); i++) {
					String columnName = columnsList.get(i).getText();
					columnNameList.add(columnName);
				}
				statusAndDetails(Status.INFO, "Get column details of the list is found by column number sucessfully");
			} catch (StaleElementReferenceException e) {
				columnsList = myFindElements(tableXpath + "//tr//td[" + columnNumber + "]");
				for (int i = 1; i < columnsList.size(); i++) {
					String columnName = columnsList.get(i).getText();
					columnNameList.add(columnName);
				}
				statusAndDetails(Status.INFO, "Get column details of the list is found by column number sucessfully");
				return columnNameList;
			} catch (ElementNotInteractableException e) {
				columnsList = myFindElements(tableXpath + "//tr//td[" + columnNumber + "]");
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				for (int i = 0; i < columnsList.size(); i++) {
					WebElement webColumnDetail = columnsList.get(i);
					String script = "return arguments[0].innerText;";
					String rowDetail = (String) jsExecutor.executeScript(script, webColumnDetail);
					columnNameList.add(rowDetail);
				}
				statusAndDetails(Status.INFO, "Get column details of the list is found by column number sucessfully");
				return columnNameList;
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO,
						"Get column details of the list is not found by column number sucessfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");
		}
		return columnNameList;
	}

	//////////// getColumnDataListByColumnName \\\\\\\\\\

	public List<String> getColumnDataListByColumnName(String tableXpath, String tableName, String columnName) {

		int columnNumber = getColumnHeaderNumberByColumnName(tableXpath, tableName, columnName);
		List<String> rowDetailsList = getColumnDataListByColumnNumber(tableXpath, tableName, columnNumber);

		return rowDetailsList;
	}

	//////////// getRowNumberByUniqueColumnRowID \\\\\\\\\\

	public int getRowNumberByUniqueColumnRowID(String tableXpath, String tableName, String uniquColumnName,
			String uniqueColumnName) {
		List<String> columnDtailsList = getColumnDataListByColumnName(tableXpath, tableName, uniquColumnName);
		int rowNumber = -1;
		if (columnDtailsList.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 0; i < columnDtailsList.size(); i++) {
					String columnData = columnDtailsList.get(i);
					if (columnData.equalsIgnoreCase(uniqueColumnName)) {
						rowNumber = i + 1;
						break;
					}
				}
				statusAndDetails(Status.PASS,
						"Get row number of the list is found by row id sucessfully so row number=" + rowNumber);
			} catch (StaleElementReferenceException e) {
				columnDtailsList = getColumnDataListByColumnName(tableXpath, tableName, uniquColumnName);
				for (int i = 0; i < columnDtailsList.size(); i++) {
					String columnData = columnDtailsList.get(i);
					if (columnData.equalsIgnoreCase(uniqueColumnName)) {
						rowNumber = i + 1;
						break;
					}
				}
				statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");

			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "Get row details of the list is not found by row number sucessfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");
		}
		return rowNumber;

	}

	//////////// getRowDataListByRowID \\\\\\\\\\

	/**
	 * with the help of this method we can find row detaills by using row id so
	 * first of all we will know row number by using row id after that we will know
	 * row details by row number.
	 * 
	 * @param tableXpath
	 * 
	 * @param tableName        we require table name to print a proper message to
	 *                         the console and this shows in which element we are
	 *                         working......
	 *
	 * @param uniqueData       with the help of this i.e uniqueData we can find
	 *                         those unique data that have a unique id.
	 * 
	 * @param uniqueColumnName
	 * 
	 * @return it returns List of String i.e List<String>
	 */
	public List<String> getRowDataListByRowID(String tableXpath, String tableName, String uniqueData,
			String uniqueColumnName) {
		int rowNumber = getRowNumberByUniqueColumnRowID(tableXpath, tableName, uniqueData, uniqueColumnName);
		List<String> rowDetails = getRowDetailsByRowNummber(tableXpath, tableName, rowNumber);
		return rowDetails;
	}
	//////////// printAllTableData \\\\\\\\\\

	/**
	 * this method is commonaly used to print all the data from the table.
	 * 
	 * @param tableXpath with the help of the table xpath we can find all the data
	 *                   frome the table because table xpath is concatinate with
	 *                   '//tr' so full xpath of the taable is [tableXpath+"//tr"]
	 * 
	 * @param tableName  we require table name to print a proper message to the
	 *                   console and this shows in which element we are
	 *                   working......
	 */
	public void printAllTableData(String tableXpath, String tableName) {
		List<WebElement> allData = myFindElements(tableXpath + "//tr");
		if (allData.isEmpty() == false) {
			statusAndDetails(Status.PASS, "List is not empty there are available multiple elements");
			try {
				for (int i = 0; i < allData.size(); i++) {
					String text = allData.get(i).getText();
					System.out.println(text);
				}
				statusAndDetails(Status.INFO, "Get  all data of the list is found sucessfully");
			} catch (StaleElementReferenceException e) {
				allData = myFindElements(tableXpath + "//tr");
				for (int i = 0; i < allData.size(); i++) {
					String text = allData.get(i).getText();
					System.out.println(text);
				}
				statusAndDetails(Status.INFO, "Get  all data of the list is found sucessfully");
			} catch (ElementNotInteractableException e) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				allData = myFindElements(tableXpath + "//tr");
				for (int i = 0; i < allData.size(); i++) {
					WebElement weAllData = allData.get(i);
					String script = "return arguments[0].innerText;";
					String allElement = (String) jsExecutor.executeScript(script, weAllData);
					System.out.println(allElement);
				}
				statusAndDetails(Status.INFO, "Get  all data of the list is found sucessfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.FAIL, "Get  all data of the list is found sucessfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "List is empty there are not available multiple elements");

		}
	}

	//////////// mySendKeys \\\\\\\\\\

	/**
	 * this method is commonaly used to send value in input box or input field.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @param inputValue  we have to send the input value in the textbox or input
	 *                    field
	 * 
	 * @throws ElementClickInterceptedException this type exception occures when the
	 *                                          element is hidden so we can use to
	 *                                          overcome this exception hen we can
	 *                                          use java script because
	 *                                          JavasriptExecutorenters any value or
	 *                                          action whether any element hidden or
	 *                                          not hidden in UI.........
	 * 
	 */
	public void sendKeys(WebElement webObj, String inputValue) throws ElementClickInterceptedException {

		// WebElement
		String elementName = webObj.getAccessibleName();
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is Displayed and enabled ");
			try {
				webObj.sendKeys(inputValue);
				statusAndDetails(Status.INFO,
						inputValue + " value is passed in " + elementName + " textbox successfully");
			} catch (ElementNotInteractableException e) {

				/**
				 * ElementNotInteractableException :- this type exception occures when the
				 * element is hidden so we can use to overcome this exception then we can use
				 * java script because JavasriptExecutorenters any value or action whether any
				 * element hidden or not hidden in UI.........
				 */
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].value='" + inputValue + "'", webObj);
				statusAndDetails(Status.INFO,
						inputValue + " value is passed in " + elementName + " textbox successfully");
			} catch (StaleElementReferenceException e) {
				webObj = driver.findElement(By.xpath("//input[@name='" + inputValue + "']"));
				statusAndDetails(Status.PASS, "we have found " + elementName + " successfully");
				webObj.sendKeys(inputValue);
				statusAndDetails(Status.INFO,
						inputValue + " value is passed in " + elementName + " textbox successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO,
						inputValue + " value is not passed in " + elementName + " textbox successfully");
				throw e;
			}

		} else {
			statusAndDetails(Status.FAIL, inputValue + " Element is Displayed and enabled");
		}
	}

	//////////// getAttributeValue \\\\\\\\\\

	/**
	 * this method is used to find the attribute value with the help of attribute
	 * name.
	 * 
	 * @param xpath         it is used to find element which we have to perform
	 *                      operation.
	 * 
	 * @param elementName   this shows in which element we are working and print a
	 *                      proper message in the console.
	 * 
	 * @param attributeName we have to find the attribute value from attribute name
	 *                      in the form of string formate
	 * 
	 * @return it returns String i.e attrubute value as a string
	 * 
	 * @throws ElementClickInterceptedException this type exception occures when the
	 *                                          element is hidden so we can use to
	 *                                          overcome this exception hen we can
	 *                                          use java script because
	 *                                          JavasriptExecutorenters any value or
	 *                                          action whether any element hidden or
	 *                                          not hidden in UI.........
	 * 
	 */
	public String getAttributeValue(WebElement webObj, String attributeName) throws ElementClickInterceptedException {
		String elementName = webObj.getAccessibleName();

		String attributeValue = null;
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.INFO, "Element is Displayed and enabled ");
			try {
				attributeValue = webObj.getAttribute(attributeName);
				statusAndDetails(Status.PASS, attributeName + " Attribute value of " + elementName + " :-"
						+ attributeValue + " is found successfully ");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute JavaScript code and get the attribute value
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				// Replace with the actual attribute name you want to retrieve
				String script = "return arguments[0].getAttribute('" + attributeName + "');";
				attributeValue = (String) jsExecutor.executeScript(script, webObj);
				statusAndDetails(Status.PASS, attributeName + " Attribute value of " + elementName + " :-"
						+ attributeValue + " is found successfully ");
				return attributeValue;
			} catch (StaleElementReferenceException e) {
				// StaleElementReferenceException :---when the page has been updated or
				// refressed then
				// this type of exception will get....

				attributeValue = webObj.getAttribute(attributeName);
				statusAndDetails(Status.PASS, attributeName + " Attribute value of " + elementName + " :-"
						+ attributeValue + " is found successfully ");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, attributeName + " Attribute value of " + elementName + " :-"
						+ attributeValue + " is not found successfully ");
				throw e;
			}

		} else {
			statusAndDetails(Status.FAIL, "Element is not Displayed and enabled ");
		}

		return attributeValue;
	}

	//////////// myClear \\\\\\\\\\
	/**
	 * The myClear() method is commonly used to clear to the textbox or input field
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	public void myClear(WebElement webObj) {
		String elementName = webObj.getAccessibleName();
		statusAndDetails(Status.INFO, "we have found " + elementName + " successfully");

		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is Displayed and enabled ");

			try {
				webObj.clear();
				statusAndDetails(Status.PASS, elementName + " textbox is cleared successfully");

			} catch (ElementNotInteractableException e) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].value=''", webObj);
				statusAndDetails(Status.PASS, elementName + " textbox is cleared successfully");
			} catch (StaleElementReferenceException e) {

				statusAndDetails(Status.PASS, "we have found " + elementName + " successfully");
				webObj.clear();
				statusAndDetails(Status.PASS, elementName + " textbox is cleared successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, elementName + " textbox is  not cleared successfully");
				throw e;
			}

		} else {

			statusAndDetails(Status.FAIL, "Element is not Displayed and enabled ");

		}

	}

	//////////// myClick \\\\\\\\\\

	/**
	 * The myClick() method is commonly used to click to the element
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	public void click(WebElement webObj) {
		String elementName = webObj.getAccessibleName();
		if (webObj.isDisplayed() && webObj.isEnabled()) {

			statusAndDetails(Status.PASS, "Element is Displayed and enabled");
			try {
				webObj.click();
				statusAndDetails(Status.INFO, elementName + " element is clicked successfully");

			} catch (ElementClickInterceptedException e) {

				JavascriptExecutor js = (JavascriptExecutor) driver;

				js.executeScript("arguments[0].click()", webObj);
				statusAndDetails(Status.INFO, elementName + " element is clicked successfully");

				throw e;
			} catch (StaleElementReferenceException e) {

				webObj.click();
				statusAndDetails(Status.INFO, elementName + " element is clicked successfully");

			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.FAIL, elementName + " element is not clicked successfully");
				throw e;

			}
		} else {
			statusAndDetails(Status.PASS, "Element is not Displayed and enabled ");

		}
	}

	//////////// checkAllCheckBoxes \\\\\\\\\\
	/**
	 * this method is used to check all the check boxes whether they are already
	 * checked or unchecked.
	 * 
	 * @param xpath it is used to find element which we have to perform operation.
	 */
	public void checkAllCheckBoxes(String xpath) {
		List<WebElement> webList = myFindElements(xpath);

		if (webList.isEmpty() == false) {
			try {
				for (int i = 0; i < webList.size(); i++) {
					WebElement web = webList.get(i);
					if (web.isSelected() == false) { // isSelected---checkbox or radio button is selected or not
						try {
							web.click();
						} catch (ElementNotInteractableException e) {
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", web);
						}
					}
					statusAndDetails(Status.PASS, "All the checkBoxes is checked successfully");
				}

			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "All the checkBoxes is not checked successfully");

			}

			statusAndDetails(Status.PASS, "this List is not empty");

		} else {
			statusAndDetails(Status.FAIL, "this List is empty");
		}

	}

	//////////// uncheckAllCheckBoxes \\\\\\\\\\

	/**
	 * this method is used to uncheck all the check boxes whether they are already
	 * checked or unchecked.
	 * 
	 * @param xpath it is used to find the element which we have to perform
	 *              operation.
	 */
	public void uncheckAllCheckBoxes(String xpath) {

		List<WebElement> webList = myFindElements(xpath);

		if (webList.isEmpty() == false) {
			try {
				for (int i = 0; i < webList.size(); i++) {
					WebElement web = webList.get(i);
					if (web.isSelected() == true) { // isSelected---checkbox or radio button is selected or not
						try {
							web.click();
						} catch (ElementNotInteractableException e) {
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].click()", web);
						}
					}
					statusAndDetails(Status.PASS, "All the checkBoxes is unchecked successfully");
				}

			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "All the checkBoxes is checked successfully");

			}

			statusAndDetails(Status.PASS, "this List is not empty");

		} else {
			statusAndDetails(Status.FAIL, "this List is empty");
		}

	}

	//////////// myInnerText \\\\\\\\\\
	/**
	 * The myInnerText() method is commonly used to find the inner text of any
	 * element.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 */
	public String myInnerText(WebElement webObj) {
		String innerText = null;
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and enabled");
			try {
				innerText = webObj.getText();
				statusAndDetails(Status.INFO, "Inner Text :-" + innerText + " is Found Successfully");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute JavaScript code and get the inner text
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				String script = "return arguments[0].innerText;";
				innerText = (String) jsExecutor.executeScript(script, webObj);
				statusAndDetails(Status.INFO, "Inner Text :-" + innerText + " is Found Successfully");
				return innerText;
			} catch (StaleElementReferenceException e) {

				innerText = webObj.getText();
				statusAndDetails(Status.INFO, "Inner Text :-" + innerText + " is Found Successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "Inner Text :-" + innerText + " is not Found Successfully");
				throw e;
			}
		} else {
			statusAndDetails(Status.FAIL, "Element is displayed and enabled");
		}
		return innerText;
	}

	//////////// getXAndYOffSet \\\\\\\\\\
	/**
	 * The getXAndYOffSet() method is commonly used to find the location of any
	 * element that means x offset and y offset.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns the object of Point class.
	 * 
	 */
	public Point getXAndYOffSet(WebElement webObj) {

		Point location = null;
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and enabled");
			try {
				location = webObj.getLocation();
				statusAndDetails(Status.INFO, "the location of the element is found successfully");
			} catch (StaleElementReferenceException e) {

				location = webObj.getLocation();
				statusAndDetails(Status.INFO, "the location of the element is found successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "the location of the element is not found successfully");
				throw e;
			}
		} else {
			statusAndDetails(Status.FAIL, "Element is displayed and enabled");
		}
		return location;
	}

	//////////// getHeightAndWidth \\\\\\\\\\
	/**
	 * The getHeightAndWidth() method is commonly used to find height and weidth of
	 * any element.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns the object of Dimension class.
	 * 
	 */

	public Dimension getHeightAndWidth(WebElement webObj) {

		Dimension size = null;
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and enabled");
			try {
				size = webObj.getSize();
				statusAndDetails(Status.INFO, "the size of the element is found successfully");
			} catch (StaleElementReferenceException e) {

				size = webObj.getSize();
				statusAndDetails(Status.INFO, "the size of the element is found successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "the size of the element is not found successfully");
				throw e;
			}
		} else {
			statusAndDetails(Status.FAIL, "Element is displayed and enabled");
		}
		return size;
	}

	public void verifyTitle(String expTitle) {
		String actPageTitle = getPageTitle();
		if (actPageTitle.equalsIgnoreCase(expTitle)) {
			webUtil.statusAndDetails(Status.PASS, "Passed. Actual-" + actPageTitle + " && Expected-" + expTitle);
		} else {
			webUtil.statusAndDetails(Status.FAIL, "Failed. Actual-" + actPageTitle + " && Expected-" + expTitle);
		}
		Assert.assertEquals(actPageTitle, expTitle);

	}

	public void verifyUrl(String expectedUrl) {
		String actualUrl = getPresentUrl();
		if (actualUrl.equalsIgnoreCase(expectedUrl)) {
			webUtil.statusAndDetails(Status.PASS, "Passed. Actual-" + actualUrl + " && Expected-" + expectedUrl);
		} else {
			webUtil.statusAndDetails(Status.FAIL, "Failed. Actual-" + actualUrl + " && Expected-" + expectedUrl);
		}
		Assert.assertEquals(actualUrl, expectedUrl);

	}

	//////////// verifyString \\\\\\\\\\

	/**
	 * it is used to verify two String that is same or not.
	 * 
	 * @param actualString   it refers to the actual String.
	 * 
	 * @param expectedString it refers to the expected String.
	 */

	//////////////////// takeSnapshot////////////////////////////

	public String takeSnapshot(String testcaseName) {
		TakesScreenshot tss = (TakesScreenshot) driver;
		File snapshotSourceFileObj = tss.getScreenshotAs(OutputType.FILE);
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy hh_mm_ss a");
		String timeStamp = df.format(new Date());
		File snapshotDestinationFileObj = new File("test-output\\" + testcaseName + timeStamp + ".png");
		try {
			Files.copy(snapshotSourceFileObj, snapshotDestinationFileObj);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return snapshotDestinationFileObj.getAbsolutePath();
	}

	public void verifText(String actualString, String expectedString, String message) {
		if (actualString.equalsIgnoreCase(expectedString)) {
			statusAndDetails(Status.PASS,
					"the actual :-" + actualString + " && expected :-" + expectedString + " both are same");
		} else {

			statusAndDetails(Status.FAIL,
					"the actual :-" + actualString + " && expected :-" + expectedString + " both are not same");
		}
		Assert.assertEquals(actualString, expectedString, message);
	}

	//////////// verifyInt \\\\\\\\\\

	/**
	 * it is used to verify two integer number that is same or not.
	 * 
	 * @param actInt it refers to the actual number.
	 * 
	 * @param expInt it refers to the expected number.
	 */
	public void verifyInt(int actInt, int expInt) {

		if (actInt == expInt) {
			statusAndDetails(Status.PASS,
					"the actual integer :-" + actInt + " and expected integer :-" + expInt + " both are same");
		} else {
			statusAndDetails(Status.FAIL,
					"the actual integer :-" + actInt + " and expected integer :-" + expInt + " both are not same");
		}
		Assert.assertEquals(actInt, expInt, "It is also verified by Assertion");
	}

	//////////// checkDisplayed \\\\\\\\\\
	/**
	 * The checkDisplayed() method is commonly used to check the element that is
	 * display or not
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns boolean i.e true or false
	 * 
	 */
	public boolean checkDisplayed(WebElement webObj) {

		boolean status = false;
		try {
			status = webObj.isDisplayed();
			statusAndDetails(Status.INFO, "Element is displayed");
		} catch (StaleElementReferenceException e) {

			status = webObj.isDisplayed();
			statusAndDetails(Status.INFO, "Element is displayed");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.INFO, "Element is not displayed");
			throw e;

		}
		return status;
	}

	public void verifyElementVisibility(WebElement we) {
		boolean actStatus = checkDisplayed(we);
		if (actStatus == true) {
			statusAndDetails(Status.PASS, "Passed !,The Element is Displayed");
		} else {
			statusAndDetails(Status.FAIL, "Failed !,The Element is Displayed");
		}
		Assert.assertTrue(actStatus, "It is also verified By Assertion");

	}

	public void verifySelectedDropOrRadioBT(WebElement we) {
		boolean actStatus = checkSelected(we);
		if (actStatus == true) {
			statusAndDetails(Status.PASS, "Passed !,The Element is Selected");
		} else {
			statusAndDetails(Status.FAIL, "Failed !,The Element is Selected");
		}
		Assert.assertTrue(actStatus, "It is also verified By Assertion");

	}

	public void verifyEnabled(WebElement we) {
		boolean actStatus = checkEnabled(we);
		if (actStatus == true) {
			statusAndDetails(Status.PASS, "Passed !,The Element is Enabled");
		} else {
			statusAndDetails(Status.FAIL, "Failed !,The Element is Enabled");
		}
		Assert.assertTrue(actStatus, "It is also verified By Assertion");

	}

	//////////// checkEnabled \\\\\\\\\\
	/**
	 * The checkEnabled() method is commonly used to check the element that is
	 * enable or not
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns boolean i.e true or false
	 * 
	 */
	public boolean checkEnabled(WebElement webObj) {
		boolean status = false;
		try {
			status = webObj.isEnabled();
			statusAndDetails(Status.INFO, "Element is enabled");
		} catch (ElementNotInteractableException e) {
			WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			webWait.until(ExpectedConditions.visibilityOf(webObj));
		} catch (StaleElementReferenceException e) {

			status = webObj.isEnabled();
			statusAndDetails(Status.INFO, "Element is enabled");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.FAIL, "Element is not enabled");
			throw e;

		}
		return status;

	}

	//////////// isSelected \\\\\\\\\\

	/**
	 * The isSelected() method is commonly used with radio buttons, checkboxes, and
	 * options in a select dropdown.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns boolean i.e true or false
	 * 
	 */
	public boolean checkSelected(WebElement webObj) {

		// The isSelected() method is commonly used with radio buttons, checkboxes,
		// and options in a select dropdown.

		boolean status = false;
		if (webObj.isDisplayed() && webObj.isEnabled()) {

			statusAndDetails(Status.PASS, "Element is displayed and enabked");

			try {
				status = webObj.isSelected();
				statusAndDetails(Status.PASS,
						"textBox or radio button or option in select dropDown is selected successfilly");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute JavaScript code and check if the element is
				// selected
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				status = (boolean) jsExecutor.executeScript("return arguments[0].selected;", webObj);
				statusAndDetails(Status.PASS,
						"textBox or radio button or option in select dropDown is selected successfilly");
				return status;
			} catch (StaleElementReferenceException e) {

				status = webObj.isSelected();
				statusAndDetails(Status.PASS,
						"textBox or radio button or option in select dropDown is selected successfilly");

			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS,
						"textBox or radio button or option in select dropDown is not selected successfilly");

			}

		} else {
			statusAndDetails(Status.FAIL, "Element is  not displayed and enabked");

		}

		return status;
	}

	//////////// selectByValueAttribute \\\\\\\\\\

	/**
	 * with the help of this method we can select the element by it's value
	 * attributes from drop down.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @param value       it is used to select by value attribute
	 */
	public void selectByValueAttribute(WebElement webObj, String value) {

		Select slct = new Select(webObj);

		if (webObj.isDisplayed() && webObj.isEnabled()) {

			statusAndDetails(Status.PASS, "Element is Displayed and enabled");
			try {
				slct.selectByValue(value);
				statusAndDetails(Status.INFO, "you have selected the element in dropDown by it's value :--" + value);
			} catch (ElementNotInteractableException eo) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].value='" + value + "'", webObj);
				statusAndDetails(Status.INFO, "you have selected the element in dropDown by it's value :--" + value);
			} catch (StaleElementReferenceException e) {

				slct.selectByValue(value);
				statusAndDetails(Status.INFO, "you have selected the element in dropDown by it's value :--" + value);
			} catch (NullPointerException e) {

				slct.selectByValue(value);
				statusAndDetails(Status.INFO, "you have selected the element in dropDown by it's value :--" + value);
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "you haven't selected the element in dropDown by it's value :--" + value);
				throw e;

			}
		} else {
			statusAndDetails(Status.FAIL, "Element is not Displayed and enabled ");
		}
	}

	//////////// selectByIndexNumber \\\\\\\\\\
	/**
	 * it is used to select that element with the help of it's index number from
	 * drop down the index number is starts 0 to number-1
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @param indexNumber it refers to the index number of drop down
	 */
	public void selectByIndexNumber(WebElement webObj, int indexNumber) {

		Select slct = new Select(webObj);

		if (webObj.isDisplayed() && webObj.isEnabled()) {

			statusAndDetails(Status.PASS, "Element is Displayed and enabled");
			try {
				slct.selectByIndex(indexNumber);
				statusAndDetails(Status.INFO,
						"you have selected the element in dropDown by it's index number :--" + indexNumber);
			} catch (ElementNotInteractableException eo) {
				String script = "arguments[0].selectedIndex = " + indexNumber + "; "
						+ "arguments[0].dispatchEvent(new Event('change'));";
				((JavascriptExecutor) driver).executeScript(script, webObj);
				statusAndDetails(Status.INFO,
						"you have selected the element in dropDown by it's index number :--" + indexNumber);

			} catch (StaleElementReferenceException e) {

				slct.selectByIndex(indexNumber);
				statusAndDetails(Status.INFO,
						"you have selected the element in dropDown by it's index number :--" + indexNumber);

			} catch (NullPointerException e) {

				slct.selectByIndex(indexNumber);
				statusAndDetails(Status.INFO,
						"you have selected the element in dropDown by it's index number :--" + indexNumber);
			} catch (Exception e) {

				e.printStackTrace();
				statusAndDetails(Status.INFO,
						"you haven't selected the element in dropDown by it's index number :--" + indexNumber);
				throw e;

			}
		} else {
			statusAndDetails(Status.FAIL, "Element is not Displayed and enabled ");
		}
	}

	//////////// selectByVisibleString \\\\\\\\\\

	/**
	 * it is used to select that element with the help of inner text or that we are
	 * seeing the element in UI.
	 * 
	 * @param xpath         it is used to find element which we have to perform
	 *                      operation.
	 * 
	 * @param elementName   this shows in which element we are working and print a
	 *                      proper message in the console.
	 * 
	 * @param visibleString it refers to String as a text
	 */
	public void selectByVisibleString(WebElement webObj, String visibleString) {

		if (webObj.isDisplayed() && webObj.isEnabled()) {

			Select slct = new Select(webObj);
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				slct.selectByVisibleText(visibleString);
				statusAndDetails(Status.INFO,
						"you have selected the element in dropDown by it's visible String :--" + visibleString);
			} catch (StaleElementReferenceException e) {

				slct.selectByVisibleText(visibleString);
				statusAndDetails(Status.INFO,
						"you have selected the element in dropDown by it's visible String :--" + visibleString);
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO,
						"you haven't selected the element in dropDown by it's visible String :--" + visibleString);
				throw e;
			}

		} else {
			statusAndDetails(Status.FAIL, "Element is not displayed and Enabled");

		}
	}

	/**
	 * this method is used to Fetch the element that is by defult selected in the
	 * dropDown with the help of getFirstSelectedElement method.
	 * 
	 * @param xpath       it is used to find element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	//////////// getFirstSelectedElement \\\\\\\\\\

	public String getFirstSelectedElement(WebElement webObj) {

		String elementName = webObj.getAccessibleName();

		Select slt = new Select(webObj);
		String firstSelected = null;
		try {
			firstSelected = slt.getFirstSelectedOption().getText();
			statusAndDetails(Status.PASS, "Fetch the first selected element :- " + firstSelected + " of " + elementName
					+ " dropDown  successfully");
		} catch (StaleElementReferenceException e) {

			firstSelected = slt.getFirstSelectedOption().getText();
			statusAndDetails(Status.PASS, "Fetch the first selected element :- " + firstSelected + " of " + elementName
					+ " dropDown  successfully");
		} catch (ElementNotInteractableException e) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String script = "arguments[0].selectedIndex = 0;";
			firstSelected = (String) jsExecutor.executeScript(script, webObj);
			statusAndDetails(Status.PASS, "Fetch the first selected element :- " + firstSelected + " of " + elementName
					+ " dropDown  successfully");
			return firstSelected;
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.FAIL, "Didn't Fetch the first selected element :- " + firstSelected + " of "
					+ elementName + " dropDown successfully");
		}
		return firstSelected;
	}

	//////////// getAllElements \\\\\\\\\\
	/**
	 * this method is used to Fetch all the webElements from the dropDown with the
	 * help of getAllElements method.
	 * 
	 * @param xpath       it is used to find multiple elements which we have to
	 *                    perform operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	public List<WebElement> getAllElements(WebElement webObj) {

		Select slctObj = new Select(webObj);
		List<WebElement> lstElements = null;
		try {
			lstElements = slctObj.getOptions();
			statusAndDetails(Status.PASS, "Fetched all the elements from the dropDown successfully ");
		} catch (StaleElementReferenceException e) {

			lstElements = slctObj.getOptions();
			statusAndDetails(Status.PASS, "Fetched all the elements from the dropDown successfully ");
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.FAIL, "Didn't Fetch all the elements from the dropDown successfully ");
		}
		return lstElements;
	}

	//////////// selectedByTextContains \\\\\\\\\\
	/**
	 * it is used to select the element with the help of contains text in drop down.
	 * 
	 * @param xpath       it is used to find that element which we have to perform
	 *                    operation.
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @param selectText  it refers to the string as a text that shows which text we
	 *                    have to selected in drop down.....
	 */

	public void selectedByTextContains(WebElement webObj, String selectText) {

		int indexNumber = 0;
		Select slct = new Select(webObj);
		try {
			List<WebElement> webListOptions = slct.getOptions();
			statusAndDetails(Status.PASS, "All the WebElement of dropDown is fetched");
			for (int i = 0; i < webListOptions.size(); i++) {
				String optionText = webListOptions.get(i).getText();
				boolean status = optionText.contains(selectText);
				if (status == true) {
					indexNumber = i;
					break;
				}
			}
			slct.selectByIndex(indexNumber);
			statusAndDetails(Status.PASS, "select text " + selectText + "  is selected from dropDown successfully");
		} catch (StaleElementReferenceException e) {

			List<WebElement> webListOptions = slct.getOptions();
			statusAndDetails(Status.PASS, "All the WebElement of dropDown is fetched");
			for (int i = 0; i < webListOptions.size(); i++) {
				String optionText = webListOptions.get(i).getText();

				boolean status = optionText.contains(selectText);
				if (status == true) {
					indexNumber = i;
					break;
				}
				slct.selectByIndex(indexNumber);
				statusAndDetails(Status.PASS, "select text " + selectText + "  is selected from dropDown successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.FAIL, "select text " + selectText + "  is not selected from dropDown successfully");

		}
	}

	//////////// getOptionsCount \\\\\\\\\\

	/**
	 * it is used to get the total number of element in drop down.
	 * 
	 * @param xpath       it is used to find that element which we have to perform
	 *                    operation.
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 * 
	 * @return it returns int i.e count of option in dropDown
	 */
	public int getOptionsCount(WebElement webObj) {

		Select slt = new Select(webObj);
		int optionCount = 0;
		try {
			optionCount = slt.getOptions().size();
			statusAndDetails(Status.PASS, "Fetch the total element of the dropDown is " + optionCount);
		} catch (StaleElementReferenceException e) {

			optionCount = slt.getOptions().size();
			statusAndDetails(Status.PASS, "Fetch the total element of the dropDown is " + optionCount);
		} catch (Exception e) {
			e.printStackTrace();
			statusAndDetails(Status.FAIL, "Fetch the total element of the dropDown is " + optionCount);
		}
		return optionCount;
	}

	//////////// mouseClick \\\\\\\\\\

	/**
	 * this method is used to click action on the webElement with the help of
	 * mouseClick method using Actions class.
	 * 
	 * @param xpath       it is used to find that element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	public void mouseClick(WebElement webObj) {

		if (webObj.isDisplayed() && webObj.isEnabled()) {

			statusAndDetails(Status.PASS, "Element is displayed and enabled");
			Actions act = new Actions(driver);
			try {
				act.click(webObj).build().perform();
				statusAndDetails(Status.PASS, "Click action is done successfully by Actions method");
			} catch (StaleElementReferenceException e) {

				act.click(webObj).build().perform();
				statusAndDetails(Status.PASS, "Click action is done successfully by Actions method");
			} catch (ElementNotInteractableException e) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click()", webObj);
				statusAndDetails(Status.PASS, "Click action is done successfully by Actions method");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "Click Action is not done successfully by Actions method");
				throw e;
			}
		} else {
			statusAndDetails(Status.PASS, "Element is not displayed and enabled");

		}
	}

	//////////// mouseRightClick \\\\\\\\\\

	/**
	 * this method is used to right click action on the webElement with the help of
	 * mouseRightClick method using Actions class.
	 * 
	 * @param xpath       it is used to find that element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	public void mouseRightClick(WebElement webObj) {

		Actions act = new Actions(driver);
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				act.contextClick(webObj).build().perform();
				statusAndDetails(Status.PASS, "Right click is performed successfully by Actions method");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute the right-click event
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				String script = "var event = new MouseEvent('contextmenu', { 'bubbles': true, "
						+ "'cancelable': true });" + "arguments[0].dispatchEvent(event);";
				jsExecutor.executeScript(script, webObj);
				statusAndDetails(Status.PASS, "Right click is  performed successfully by Actions method");

			} catch (StaleElementReferenceException e) {

				act.contextClick(webObj).build().perform();
				statusAndDetails(Status.PASS, "Right click is performed successfully by Actions method");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "Right click is not performed successfully by Actions method");
				throw e;
			}

		} else {
			statusAndDetails(Status.PASS, "Element is not displayed and Enabled");

		}
	}

	//////////// mouseDoubleClick \\\\\\\\\\
	/**
	 * this method is used to perform double click action on the webElement with the
	 * help of mouseDoubleClick method using Actions class.
	 * 
	 * @param xpath       with the help of xpath we find the element by
	 *                    myFindElement method.
	 * 
	 * @param elementName this is the element name where we have to perform action
	 *                    on an element.
	 * 
	 */
	public void mouseDoubleClick(WebElement webObj) {

		Actions act = new Actions(driver);
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				act.doubleClick(webObj).build().perform();
				statusAndDetails(Status.PASS, "Double click is performed successfully by Actions method");
			} catch (ElementNotInteractableException e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript(
						"arguments[0].dispatchEvent(new MouseEvent('dblclick'," + " { bubbles: true }));", webObj);
				statusAndDetails(Status.PASS, "Double click is performed successfully by Actions method");
			} catch (StaleElementReferenceException e) {

				act.doubleClick(webObj).build().perform();
				statusAndDetails(Status.PASS, "Double click is performed successfully by Actions method");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "Double click is not performed successfully by Actions method");
				throw e;
			}
		} else {
			statusAndDetails(Status.FAIL, "Element is not displayed and Enabled");

		}
	}
	//////////// mouseSendKeys \\\\\\\\\\

	/**
	 * this method is used to send any input value in textbox or any input field
	 * with the help of mouseSendKeys method using Actions class.
	 * 
	 * @param xpath       with the help of xpath we find the element by
	 *                    myFindElement method.
	 * 
	 * @param elementName this is the element name where we have to send input
	 *                    value.
	 * 
	 * @param inputValue  here we have to send input value
	 * 
	 */
	public void mouseSendKeys(WebElement webObj, String inputValue) {

		Actions act = new Actions(driver);
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				// WebElement we=driver.findElement(By.xpath(""));
				webObj.clear();
				driver.navigate().refresh();
				webObj.getTagName();
				webObj.sendKeys("");

				webObj.clear();
				act.sendKeys(webObj, inputValue).build().perform();
				statusAndDetails(Status.PASS, "SendKeys is performed successfully by Actions method");
			} catch (ElementNotInteractableException e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].value='" + inputValue + "'", webObj);
				statusAndDetails(Status.PASS, "SendKeys is performed successfully by Actions method");
			} catch (StaleElementReferenceException e) {

				act.sendKeys(webObj, inputValue).build().perform();
				statusAndDetails(Status.PASS, "SendKeys is performed successfully by Actions method");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "SendKeys is not performed successfully by Actions method");
				throw e;
			}
		} else {
			statusAndDetails(Status.FAIL, "Element is not displayed and Enabled");

		}
	}

	
	///////////////////verifyInnerText/////////////////////
	
	
	public void verifyInnerText(WebElement we, String expectedText) {
		String actualText = we.getText();
		if (actualText.equalsIgnoreCase(expectedText)) {

			extTest.log(Status.PASS, "Passed. Actual-" + actualText + " && Expected-" + expectedText);
		} else {
			extTest.log(Status.FAIL, "Passed. Actual-" + actualText + " && Expected-" + expectedText);
		}

		SoftAssert sa = new SoftAssert();
		sa.assertEquals(false, true);
		sa.assertAll();

		// SoftAssert HardAssert
		/// Verify Assert

//       try {
//    	   Assert.assertEquals(actualText, expectedText);  // fail
//       }catch(Throwable e) {
//    	   
//       }

	}
	
	///////////////////verifyAttributeValue////////////////////
	
	public void verifyAttributeValue(WebElement we, String attributeName, String expectedAttribute) {
		String actualAttribute = we.getAttribute(attributeName);

		if (actualAttribute.equalsIgnoreCase(expectedAttribute)) {
			webUtil.statusAndDetails(Status.PASS,
					"Passed. Actual-" + actualAttribute + " && Expected-" + expectedAttribute);
		} else {
			webUtil.statusAndDetails(Status.FAIL,
					"Failed. Actual-" + actualAttribute + " && Expected-" + expectedAttribute);
		}
	}

	////// verifyTitle
	//// verifyUrl
	//// verifyEnabled
	//// verifyDisabled
	//// verifyElementVisible
	// verifyDropdownValue

	//////////// scrollingToElement \\\\\\\\\

	/**
	 * this method is used to scroll to any particular element.
	 * 
	 * @param xpath       it is used to find that element which we have to perform
	 *                    operation.
	 * 
	 * @param elementName this shows in which element we are working and print a
	 *                    proper message in the console.
	 */
	public void scrollingToElement(WebElement webObj) {

		Actions act = new Actions(driver);
		if (webObj.isDisplayed() && webObj.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				act.scrollToElement(webObj).build().perform();
				statusAndDetails(Status.PASS, "scrolling to the element is performed successfully by Actions method");
			} catch (ElementNotInteractableException e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].scrollIntoView(true);", webObj);
				statusAndDetails(Status.PASS, "scrolling to the element is performed successfully by Actions method");
			} catch (StaleElementReferenceException e) {

				act.scrollToElement(webObj).build().perform();
				statusAndDetails(Status.PASS, "scrolling to the element is performed successfully by Actions method");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS,
						"scrolling to the element is not performed successfully by Actions method");
				throw e;
			}
		} else {
			statusAndDetails(Status.PASS, "Element is not displayed and Enabled");

		}
	}

	//////////// mouseDragDrop \\\\\\\\\

	/**
	 * this method is especially designed to drag and drop operation from one
	 * webElement to another webElement
	 * 
	 * @param dragXpath       this is the source element xpath which we have to drag
	 * @param dragElementName this is the drag element name which is draged
	 * 
	 * @param dropXpath       this is the target element xpath which we have to drop
	 * 
	 * @param dropElementName this is the drop element name where we have to drop.
	 */
	public void mouseDragDrop(WebElement dragWeb, WebElement dropWeb) {
		String dragElementName = dragWeb.getAccessibleName();
		String dropElementName = dropWeb.getAccessibleName();
		Actions ac = new Actions(driver);
		if (dragWeb.isDisplayed() && dropWeb.isEnabled() && dropWeb.isDisplayed() && dropWeb.isEnabled()) {
			statusAndDetails(Status.PASS,
					"Both element " + dragElementName + " and " + dropElementName + "is disable and enable");
			try {
				ac.dragAndDrop(dragWeb, dropWeb).build().perform();
				statusAndDetails(Status.PASS, "Drag operation " + dragElementName + " is  droped to the "
						+ dropElementName + " successfully");
			} catch (StaleElementReferenceException e) {

				ac.dragAndDrop(dragWeb, dropWeb).build().perform();
				statusAndDetails(Status.PASS, "Drag operation " + dragElementName + " is  droped to the "
						+ dropElementName + " successfully");
			} catch (ElementNotInteractableException e) {

				String xto = Integer.toString(dropWeb.getLocation().x);
				String yto = Integer.toString(dropWeb.getLocation().y);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("function simulate(f,c,d,e){var b,a=null;"
						+ "for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)"
						+ "return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\""
						+ "?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,"
						+ "d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),"
						+ "a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a."
						+ "altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));"
						+ "return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|"
						+ "change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick"
						+ "|mouse(?:down|up|over|move|out))$/}; "
						+ "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\""
						+ ",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
						dragWeb, xto, yto);
				statusAndDetails(Status.PASS, "Drag operation " + dragElementName + " is not droped to the "
						+ dropElementName + " successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "Drag operation " + dragElementName + " is not droped to the "
						+ dropElementName + " successfully");
				throw e;
			}

		} else {
			statusAndDetails(Status.PASS,
					"Both element " + dragElementName + " and " + dropElementName + "is not disable and enable");
		}
	}

	//////////// mouseClickAndHold \\\\\\\\\

	/**
	 * this method is used to click and hold of that element with the help of mouse
	 * it means that pressing the left button on an element without releasing
	 * it.This method is often used in combination with other Actions methods to
	 * perform complex interactions like drag-and-drop.
	 * 
	 * @param xpath       it is used to find the element with the help of
	 *                    myFindElement method.
	 * 
	 * @param elementName it is used in which element we are working and print a
	 *                    proper message to include this.
	 */
	
	///////////////////////////mouseClickAndHold////////////////////////
	
	
	public void mouseClickAndHold(WebElement webClickHold) {
		Actions ac = new Actions(driver);
		if (webClickHold.isDisplayed() && webClickHold.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				ac.clickAndHold(webClickHold).build().perform();
				statusAndDetails(Status.PASS, "Element is clicked and hold on successfully");

			} catch (StaleElementReferenceException e) {

				ac.clickAndHold(webClickHold).build().perform();
				statusAndDetails(Status.PASS, "Element is clicked and hold on successfully");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute JavaScript code for the click and hold
				// action
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				String script = "function simulateClickAndHold(element) { "
						+ "   var event = new MouseEvent('mousedown', { " + "       bubbles: true, "
						+ "       cancelable: true, " + "       view: window, " + "       buttons: 1 " + "   }); "
						+ "   element.dispatchEvent(event); " + "} " + "simulateClickAndHold(arguments[0]);";
				jsExecutor.executeScript(script, webClickHold);
				statusAndDetails(Status.PASS, "Element is clicked and hold on successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "Element is not clicked and hold on successfully");
			}

		} else {
			statusAndDetails(Status.PASS, "Element is not displayed and Enabled");

		}

	}
	//////////// mouseReleaseElement \\\\\\\\\

	/**
	 * it is used to release mouse button after performing mouse related action. the
	 * release() method should be called after performing mouse action just like
	 * clickAndHold,click and moveToElement.
	 * 
	 * @param xpath       with the help of xpath we can easily find the element via
	 *                    myFindElement method if xpath is wrong then it throws
	 *                    NoSuchElementException and in case of syntax of xpath is
	 *                    wrong then it throws InvalidSelectorException.
	 * 
	 * @param elementName we need to element name to write a sitable message In case
	 *                    of fail and pass conditions .
	 */
	
	//////////////////mouseReleaseElement///////////////////
	
	public void mouseReleaseElement(WebElement webRelease) {

		Actions ac = new Actions(driver);
		if (webRelease.isDisplayed() && webRelease.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				ac.release(webRelease).build().perform();
				statusAndDetails(Status.INFO, "Element is released successfully");

			} catch (StaleElementReferenceException e) {

				ac.release(webRelease).build().perform();
				statusAndDetails(Status.INFO, "Element is released successfully");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute JavaScript code for the click and hold
				// action
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				String releaseScript = "function simulateMouseRelease() { "
						+ "   var event = new MouseEvent('mouseup', { " + "       bubbles: true, "
						+ "       cancelable: true, " + "       view: window, " + "       buttons: 0 " + "   }); "
						+ "   document.dispatchEvent(event); " + "} " + "simulateMouseRelease();";
				jsExecutor.executeScript(releaseScript);
				statusAndDetails(Status.PASS, "Element is released successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.PASS, "Element is not released successfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "Element is not displayed and Enabled");

		}

	}
	//////////// mouseMoveToElement \\\\\\\\\

	/**
	 * with the help of mouseMoveToElement method we can mouse hover of any element
	 * it means that we can move to the mouse in any given element
	 * 
	 * @param xpath       we pass xpath to the findElement method to find the
	 *                    element.
	 * @param elementName we needs to give the name of any element to print a
	 *                    suitable message .
	 */
	
	///////////////////mouseMoveToElement/////////////////////
	
	public void mouseMoveToElement(WebElement webMoveToElement) {
		Actions ac = new Actions(driver);
		if (webMoveToElement.isDisplayed() && webMoveToElement.isEnabled()) {
			statusAndDetails(Status.PASS, "Element is displayed and Enabled");
			try {
				ac.moveToElement(webMoveToElement).build().perform();
				statusAndDetails(Status.PASS, "Move to Element is performed successfully");

			} catch (StaleElementReferenceException e) {

				ac.release(webMoveToElement).build().perform();
				statusAndDetails(Status.PASS, "Move to Element is performed successfully");
			} catch (ElementNotInteractableException e) {
				// Use JavascriptExecutor to execute JavaScript code for moving to the element
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				String script = "var element = arguments[0];" + "var rect = element.getBoundingClientRect();"
						+ "var centerX = rect.left + rect.width / 2;" + "var centerY = rect.top + rect.height / 2;"
						+ "var mouseMoveEvent = new MouseEvent('mousemove', {" + "  bubbles: true,"
						+ "  cancelable: true," + "  view: window," + "  clientX: centerX," + "  clientY: centerY"
						+ "});" + "element.dispatchEvent(mouseMoveEvent);";
				jsExecutor.executeScript(script, webMoveToElement);

				statusAndDetails(Status.PASS, "Element is released successfully");
			} catch (Exception e) {
				e.printStackTrace();
				statusAndDetails(Status.INFO, "Move to Element is not performed successfully");
			}

		} else {
			statusAndDetails(Status.FAIL, "Element is not displayed and Enabled");

		}

	}
	
	///////////////// getRandomName/////////////////////

	public String getRandomName(int i) {
		RandomString rs = new RandomString();
		String str = rs.nextString();
		return str;
	}

	public SearchContext getDriver() {
		// TODO Auto-generated method stub
		return null;
	}

}
