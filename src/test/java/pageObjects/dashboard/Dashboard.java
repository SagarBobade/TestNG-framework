package pageObjects.dashboard;

import org.openqa.selenium.WebDriver;

public class Dashboard {

	WebDriver driver = null;
	
	public Dashboard(WebDriver driver) {
		System.out.println("in dashboard constructors");
		this.driver = driver;
	}
	
	
}
