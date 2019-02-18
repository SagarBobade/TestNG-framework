package pageObjects.loginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	WebDriver driver = null;

	public LoginPage(WebDriver driver) {
		System.out.println("in loginPage constructor");
		this.driver = driver;
	}

	public WebElement getUserId() {
		
		System.out.println("in userid method");
		return userId;
	}

	public void setUserId(WebElement userId) {
		this.userId = userId;
	}

	public WebElement getPassword() {
		return password;
	}

	public void setPassword(WebElement password) {
		this.password = password;
	}

	public WebElement getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(WebElement orgCode) {
		this.orgCode = orgCode;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(WebElement loginButton) {
		this.loginButton = loginButton;
	}

	//update key column in excel
	@FindAll({ @FindBy(id = "userid"), @FindBy(xpath = "//*[@id='userid']") })
	private WebElement userId;

	@FindAll({ @FindBy(id = "password") })
	private WebElement password;

	@FindAll({ @FindBy(id = "organizationcode") })
	private WebElement orgCode;

	@FindAll({ @FindBy(xpath = "//button[@type='submit']") })
	private WebElement loginButton;
}
