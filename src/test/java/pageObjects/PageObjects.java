package pageObjects;

public class PageObjects {

	//url
	public static String loginUrl = "http://192.168.91.48/login";
	public static String urlAdmin = "http://192.168.91.48/administration/admin";
	
	//id
	public static String userIdId = "userid";
	public static String passwordId = "password";
	public static String orgCodeId = "organizationcode";
	public static String questionLevelId = "difficultylevel";
	public static String marksPerQuestionId = "totalmarks";
	public static String activeQuestionId = "status";
	public static String optionsCountId = "labelForOptions";
	public static String chooseFile = "dataPath";
	
	//xpath
	public static String loginBtnXpath = "//button[@type='submit']";
	public static String courseManagementXpath = "//*[@iDd=\"pills-home-tab 0\"]";
	public static String assessmentQueXpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-admin/div/div[2]/div/div/div/div[1]/div[1]/div/div";
	public static String addNewQueXpath = "/html/body/app-root/app-full-layout/div[1]/main/div/app-assessment/div[2]/div/div/form/div[1]/button[1]/i";
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
	public static int counter = 0;
	public static int retryLimit = 0;
	
	
}
