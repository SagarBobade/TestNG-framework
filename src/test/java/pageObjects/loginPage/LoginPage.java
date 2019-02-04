package pageObjects.loginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	WebDriver driver = null;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindAll({ @FindBy(id = "userid"), @FindBy(xpath = "//*[@id=\"userid\"]") })
	public WebElement userIdId;

	@FindAll({ @FindBy(id = "password") })
	public WebElement passwordId;

	@FindAll({ @FindBy(id = "organizationcode") })
	public WebElement orgCodeId;

	@FindAll({ @FindBy(xpath = "//button[@type='submit']") })
	public WebElement loginButton;
}
