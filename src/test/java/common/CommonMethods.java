package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

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
				return valuefromExcel;
			} else {
				i++;
			}
		}
		workbook.close();
		inputStream.close();
		return null;
	}

	public static void TestfindElement(WebElement data, String values, String action) throws Exception {

		if (data != null) {

			switch (action) {
			case "type":
				data.click();
				data.clear();
				data.sendKeys(values);
				break;
			case "click":
				data.click();
				break;
			case "tick":
				data.click();
				break;
			case "selectRadio":
				data.click();
				break;
			default:
				throw new Exception("locatorType is not from defined set");

			}
			return;
		}
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
		int i = 0;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String valuefromExcel = null;

		File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		workbook = new XSSFWorkbook(inputStream);
		sheet = (XSSFSheet) workbook.getSheet(PageObjects.methodNameToGetSheetName);
		
		return sheet;
	}

	public static void getAPIDetailsAndHit(String methodNameToGetSheetName) throws IOException {

		XSSFSheet sheet = getSheet(methodNameToGetSheetName);
		
		
	}
}
