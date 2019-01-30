package plain;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.paulhammant.ngwebdriver.NgWebDriver;

import common.CommonMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.PageObjects;

public class QuestionAdd{

	public static WebDriver driver;
	public static String methodName;
	public static boolean isFind = true;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	
	public QuestionAdd() {
		PageObjects page = PageFactory.initElements(driver,PageObjects.class);
	}
	
	@BeforeSuite
	public void openBrowser() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
		ngWebDriver.waitForAngularRequestsToFinish();
		driver.get(PageObjects.loginUrl);
		driver.manage().window().maximize();
	}

	@Test(enabled = true,
			priority=1,
			description = "login to the application",
			retryAnalyzer = common.CommonMethods.class)
	public void login() throws Exception {
		
		System.out.println("in login");
		
		CommonMethods.TestfindElement(PageObjects.userIdId, CommonMethods.getValue("userIdValue"), "type");
		CommonMethods.TestfindElement(PageObjects.passwordId, CommonMethods.getValue("passwordValue"), "type");
		CommonMethods.TestfindElement(PageObjects.orgCodeId, CommonMethods.getValue("orgCodeValue"), "type");
		CommonMethods.TestfindElement(PageObjects.loginBtnXpath, null, "click");

				Thread.sleep(5000);
				Assert.assertNotEquals(driver.getCurrentUrl(),"http://192.168.91.48/login");
			}

	/*
	 * @Test(enabled = true, priority=2, dependsOnMethods = { "login" }, description
	 * = "add question manually", retryAnalyzer = common.CommonMethods.class) public
	 * void createQuestionManually() throws InterruptedException {
	 * 
	 * driver.navigate().to(PageObjects.urlAdmin);
	 * 
	 * // navigate que management
	 * driver.findElement(By.xpath(PageObjects.courseManagementXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.assessmentQueXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.addNewQueXpath)).click();
	 * 
	 * // typing question
	 * driver.findElement(By.xpath(PageObjects.questionTextboxXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.questionTextboxXpath)).clear();
	 * driver.findElement(By.xpath(PageObjects.questionTextboxXpath)).sendKeys(
	 * PageObjects.typeQuestion);
	 * 
	 * // typing meta data
	 * driver.findElement(By.xpath(PageObjects.metaDataXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.metaDataXpath)).clear();
	 * driver.findElement(By.xpath(PageObjects.metaDataXpath)).
	 * sendKeys("test metadata");
	 * 
	 * // select que level Select queLevel = new
	 * Select(driver.findElement(By.id(PageObjects.questionLevelId)));
	 * queLevel.selectByVisibleText(PageObjects.questionLevelValue);
	 * 
	 * // select marks Select marksForQue = new
	 * Select(driver.findElement(By.id(PageObjects.marksPerQuestionId)));
	 * marksForQue.selectByVisibleText(PageObjects.marksPerQuestionIdValue);
	 * 
	 * // make quest Active if
	 * (driver.findElement(By.id(PageObjects.activeQuestionId)).isSelected()) { //
	 * how to toggle System.out.println("selected"); } else {
	 * System.out.println("De-selected"); } // select OptionsCount
	 * 
	 * Select optionsCount = new
	 * Select(driver.findElement(By.id(PageObjects.optionsCountId)));
	 * marksForQue.selectByVisibleText(PageObjects.optionsCountValue);
	 * 
	 * // need for loop for options and ans selection // typing options
	 * driver.findElement(By.xpath(PageObjects.optionAXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.optionAXpath)).clear();
	 * driver.findElement(By.xpath(PageObjects.optionAXpath)).sendKeys("option a");
	 * 
	 * driver.findElement(By.xpath(PageObjects.optionBXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.optionBXpath)).clear();
	 * driver.findElement(By.xpath(PageObjects.optionBXpath)).sendKeys("option b");
	 * 
	 * // select option as answer
	 * driver.findElement(By.xpath(PageObjects.correctAnswerXpath)).click();
	 * 
	 * // add question
	 * driver.findElement(By.xpath(PageObjects.addQuestionXpath)).click();
	 * 
	 * // save driver.findElement(By.xpath(PageObjects.saveQuestionXpath)).click();
	 * }
	 * 
	 * @Test(enabled = true, priority=3, dependsOnMethods = { "login",
	 * "createQuestionManually" }, description = "add question by import",
	 * retryAnalyzer = common.CommonMethods.class) public void questionAddImport()
	 * throws InterruptedException {
	 * 
	 * driver.navigate().to(PageObjects.urlAdmin);
	 * 
	 * Thread.sleep(5000); // navigate que management
	 * driver.findElement(By.xpath(PageObjects.courseManagementXpath)).click();
	 * driver.findElement(By.xpath(PageObjects.assessmentQueXpath)).click();
	 * 
	 * // click import icon
	 * driver.findElement(By.xpath(PageObjects.importIconXpath)).click();
	 * 
	 * // click choose file
	 * driver.findElement(By.id(PageObjects.chooseFile)).click(); // select file
	 * driver.findElement(By.id(PageObjects.chooseFile)).sendKeys(PageObjects.
	 * questionBankImportFilePath); // click import
	 * driver.findElement(By.xpath(PageObjects.importSelectedFile)).click();
	 * 
	 * if (!driver.findElement(By.xpath(
	 * "/html/body/modal-container/div/div/div[2]/div[1]/alert/div/strong"))
	 * .getText().toString().contains("Total number of record record rejected : 0"))
	 * { System.out.println("failed to import"); } }
	 * 
	 * @Test(enabled = true, priority=4, dependsOnMethods = { "login",
	 * "createQuestionManually" }, description = "edit question manually",
	 * retryAnalyzer = common.CommonMethods.class) public void editQuestion() throws
	 * InterruptedException {
	 * 
	 * driver.navigate().to(PageObjects.urlAdmin);
	 * 
	 * Thread.sleep(5000); // navigate Course management
	 * driver.findElement(By.xpath(PageObjects.courseManagementXpath)).click(); //
	 * navigate assessment management
	 * driver.findElement(By.xpath(PageObjects.assessmentQueXpath)).click();
	 * 
	 * driver.findElement(By.xpath(
	 * "/html/body/app-root/app-full-layout/div[1]/main/div/app-assessment/div[2]/div/div/app-assessment-grid/div/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[6]/div/i[1]"
	 * )) .click();
	 * 
	 * // update Question text }
	 * 
	 * @AfterTest public void afterEachTest() { PageObjects.counterOfTry = 0;
	 * PageObjects.retryLimit = 0; }
	 */
	@AfterSuite(enabled = true,
			alwaysRun = true)
	public void closeBrowser() {
		driver.close();
	}

	}

