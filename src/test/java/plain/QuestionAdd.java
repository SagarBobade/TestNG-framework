package plain;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.paulhammant.ngwebdriver.NgWebDriver;

import common.CommonMethods;
import common.SendMail;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.PageObjects;
import pageObjects.loginPage.LoginPage;

public class QuestionAdd {

	// public static WebDriver driver;
	// public PageObjects pageObjects;
	public LoginPage loginPage;

	/*
	 * public static String sheetNameForMethodName;
	 */ public static boolean isFind = true;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static Boolean checkAPI = false;
	public static Boolean checkUI = true;
	
	@BeforeClass
	public void beforeClass() {
		loginPage = PageFactory.initElements(PageObjects.driver, LoginPage.class);
	}

	@BeforeSuite
	public void openBrowser() throws IOException {

		PageObjects.prop = CommonMethods.readPropertiesFile();

		File directory = new File(PageObjects.htmlDirectoryPath);
		if (!directory.exists()) {
			directory.mkdir();
		}
		PageObjects.reporter = new ExtentHtmlReporter(PageObjects.htmlReportPath);

		if (PageObjects.prop.getProperty("OnlyCheckAPI").equalsIgnoreCase("true")) {
			checkAPI = true;
			checkUI = false;
		} else {
			WebDriverManager.firefoxdriver().setup();
			PageObjects.driver = new ChromeDriver();
			NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) PageObjects.driver);
			ngWebDriver.waitForAngularRequestsToFinish();
			PageObjects.driver.manage().window().maximize();
		}
	}

	@BeforeTest
	public void beforeTest() {
		PageObjects.extent = new ExtentReports();
		PageObjects.extent.attachReporter(PageObjects.reporter);
	}

	@AfterTest
	public void afterTest() {
		PageObjects.extent.flush();
	}

	@Test(enabled = true, priority = 1, description = "login to the application")
	public void login() throws Exception {

		PageObjects.methodNameToGetSheetName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		ExtentTest logger = PageObjects.extent.createTest(PageObjects.methodNameToGetSheetName);

		try {
			if (checkAPI == false && checkUI == true) {
				PageObjects.driver.get(PageObjects.loginUrl);
				CommonMethods.TestfindElement(logger, loginPage.getUserId(), CommonMethods.getValue("userIdValue"),
						"type");
				CommonMethods.TestfindElement(logger, loginPage.getPassword(), CommonMethods.getValue("passwordValue"),
						"type");
				CommonMethods.TestfindElement(logger, loginPage.getOrgCode(), CommonMethods.getValue("orgCodeValue"),
						"type");
				CommonMethods.TestfindElement(logger, loginPage.getLoginButton(), "", "click");

				Thread.sleep(2000);
				Assert.assertEquals(PageObjects.driver.getCurrentUrl(), PageObjects.loginUrl);
				logger.log(Status.PASS, "login successfully");
			} else {
				Assert.assertEquals(api.CommonMethods.hitUserAuthAPI("http://192.168.91.48/connect/token/", "ltfs",
						"sammir",
						"EED96928D820D2DE920F2294988414577C0069F878011A20F8091ED442D36AB73C93A2675567CA015A10337AE204202FEAB2AD3FC2353A1682F9190A33171E8A"),
						200);
				logger.log(Status.PASS, PageObjects.methodNameToGetSheetName + " successful");
			}
		} catch (Exception e) {
			CommonMethods.insideCatchOne(logger);
		}
	}

	/*
	 * @Test(enabled = true, priority=2, dependsOnMethods = { "login" }, description
	 * = "add question manually", retryAnalyzer = common.CommonMethods.class) public
	 * void createQuestionManually() throws InterruptedException {
	 * 
	 * driver.navigate().to(PageObjects.urlAdmin);
	 * 
	 * CommonMethods.TestfindElement(pageObjects.courseManagementXpath, "",
	 * "click"); CommonMethods.TestfindElement(pageObjects.assessmentQueXpath, "",
	 * "click"); CommonMethods.TestfindElement(pageObjects.addNewQueXpath, "",
	 * "click");
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
	 */

	/*
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
	@AfterSuite(enabled = true)
	public void closeBrowser() {
		// PageObjects.driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
		System.out.println("in after suit---------");
		if(PageObjects.prop.getProperty("EnableMailShoot").equalsIgnoreCase("true")) {
		String from = PageObjects.prop.getProperty("From");
		
		String MailIdPassword = PageObjects.prop.getProperty("MailIdPassword");
		
		String toCommaSeperated  = PageObjects.prop.getProperty("To");
		List<String> toList = Arrays.asList(toCommaSeperated.split("\\s*,\\s*"));
		 String []to = toList.toArray(new String[toList.size()]);
		 
		 String ccCommaSeperated = PageObjects.prop.getProperty("Cc");
	        List<String> ccList = Arrays.asList(ccCommaSeperated.split("\\s*,\\s*"));
	        String []cc = toList.toArray(new String[ccList.size()]);
		
		SendMail.sendMail(from, MailIdPassword, to, cc);	
	}
		PageObjects.driver.get(PageObjects.htmlReportPath);
	}

}
