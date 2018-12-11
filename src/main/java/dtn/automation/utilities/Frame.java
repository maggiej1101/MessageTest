package dtn.automation.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dtn.automation.core.Page;

//import utilities.PageGeneral;

public class Frame {
		
	public static void findAndSwitchToFrame(WebDriver driver, String frame) {
		int timeout = DTNConstant.EXPLICIT_WAITTIME;
		
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver,timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));		
	}
	
	public static String getCurrentFrameName(WebDriver driver) {
		String frameName = ((JavascriptExecutor) driver).executeScript("return self.name").toString();
		if (frameName.isEmpty())
			frameName = null;
		return frameName;
	}
	/**
	 * Move to Frame by looing for different layers.
	 * @param driver
	 * @Param Frame to switch to
	 * @author Maggie Jiang
	 */
	public static void moveToFrame(WebDriver driver, String FrmName) {
		if (!Frame.moveToFrameAfterSearchAD(driver, FrmName)) {
			if(!Frame.moveToFrame0AfterSearchAD(driver, FrmName)){
				if(!Frame.moveToFrameAfterSearchAP(driver,FrmName)){
					Frame.moveToFrame0AfterSearchAP(driver,FrmName);
				}
			}
		}
	}
	
	/**
	 * Move to Main after open searched deal in AD status
	 * @param driver
	 * @Param Frame to switch to
	 * @author Maggie Jiang
	 */
	public static Boolean moveToFrame0AfterSearchAD(WebDriver driver, String strFrame) {
		WebDriverWait wait = new WebDriverWait(driver,60);	
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		//String strFramename = (String) jsExecutor.executeScript("return self.name");
		//System.out.println("frame(AD).main.....................OK1"+strFramename);
			try {
				Page.waitForPageLoad(driver);
				driver.switchTo().defaultContent();
				driver.switchTo().frame("iFrm");
				Thread.sleep(3000);
				driver.switchTo().frame(0).switchTo().frame(strFrame);
				//driver.switchTo().frame(strFrame);
				//strFramename = (String) jsExecutor.executeScript("return self.name");
				//System.out.println("frame(AD).main.....................OK2"+strFramename);
				return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	/**
	 * Move to Main after open searched deal with decision back
	 * @param driver
	 * @Param Frame to switch to
	 * @author Maggie Jiang
	 * @author Maggie Jiang
	 */
	public static Boolean moveToFrameAfterSearchAP(WebDriver driver, String strFrame) {
		WebDriverWait wait = new WebDriverWait(driver,60);	
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		//String strFramename = (String) jsExecutor.executeScript("return self.name");
		//System.out.println("frame(AP).main.....................OK1"+strFramename);
			try {
				Page.waitForPageLoad(driver);
				driver.switchTo().defaultContent();
				driver.switchTo().frame("iFrm");
				driver.switchTo().frame("iFrm");
				Thread.sleep(3000);
				driver.switchTo().frame(strFrame);
				//strFramename = (String) jsExecutor.executeScript("return self.name");
				//System.out.println("frame(AP).main.....................OK2"+strFramename);
				return true;
			}catch(Exception e) {
				return false;
			}

	}
	
	
	/**
	 * Move to Main after open searched deal in AD status
	 * @param driver
	 * @Param Frame to switch to
	 * @author Maggie Jiang
	 */
	public static Boolean moveToFrameAfterSearchAD(WebDriver driver, String strFrame) {
		WebDriverWait wait = new WebDriverWait(driver,60);	
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;

			try {
				Page.waitForPageLoad(driver);
				driver.switchTo().defaultContent();
				driver.switchTo().frame("iFrm");
				Thread.sleep(3000);
				driver.switchTo().frame(strFrame);
				//strFramename = (String) jsExecutor.executeScript("return self.name");
				//System.out.println("frame(AD).main.....................OK2"+strFramename);
				return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	/**
	 * Move to Main after open searched deal with decision back
	 * @param driver
	 * @Param Frame to switch to
	 * @author Maggie Jiang
	 * @author Maggie Jiang
	 */
	public static Boolean moveToFrame0AfterSearchAP(WebDriver driver, String strFrame) {

			try {
				Log.debug("Move from iframe to "+strFrame);
				Page.waitForPageLoad(driver);
				driver.switchTo().defaultContent();
				driver.switchTo().frame("iFrm");
				driver.switchTo().frame("iFrm");
				Thread.sleep(3000);
				driver.switchTo().frame(0).switchTo().frame(strFrame);
				//strFramename = (String) jsExecutor.executeScript("return self.name");
				//System.out.println("frame(AP).main.....................OK2"+strFramename);
				return true;
			}catch(Exception e) {
				Log.info("Move from iframe to "+strFrame);
				Log.fail("Fail to move from iframe "+strFrame,"- Exception occured - "+e.toString() +
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
	
				return false;
			}

	}
	

	public static void moveToDefaultContext(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public static void moveToParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}



	public static void moveToSiblingFrame(WebDriver driver, By byFrameLocator) {
		moveToParentFrame(driver);
		switchToFrame(driver, byFrameLocator);
	}

	public static void moveToChildFrame(WebDriver driver, String frameName) {
		switchToFrame(driver, frameName);
	}

	public static void moveToChildFrame(WebDriver driver, By byFrameLocator) {
		switchToFrame(driver, byFrameLocator);
	}

	public static void moveToChildFrame(WebDriver driver, String... frameName) throws Exception{
		try{
			Log.debug("Move from iframe to child frame");
			moveToDefaultContext(driver);
			for (int x = 0; x < frameName.length; x++) {
				moveToChildFrame(driver, frameName[x]);
			}
		}catch(Exception e){
			Log.info("Move from iframe to child frame");
			Log.fail("Fail to move from iframe to child frame","- Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw (e);
		}
	}	
	/**
	 * Move from parent frame to child frame, then to sub child frame
	 * @param driver
	 * @param parentFrame
	 * @param childFrame
	 * @param subChildFrame
	 * @author Maggie Jiang
	 */
	public static void moveToChildFrame(WebDriver driver, String parentFrame, String childFrame, By subChildFrame) throws Exception {
		try{
			Log.debug("Move from '"+ parentFrame +"' iframe to "+childFrame+" iframe, then to '"+subChildFrame+"' iframe");
			moveToDefaultContext(driver);
			moveToChildFrame(driver, parentFrame);
			moveToChildFrame(driver, childFrame);
			moveToChildFrame(driver, subChildFrame);
		}catch(Exception e){
			Log.info("Move from '"+ parentFrame +"' iframe to "+childFrame+" iframe, then to '"+subChildFrame+"' iframe");
			Log.fail("Fail to move from '"+ parentFrame +"' iframe to "+childFrame+" iframe, then to '"+subChildFrame+"' iframe","- Exception occured - "+e.toString() + " - locator - " + subChildFrame +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw (e);
		}
	}	
		
	/**
	 * Move from parent frame to child frame
	 * @param driver
	 * @param parentFrame
	 * @param childFrame
	 * @author Maggie Jiang
	 */
	public static void moveToChildFrame(WebDriver driver, String parentFrame, String childFrame) throws Exception {
		try{
			Log.debug("Move from '"+parentFrame +"' iframe to '"+childFrame+"' iframe");
			moveToDefaultContext(driver);
			moveToChildFrame(driver, parentFrame);
			moveToChildFrame(driver, childFrame);
		}catch(Exception e){
			Log.info("Move from '"+parentFrame +"' iframe to '"+childFrame+"' iframe");
			Log.fail("Fail to move from '"+parentFrame +"' iframe to '"+childFrame+"' iframe","- Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw (e);
		}
	}	
	
	public static void waitFramesDisplay(WebDriver driver, String parentFrame, String childFrame, int timeout) {
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver,timeout);					
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(parentFrame));		
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(childFrame));
	}
	public static void moveToChildFrame(WebDriver driver, By[] frameName) {
		for (int x = 0; x < frameName.length; x++) {
			moveToChildFrame(driver, frameName[x]);
		}
	}

	private static void switchToFrame(WebDriver driver, String frameName) {				
		WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
	}

	private static void switchToFrame(WebDriver driver, By byFrameLocator) {
		int timeout = DTNConstant.EXPLICIT_WAITTIME;
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(byFrameLocator));
	}
}