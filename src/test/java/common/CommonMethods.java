package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import pageObjects.PageObjects;
import plain.QuestionAdd;

public class CommonMethods implements IRetryAnalyzer {
	
	public boolean retry(ITestResult result) {

		if(PageObjects.counterOfTry <= PageObjects.retryLimit)
		 {
			 System.out.println("Trying : "+PageObjects.counterOfTry+ "time");
			 PageObjects.counterOfTry++;
		 return true;
		 }
		 return false;
	}
	
	public static void findElement(
			WebDriver driver, 
			String locatorType,  
			String data,
			String values, 
			String action) throws Exception {
		// find element, if not found then wait and try again 3 times , still not found
		// then throw exception
		//value = (T) values;
		
		if (locatorType.equalsIgnoreCase("id")) {

			// do code if locatorvalue is int
			if (action.equalsIgnoreCase("type")) {
				driver.findElement(By.id(data)).click();
				driver.findElement(By.id(data)).clear();
				driver.findElement(By.id(data)).sendKeys(values);
			} else if (action.equalsIgnoreCase("click")) { //
				driver.findElement(By.id(data)).click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				driver.findElement(By.id(data)).click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				driver.findElement(By.id(data)).click();
			}

		} else if (locatorType.equalsIgnoreCase("xpath")) {

			// do code if locatorvalue is int
			if (action.equalsIgnoreCase("type")) {
				driver.findElement(By.xpath(data)).sendKeys(values);
			} else if (action.equalsIgnoreCase("click")) { //
				driver.findElement(By.xpath(data)).click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				driver.findElement(By.xpath(data)).click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				driver.findElement(By.xpath(data)).click();
			}

		} else if (locatorType.equalsIgnoreCase("name")) {

			// do code if locatorvalue is int
			if (action.equalsIgnoreCase("type")) {
				driver.findElement(By.name(data)).sendKeys(values);
			} else if (action.equalsIgnoreCase("click")) { //
				driver.findElement(By.name(data)).click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				driver.findElement(By.name(data)).click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				driver.findElement(By.name(data)).click();
			}

		} else {
			throw new Exception("locatorType is not from defined set");
		}
		return;
	}
	
	public static String getValue(String key) throws IOException {
		
		String value = null;
		int i=0;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		
		System.out.println("in getValue");
		System.out.println("11111");
		 File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		 FileInputStream inputStream = new FileInputStream(file);
			System.out.println("222222");

		 workbook = new XSSFWorkbook(inputStream); 
		 sheet = (XSSFSheet)workbook.getSheet(PageObjects.QuestionAddSheetNameForKeyValue);
			System.out.println("3333333");

		while(i < sheet.getLastRowNum()) {
			System.out.println("checking "+ i + sheet.getRow(i).getCell(0).toString());
			if(sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(key)) {
				System.out.println("yes we got : "+ sheet.getRow(i).getCell(0).toString());
				inputStream.close();
				return sheet.getRow(i).getCell(1).toString();
			}
			else {
				i++;
			}
		}
		System.out.println("44444444");
		inputStream.close();
		return null;
	}
	
	public static void TestfindElement(
			WebElement data,
			String values, 
			String action) throws Exception {
		// find element, if not found then wait and try again 3 times , still not found
		// then throw exception
		//value = (T) values;
		
		if (data!= null) {
			System.out.println("hiiiiiiiiiiiiiiiiiiiiiii"+values+" : "+action);
			// do code if locatorvalue is int
			if (action.equalsIgnoreCase("type")) {
				data.click();
				data.clear();
				data.sendKeys(values);
				//QuestionAdd.driver.findElement(By.id(data)).sendKeys(values);
			} else if (action.equalsIgnoreCase("click")) { //
				data.click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				data.click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				data.click();
			}

		/*} else if (QuestionAdd.driver.findElements(By.xpath(data)).size()!=0){

			// do code if locatorvalue is int
			if (action.equalsIgnoreCase("type")) {
				QuestionAdd.driver.findElement(By.xpath(data)).sendKeys(values);
			} else if (action.equalsIgnoreCase("click")) { //
				QuestionAdd.driver.findElement(By.xpath(data)).click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				QuestionAdd.driver.findElement(By.xpath(data)).click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				QuestionAdd.driver.findElement(By.xpath(data)).click();
			}

		} else if (QuestionAdd.driver.findElements(By.name(data)).size()!=0) {

			// do code if locatorvalue is int
			if (action.equalsIgnoreCase("type")) {
				QuestionAdd.driver.findElement(By.name(data)).sendKeys(values);
			} else if (action.equalsIgnoreCase("click")) { //
				QuestionAdd.driver.findElement(By.name(data)).click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				QuestionAdd.driver.findElement(By.name(data)).click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				QuestionAdd.driver.findElement(By.name(data)).click();
			}

		*/} else {
			throw new Exception("locatorType is not from defined set");
		}
		return;
	}
}
