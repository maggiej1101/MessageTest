package dtn.automation.core;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dtn.automation.core.Element;
import com.google.common.base.Predicate;

import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;

/**
 * To handle all methods related with Page such as verify elements on Page, 
 * verify title of page, verify text in page, wait for page completely loaded
 * 
 * @author Maggie.Jiang
 *
 */
public class Page {
	/**
	 * To verify if expected elements are displayed on page
	 * @param PageName
	 * @param elements
	 * @throws Exception
	 */
	public static void verifyPageWithElement(String PageName,Element...elements) throws Exception{
		String listElementNames = "";		
		int i=0;		
		for(Element element:elements){	
			Log.debug("Verify "+ element.getElementName()+" is displayed on "+ PageName);
			try{
				if(i>0)
					listElementNames = element.getElementName()+ "," +listElementNames;
				else
					listElementNames = element.getElementName();
				if(!element.isDisplayed()){			
					Log.info("*** Expected result:"+ element.getElementName()+" is displayed on "+ PageName);
					Log.fail("*** Actual result:"+ element.getElementName()+" is NOT displayed on "+ PageName,"Class: core.Page | Method: verifyPageWithElement - "
							+ "Element: "+element.getElementName()+ " - locator - "+element.getBy().toString());	       		
					throw new Exception("Missing element "+ element.getElementName());
				}
				i++;
			}catch(Exception e){
				Log.info("*** Expected result:"+ element.getElementName()+" is displayed on "+ PageName);
	       		Log.fail("*** Actual result:"+ element.getElementName()+" is NOT displayed on "+ PageName,"Class: core.Page | Method: verifyPageWithElement - Element: "+element.getElementName()+ " - locator - "+element.getBy().toString()+" - Exception: "+ e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());       		   		       	
				throw new Exception("Missing element "+ element.getElementName());			
			}
		}				
		Log.pass("*** Actual result:"+PageName + " displayed with "+ listElementNames + " successfully");
	}
	
	
		/**
		 * To wait until Page is displayed with expected Page title and text 
		 * @param driver
		 * @param pageTitle
		 * @param text
		 * @param second
		 * @return
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean waitUntilPageDisplayedWithText(WebDriver driver,String pageTitle,String text,int second) throws Exception{				
			boolean bStatus = false;	
			String bodyText = "";
			try{						
				for (int i = 0;i<second; i++) {	
					if (validatePage(driver,pageTitle)) {
						bodyText = driver.findElement(By.tagName("body")).getText();
						if(bodyText.contains(text)){
				    		bStatus = true;	
				    		break;		    		 
				    	}
					}
			    	Thread.sleep(1000);// in milliseconds		    
			    }			
			}catch(Exception e){		
				Log.fail(text + " is not displayed", e.toString());
				throw(e);			
			}
			if (!bStatus) {
				Log.fail("Page title is not '"+pageTitle + "' or '" + text +"' is not displayed on page.","");
				throw new Exception("PageNotOpenORTextNotDisplayed");
			}
			return bStatus;
		}

		/**
		 * To verify if text is displayed on page successfully.
		 * @param driver
		 * @param pageTitle
		 * @param text
		 * @throws Exception
		 * @author Thao Le
		 */
		public static void verifyPageDisplayedWithText(WebDriver driver,String pageTitle,String text) throws Exception{				
			boolean bStatus = false;	
			String bodyText = "";						
			Log.debug("Verify if "+ text +" is displayed on "+pageTitle+" page");				
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {	
				try{
					bodyText = driver.getPageSource();					
					if(bodyText.contains(text)){
			    		bStatus = true;	
			    		break;		    		 
			    	}else{			    		
			    		Thread.sleep(1000);// in milliseconds
			    	}
				}catch(Exception e){	
					if(retry==DTNConstant.EXPLICIT_WAITTIME-1){
						Log.info("*** Expected result: '"+text+"' is displayed on "+ pageTitle+" page");
			       		Log.fail("*** Actual result: '"+text+"' is NOT displayed on "+ pageTitle+" page","Class: core.Page | Method: verifyPageDisplayedWithText - Exception: "+ e.toString()
			       			+" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
						throw new Exception("Missing Text on Page");
					}
					Thread.sleep(1000);
				}
		    }								
			if (!bStatus) {
				Log.info("*** Expected result: '"+text+"' is displayed on "+ pageTitle+" page");
				Log.fail("*** Actual result: '"+text +"' is NOT displayed on " + pageTitle +" page.");
				throw new Exception("Missing Text on Page");
			}
			Log.pass("*** Actual result:'"+text+"' is displayed on "+ pageTitle+" page successfully");
		}
		
		/**
		 * To verify if expected text is displayed on page and return true if expected text is on Page and false if expected text is not on the page.
		 * @param driver
		 * @param pageTitle
		 * @param text
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean verifyTextOnPage(WebDriver driver,String pageTitle,String text) throws Exception{				
			boolean status = false;	
			String bodyText = "";			
			try{	
				Log.debug("Verify if "+ text +" is displayed on "+pageTitle+" page");
				for (int i = 0;i<DTNConstant.EXPLICIT_WAITTIME; i++) {						
					bodyText = driver.getPageSource();
					if(bodyText.contains(text)){
			    		status = true;	
			    		break;		    		 
			    	}
			    	Thread.sleep(1000);// in milliseconds		    
			    }						
			}catch(Exception e){	
				Log.debug("*** Actual result:'"+text+"' is NOT displayed on "+ pageTitle+" page");
				status = false;			
			}			
			return status;
		}
		/**
		 * To wait until page and element is displayed
		 * @param driver
		 * @param pageTitle
		 * @param element
		 * @param second
		 * @return
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean waitUntilPageAndElementDisplayed(WebDriver driver,String pageTitle, Element element,int second) throws Exception{	
			//DRSConstant.iObjectWaitTime
			boolean bStatus = false;
			try{						
				for (int i = 0;i<second; i++) {	
					if (validatePage(driver,pageTitle)) {
				    	if (element.isDisplayed()){
				    		bStatus = true;	
				    		break;		    		 
				    	}
					}
			    	Thread.sleep(1000);// in milliseconds
			    }			
			}catch(Exception e){	
				Log.fail(pageTitle + " Page is not opened or " + element.elementName +" is not displayed." , "Class: ultility.commonTestMethods | Method: logIn | Exception occured - "+ e.toString() +
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
				throw(e);			
			}		
			if (!bStatus) {
				Log.fail("Page title is not '"+pageTitle + "' or " + element.elementName +" is not displayed.","");
				throw new Exception("PageNotOpenORElementNotDisplayed");
			}
			return bStatus;
		}

		
		/**
		 * To wait until page and element is displayed
		 * @param driver
		 * @param pageTitle
		 * @param by
		 * @param second
		 * @return
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean waitUntilPageAndElementDisplayed(WebDriver driver,String pageTitle, By by,int second) throws Exception{				
			boolean bStatus = false;		
			try{						
				for (int i = 0;i<second; i++) {	
					if (validatePage(driver,pageTitle)) {
				    	if (driver.findElement(by).isDisplayed()){
				    		bStatus = true;	
				    		break;		    		 
				    	}
					}
			    	Thread.sleep(1000);// in milliseconds
			    }			
			}catch(Exception e){	
				Log.fail(pageTitle + " Page is not opened or " + by.toString() +" is not displayed." , "Class: ultility.commonTestMethods | Method: logIn | Exception occured - "+ e.toString() +
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
				throw(e);			
			}		
			if (!bStatus) {
				Log.fail("Page title is not '"+pageTitle + "' or " + by.toString() +" is not displayed.","");
				throw new Exception("PageNotOpenORElementNotDisplayed");
			}
			return bStatus;
		}
		
		/**
		 * To validate page Title is same as expected
		 * @param driver
		 * @param pageTitle
		 * @return
		 * @author Thao Le
		 */
		private static boolean validatePage(WebDriver driver,String pageTitle){	
			String currentPage;
			try{		
				currentPage = driver.getTitle();
				if(currentPage.equals(pageTitle)){
					return true;								
				}else{					
					return false;
				}
			}catch(Exception e){						
				return false;
			}		
		}
		
		/**
		 * To wait for page loading completely
		 * @param driver
		 * @throws InterruptedException
		 * @author Thao Le
		 */
		/*public static void waitForPageLoad(WebDriver driver){
			int seconds = 50;
			JavascriptExecutor js = (JavascriptExecutor)driver;	
			Log.debug("Waiting for page load complete...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			Long milliSecond;
			try{
				Thread.sleep(2000);						
					Log.debug("Check if document is readyState...");	
				   if(js.executeScript("return document.readyState").toString().equals("complete")){				   
				   }	
				   Log.debug("Check if jQuery is active...");
				   if((boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0")){	
					   if(js.executeScript("return document.readyState").toString().equals("complete")){				   
					   }
				   }			   			   
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				Log.debug("Page load completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);
			}catch(Exception e){
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				Log.debug("Page load completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);
			}
		}*/
		public static void waitForPageLoad(WebDriver driver){
			int seconds = 50;
			JavascriptExecutor js = (JavascriptExecutor)driver;	
			Log.debug("Waiting for page load complete...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			Long milliSecond;
			try{								
					Log.debug("Check if document is readyState...");	
					waitForDomLoad(driver);	
//					Log.debug("Check if jQuery is active...");
//					waitForAjaxLoad(driver);
//				   	Log.debug("Check if Ajax is active...");
//				   	waitForJQueryLoad(driver);
//				   	waitForDomLoad(driver);
				   if((boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0")){	
					   if(js.executeScript("return document.readyState").toString().equals("complete")){				   
					   }
				   }			   			   
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				Log.debug("Page load completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);
			}catch(Exception e){
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				Log.debug("Page load completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);
			}
		}	

		/**
		 * Wait until popup message to show up and disappear
		 * @author Quinn Song
		 * @param driver
		 * @param by		: Element by
		 * @param seconds	: Wait timeout
		 * @return			: True if popup is handled successfully; false if exception occurs
		 * @throws Exception
		 */
		public static boolean waitUntilPopupDisappear(WebDriver driver, By by,int seconds) throws Exception{	
			//	
			Log.debug("Handling Modal popup...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			Long milliSecond;
			try {
				Log.debug("Waiting until Modal popup shows up...");
				new WebDriverWait(driver, seconds).until(ExpectedConditions.presenceOfElementLocated(by));
				Log.debug("Waiting until Modal popup disappears...");
				new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				Log.debug("Popup alert completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);			
				return true;
			}catch(Exception e) {
				Log.fail("Popup message is not displayed.", "Class: dtn.automation.core | Method: waitUntilPopupDisappear | Exception occured - "+ e.toString() + " - locator - " + by + 
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
				Log.debug("Popup alert is not displayed after " + seconds + " seconds.");
				return false;
		    }
		}
		public static void waitUntilPopupDisappear(WebDriver driver, By by) throws Exception{	
			//	
			Log.debug("Handling Modal popup...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			int timeOut = 2;
			Long milliSecond;
			try {
				Log.debug("Waiting until Modal popup shows up...");
				new WebDriverWait(driver, timeOut).until(ExpectedConditions.presenceOfElementLocated(by));
				Log.debug("Waiting until Modal popup disappears...");
				new WebDriverWait(driver, DTNConstant.LOADING_POPUP_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(by));
				Thread.sleep(1500);
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				Log.debug("Popup alert completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);							
			}catch(Exception e) {				
				Log.debug("Popup alert is not displayed after " + timeOut + " seconds.");				
		    }
		}
		/**
		 * To verify if expected elements are displayed on page
		 * @author Quinn Song
		 * @param PageName
		 * @param element
		 * @param js		:javascript string.
		 * @param seconds	:timeout in seconds.
		 * @throws Exception
		 */	
		public static void verifyPageWithElement(WebDriver driver, String PageName, Element element, String js, int seconds) throws Exception  {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			boolean bStatus = false;
			Log.debug("verify started");
			
			for (int i = 0;i<seconds; i++) {		
			try {
					//FrameHandler.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.mainFrame);				
					String re = (jse.executeScript("return window.document.getElementById('txtApplicationNumber').value")).toString();
					Log.debug(re);
					bStatus = (! re.isEmpty());
					
					if (bStatus) {
						Log.debug("done with verify");
						break;
					}
					else {
						Thread.sleep(1000);
						Log.debug("after." + Frame.getCurrentFrameName(driver));
					}
				}
				
				catch(Exception e) {
					//Log.debug(e.toString());
					Thread.sleep(1000);
				}
			}

			
			if (!bStatus){
				Log.fail("*** Expected result:"+ element.getElementName()+" is displayed on "+ PageName);
				Log.fail("*** Actual result:"+ element.getElementName()+" is NOT displayed on "+ PageName,"Class: core.Page | Method: verifyPageWithElement - Element: "+element.getElementName()+ " - locator - "+element.getBy().toString());
				throw new Exception("Missing element "+ element.getElementName());	
			}
			else {
				Log.pass("*** Actual result:"+PageName + " displayed with "+ element.elementName + " successfully");
			}
		}	
		/**
	     * @author Maggie.Jiang
	     * To scroll down until the end of page
	     * @param WebDriver
	     */
	    public static void scrollDown(WebDriver driver)
	    {	    	
	    	try{	    		
	    		Log.debug("Scroll down to the end of page");    			    		
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0,document.body.scrollHeight);");
				Thread.sleep(2000);
	    	}catch(Exception e){
	    		Log.info("Scroll down to the beginning of page");
				Log.fail("*** Actual result: FAIL to scroll down to the beginning of page","Class: core.Page | Method: scrollDown()| Exception occured - "+e.toString() +
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
	    	}
	    }
	    /**
	     * @author Maggie.Jiang
	     * To scroll up until the end of page
	     * @param WebDriver
	     */
	    public static void scrollUp(WebDriver driver)
	    {	    	
	    	try{	    		
	    		Log.debug("Scroll up to the beginning of page");    			    		
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0,-document.body.scrollHeight);");
				Thread.sleep(2000);
	    	}catch(Exception e){
	    		Log.info("Scroll up to the beginning of page");
				Log.fail("*** Actual result: FAIL to scroll up to the beginning of page","Class: core.Page | Method: scrollUp()| Exception occured - "+e.toString() +
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
	    	}
	    }
	    private static void waitForJQueryLoad(WebDriver driver) {
	        (new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                JavascriptExecutor js = (JavascriptExecutor) d;
	                return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
	            }
	        });
	    }
	    private static void waitForDomLoad(WebDriver driver){
	    	(new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                JavascriptExecutor js = (JavascriptExecutor) d;
	                return (Boolean) js.executeScript("return document.readyState").equals("complete");
	            }
	        });
	    }
	    private static void waitForAjaxLoad(WebDriver driver){
	    	Dimension zeroDim = new Dimension(0,0);
	    	(new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {	                
	                return driver.findElement(By.cssSelector(".waiting, .tb-loading")).getSize() == zeroDim;
	            }
	        });
	    }
}
