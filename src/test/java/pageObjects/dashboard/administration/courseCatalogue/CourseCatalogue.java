package pageObjects.dashboard.administration.courseCatalogue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class CourseCatalogue {

	WebDriver driver = null;

	public CourseCatalogue(WebDriver driver) {
		System.out.println("in CourseCatalogue constructor");
		this.driver = driver;
	}

	@FindAll({ @FindBy(xpath = "//*[@tooltip='Add New']") })
	private WebElement addNew;

	public WebElement getAddNew() {
		return addNew;
	}

}
