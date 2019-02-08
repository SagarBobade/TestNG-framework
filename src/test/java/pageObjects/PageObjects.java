package pageObjects;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;



public class PageObjects {

	public static WebDriver driver = null;
	public static String propertiesFilePath = "D://my data//Eclipse - jan18 19 workspace//ModuleTestA//src//test//java//Configuration.Properties";
	public static Properties prop = null;
	
	public PageObjects(WebDriver driver)
	{
		this.driver = driver;
	}
	
	//url
	public static String loginUrl = "http://192.168.91.48/login";
	public static String urlAdmin = "http://192.168.91.48/administration/admin";
	
	//id
	/*@FindAll({@FindBy(id = "userid"),
			@FindBy(xpath = "//*[@id=\"userid\"]")})
	public WebElement userIdId;
	
	@FindAll({@FindBy(id = "password")})
	public WebElement passwordId;
	
	@FindAll({@FindBy(id = "organizationcode")})
	public WebElement orgCodeId;
	
	@FindAll({@FindBy(xpath = "//button[@type='submit']")})
	public WebElement loginButton;*/
	
	@FindAll({@FindBy(id = "difficultylevel")})
	public WebElement questionLevelId;
	
	@FindAll({@FindBy(id = "totalmarks")})
	public WebElement marksPerQuestionId;
	
	@FindAll({@FindBy(id = "status")})
	public WebElement activeQuestionId;
	
	@FindAll({@FindBy(id = "labelForOptions")})
	public WebElement optionsCountId;
	
	@FindAll({@FindBy(id = "dataPath")})
	public WebElement chooseFile;
	
	//xpath
	@FindAll({@FindBy(xpath = "//*[@id=\\\"pills-home-tab 0\\\"]")})
	public WebElement courseManagementXpath;
	//public static String courseManagementXpath = "//*[@id=\"pills-home-tab 0\"]";
	
	@FindAll({@FindBy(xpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-admin/div/div[2]/div/div/div/div[1]/div[1]/div/div")})
	public WebElement assessmentQueXpath;
	//public static String assessmentQueXpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-admin/div/div[2]/div/div/div/div[1]/div[1]/div/div";
	
	@FindAll({@FindBy(xpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-assessment/div[2]/div/div/form/div[1]/button[1]/i")})
	public WebElement addNewQueXpath;
	//public static String addNewQueXpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-assessment/div[2]/div/div/form/div[1]/button[1]/i";
	
	
	public static String questionTextboxXpath = "//*[@id=\"questionTex\"]";
	public static String metaDataXpath = "//input[@placeholder='Please enter metadata']";
	public static String optionAXpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[2]/div[1]/div/div[1]/textarea";
	public static String optionBXpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[2]/div[2]/div/div[1]/textarea";
	public static String correctAnswerXpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[2]/div[1]/div/div[2]/label[2]/span";
	public static String addQuestionXpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[3]/div[2]/div/button";
	public static String saveQuestionXpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div[2]/button[1]";
	public static String importIconXpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-assessment/div[2]/div/div/form/div[1]/button[2]";
	public static String importSelectedFile = "/html[1]/body[1]/modal-container[1]/div[1]/div[1]/div[2]/div[2]/button[1]";
	
	//values
	public static String userIdValue = "sammir";
	public static String passwordValue = "Pass@123";
	public static String orgCodeValue = "ltfs";
	public static String typeQuestion = "Manually added question";
	public static String questionLevelValue = "Difficult";
	public static String marksPerQuestionIdValue = "3";
	public static String optionsCountValue = "2";
	public static String questionBankImportFilePath = "D:\\my data\\Eclipse - jan18 19 workspace\\ModuleTestA\\src\\test\\resources\\importFiles\\AssessmentQuestionBank.xlsx";
	public static int counterOfTry = 1;
	public static int retryLimit = 3;
	public static String QuestionAddSheetNameForKeyValue = "QuestionAdd";
	
	//other
	public static String methodNameToGetSheetName = null;
	public static String tokenValue = "";
	public static WebElement lastAccessedWebElement = null;
	public static boolean alreadyCatchedException = false;
	public static ExtentReports extent;
	public static ExtentHtmlReporter reporter;
	public static String htmlDirectoryPath = "D://my data//Eclipse - jan18 19 workspace//ModuleTestA//test-output//Reports";
	public static String htmlReportPath = htmlDirectoryPath + "//Report.html";
	public static ExtentTest logger = null;
}
