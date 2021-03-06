package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;

import pageObjects.PageObjects;

public class CommonMethods implements IRetryAnalyzer {

	public static Properties prop;
	public static InputStream input;
	public static Select dropdown;

	public boolean retry(ITestResult result) {

		if (PageObjects.counterOfTry <= PageObjects.retryLimit) {
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
				return valuefromExcel;
			} else {
				i++;
			}
		}

		workbook.close();
		inputStream.close();
		return null;
	}

	public static String getValue(String key, String sheetName) throws IOException {

		int i = 0;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String valuefromExcel = null;

		File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		workbook = new XSSFWorkbook(inputStream);
		sheet = (XSSFSheet) workbook.getSheet(sheetName);

		while (i < sheet.getLastRowNum()) {
			if (sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(key)) {
				valuefromExcel = sheet.getRow(i).getCell(1).toString();
				workbook.close();
				inputStream.close();
				return valuefromExcel;
			} else {
				i++;
			}
		}

		workbook.close();
		inputStream.close();
		return null;
	}

	public static List<String> getKeys() throws IOException {

		int i = 0;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		List<String> valuesfromExcel = new ArrayList();

		File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		workbook = new XSSFWorkbook(inputStream);
		sheet = (XSSFSheet) workbook.getSheet(PageObjects.methodNameToGetSheetName);

		while (i <= sheet.getLastRowNum()) {
			if (sheet.getRow(i).getCell(0).toString().contains("update")) {
				valuesfromExcel.add(sheet.getRow(i).getCell(0).toString());
			}
			i++;
		}
		workbook.close();
		inputStream.close();
		return valuesfromExcel;
	}

	public static List<WebElement> wait(List<WebElement> list) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(PageObjects.driver, 20);
		wait.until(ExpectedConditions.visibilityOfAllElements(list));
		return list;
	}

	public static WebElement wait(WebElement webElement) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(PageObjects.driver, 20);
		wait.until(ExpectedConditions.visibilityOfAllElements(webElement));

		return webElement;
	}

	public static String TestfindElement(ExtentTest logger, WebElement data, String values, String action)
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
				case "enter":
					foundElement.sendKeys(Keys.ENTER);
					;
					logger.info("Pressed Enter");
					break;
				case "click":
					foundElement.click();
					logger.info("Clicked");
					break;
				case "tick":
					if (values.equalsIgnoreCase("true")) {
						if (!foundElement.isSelected()) {
							foundElement.click();
						}
					} else if (values.equalsIgnoreCase("false")) {
						foundElement.click();
					}
					logger.info("Ticked checkbox");
					break;
				case "select":
					if (values.contains(".0")) {
						int temp = (int) Double.parseDouble(values);
						values = Integer.toString(temp);
					}
					dropdown = new Select(foundElement);

					dropdown.selectByValue(values);
					logger.info("Selected " + values);
					break;
				case "webtable":
					foundElement.click();
					logger.info("Selected radio button");
					break;
				case "selectRadio":
					foundElement.click();
					logger.info("Selected radio button");
					break;
				case "getText":
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
		return "null";
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

	public static String webTableOp(ExtentTest logger, String xpath1, String xpath2, String value, String OP)
			throws IOException {

		switch (OP) {

		case "type":
			// This can be used to type in particular textbox in grid of textboxes
			int tot = Integer.parseInt(value);
			for (int i = 1; i <= tot; i++) {

				PageObjects.driver.findElement(By.xpath(xpath1 + i + xpath2)).click();
				PageObjects.driver.findElement(By.xpath(xpath1 + i + xpath2)).clear();
				PageObjects.driver.findElement(By.xpath(xpath1 + i + xpath2))
						.sendKeys(CommonMethods.getValue("Option" + i));
			}
			break;
		case "tick":
			// This can be used to Tick the particular TOGGLE BUTTON in grid of toggle
			// buttons
			String webElementToTick = value.substring(value.length() - 1);
			PageObjects.driver.findElement(By.xpath(xpath1 + webElementToTick + xpath2)).click();
			break;
		case "getText":
			// String webElementToTick2 = null;
			// This can be used to Tick the particular TEXT in grid
			// String webElementToTick = value.substring(value.length() - 1);
			return PageObjects.driver.findElement(By.xpath(xpath1 + value + xpath2)).getText().toString();
		}
		return "";

	}
}
