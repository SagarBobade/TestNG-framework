import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import pageObjects.PageObjects;

public class Test {

	static String methodName;
	
	public static void fun() {
		
		 methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	      
		System.out.println(methodName);
	}
	

	public static void main(String[] args) {

		fun();

	}

}
