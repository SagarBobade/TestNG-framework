package plain;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
import pageObjects.dashboard.administration.Administration;
import pageObjects.dashboard.administration.assessmentQue.AssessmentQuestion;
import pageObjects.dashboard.administration.courseCatalogue.CourseCatalogue;
import pageObjects.loginPage.LoginPage;

public class QuestionAdd {

	public LoginPage loginPage;
	public PageObjects pageObjects;
	public Administration administration;
	public AssessmentQuestion assessmentQuestion;

	public static boolean isFind = true;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static Boolean checkAPI = false;
	public static Boolean checkUI = true;
	private String browserName = null;
	public ExtentTest logger = null;
	public WebElement requiredTab = null;

	@BeforeClass
	public void beforeClass() {
		loginPage = PageFactory.initElements(PageObjects.driver, LoginPage.class);
		administration = PageFactory.initElements(PageObjects.driver, Administration.class);
		pageObjects = PageFactory.initElements(PageObjects.driver, PageObjects.class);
		assessmentQuestion = PageFactory.initElements(PageObjects.driver, AssessmentQuestion.class);
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
			browserName = PageObjects.prop.getProperty("BrowserName");
			switch (browserName) {
			case "chrome":
				System.out.println("in chrome");
				WebDriverManager.chromedriver().setup();
				PageObjects.driver = new ChromeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				PageObjects.driver = new FirefoxDriver();
				break;
			case "ie":
				WebDriverManager.iedriver().setup();
				PageObjects.driver = new InternetExplorerDriver();
				break;
			case "headless":
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				options.addArguments("window-size=1200x600");
				PageObjects.driver = new ChromeDriver(options);
				break;
			}
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
		logger = PageObjects.extent.createTest(PageObjects.methodNameToGetSheetName);

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

				Thread.sleep(5000);
				if (!PageObjects.driver.getCurrentUrl().contains("admin")) {
					throw new Exception();
				}
				logger.log(Status.PASS, "login successfully");
			} else {

				Assert.assertEquals(
						api.CommonMethods.hitUserAuthAPI(CommonMethods.getValue("API request"),
								CommonMethods.getValue("orgCodeValue"), CommonMethods.getValue("userIdValue"),
								CommonMethods.getValue("EncodedPassword")),
						CommonMethods.getValue("Expected response code"));

				logger.log(Status.PASS, PageObjects.methodNameToGetSheetName + " successfully");
			}
		} catch (Exception e) {
			CommonMethods.insideCatchOne(logger);
		}
	}

	@Test(enabled = true, priority = 2, dependsOnMethods = { "login" }, description = "add question manually")
	public void createQuestionManually() throws IOException, Exception {

		PageObjects.methodNameToGetSheetName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		logger = PageObjects.extent.createTest(PageObjects.methodNameToGetSheetName);
		Thread.sleep(5000);
		if (PageObjects.driver.getCurrentUrl().contains("admin")) {
			CommonMethods.TestfindElement(logger, PageObjects.getAdministration(), "", "click");
			requiredTab = administration.getTab(logger, "Course Management");
			CommonMethods.TestfindElement(logger, requiredTab, "", "click");
			CommonMethods.TestfindElement(logger, administration.getAssessmentQuestion(), "", "click");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getAddNew(), "", "click");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestion(), CommonMethods.getValue("Question"),
					"type");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getMetaData(), CommonMethods.getValue("MetaData"),
					"type");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestionLevel(),
					CommonMethods.getValue("QuestionLevel"), "select");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getMarksPerQuestion(),
					CommonMethods.getValue("MarksPerQuestion"), "select");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getIsActiveQuestion(),
					CommonMethods.getValue("IsActiveQuestion"), "tick");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getIsMemoQuestion(),
					CommonMethods.getValue("IsMemoQuestion"), "tick");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getOptionsCount(),
					CommonMethods.getValue("OptionsCount"), "select");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestionType(),
					CommonMethods.getValue("QuestionType"), "select");
			CommonMethods.webTableOp(logger, assessmentQuestion.optionWebTable1,
					assessmentQuestion.optionsWebTable2, CommonMethods.getValue("OptionsCount"), "type");
			CommonMethods.webTableOp(logger, assessmentQuestion.correctOption1, assessmentQuestion.correctOption2,
					CommonMethods.getValue("correctOption"), "tick");
			CommonMethods.TestfindElement(logger, assessmentQuestion.getSaveQuestion(), "", "click");
			
			/*if(CommonMethods.webTableOp(logger, assessmentQuestion.savedQuestionText1, assessmentQuestion.savedQuestionText2,
					CommonMethods.getValue("Question"), "getText").equalsIgnoreCase(CommonMethods.getValue("Question"))){
				System.out.println("yes it is saved successfully");
			}
*/
			Thread.sleep(5000);
			String textFromGrid = CommonMethods.webTableOp(logger, assessmentQuestion.savedQuestionText1, assessmentQuestion.savedQuestionText2,
					CommonMethods.getValue("Question"), "getText");
			
			System.out.println(textFromGrid);

			if(textFromGrid.equalsIgnoreCase(CommonMethods.getValue("Question"))) {
				System.out.println("saved que successfully");
			}

		}
	}

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
	public void closeBrowser() throws ParseException, InterruptedException {
		// PageObjects.driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
		System.out.println("in after suit---------");
		if (PageObjects.prop.getProperty("EnableMailShoot").equalsIgnoreCase("true")) {
			String from = PageObjects.prop.getProperty("From");

			String MailIdPassword = PageObjects.prop.getProperty("MailIdPassword");

			String toCommaSeperated = PageObjects.prop.getProperty("To");
			List<String> toList = Arrays.asList(toCommaSeperated.split("\\s*,\\s*"));
			String[] to = toList.toArray(new String[toList.size()]);

			String ccCommaSeperated = PageObjects.prop.getProperty("Cc");
			List<String> ccList = Arrays.asList(ccCommaSeperated.split("\\s*,\\s*"));
			String[] cc = toList.toArray(new String[ccList.size()]);

			SendMail.sendMail(from, MailIdPassword, to, cc);
		}
		if (PageObjects.prop.getProperty("OnlyCheckAPI").equalsIgnoreCase("true")) {
			browserName = PageObjects.prop.getProperty("BrowserName");
			switch (browserName) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				PageObjects.driver = new ChromeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				PageObjects.driver = new FirefoxDriver();
				break;
			case "ie":
				WebDriverManager.iedriver().setup();
				PageObjects.driver = new InternetExplorerDriver();
				break;

			}
		}
		// Thread.sleep(10000);
		// PageObjects.driver.get(PageObjects.htmlReportPath);
	}

}
