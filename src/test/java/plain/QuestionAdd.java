package plain;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
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
import io.restassured.response.Response;
import pageObjects.PageObjects;
import pageObjects.dashboard.administration.Administration;
import pageObjects.dashboard.administration.assessmentQue.AssessmentQuestion;
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

			// initialization
			String userId = CommonMethods.getValue("userIdValue");
			String password = CommonMethods.getValue("EncodedPassword");
			String org = CommonMethods.getValue("orgCodeValue");

			// checking for api testing or GUI testing
			if (checkAPI == false && checkUI == true) {
				PageObjects.driver.get(PageObjects.loginUrl);
				CommonMethods.TestfindElement(logger, loginPage.getUserId(), userId, "type");
				CommonMethods.TestfindElement(logger, loginPage.getPassword(), password, "type");
				CommonMethods.TestfindElement(logger, loginPage.getOrgCode(), org, "type");
				CommonMethods.TestfindElement(logger, loginPage.getLoginButton(), "", "click");

				Thread.sleep(3000);
				if (!PageObjects.driver.getCurrentUrl().contains("admin")) {
					throw new Exception();
				}
				logger.log(Status.PASS, "login successfully");
			} else {
				// api call
				api.CommonMethods.hitUserAuthAPI(CommonMethods.getValue("API request"), org, userId, password);

				// reporting
				logger.log(Status.INFO, userId);
				logger.log(Status.INFO, password);
				logger.log(Status.INFO, org);
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

		// initialization
		String question = CommonMethods.getValue("Question");
		String metaData = CommonMethods.getValue("MetaData");
		String questionLevel = CommonMethods.getValue("QuestionLevel");
		String marksPerQuestion = CommonMethods.getValue("MarksPerQuestion");
		String isActiveQuestion = CommonMethods.getValue("IsActiveQuestion");
		String isMemoQuestion = CommonMethods.getValue("IsMemoQuestion");
		String optionsCount = CommonMethods.getValue("OptionsCount");
		String questionType = CommonMethods.getValue("QuestionType");
		String correctOption = CommonMethods.getValue("correctOption");

		if (checkAPI == false && checkUI == true) {
			Thread.sleep(5000);
			if (PageObjects.driver.getCurrentUrl().contains("admin")) {
				CommonMethods.TestfindElement(logger, PageObjects.getAdministration(), "", "click");
				requiredTab = administration.getTab(logger, "Course Management");
				CommonMethods.TestfindElement(logger, requiredTab, "", "click");
				CommonMethods.TestfindElement(logger, administration.getAssessmentQuestion(), "", "click");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getAddNew(), "", "click");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestion(), question, "type");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getMetaData(), metaData, "type");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestionLevel(), questionLevel, "select");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getMarksPerQuestion(), marksPerQuestion,
						"select");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getIsActiveQuestion(), isActiveQuestion,
						"tick");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getIsMemoQuestion(), isMemoQuestion, "tick");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getOptionsCount(), optionsCount, "select");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestionType(), questionType, "select");
				CommonMethods.webTableOp(logger, assessmentQuestion.optionWebTable1,
						assessmentQuestion.optionsWebTable2, optionsCount, "type");
				CommonMethods.webTableOp(logger, assessmentQuestion.correctOption1, assessmentQuestion.correctOption2,
						correctOption, "tick");
				CommonMethods.TestfindElement(logger, assessmentQuestion.getSaveQuestion(), "", "click");

				Thread.sleep(3000);
				String textFromGrid = CommonMethods.webTableOp(logger, assessmentQuestion.savedQuestionText1,
						assessmentQuestion.savedQuestionText2, question, "getText");

				if (textFromGrid.equalsIgnoreCase(question)) {
					logger.log(Status.PASS, PageObjects.methodNameToGetSheetName + " successfully");
				} else {
					logger.log(Status.FAIL, PageObjects.methodNameToGetSheetName + " Failed");
				}
			}
		} else {
			// Checking whether entered question is unique or not ?
			// initialization
			Response response = null;
			String APIUrl = CommonMethods.getValue("API request1");
			String httpMethod = CommonMethods.getValue("Method1");
			Double expectedResponseCode = Double.parseDouble(CommonMethods.getValue("Expected response code1"));
			String requestBody = CommonMethods.getValue("Parameter json1");

			// api call
			response = api.CommonMethods.testResponseCode(APIUrl, httpMethod, expectedResponseCode, requestBody);

			if (response != null) {
				// if not duplicate que
				if (response.getBody().asString().toString().equals("false")) {
					logger.log(Status.INFO, PageObjects.methodNameToGetSheetName + " Unique question");

					response = null;
					APIUrl = CommonMethods.getValue("API request2");
					httpMethod = CommonMethods.getValue("Method2");
					expectedResponseCode = Double.parseDouble(CommonMethods.getValue("Expected response code2"));
					requestBody = CommonMethods.getValue("Parameter json2");
					// Saving question
					response = api.CommonMethods.testResponseCode(APIUrl, httpMethod, expectedResponseCode,
							requestBody);
					if (response != null) {
						if (response.getStatusCode() == Integer
								.parseInt(CommonMethods.getValue("Expected response code2"))) {
						}
					}
				} else {
					logger.log(Status.INFO, "Duplicate question " + requestBody);
				}
			}
			logger.log(Status.PASS, "Duplicate question");
		}
	}
/*
	@Test(enabled = true, priority = 3, dependsOnMethods = { "login" }, description = "Search question")
	public void searchQuestion() throws IOException, Exception {
		
		  PageObjects.methodNameToGetSheetName = new Object() {
		  }.getClass().getEnclosingMethod().getName();
		  System.out.println("We are in:: " + PageObjects.methodNameToGetSheetName);
		  logger = PageObjects.extent.createTest(PageObjects.methodNameToGetSheetName);
		  System.out.println("1"); CommonMethods.TestfindElement(logger,
		  assessmentQuestion.getSearchQuestion(), CommonMethods.getValue("Question",
		  "createQuestionManually"), "type"); System.out.println("2");
		  CommonMethods.TestfindElement(logger, assessmentQuestion.getSearchIcon(), "",
		  "click"); Thread.sleep(2000);
		  }

	@Test(enabled = true, priority = 4, dependsOnMethods = { "login" }, description = "Edit question")
	public void editQuestion() throws IOException, Exception {

		PageObjects.methodNameToGetSheetName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		logger = PageObjects.extent.createTest(PageObjects.methodNameToGetSheetName);

		
		  List<String> editList = CommonMethods.getKeys();
		  
		  for (String editElement : editList) { System.out.println(editElement);
		  edit(editElement, ) }
		 
		 CommonMethods.TestfindElement(logger, assessmentQuestion.getQuestion(),
		 CommonMethods.getValue("Question"),
		 "type");

		 CommonMethods.TestfindElement(logger, assessmentQuestion.getEditQuestion(),
		 "", "click");
		 CommonMethods.TestfindElement(logger, assessmentQuestion.getEditQuestion(),
		 "", "click");

	}
*/
	@AfterSuite(enabled = true)
	public void closeBrowser() throws ParseException, InterruptedException {
		// PageObjects.driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
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
		Thread.sleep(5000);
		PageObjects.driver.get(PageObjects.htmlReportPath);
	}

}
