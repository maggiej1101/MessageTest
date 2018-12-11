package dtn.automation.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.util.Strings;

import dtn.automation.core.BaseDriver;
import dtn.automation.core.Page;
import dtn.automation.utilities.CommonMethods;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Log;


/*
 	Description: This is a core functions for all elements in Web application.
 	This class has all methods to perform an action of HTML element such as click, enter text, select from the list,...
 */

public class Element {
	private WebDriver driver;
	private WebElement element=null;
	private By by;
	public String elementName;
	public Element(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * This is a construction method to initial a Element object
	 * @author Thao Le
	 * @param Driver
	 * @param Element
	 */
	public Element(WebDriver Driver,WebElement Element){
		this.driver = Driver;
		this.element = Element;		
	}
	/**
	 * This is a construction method to initial a Element object
	 * @author Thao Le
	 * @param Driver
	 * @param by
	 */
	public Element(WebDriver Driver, By by){
		this.driver = Driver;		
		this.by = by;
	}	
	/**
	 * This is a construction method to initial a Element object
	 * @param Driver
	 * @param by
	 * @param elementName
	 */
	public Element(WebDriver Driver, By by, String elementName){
		try{
			this.driver = Driver;			
			this.by = by;
			this.elementName = elementName;
		}catch(Exception e){
			throw(e);
		}
	}
	/**
	 * This is a construction method to initial a Element object
	 * @param Driver
	 * @param by
	 * @param elementName
	 */
	public Element(By by, String elementName){
		try{				
			this.by = by;
			this.elementName = elementName;
		}catch(Exception e){
			throw(e);
		}
	}
	/**
	 * Wait until the expected element is displayed. Time out for waiting period is in second.
	 * @author Thao Le
	 * @param by Element by value
	 * @param iSecond Time in second to wait until the element is displayed
	 * @return True if element is displayed, False if element is not displayed 
	 */
	public boolean waitUntilElementDisplay(By by,int iSecond){				
		boolean bStatus = false;		
		try{						
			for (int second = 0;second<iSecond; second++) {		    	
		    	if (isElementPresent(by)){
		    		bStatus = true;
		    		break; 
		    	}
		    	Thread.sleep(1000);
		    }			
		}catch(Exception e){				
			return false;
		}
		return bStatus;
	}	
	public void setElementTimeout(int timeout, TimeUnit timeUnit) {		
		driver.manage().timeouts().implicitlyWait(timeout, timeUnit);
	}
	/**
	 * Wait for the element visible on page using a constant timeout.
	 * @update Thao Le
	 * @param by Element by value
	 * @return True if element is displayed, False if element is not displayed 
	 */
	public boolean waitElementVisible(By by) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = driver.findElement(by);
			return true;
		}catch(Exception e){
			return false;
		}		
	}	
	
	/**
	 * Find an element on page using constant timeout. 
	 * @param by Element by value
	 * @return an element 
	 */
	public WebElement findElement() throws Exception{	
		try{
			Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is starting...");
			driver = BaseDriver.getDriver();			
			for(int timeout=0;timeout<DTNConstant.EXPLICIT_WAITTIME;timeout++){
				try{
					WebDriverWait wait = new WebDriverWait(driver,1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					element = driver.findElement(by);
					break;
				}catch(Exception e){	
					if(timeout==DTNConstant.EXPLICIT_WAITTIME-1){
						Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is NOT finished successfully. Total time waiting: "+timeout+1+" seconds.");
						throw (e);
					}					
				}
			}			
			Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is finished successfully.");
		}catch(Exception e){
			Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is NOT finished successfully.");
			throw (e);
		}
		return element;	
	}
	/**
	 * Find an element on page using constant timeout. 
	 * @param by Element by value
	 * @return an element 
	 */
	public WebElement findElement(int timeOut) throws Exception{	
		try{
			Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is starting...");
			driver = BaseDriver.getDriver();			
			for(int timeout=0;timeout<timeOut;timeout++){
				try{
					WebDriverWait wait = new WebDriverWait(driver,1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					break;
				}catch(Exception e){	
					if(timeout==timeOut-1){
						Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is NOT finished successfully. Total time waiting: "+timeout+1+" seconds.");
						throw (e);
					}					
				}
			}
			element = driver.findElement(by);
			Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is finished successfully.");
		}catch(Exception e){
			Log.debug("In Class: core.Element | Method: findElement() for "+this.elementName+" is NOT finished successfully.");
			throw (e);
		}
		return element;	
	}
	/**
	 * Check if element is enable.
	 * @return True if element is enable, False if element is disable.
	 */
	public boolean isEnabled() {		
		boolean enabled =  element.isEnabled();	
		return enabled;
	}
	
	/**
	 * Check if element is displayed on page.
	 * @return True if element is displayed, False if element is not displayed.
	 * @throws Exception 
	 */
	public boolean isDisplayed() throws Exception {		
		return findElement().isDisplayed();		
	}
	/**
	 * Check if element is displayed on page.
	 * @return True if element is displayed, False if element is not displayed.
	 * @throws Exception 
	 */
	public boolean isDisplayed(int timeOut) throws Exception {		
		return findElement(timeOut).isDisplayed();		
	}
	
	/**
	 * Check if element is displayed on page.
	 * @return True if element is displayed, False if element is not displayed.
	 * @throws Exception 
	 */
	 public Boolean isDisplayed(By by) {//throws Exception {
	    	// Boolean status = false;
		 	driver = BaseDriver.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, 30);
	    	try {
	    		 wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException	
	    	      if(driver.findElement(by).isDisplayed()) return true;
	    	      return false;
	    	   } catch (Exception e) {
	    	    //System.out.println("NO Element ..... false "+ by.toString());
	    	      return false;
	    	}
	    }
	
	
	/**
	 * Check if element is present on page (element may be presented on page, but disable or not displayed).
	 * @param by
	 * @return True if element is present, False if element is not present.
	 */
	private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	 }
	/**
	 * Check if an element (checkbox or radio-box) is selected or not.
	 * @return True if element is checked, False if element is unchecked.
	 * @throws Exception 
	 */
	public boolean isSelected() throws Exception {	
		this.element = findElement();
		boolean selected =  this.element.isSelected();		
		return selected;
	}
	/**
	 * Click on the element and wait until Page is loaded completely. 
	 * This method will log as debug in log file.
	 * @throws Exception
	 */
	public void click() throws Exception{	
		WebDriverWait wait;
		//int timeOut = DTNConstant.EXPLICIT_WAITTIME<=1 ? 1:DTNConstant.EXPLICIT_WAITTIME/2;
		try {
			Log.info("+ Click on " + elementName);			
			this.element = findElement();			
			Thread.sleep(200);
			//if element is disable, then wait until element is enable
			wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException		
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {					
					Thread.sleep(1000);
					this.element = driver.findElement(by);
					CommonMethods.clickDifferentBrowser(this.by);						
					Page.waitForPageLoad(driver);
					Page.waitUntilPopupDisappear(driver, DTNConstant.DTNPopupModal.getBy());	
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){			    		
			    		throw (e1);
			    	}
					
				}catch(Exception e){	//StaleElementReferenceException
					Log.debug(e.toString());
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){			    		
			    		throw (e);
			    	}		    	
				}
			}		
		}catch (Exception e){ 
       		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.Element | Method: click | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}	
	/**
	 * Click on the element and wait until Page is loaded completely. 
	 * This method will log as debug in log file.
	 * @throws Exception
	 */
	public void click(Element loadingPopup) throws Exception{
		try {
			Log.info("+ Click on " + elementName);			
			this.element = findElement();	
			//new Actions(driver).moveToElement(this.element).perform();
			Thread.sleep(200);
			//if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					Thread.sleep(1000);
					this.element = driver.findElement(by);
					CommonMethods.clickDifferentBrowser(this.by);	
					Thread.sleep(1000);
					Page.waitForPageLoad(driver);
					Page.waitUntilPopupDisappear(driver, loadingPopup.getBy());					
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){			    		
			    		throw (e1);
			    	}					
				}catch(Exception e){
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){			    		
			    		throw (e);
			    	}
				}
			}		
		}catch (Exception e){			
       		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.Element | Method: click | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	
	/**
	 * Click on the element and wait until Page is loaded completely. 
	 * This method will log as debug in log file.
	 * @throws Exception
	 * @author Thao Le
	 */
	public void clickAndIgnoreAlert() throws Exception{
		try {
			Log.info("+ Click on " + elementName);			
			this.element = findElement();
			new Actions(driver).moveToElement(this.element).perform();
			Thread.sleep(200);
			//if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					Thread.sleep(1000);
					this.element = driver.findElement(by);
					CommonMethods.clickDifferentBrowser(this.by);		
					Thread.sleep(1000);
					break;
				}catch(Exception e){	
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
			    		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.Element | Method: clickAndIgnoreAlert | Exception occured - "+e.toString());
			    		throw (e);
			    	}			    	
				}
			}	
			try{
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}catch(Exception e){				
			}
			Page.waitForPageLoad(driver);
			Thread.sleep(500);
		}catch (Exception e){			
       		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.Element | Method: click | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	/**
	 * Click on the element and wait until Page is loaded completely. 
	 * This method will log as debug in log file.
	 * @throws Exception
	 */
	public void clickJS() throws Exception{		
		try {
			Log.info("+ Click on " + elementName);	
			JavascriptExecutor js = (JavascriptExecutor)driver;	
			this.element = findElement();			
			Thread.sleep(200);
			//if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.elementToBeClickable(by));	//TimeoutException		
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					//js.executeScript("document.getElementById(objectID).click();");
					Thread.sleep(1000);
					this.element = driver.findElement(by);
					js.executeScript("arguments[0].click();",this.element);
					Page.waitForPageLoad(driver);
					Page.waitUntilPopupDisappear(driver, DTNConstant.DTNPopupModal.getBy());	
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){			    		
			    		throw (e1);
			    	}					
				}catch(Exception e){	//StaleElementReferenceException
					Log.debug(e.toString());
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){			    		
			    		throw (e);
			    	}	    	
				}
			}		
		}catch (Exception e){ 
       		Log.fail("** Actual result: User can NOT click on "+ elementName,"Class: core.Element | Method: click | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	/**
	 * Enter a text into a textbox element.
	 * @param text
	 * @throws Exception 
	 */
	public void enter(String text) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to select is null");
				return;
			}						
			this.element = findElement();
			String textInElement = this.element.getAttribute("value");
			if(textInElement.equals(text)){
				Log.debug(text + " is already in "+this.elementName);
				return;
			}		
			
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {										
					this.element.clear();			
					this.element.sendKeys(text);	
					Thread.sleep(1000);					
					break;
				}catch(Exception e){	
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
			    		throw (e);
			    	}
			    	Thread.sleep(1000);
			    	this.element = driver.findElement(by);
				}
			}
			Log.info("+ Enter "+text +" into " + elementName);		
		}catch (Exception e){	
			Log.info("+ Enter "+text +" into " + elementName);
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	
	/**
	 * Send a text into a textbox element (instead of enter chars one by one).
	 * @author Quinn Song modified	Added a loop to retry
	 * @param text					text to enter into textbox
	 * @throws Exception 			Exceptions that may occur
	 * @author Thao Le
	 */
	public void sendTextWithRetry(String text) throws Exception{
		try {
			if(Strings.isNullOrEmpty(text)){
				Log.debug("Warning: Text to enter is null or empty for " + elementName);
				return;
			}

			Log.info("+ Enter "+text +" into " + elementName + " with retry");

			this.element = findElement();
			String valueInTextbox = null;
			valueInTextbox = this.element.getAttribute("value");
			Log.debug("Current text is: " + valueInTextbox);
			// CHECK IF TEXT TO ENTER ALREADY EXISTS
			if (CommonMethods.getPureText(valueInTextbox).equals(CommonMethods.getPureText(text))) {
				Log.debug("Text to be entered already exists. Skipping this step.");
				return;
			}														
			//this.element = findElement();	
			valueInTextbox = null;
			// Replace DTNConstant.EXPLICIT_WAITTIME with MAX_FAIL_RETRY to avoid trying forever :)
			for (int retry = 0;retry<=DTNConstant.MAX_FAIL_RETRY; retry++) {
				this.element.clear();	
				Thread.sleep(1000);
				this.element.sendKeys(text);
				Thread.sleep(1000);
				enterKeyBoard(Keys.TAB);
				Thread.sleep(1000);
				valueInTextbox = this.element.getAttribute("value");
				if(CommonMethods.getPureText(valueInTextbox).equals(CommonMethods.getPureText(text))){
					break; 		    				    
				}else{
					Log.debug("Retry "+retry+": enter "+text+" into "+this.elementName);
				}
			}
		}catch (Exception e){
			Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}		
	}

	/**
	 * Enter a text into a currency textbox element.
	 * @param text
	 * @throws Exception 
	 */
	public void enterInCurrencyField(String text) throws Exception{
		try {
			
			if(text==null){
				Log.debug("Warning: Text to select is null");
				return;
			}
			NumberFormat formatter = new DecimalFormat("#0.00");
			this.element = findElement();
			String textInElement = this.element.getAttribute("value");
			//convert to currency 
			String currencyType = DTNConstant.LOCALE;
			if(currencyType.equalsIgnoreCase("US")){
				int expected = Integer.valueOf(text);						         
				String expectedCurrency = CommonMethods.priceInUSD(expected);
				String expectedCurrencyWithFocus = formatter.format(expected);
		        if(expectedCurrency.equals(textInElement)||expectedCurrencyWithFocus.equals(textInElement)){
		             Log.debug(text+ " is already in "+ this.elementName);
		             return;
		        }
			}

			if(textInElement.equals(text)){
				Log.debug(text + " is already in "+this.elementName);
				return;
			}
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {					
					this.element.clear();			
					this.element.sendKeys(text);	
					Thread.sleep(1000);
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);	
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e1.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			    		throw (e1);
			    	}
					Thread.sleep(500);
					this.element = driver.findElement(by);
				}catch(Exception e){	
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);	
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			    		throw (e);
			    	}
			    	Thread.sleep(1000);
			    	this.element = driver.findElement(by);
				}
			}
			Log.info("+ Enter "+text +" into " + elementName);			
		}catch (Exception e){
			Log.info("+ Enter "+text +" into " + elementName);	
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	/**
	 * Enter text into textbox by entering each character of text 
	 * @param text
	 * @throws Exception
	 * @author Thao Le
	 */
	public void enterEachCharInString(String text) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to enter is null");
				return;
			}
						
			this.element = findElement();
			String textInElement = this.element.getAttribute("value");
			if(textInElement.equals(text)){
				Log.debug(text + " is already in "+this.elementName);
				return;
			}
			
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {					
					this.element.clear();	
					Thread.sleep(100);	
					char[] chars = text.toCharArray();
					String charValue;
					String valueInTextbox = null;			
					for(char eachChar:chars){
						charValue = String.valueOf(eachChar);
						this.element.sendKeys(charValue);
						Thread.sleep(100);					
					}						
					enterKeyBoard(Keys.TAB);
					Thread.sleep(100);
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e1.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw (e1);
			    	}
					Thread.sleep(1000);
					this.element = driver.findElement(by);
				}catch(Exception e){
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw(e);
					}
			    	Thread.sleep(1000);
			    	this.element = driver.findElement(by);
				}
			}
			Log.info("+ Enter "+text +" into " + elementName);	
		}catch (Exception e){	
			Log.info("+ Enter "+text +" into " + elementName);
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	/**
	 * Enter text into textbox by entering each character of text 
	 * @param text
	 * @throws Exception
	 * @author Thao Le
	 */
	public void enterEachCharInStringInCurrencyField(String text) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to enter is null");
				return;
			}
						
			NumberFormat formatter = new DecimalFormat("#0.00");
			this.element = findElement();
			String textInElement = this.element.getAttribute("value");
			//convert to currency 
			String currencyType = DTNConstant.LOCALE;
			if(currencyType.equalsIgnoreCase("US")){
				int expected = Integer.valueOf(text);						         
				String expectedCurrency = CommonMethods.priceInUSD(expected);
				String expectedCurrencyWithFocus = formatter.format(expected);
		        if(expectedCurrency.equals(textInElement)||expectedCurrencyWithFocus.equals(textInElement)){
		             Log.debug(text+ " is already in "+ this.elementName);
		             return;
		        }
			}
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {					
					this.element.clear();	
					Thread.sleep(100);	
					char[] chars = text.toCharArray();
					String charValue;
					String valueInTextbox = null;			
					for(char eachChar:chars){
						charValue = String.valueOf(eachChar);
						this.element.sendKeys(charValue);
						Thread.sleep(100);					
					}						
					enterKeyBoard(Keys.TAB);
					Thread.sleep(100);
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e1.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw (e1);
			    	}
					Thread.sleep(1000);
					this.element = driver.findElement(by);
				}catch(Exception e){
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw(e);
					}
			    	Thread.sleep(1000);
			    	this.element = driver.findElement(by);
				}
			}
			Log.info("+ Enter "+text +" into " + elementName);	
		}catch (Exception e){
			Log.info("+ Enter "+text +" into " + elementName);
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	/**
	 * Enter text into textbox by entering each character of text 
	 * @param text
	 * @throws Exception
	 * @author Thao Le
	 */
	public void enterEachCharInString(String text, int millisecondSleep) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to enter is null");
				return;
			}
						
			this.element = findElement();
			String textInElement = this.element.getAttribute("value");
			if(textInElement.equals(text)){
				Log.debug(text + " is already in "+this.elementName);
				return;
			}
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {					
					this.element.clear();	
					Thread.sleep(400);	
					char[] chars = text.toCharArray();
					String charValue;
					String valueInTextbox = null;			
					for(char eachChar:chars){
						charValue = String.valueOf(eachChar);
						this.element.sendKeys(charValue);
						Thread.sleep(millisecondSleep);					
					}	
					Thread.sleep(300);
					enterKeyBoard(Keys.TAB);
					Thread.sleep(300);
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e1.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw (e1);
			    	}
					Thread.sleep(1000);
					this.element = driver.findElement(by);
				}catch(Exception e){		
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw(e);
					}
			    	Thread.sleep(1000);
			    	this.element = driver.findElement(by);
				}
			}
			Log.info("+ Enter "+text +" into " + elementName);	
		}catch (Exception e){		
			Log.info("+ Enter "+text +" into " + elementName);
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}	
	/**
	 * Enter text into textbox by entering each character of text 
	 * @param text
	 * @throws Exception
	 * @author Thao Le
	 */
	public void enterEachCharInStringInCurrencyField(String text, int millisecondSleep) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to enter is null");
				return;
			}
					
			NumberFormat formatter = new DecimalFormat("#0.00");
			this.element = findElement();
			String textInElement = this.element.getAttribute("value");
			//convert to currency 
			String currencyType = DTNConstant.LOCALE;
			if(currencyType.equalsIgnoreCase("US")){
				int expected = Integer.valueOf(text);						         
				String expectedCurrency = CommonMethods.priceInUSD(expected);
				String expectedCurrencyWithFocus = formatter.format(expected);
		        if(expectedCurrency.equals(textInElement)||expectedCurrencyWithFocus.equals(textInElement)){
		             Log.debug(text+ " is already in "+ this.elementName);
		             return;
		        }
			}
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {					
					this.element.clear();	
					Thread.sleep(400);	
					char[] chars = text.toCharArray();
					String charValue;
					String valueInTextbox = null;			
					for(char eachChar:chars){
						charValue = String.valueOf(eachChar);
						this.element.sendKeys(charValue);
						Thread.sleep(millisecondSleep);					
					}	
					Thread.sleep(300);
					enterKeyBoard(Keys.TAB);
					Thread.sleep(300);
					break;
				}catch(StaleElementReferenceException e1){
					Log.debug("StaleElementReferenceException: "+retry+" retry to findElement()");
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);	
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e1.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw (e1);
			    	}
					Thread.sleep(1000);
					this.element = driver.findElement(by);
				}catch(Exception e){		
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
//						Log.info("+ Enter "+text +" into " + elementName);	
//						Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
//			       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			       		throw(e);
					}
			    	Thread.sleep(1000);
			    	this.element = driver.findElement(by);
				}
			}
			Log.info("+ Enter "+text +" into " + elementName);		
		}catch (Exception e){		
			Log.info("+ Enter "+text +" into " + elementName);	
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	
	
	/**
	 * Enter password into password textbox.
	 * @param text
	 * @throws Exception 
	 */
	public void enterPassword(String text) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to select is null");
				return;
			}
			Log.info("+ Enter ****** into " + elementName);			
			this.element = findElement();			
			this.element.clear();			
			this.element.sendKeys(text);	
			Thread.sleep(1000);
		}catch (Exception e){			
       		Log.fail("** Actual result: user cannot enter ****** into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	
	
	/**
	 * Wait and select an item by index in drop-down list. Popup after is not handled
	 * Skip if text is already being selected by index from drop-down
	 * @author Quinn Song 
	 * @update Thao Le : adding 2000 milliseconds sleep after selecting action finished.
	 * @param index			: item index which needed to select
	 * @throws Exception 
	 *//*
	public void select(int index) throws Exception{
		boolean bStatus = false;
		try {
			Log.info("+ Select index " + index + " from " + elementName);
			this.element = findElement();
			Log.debug("index to select is " + index);
			// WAIT UNTIL ELEMENT IS ENABLED
			String selectedOption = "";
			try {
				selectedOption = new Select(driver.findElement(by)).getFirstSelectedOption().getText();
			}
			catch (Exception e1) {
	
				WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
				wait.until((WebDriver dr) -> {
					try {
						this.element = driver.findElement(by);
						boolean enabled = this.element.isEnabled();
						Thread.sleep(1000);
						return enabled;
					}
					catch (Exception e2) {
						return false;
					}
				});
			}
			
			Log.debug(String.valueOf(element.isEnabled()));
			
			if (!element.isEnabled()) {
				Log.fail("Failed to select by index "+index+" from dropdown of "+ elementName + " (Element not enabled)","Class: dtn.automation.core.Element | Method: selectText(int) | Timeout occured.");
				throw new Exception ("Element not enabled.");
			}
			Log.debug("option currently selected: " + selectedOption);
			
			String indexedOption = new Select(element).getOptions().get(index).getText();
			// Log.debug(Boolean.toString(indexedOption.equals(selectedOption)));
			// CHECK IF TEXT TO SELECT ALREADY EXISTS
			if (indexedOption.equals(selectedOption)) {
				Log.debug("option by index already selected. Skipping this step.");
				return;
			}	
	
			Log.debug("+ Select index "+index +" from dropdown of " + elementName + " in Element " + element);
			// LOOP TO RETRY 
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					new Select(element).selectByIndex(index);
					bStatus = true;
					Thread.sleep(1000); 
			    	break; 
		    				    			
				}catch(Exception e){				
			    	Thread.sleep(1000);
				}
			}			
			
			if (bStatus){
				Log.debug("Selecting by index " + index + " from dropdown completed.");
	       	}
			else {
				Log.fail("+ Cannot be able to select index "+index+" from dropdown of "+ elementName,"Class: dtn.automation.core.Element | Method: select() | Timeout occured.");
				Log.debug("Failed to select from text by index " + index);
			}
		}catch (Exception e){
			Log.fail("+ Cannot be able to select index "+index+" from dropdown of "+ elementName,"Class: dtn.automation.core.Element | Method: select() | Timeout occured.");
       		throw(e);
       	}
	}*/
	
	/**
	 * Wait and select an item by text in drop-down list
	 * Skip if text is already being selected from drop-down
	 * @author Quinn Song 
	 * @update Thao Le : adding 2000 milliseconds sleep after selecting action finished.
	 * @param text 			: Item which needed to select
	 * @param expectPopup	: True if popup will show afterwards
	 * @throws Exception 
	 */
	public void select(String text) throws Exception{
		boolean bStatus = false;	
		if(text==null){
			Log.debug("Warning: Text to select is null");
			return;
		}
		try{							
			this.element = findElement();
		}catch(Exception e){
			Log.info("+ Select '" + text + "' from " + elementName);
			Log.fail("Failed to select by text "+text+" from "+ elementName ,"Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
    		throw (e);	    	
		}
		// WAIT UNTIL ELEMENT IS ENABLED
		String selectedOption = "";
		Log.debug("Wait until "+this.elementName+" is enable.");
		boolean enabled = false;
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				enabled = this.element.isEnabled();
				if(enabled){
					break;
				}			
			}catch (Exception e) {
				if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
					Log.info("+ Select '" + text + "' from " + elementName);
					Log.fail("Failed to select by text "+text+" from "+ elementName + " (Element not enabled)","Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
		    		throw (e);
		    	}				
				Thread.sleep(1000);
				this.element = driver.findElement(by);
			}
		}	
		Log.debug("check if "+text+" is already selected in "+this.elementName);
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				selectedOption = new Select(driver.findElement(by)).getFirstSelectedOption().getText();
				break;
			}
			catch (Exception e) {
				if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
					Log.info("+ Select '" + text + "' from " + elementName);
					Log.fail("Failed to select by text "+text+" from "+ elementName ,"Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
		    		throw (e);
		    	}				
				Thread.sleep(1000);
				this.element = driver.findElement(by);
			}	
		}
		// CHECK IF TEXT TO SELECT ALREADY EXISTS
		if (text.equals(selectedOption)) {
			Log.debug("option by text already selected. Skipping this step.");
			return;
		}
		
		Log.info("+ Select '" + text + "' from " + elementName);
		// LOOP TO RETRY			
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				new Select(element).selectByVisibleText(text);
				Thread.sleep(1000);  // Is this optional?
				bStatus = true;
		    	break; 			    				    			
			}catch(Exception e){
				if(retry==DTNConstant.EXPLICIT_WAITTIME-1){					
					Log.fail("Failed to select by text "+text+" from "+ elementName ,"Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
		    		throw (e);
		    	}				
				Thread.sleep(1000);
				this.element = driver.findElement(by);
			}			
		}										
		
	}
	/**
	 * Wait and select an item by text in drop-down list
	 * Skip if text is already being selected from drop-down
	 * @author Quinn Song 
	 * @update Thao Le : adding 2000 milliseconds sleep after selecting action finished.
	 * @param text 			: Item which needed to select
	 * @param expectPopup	: True if popup will show afterwards
	 * @throws Exception 
	 */
	public void select(String text, Element loadingPopup) throws Exception{
		boolean bStatus = false;	
		if(text==null){
			Log.debug("Warning: Text to select is null");
			return;
		}
									
		this.element = findElement();					
		// WAIT UNTIL ELEMENT IS ENABLED
		String selectedOption = "";
		Log.debug("Wait until "+this.elementName+" is enable.");
		boolean enabled = false;
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				enabled = this.element.isEnabled();
				if(enabled){
					break;
				}			
			}catch (Exception e) {
				if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
					Log.info("+ Select '" + text + "' from " + elementName);
					Log.fail("Failed to select by text "+text+" from "+ elementName + " (Element not enabled)","Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
		    		throw (e);
		    	}				
				Thread.sleep(1000);
				this.element = driver.findElement(by);
			}
		}	
		Log.debug("check if "+text+" is already selected in "+this.elementName);
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				selectedOption = new Select(driver.findElement(by)).getFirstSelectedOption().getText();
				break;
			}
			catch (Exception e) {
				if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
					Log.info("+ Select '" + text + "' from " + elementName);
					Log.fail("Failed to select by text "+text+" from "+ elementName ,"Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
		    		throw (e);
		    	}				
				Thread.sleep(1000);
				this.element = driver.findElement(by);
			}	
		}
		// CHECK IF TEXT TO SELECT ALREADY EXISTS
		if (text.equals(selectedOption)) {
			Log.debug("option by text already selected. Skipping this step.");
			return;
		}
		
		Log.info("+ Select '" + text + "' from " + elementName);
		// LOOP TO RETRY			
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				new Select(element).selectByVisibleText(text);
				Thread.sleep(1000);  
				Page.waitUntilPopupDisappear(driver, loadingPopup.getBy());	
				bStatus = true;
		    	break; 			    				    			
			}catch(Exception e){
				if(retry==DTNConstant.EXPLICIT_WAITTIME-1){					
					Log.fail("Failed to select by text "+text+" from "+ elementName ,"Class: dtn.automation.core.Element | Method: selectText(String, boolean) | Timeout occured " +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"+e.toString());
		    		throw (e);
		    	}				
				Thread.sleep(1000);
				this.element = driver.findElement(by);
			}			
		}										
		
	}	
	
	/**
	 * To Clear input in control
	 * @throws Exception
	 * @author maggie.jiang
	 */
	public void clear() throws Exception{
		try {
			Log.debug("+ clear... " );				
			driver.findElement(by).clear();
			Thread.sleep(1000);
		}catch (Exception e){
       		Log.fail("+ Cannot Clear ","Class: core.Element | Method: enterKeyBoard | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}	
	/**
	 * To  input in control using sendkeys
	 * @throws Exception
	 * @author maggie.jiang
	 */
	public void sendKeys(String keys) throws Exception{
		try {
			Log.debug("+ clear... " );				
			driver.findElement(by).sendKeys(keys);
			Thread.sleep(1000);
		}catch (Exception e){
       		Log.fail("+ Cannot key In " + keys,"Class: core.Element | Method: enterKeyBoard | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}	
	
	/**
	 * To hit key on keyboard 
	 * @param keyBoard : key on keyboard
	 * @throws Exception
	 * @author Thao Le
	 */
	public void enterKeyBoard(Keys keyBoard) throws Exception{
		try {
			Log.debug("+ Enter " +keyBoard.name()+ " on keyboard");				
			driver.findElement(by).sendKeys(keyBoard);
			Thread.sleep(1000);
		}catch (Exception e){
       		Log.fail("+ Cannot enter "+keyBoard.toString() +" on keyboard","Class: core.Element | Method: enterKeyBoard | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}	
	/**
	 * Enter a text into a textbox element.
	 * @author Quinn Song modified: Added a loop to retry
	 * Modified by Thao Le
	 * @param text
	 * @throws Exception 
	 */
	public void enterWithRetry(String text) throws Exception{
		try {
			if(text==null){
				Log.debug("Warning: Text to be entered is null");
				return;
			}
			
			Thread.sleep(2000);
			this.element = findElement();
			String valueInTextbox = null;
			valueInTextbox = this.element.getAttribute("value");
			Log.debug("Current text is: " + valueInTextbox);
			// CHECK IF TEXT TO ENTER ALREADY EXISTS
			if (valueInTextbox.equals(text)) {
				Log.debug("Text to be entered already exists. Skipping this step.");
				return;
			}														
			this.element = findElement();			
			this.element.clear();	
			Thread.sleep(200);	
			char[] chars = text.toCharArray();
			String charValue;
			valueInTextbox = null;
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				for(char eachChar:chars){
					charValue = String.valueOf(eachChar);
					this.element.sendKeys(charValue);
					Thread.sleep(100);					
				}	
				Thread.sleep(200);
				enterKeyBoard(Keys.TAB);
				Thread.sleep(1000);
				valueInTextbox = this.element.getAttribute("value");
				if(valueInTextbox.equals(text)){
					break; 		    				    
				}else{
					Log.debug("Retry "+retry+": enter "+text+" into "+this.elementName);
				}
			}		
			Log.info("+ Enter "+text +" into " + elementName);	
		}catch (Exception e){
			Log.info("+ Enter "+text +" into " + elementName);	
       		Log.fail("** Actual result: user cannot enter "+text+" into "+ elementName,"Class: core.Element | Method: enter | Exception occured - "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	
	/**
	 * @return driver of this element
	 */
	public WebDriver getDriver() {
		return driver;
	}
	/**
	 * @param driver Driver of this element
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	/**
	 * @return Element object
	 */
	public WebElement getElement() {
		return element;
	}
	/**
	 * @param element Element to set for this element in this class
	 */
	public void setElement(WebElement element) {
		this.element = element;
	}
	/**
	 * @return By value of element 
	 */
	public By getBy() {
		return by;
	}
	/**
	 * @param by Element by value
	 */
	public void setBy(By by) {
		this.by = by;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	/**
	 * To verify if element contains expected text
	 * @param text
	 * @return
	 * @author Thao Le
	 * @throws Exception 
	 */
	public boolean containText(String text) throws Exception{
		try {
			Log.debug("+ Verify if '"+text +"' is displayed");
			this.element = findElement();
			String textOnElement = this.element.getText();
			if(!textOnElement.contains(text)){				
				throw new Exception("Expected text is missing");				
			}else{
				return true;
			}
		}catch (Exception e){
			Log.fail(this.elementName +" is NOT displayed","Class: dtn.automation.core.Element | Method: containText() | " + e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			
			throw (e);
       	}
		
	}
	/**
	 * To get text from element
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public String getText() throws Exception{
		String text = null;
		try {
			Log.debug("+ Get text on "+this.elementName);
			this.element = findElement();			
			text = this.element.getText();			
			return text;
		}catch (Exception e){
			Log.fail(this.elementName +" is NOT displayed.","Class: dtn.automation.core.Element | Method: getTextOnLabel() | " + e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());				
			throw (e);
       	}
		
	}
	/**
	 * To get text from element
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public String getAttribute(String value) throws Exception{
		String text = null;
		try {
			Log.debug("+ Get text on "+this.elementName);
			this.element = findElement();
			text = this.element.getAttribute("value");			
			return text;
		}catch (Exception e){
			Log.fail(this.elementName +" is NOT displayed.","Class: dtn.automation.core.Element | Method: getTextOnLabel() | " + e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());				
			throw (e);
       	}
		
	}	
	
	/**
	 * To get text from element
	 * @return
	 * @throws Exception
	 * @author Maggie Jiang
	 */
	public String getValue() throws Exception{
		String text = null;
		try {
			Log.debug("+ Get text on "+this.elementName);
			this.element = findElement();
			text = this.element.getAttribute("value");			
			return text;
		}catch (Exception e){
			Log.fail(this.elementName +" is NOT displayed.","Class: dtn.automation.core.Element | Method: getTextOnLabel() | " + e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());				
			throw (e);
       	}
		
	}
	
}
