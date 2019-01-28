package plain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import common.CommonMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.PageObjects;

public class QuestionAdd implements IRetryAnalyzer {

	public static WebDriver driver;
	public static int count = 0;
	public static int maxTries = 3;
	public static String methodName;
	public static boolean isFind = true;

	@BeforeTest
	public void setValueBefore() {
		count = 4;

	}

	@BeforeSuite
	public void openBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(PageObjects.loginUrl);
		driver.manage().window().maximize();
	}

	
	@Test(enabled = true,
			priority=1,
			retryAnalyzer = plain.QuestionAdd.class)
	public void login() throws InterruptedException {

		while (isFind == true) {
			try {
				CommonMethods.findElement(driver, "id", PageObjects.userIdId, PageObjects.userIdValue, "type");
				CommonMethods.findElement(driver, "id", PageObjects.passwordId, PageObjects.passwordValue, "type");
				CommonMethods.findElement(driver, "id", PageObjects.orgCodeId, PageObjects.orgCodeValue, "type");
				CommonMethods.findElement(driver, "xpath", PageObjects.loginBtnXpath, null, "click");
			
				Thread.sleep(5000);
				if (!driver.getTitle().equalsIgnoreCase("TLS")) {
					throw new Exception();
				}
				
				isFind = false;
				count = 0;
				if(driver.getCurrentUrl().equalsIgnoreCase(PageObjects.loginUrl)) {
					throw new Exception("Login failed with after login button click");
				}
			} catch (Exception e) {
				methodName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				CommonMethods.catchException(e, methodName);
			}
		}
	}

	@Test(enabled = true,
			priority=2,
			retryAnalyzer = plain.QuestionAdd.class)
	public void createQuestionManually() throws InterruptedException {

		while (QuestionAdd.count > QuestionAdd.maxTries) {
			try {

				driver.navigate().to(PageObjects.urlAdmin);

				Thread.sleep(5000);
System.out.println("sdfsfsfsdfsf");
				// navigate que management
				driver.findElement(By.xpath(PageObjects.courseManagementXpath)).click();
				driver.findElement(By.xpath(PageObjects.assessmentQueXpath)).click();
				driver.findElement(By.xpath(PageObjects.addNewQueXpath)).click();

				// typing question
				driver.findElement(By.xpath(PageObjects.questionTextboxXpath)).click();
				driver.findElement(By.xpath(PageObjects.questionTextboxXpath)).clear();
				driver.findElement(By.xpath(PageObjects.questionTextboxXpath)).sendKeys(PageObjects.typeQuestion);

				// typing meta data
				driver.findElement(By.xpath(PageObjects.metaDataXpath)).click();
				driver.findElement(By.xpath(PageObjects.metaDataXpath)).clear();
				driver.findElement(By.xpath(PageObjects.metaDataXpath)).sendKeys("test metadata");

				// select que level
				Select queLevel = new Select(driver.findElement(By.id(PageObjects.questionLevelId)));
				queLevel.selectByVisibleText(PageObjects.questionLevelValue);

				// select marks
				Select marksForQue = new Select(driver.findElement(By.id(PageObjects.marksPerQuestionId)));
				marksForQue.selectByVisibleText(PageObjects.marksPerQuestionIdValue);

				// make quest Active
				if (driver.findElement(By.id(PageObjects.activeQuestionId)).isSelected()) {
					// how to toggle
					System.out.println("selected");
				} else {
					System.out.println("De-selected");
				}
				// select OptionsCount

				Select optionsCount = new Select(driver.findElement(By.id(PageObjects.optionsCountId)));
				marksForQue.selectByVisibleText(PageObjects.optionsCountValue);

				// need for loop for options and ans selection
				// typing options
				driver.findElement(By.xpath(PageObjects.optionAXpath)).click();
				driver.findElement(By.xpath(PageObjects.optionAXpath)).clear();
				driver.findElement(By.xpath(PageObjects.optionAXpath)).sendKeys("option a");

				driver.findElement(By.xpath(PageObjects.optionBXpath)).click();
				driver.findElement(By.xpath(PageObjects.optionBXpath)).clear();
				driver.findElement(By.xpath(PageObjects.optionBXpath)).sendKeys("option b");

				// select option as answer
				driver.findElement(By.xpath(PageObjects.correctAnswerXpath)).click();

				// add question
				driver.findElement(By.xpath(PageObjects.addQuestionXpath)).click();

				// save
				driver.findElement(By.xpath(PageObjects.saveQuestionXpath)).click();
				count = 0;
			} catch (Exception e) {
				methodName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				CommonMethods.catchException(e, methodName);
			}
		}
	}

	@Test(enabled = true,
			priority=3,
			retryAnalyzer = plain.QuestionAdd.class)
	public void questionAddImport() throws InterruptedException {

		while (QuestionAdd.count < QuestionAdd.maxTries) {
			try {
				driver.navigate().to(PageObjects.urlAdmin);

				Thread.sleep(5000);
				// navigate que management
				driver.findElement(By.xpath(PageObjects.courseManagementXpath)).click();
				driver.findElement(By.xpath(PageObjects.assessmentQueXpath)).click();

				// click import icon
				driver.findElement(By.xpath(PageObjects.importIconXpath)).click();

				// click choose file
				driver.findElement(By.id(PageObjects.chooseFile)).click();
				// select file
				driver.findElement(By.id(PageObjects.chooseFile)).sendKeys(PageObjects.questionBankImportFilePath);
				// click import
				driver.findElement(By.xpath(PageObjects.importSelectedFile)).click();

				if (!driver.findElement(By.xpath("/html/body/modal-container/div/div/div[2]/div[1]/alert/div/strong"))
						.getText().toString().contains("Total number of record record rejected : 0")) {
					System.out.println("failed to import");
				}
			} catch (Exception e) {
				methodName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				CommonMethods.catchException(e, methodName);
			}
		}
	}

	@Test(enabled = true,
			priority=4,
			retryAnalyzer = plain.QuestionAdd.class)
	public void editQuestion() throws InterruptedException {

		while (QuestionAdd.count < QuestionAdd.maxTries) {
			try {
				driver.navigate().to(PageObjects.urlAdmin);

				Thread.sleep(5000);
				// navigate Course management
				driver.findElement(By.xpath(PageObjects.courseManagementXpath)).click();
				// navigate assessment management
				driver.findElement(By.xpath(PageObjects.assessmentQueXpath)).click();

				driver.findElement(By.xpath(
						"/html/body/app-root/app-full-layout/div[1]/main/div/app-assessment/div[2]/div/div/app-assessment-grid/div/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[6]/div/i[1]"))
						.click();

				// update Question text
			} catch (Exception e) {
				methodName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				CommonMethods.catchException(e, methodName);
			}
		}
	}

	@AfterSuite(enabled = true,
			alwaysRun = true)
	public void closeBrowser() {
		while (QuestionAdd.count < QuestionAdd.maxTries) {
			try {
				driver.close();
			} catch (Exception e) {
				methodName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				System.out.println(methodName);
				CommonMethods.catchException(e, methodName);
			}
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

}
