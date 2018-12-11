package dtn.automation.core;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;

import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Log;


/*
    Description: Methods/Functions for Browser
    @author Thao.Le
 */

public class Browser {
		
	private WebDriver driver;	
	private static String browser;
	
	public Browser(){		
		browser = "Chrome";	
		launchBrowser();
	}
	public Browser(String sBrowser){		
		browser = sBrowser;		
		launchBrowser();
	}
	public Browser(WebDriver driver, String browser) {		
		this.driver = driver;
		this.browser = browser;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public static String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public void launchBrowser(){	
		String tempBrowser = browser.toUpperCase();
		try{
			switch(tempBrowser){
			case "FIREFOX":
				System.setProperty("webdriver.gecko.driver", DTNConstant.DriverPath + "geckodriver.exe");
				driver =new FirefoxDriver();
				break;
			case "IE":
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();;
				capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//				capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
				capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS,true);				
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				
				InternetExplorerOptions options = new InternetExplorerOptions(capabilities);
				String startCmd = "cmd /c start " + DTNConstant.DriverPath+ "DisableIESecuritiySettings.vbs";
				Log.debug("Run DisableIESecuritiySettings.vbs to disable all IE Security Setting.");
				Runtime.getRuntime().exec(startCmd);
				System.setProperty("webdriver.ie.driver", DTNConstant.DriverPath + "IEDriverServer.exe");			 				
				driver =new InternetExplorerDriver(options);
				break;
			case "CHROME":			
				System.setProperty("webdriver.chrome.driver", DTNConstant.DriverPath + "chromedriver.exe");
				driver =new ChromeDriver();
				break;
			default : 
				System.setProperty("webdriver.chrome.driver", DTNConstant.DriverPath + "chromedriver.exe");
				driver =new ChromeDriver();
				break;
			}					
			driver.manage().window().maximize();			
			Page.waitForPageLoad(driver);	
		}catch (Exception e){
			Log.fail("FAILED : "+ browser + " browser is not opened","Class: core.Browser | Method: launchBrowser | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			//throw(e);			
		}
	}
	public void navigateURL(String sURL) throws Exception{		
		Log.info("+ Navigate to "+ sURL);
		try {
			driver.get(sURL);	
			Page.waitForPageLoad(driver);
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.fail("FAILED : '"+sURL + "' is not opened.","Class: core.Browser | Method: navigateURL | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);			
		}		
	}
	public void closeBrowser() throws Exception{					
		try {
			Log.info("+ Close "+browser+" browser");
			driver.quit();			
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.fail("FAILED : "+ browser + " browser is not closed","Class: core.Browser | Method: closeBrowser | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}		
	}
}
