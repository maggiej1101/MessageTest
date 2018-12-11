package dtn.automation.core;

import org.openqa.selenium.WebDriver;

public class BaseDriver {
	private static WebDriver driver;
	public BaseDriver(WebDriver driver){
		this.driver = driver;
	}
	public static WebDriver getDriver(){
		return driver;
	}
}
