package dtn.automation.utilities;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import dtn.automation.core.BaseDriver;
import dtn.automation.core.Browser;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dtn.automation.core.Element;
import com.google.common.base.Predicate;


/*
 	Description: common Methods/Functions
 	@author: Thao.Le
*/

public class CommonMethods {

	//Description: wait until window exists with title
	public static boolean waitUntilWindowExistsWithTitle(WebDriver driver, String windowName){
	    return waitUntilWindowExistsWithTitle(driver, windowName, DTNConstant.EXPLICIT_WAITTIME);
	}

	//Description: wait until window exists with title in timeout (seconds)
	public static boolean waitUntilWindowExistsWithTitle(WebDriver driver, String windowName, int timeoutInSeconds){
		Log.debug("Wait until window with tile '"+windowName+"' is displayed in [ " + timeoutInSeconds + " ] seconds");		
	    try{	    	
	    	WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);		    	
			wait.until(ExtendedExpectedConditions.findWindowWithTitleAndSwitchToIt(windowName));			
	    }catch(TimeoutException e){
	    	Log.fail("Failed: failed to find Window with title of [ " + windowName + " ]",e.toString() +
	    			" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
	    	return false;
	    }	    
	    return true;
	}

	//Description: wait until until expected number of window
	public static boolean waitUntilNumberOfWindowsAre(WebDriver driver, int expectedNumberOfWindows){
		return waitUntilNumberOfWindowsAre(driver, expectedNumberOfWindows, DTNConstant.EXPLICIT_WAITTIME);
	}

	//Description: wait until until expected number of window in timeout
	public static boolean waitUntilNumberOfWindowsAre(WebDriver driver, int expectedNumberOfWindows, int timeoutInSeconds){									
		try{	    	
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);	    	
			wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));				
		}catch(TimeoutException e){	    			
			return false;
		}		
		return true;
	}

	//Description:
	public static boolean waitUntilWindowExistsTitleContains(WebDriver driver, String windowNameOrHandle, int timeoutInSeconds){
		Log.debug("Wait until window exists with title contains '"+windowNameOrHandle+"' in "+timeoutInSeconds +" seconds.");		
		
	    try{	    	
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);	    	
			wait.until(ExtendedExpectedConditions.findWindowContainsTitleAndSwitchToIt(windowNameOrHandle));			
	    }catch(TimeoutException e){	    	
	    	Log.fail("Failed to find Window with title of [ " + windowNameOrHandle + " ]",e.toString() +
	    			" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
	    	return false;
	    }		
	    return true;
	}

	//Description:
	public static boolean waitUntilElementDisplay(WebDriver driver,By by,int second){				
		boolean bStatus = false;		
		try{						
			for (int i = 0;i<second; i++) {		    	
		    	if (isElementDisplay(driver,by)){
		    		bStatus = true;
		    		break; 
		    	}
		    	Thread.sleep(1000);// in milliseconds
		    }			
		}catch(Exception e){		
			System.out.println(e);
			return false;
		}
		return bStatus;
	}
	/** 
	 * 	method to extract pure input with the help of regex.
	 * 	It can be used for checking if entered fields have correct values
	 *	@param	 text		The input raw text
	 *  @return	 The parsed digits string
	 *  @author Quinn Song	
	 */
	public static String getPureText(String text) {
		if (text.matches("\\w\\d\\w\\s\\d\\w\\d")) {
			// For postal code
			return text.replace(" ", "");
		}
		else
			return text.replaceAll("[)(`$,\\s-]|\\.[0]+", "");
	}

	//Description:
	public static boolean waitUntilTextDisplayOnElement(WebDriver driver,By by,String text,int second) throws Exception{				
		boolean bStatus = false;		
		try{						
			for (int i = 0;i<second; i++) {		    	
		    	if (isElementDisplay(driver,by)){
		    		bStatus = true;
		    		if(driver.findElement(by).getText().contains(text)){
		    			bStatus = true;
		    			break;
		    		}
		    		bStatus = false;
		    		break; 
		    	}
		    	Thread.sleep(1000);// in milliseconds
		    }			
		}catch(Exception e){		
			Log.fail(text + " is not displayed", " | Exception occured - "+e.toString() + " - locator - " + by +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);			
		}
		return bStatus;
	}

	//Description:
	public static boolean waitUntilTextDisplayOnElement(WebDriver driver, Element element, String text, int second) throws Exception{
		boolean bStatus = false;		
		try{						
			for (int i = 0;i<second; i++) {		    			    	
	    		if(element.getElement().getText().contains(text)){
	    			bStatus = true;
	    			break;
	    		}		    				    	
		    	Thread.sleep(1000);// in milliseconds
		    }			
		}catch(Exception e){		
			Log.fail(text + " is not displayed", " | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);			
		}
		return bStatus;
	}

	

	//Description:
	private static boolean isElementDisplay(WebDriver driver,By by) {
	    try {
	      if(driver.findElement(by).isDisplayed()) return true;
	      return false;
	    } catch (NoSuchElementException e) {
	    	System.out.println(e);
	      return false;
	    }
	 }
	//Description: to get substring between 2 words in a string
	public static String getSubstring(String text, String startText, String endText){
		int beginIndex;
        int endIndex;
        String subString = "";
        subString = text;
     
        beginIndex = subString.indexOf(startText) + startText.length();
        endIndex = subString.indexOf(endText);
        subString = subString.substring(beginIndex, endIndex);         
		return subString;
	}	
	/**
	 * To convert a number to USD currency
	 * @param price
	 * @return
	 * @author Thao Le
	 */
	public static String priceInUSD(int price) {
        int result = price;
         double priceUSD = (double) result;         
          NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); 
          String priceInString = String.valueOf(currencyFormat.format(priceUSD));  
          return priceInString;
          
    }
	public static void clickDifferentBrowser(By by) throws Exception{
		String browserTest = Browser.getBrowser();
		WebDriver driver = BaseDriver.getDriver();	
		WebElement elementToClick;		
		if(browserTest.equalsIgnoreCase("IE")){
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			elementToClick = driver.findElement(by);
			/*JavascriptExecutor js = (JavascriptExecutor)driver;	
			//js.executeScript("arguments[0].click();",elementToClick);
			js.executeScript("if( document.createEvent ) {var click_ev = document.createEvent('MouseEvents'); click_ev.initEvent('click', true , true )"
                            + ";arguments[0].dispatchEvent(click_ev);} else { arguments[0].click();}",
                    elementToClick);*/
			
			new Actions(driver).moveToElement(elementToClick).perform();
			Thread.sleep(100);
			new Actions(driver).click().build().perform(); 			
		}else{			
			elementToClick = driver.findElement(by);
			new Actions(driver).moveToElement(elementToClick).perform();
			elementToClick.click();
		}
	}
	
}
