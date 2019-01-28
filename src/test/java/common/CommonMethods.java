package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import pageObjects.PageObjects;
import plain.QuestionAdd;

public class CommonMethods<T> {

//	private static T value;
	

	public static void catchException(Exception e, String exceptionIn) {
		QuestionAdd.count++;
		System.out.println("------------------"+exceptionIn+" Failed function on " + QuestionAdd.count
				+ " time ------------------");
		if (QuestionAdd.count == QuestionAdd.maxTries) {
			System.out.println("In catch exception : "+ exceptionIn);
			QuestionAdd.count = 0;
			System.out.println("***************************************************************************");
			e.printStackTrace();
			return;
		}
		else {
			return;
		}
	}
	
	public boolean retry(ITestResult result) {
		 if(PageObjects.counter < PageObjects.retryLimit)
		 {
			 System.out.println("Trying : "+PageObjects.counter+ "time");
			 PageObjects.counter++;
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
}
