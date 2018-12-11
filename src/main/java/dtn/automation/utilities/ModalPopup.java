package dtn.automation.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import dtn.automation.core.Element;

import dtn.automation.core.BaseDriver;
import dtn.automation.core.Browser;
import dtn.automation.core.Page;

public class ModalPopup {
	
	public static void clickOnButton(Element buttonToClick) throws Exception{		
		WebElement btnElement;
		try {
			Log.info("+ Click on " + buttonToClick.elementName);			
			btnElement = buttonToClick.findElement();
			for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
				try {
					CommonMethods.clickDifferentBrowser(buttonToClick.getBy());	
					Thread.sleep(500);
					Page.waitForPageLoad(BaseDriver.getDriver());									
					break;
				}catch(Exception e){				
			    	Thread.sleep(1000);
				}
			}		
		}catch (Exception e){
			Log.info("** Expected result: User can be able to click on "+ buttonToClick.elementName);
       		Log.fail("** Actual result: User can NOT click on "+ buttonToClick.elementName,"Class: core.ModalPopup | Method: clickOnButton | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
       		throw(e);
       	}		
	}
	public static void switchToFrameOfPopup(WebDriver driver, String...iFrameName) throws Exception{
		Frame.moveToChildFrame(driver,iFrameName);		
	}
	public static void containText(Element PopupContent,String text) throws Exception{
		boolean status = false;
		Log.debug("+ Verify if '"+text +"' is displayed");
		Thread.sleep(2000);
		String textOnElement = null;
		WebElement element = PopupContent.findElement();
		for (int retry = 0;retry<DTNConstant.EXPLICIT_WAITTIME; retry++) {
			try {
				textOnElement = element.getText();
				if(textOnElement.contains(text)){
					status = true;
					break; 	    				 
				}else{
					Thread.sleep(1000);
				}
			}catch(Exception e){				
		    	Thread.sleep(1000);
			}
		}
		if(!status){
			Log.fail("'"+text +"' is NOT displayed on "+ PopupContent.elementName);
			throw new Exception("Expected text is missing");				
		}else{
			Log.pass("*** Actual result:'"+text +"' is displayed in "+PopupContent.elementName+" correctly.");
		}			
	}
	public static boolean containTextOnPopup(Element PopupContent,String text) throws Exception{
		boolean status = false;
		Log.debug("+ Verify if '"+text +"' is displayed");		
		Thread.sleep(2000);
		String textOnElement = null;
		WebElement element = PopupContent.findElement();		
		textOnElement = element.getText();
		if(textOnElement.contains(text)){
			status = true;						    				
		}		
		return status;		
	}	
}

