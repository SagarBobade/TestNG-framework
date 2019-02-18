package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.google.common.base.Function;

import net.bytebuddy.asm.Advice.Exit;

import org.openqa.selenium.WebDriver;

import pageObjects.PageObjects;

public class CommonMethods implements IRetryAnalyzer {

	public static Properties prop;
	public static InputStream input;

	public boolean retry(ITestResult result) {

		if (PageObjects.counterOfTry <= PageObjects.retryLimit) {
			System.out.println("Trying : " + PageObjects.counterOfTry + "time");
			PageObjects.counterOfTry++;
			return true;
		}
		return false;
	}

	public static String getValue(String key) throws IOException {

		int i = 0;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String valuefromExcel = null;

		File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		workbook = new XSSFWorkbook(inputStream);
		sheet = (XSSFSheet) workbook.getSheet(PageObjects.methodNameToGetSheetName);

		while (i < sheet.getLastRowNum()) {
			if (sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(key)) {
				valuefromExcel = sheet.getRow(i).getCell(1).toString();
				workbook.close();
				inputStream.close();
				System.out.println("We found :" + valuefromExcel);
				return valuefromExcel;
			} else {
				i++;
			}
		}
		workbook.close();
		inputStream.close();
		return null;
	}

	public static List<WebElement> wait(List<WebElement> list) throws InterruptedException {

		System.out.println("in wait after hiii");
		WebDriverWait wait = new WebDriverWait(PageObjects.driver, 20);
		System.out.println("in wait");
		
		wait.until(ExpectedConditions.visibilityOfAllElements(list));
		
		System.out.println("in wait before return");
		return list;
	}
	
	public static WebElement wait(WebElement webElement) throws InterruptedException {

		System.out.println("in wait after hiii");
		WebDriverWait wait = new WebDriverWait(PageObjects.driver, 20);
		System.out.println("in wait");
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		
		System.out.println("in wait before return");
		return webElement;
	}
	
	public static void TestfindElement(ExtentTest logger, WebElement data, String values, String action)
			throws Exception {
		WebElement foundElement;
		JavascriptExecutor js = (JavascriptExecutor) PageObjects.driver;

		try {
			if (data != null) {
				foundElement = wait(data);
				PageObjects.lastAccessedWebElement = data;

				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						foundElement);

				switch (action) {
				case "type":
					foundElement.click();
					foundElement.clear();
					foundElement.sendKeys(values);
					logger.info("Typed - " + values);
					break;
				case "click":
					foundElement.click();
					logger.info("Clicked");
					break;
				case "tick":
					foundElement.click();
					logger.info("Ticked checkbox");
					break;
				case "selectRadio":
					foundElement.click();
					logger.info("Selected radio button");
					break;
				default:
					throw new Exception("locatorType is not from defined set");

				}

				try {
					js.executeScript("arguments[0].setAttribute('style', 'background: none; border: 2px solid none;');",
							foundElement);
				} catch (Exception e) {
					System.out.println("catched click wala");
				}
			}
		} catch (Exception e) {
			CommonMethods.insideCatch(logger, js);
		}
		return;
	}

	public static Properties readPropertiesFile() {
		prop = new Properties();
		try {
			input = new FileInputStream(PageObjects.propertiesFilePath);
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static XSSFSheet getSheet(String methodNameToGetSheetName) throws IOException {

		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;

		File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		workbook = new XSSFWorkbook(inputStream);
		sheet = (XSSFSheet) workbook.getSheet(PageObjects.methodNameToGetSheetName);

		return sheet;
	}

	public static void getAPIDetailsAndHit(String methodNameToGetSheetName) throws IOException {

		XSSFSheet sheet = getSheet(methodNameToGetSheetName);

	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = "C:\\Users\\sagarb\\Documents" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static void insideCatch(ExtentTest logger, JavascriptExecutor js) throws IOException, Exception {

		System.out.println("inside two paramters catch method");
		if (!PageObjects.prop.getProperty("OnlyCheckAPI").equalsIgnoreCase("true")) {

			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					PageObjects.lastAccessedWebElement);

			logger.log(Status.FAIL, "Last action was on below screenshot : ");
			logger.log(Status.FAIL, (Markup) logger.addScreenCaptureFromPath(
					CommonMethods.getScreenshot(PageObjects.driver, PageObjects.methodNameToGetSheetName)));
			PageObjects.alreadyCatchedException = true;
		}
		return;
	}

	public static void insideCatchOne(ExtentTest logger) throws IOException, Exception {

		if (PageObjects.alreadyCatchedException = false) {
			System.out.println("inside One paramters catch method");

			if (!PageObjects.prop.getProperty("OnlyCheckAPI").equalsIgnoreCase("true")) {

				logger.log(Status.FAIL, "Last action was on below screenshot : ");
				logger.log(Status.FAIL, (Markup) logger.addScreenCaptureFromPath(
						CommonMethods.getScreenshot(PageObjects.driver, PageObjects.methodNameToGetSheetName)));
			}
		}
	}
}
