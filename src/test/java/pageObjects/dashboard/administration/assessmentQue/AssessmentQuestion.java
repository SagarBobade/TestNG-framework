package pageObjects.dashboard.administration.assessmentQue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class AssessmentQuestion {

	WebDriver driver = null;
	final int i =0;
	public AssessmentQuestion(WebDriver driver) {
		System.out.println("in AssessmentQuestion constructor");
		this.driver = driver;
	}

	
	@FindAll({ @FindBy(xpath = "//div//button//i[@class='fa fa-plus']") })
	private WebElement addNew;

	@FindAll({ @FindBy(id = "questionTex") })
	private WebElement question;
	
	public String question2 = "questionTex";
	
	@FindAll({ @FindBy(xpath = "//input[@placeholder='Please enter metadata']") })
	private WebElement metaData;
	
	@FindAll({ @FindBy(id = "difficultylevel") })
	private WebElement questionLevel;
	
	@FindAll({ @FindBy(id = "totalmarks") })
	private WebElement marksPerQuestion;
	
	@FindAll({ @FindBy(xpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[1]/div[5]/div/label")})
	private WebElement isActiveQuestion;
	
	@FindAll({ @FindBy(xpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[1]/div[6]/div/label") })
	private WebElement isMemoQuestion;
	
	@FindAll({ @FindBy(id = "labelForOptions") })
	private WebElement optionsCount;
	
	@FindAll({ @FindBy(id = "contentType") })
	private WebElement QuestionType;
	
	@FindAll({ @FindBy(xpath = "div[+]/div/div/div/textarea") })
	private WebElement optionsWebTable;
	
	public String optionWebTable1 = "/html[1]/body[1]/modal-container[1]/div[1]/div[1]/div[2]/app-assessment-new[1]/form[1]/div[1]/div[2]/div[";
	public String optionsWebTable2 = "]/div[1]/div[1]/textarea[1]";
	
	@FindAll({ @FindBy(xpath = "//button[@class='btn btn-success ng-star-inserted']") })
	private WebElement saveQuestion;
	
	
	public String getQuestion2() {
		return question2;
	}

	public String getOptionsWebTable2() {
		return optionsWebTable2;
	}

	public WebElement getSaveQuestion() {
		return saveQuestion;
	}

	public String getCorrectOption1() {
		return correctOption1;
	}

	public String getCorrectOption2() {
		return correctOption2;
	}

	public String getOptionWebTable1() {
		return optionWebTable1;
	}

//	@FindAll({ @FindBy(xpath = "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[2]/div[1]/div/div[2]/label[2]") })
//	private WebElement correctOption;
	
	
	public String correctOption1= "/html/body/modal-container/div/div/div[2]/app-assessment-new/form/div/div[2]/div[";
	public String correctOption2= "]/div/div[2]/label[2]";
	

	public WebDriver getDriver() {
		return driver;
	}

	public int getI() {
		return i;
	}

	public WebElement getMetaData() {
		return metaData;
	}

	public WebElement getQuestionLevel() {
		return questionLevel;
	}

	public WebElement getMarksPerQuestion() {
		return marksPerQuestion;
	}

	public WebElement getIsActiveQuestion() {
		return isActiveQuestion;
	}

	public WebElement getIsMemoQuestion() {
		return isMemoQuestion;
	}

	public WebElement getOptionsCount() {
		return optionsCount;
	}

	public WebElement getQuestionType() {
		return QuestionType;
	}

	public WebElement getOptionsWebTable() {
		return optionsWebTable;
	}

/*	public WebElement getCorrectOption() {
		return correctOption;
	}
*/
	public WebElement getQuestion() {
		return question;
	}

	public WebElement getAddNew() {
		return addNew;
	}
}
