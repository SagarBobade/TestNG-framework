import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import common.CommonMethods;
import pageObjects.PageObjects;

public class ReTryTest implements IRetryAnalyzer{
	
	public static WebDriver driver;
	 int counter = 0;
	 int retryLimit = 3;
	 
	 @BeforeSuite
	 public void openBrowser() {
		 WebDriverManager.chromedriver().setup();
			
			driver = new ChromeDriver();
			driver.get(PageObjects.loginUrl);
			driver.manage().window().maximize();
	 }

@Test(retryAnalyzer = ReTryTest.class)
  public void f() throws Exception {
	  
		CommonMethods.findElement(driver, "id", PageObjects.userIdId, PageObjects.userIdValue, "type");
		CommonMethods.findElement(driver, "id", PageObjects.passwordId, PageObjects.passwordValue, "type");
		CommonMethods.findElement(driver, "id", PageObjects.orgCodeId, PageObjects.orgCodeValue, "type");
		CommonMethods.findElement(driver, "xpath", PageObjects.loginBtnXpath, null, "click");
		
  }

public boolean retry(ITestResult result) {
	 if(counter < retryLimit)
	 {
		 System.out.println("Trying : "+counter+ "time");
	 counter++;
	 return true;
	 }
	 return false;
}
}
