package dtn.automation.core;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




import dtn.automation.utilities.CommonMethods;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Log;

public class CheckBox extends Element {
	private WebDriver driver;
	private WebElement element=null;
	private By by;
	public String elementName;
	
	public CheckBox(By by, String elementName) {
		super(by, elementName);	
		this.by = by;
		this.elementName = elementName;
	}
	
	/**
	 * Click on the element and wait until Page is loaded completely. 
	 * This method will log as debug in log file.
	 * @throws Exception
	 */
	public void checked() throws Exception{		
		try {									
			this.element = super.findElement();
			driver = super.getDriver();
			if(this.element.isSelected()){
				Log.debug(this.elementName + " is checked already.");
				return;
			}
			new Actions(driver).moveToElement(this.element).perform();
			Thread.sleep(200);
			//if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException		
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					CommonMethods.clickDifferentBrowser(this.by);		
					Thread.sleep(1000);					
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
						Log.info("+ Select " + elementName);
			    		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.CheckBox | Method: checked | Exception occured - "+e1.toString());
			    		throw (e1);
			    	}
					Thread.sleep(1000);
					this.element = findElement();
				}catch(Exception e){	//StaleElementReferenceException
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
						Log.info("+ Select " + elementName);
			    		Log.fail("** Actual result: User can NOT check "+ elementName,"Class: core.CheckBox | Method: checked | Exception occured - "+e.toString());
			    		throw (e);
			    	}
			    	Thread.sleep(1000);			    	
				}
			}	
			Log.info("+ Select " + elementName);
		}catch (Exception e){ 
			Log.info("+ Select " + elementName);
       		Log.fail("** Actual result: User can NOT check "+ elementName,"Class: core.CheckBox | Method: checked | Exception occured: "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	/**
	 * Click on the element and wait until Page is loaded completely. 
	 * This method will log as debug in log file.
	 * @throws Exception
	 */
	public void unchecked() throws Exception{		
		try {									
			this.element = findElement();
			driver = super.getDriver();
			if(!this.element.isSelected()){
				Log.debug(this.elementName + " is unchecked already.");
				return;
			}
			new Actions(driver).moveToElement(this.element).perform();
			Thread.sleep(200);
			//if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException		
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					CommonMethods.clickDifferentBrowser(this.by);		
					Thread.sleep(1000);					
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
						Log.info("+ Select " + elementName);
			    		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.CheckBox | Method: checked | Exception occured - "+e1.toString());
			    		throw (e1);
			    	}
					Thread.sleep(1000);
					this.element = findElement();
				}catch(Exception e){	//StaleElementReferenceException
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
						Log.info("+ Select " + elementName);
			    		Log.fail("** Actual result: User can NOT check "+ elementName,"Class: core.CheckBox | Method: checked | Exception occured - "+e.toString());
			    		throw (e);
			    	}
			    	Thread.sleep(1000);			    	
				}
			}	
			Log.info("+ Select " + elementName);
		}catch (Exception e){ 
			Log.info("+ Select " + elementName);
       		Log.fail("** Actual result: User can NOT check "+ elementName,"Class: core.CheckBox | Method: checked | Exception occured: "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
}
