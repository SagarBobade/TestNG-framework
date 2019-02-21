package pageObjects.dashboard.administration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.PageObjects;

public class Administration {

	WebDriver driver = null;

	public Administration(WebDriver driver) {
		System.out.println("in Administration constructor");
		this.driver = driver;
	}

	//@FindAll({ @FindBy(xpath = "//nav//a[@class='nav-link border-zero tablinks']") })
	@FindAll({ @FindBy(xpath = "//*[@id=\"pills-home-tab +i+\"]") })
	private List<WebElement> administrationNavbar;
	
	@FindAll({ @FindBy(xpath = "//*[@id='cm']") })
	private WebElement courseManagement;


	@FindAll({ @FindBy(xpath = "//*[@id='aq']") })
	private WebElement assessmentQuestion;
	
	public WebElement getAssessmentQuestion() {
		return assessmentQuestion;
	}	
	
	public WebElement getCourseManagement() {
		return courseManagement;
	}

	public void setCourseManagement(WebElement courseManagement) {
		this.courseManagement = courseManagement;
	}

	public List<WebElement> getAdministrationNavbar() {
		return administrationNavbar;
	}

	public void setAdministrationNavbar(List<WebElement> administrationNavbar) {
		this.administrationNavbar = administrationNavbar;
	}

	public WebElement getTab(ExtentTest logger, String string) {

		WebElement webElement= null;
		System.out.println("in getTab for : "+ string);
		System.out.println(administrationNavbar.size());
		for(int i=0; i<8; i++) {
			webElement = PageObjects.driver.findElement(By.xpath("//*[@id=\"pills-home-tab "+i+"\"]"));
			System.out.println("+"+webElement.getText().toString()+"+ \n");
			if(webElement.getText().toString().equalsIgnoreCase(string)) {
				System.out.println("found");
				return webElement;
			}
		}
		System.out.println("null");
		return null;
	}
}
