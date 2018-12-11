package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;
import dtn.automation.core.Page;
import dtn.automation.utilities.Frame;

public class LoginPage extends BaseTestSuite{	
	public static final String PageName = "Login Page";	
	//public static Element txtPassword = new Element(By.xpath("//input[@class='signup-form-element' and @name='password']"),"Password textbox");
	//public static Element txtLoginID = new Element(By.xpath("//input[@class='signup-form-element' and @name='username']"),"Login ID textbox");
	public static Element txtPassword = new Element(By.xpath("//input[@name='password']"),"Password textbox");
	public static Element txtLoginID = new Element(By.xpath("//input[@name='username']"),"Login ID textbox");
	//public static Element btnLogin = new Element(By.id("_login"),"Login button");
	public static Element btnLogin = new Element(By.cssSelector("input[type='submit']"),"Login button");
	
	/**
	 * To log in DTN web app
	 * @param URL
	 * @param logInUserName
	 * @param logInPassword
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void logIn(String URL,String logInUserName, String logInPassword) throws Exception{					
		browser.navigateURL(URL);
		
		LoginPage.txtLoginID.enter(logInUserName);
		LoginPage.txtPassword.enterPassword(logInPassword);
		LoginPage.btnLogin.click();	
		Frame.moveToChildFrame(driver, CommonObjects.parentFrame, CommonObjects.mainFrame);		
		Page.verifyPageDisplayedWithText(driver, "Home page", "Welcome to Dealertrack");
			
	}	
}
