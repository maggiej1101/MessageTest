package dtn.automation.utilities;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import dtn.automation.core.Browser;
import dtn.automation.listeners.RetryAnalyzer;

//import dtn.automation.listeners.RetryAnalyzer;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * This is a customized log to log all information using log4j.
 * This Log has 3 logs: debug log file, Report html log file and Console information log.
 * debug.log: logs all logs at DEBUG level
 * report.html: logs at INFO level
 * console log: logs at 3 custom level. NO_STEP: no any step info will be displayed in console. 
 * TESTCASE_STEP: information of test case steps (using for user report).
 * DEBUG_STEP: information at DEBUG level for test developer debugging scripts.
 * @author thao.le
 *
 */
public class Log{	
	private static ConsoleLogLevel consoleLevel = ConsoleLogLevel.DEBUG_STEP;
	private static Logger logger = Logger.getRootLogger();												
	private static Logger debugLog = Logger.getLogger("debugLogger");
	private static Logger reportsLog = Logger.getLogger("reportsLogger");
	private static Logger consoleLog = Logger.getLogger("consoleLogger");
	private static ExtentTest extentReport = null; 
	private static TestCase testCase;
	private static String failMessage = null;
	
	/**
	 * NO_STEP: no any step info will be displayed in console. 
	 * TESTCASE_STEP: information of test case steps (using for user report).
	 * DEBUG_STEP: information at DEBUG level for test developer debugging scripts.
	 * @author thao.le
	 *
	 */
	public enum ConsoleLogLevel{
		NO_STEP,
		TESTCASE_STEP,
		DEBUG_STEP;
	}	
	
	/**
	 * To set the log level (Level: debug, info, error, fatal. Console level: NO STEP, TEST CASE STEP, DEBUG STEP)
	 * @param loggerLevel
	 * @param consoleLogLevel
	 */
	public static void setCustomLogLevel(Level loggerLevel,ConsoleLogLevel consoleLogLevel){
		reportsLog.setLevel(loggerLevel);
		debugLog.setLevel(loggerLevel);
		consoleLog.setLevel(loggerLevel);		
		consoleLevel = consoleLogLevel;
	}
	public static void setExtentReport(ExtentTest extentTest){
		extentReport = extentTest;
	}
	public static void setTestCase(TestCase currentTestCase){
		testCase = currentTestCase;
	}
	public static void info(String message){
		reportsLog.info(message);
		debugLog.info(message);	
		//extentReport.info(message);
		testCase.addTestStep(new TestStep("INFO",message));
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP))
			consoleLog.info(message);		
	}
	public static void pass(String message){
		reportsLog.info(message);
		debugLog.info(message);		
		//extentReport.pass(message);
		testCase.addTestStep(new TestStep("PASS",message));
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP))
			consoleLog.info(message);		
	}
	
	private static void infoStartEndTest(String message){
		reportsLog.info(message);
		debugLog.info(message);
		consoleLog.info(message);			
	}
	public static void debug(String message){		
		debugLog.debug(message);		
		if(consoleLevel==ConsoleLogLevel.DEBUG_STEP)
			consoleLog.debug(message);		
	}
	public static void fail(String message){		
		debugLog.fatal(message);
		reportsLog.fatal(message);
		testCase.addTestStep(new TestStep("FAIL",message));
		consoleLog.fatal(message);	
		failMessage = message;
//		Assert.fail(message);
	}
	public static void error(String message){		
		debugLog.error(message);
		reportsLog.error(message);
		testCase.addTestStep(new TestStep("ERROR",message));
		consoleLog.error(message);	
		failMessage = message;
//		Assert.fail(message);
	}
	public static void fail(String message,String exceptionMessage){		
		debugLog.fatal(message);
		reportsLog.fatal(message);
		testCase.addTestStep(new TestStep("FAIL",message));
//		testCase.addTestStep(new TestStep("FAIL",exceptionMessage));
		debugLog.fatal(exceptionMessage);
		reportsLog.fatal(exceptionMessage);		
		consoleLog.fatal(message);
		consoleLog.fatal(exceptionMessage);	
		failMessage = message;
//		Assert.fail(message);
	}
	public static void error(String message,String exceptionMessage){		
		debugLog.error(message);
		reportsLog.error(message);
		testCase.addTestStep(new TestStep("ERROR",message));
//		testCase.addTestStep(new TestStep("ERROR",exceptionMessage));
		debugLog.error(exceptionMessage);
		reportsLog.error(exceptionMessage);		
		consoleLog.error(message);
		consoleLog.error(exceptionMessage);
		failMessage = message;
//		Assert.fail(message);
	}
	public static void startTestCase(String testCaseName){		
		infoStartEndTest("************ " +testCaseName+ " has STARTED ************");		   		
	}
	
	public static void endTestCase(String testCaseName, boolean status, String message, WebDriver driver){	
		String threadName = Thread.currentThread().getName();
		String screenShotFile = DTNConstant.SCREENSHOT_FOLDER+testCaseName;
		String screenShotFileName;
		String format = "png";	
		if(!status){			
			try{				
				screenShotFile +="_Fail_"+getTimestamp();					
				screenShotFileName = testCaseName+"_Fail_"+getTimestamp() +"."+format;
				infoStartEndTest("************ " +testCaseName+ " has FAILED ************");
				//if(RetryAnalyzer.isMaxRetryFailedTestCase()||threadName.contains("TestClassSetUp")||threadName.contains("TestClassTeardown")){
				if(RetryAnalyzer.isMaxRetryFailedTestCase()){
					takeScreenshot(driver, screenShotFile,format);						
					testCase.addTestStep(new TestStep("FAIL","Please refer to screenshot:",MediaEntityBuilder.createScreenCaptureFromPath(screenShotFileName).build()));
					debug("Screenshot of failed testcase is "+screenShotFile + "\\"+screenShotFileName);
					testCase.printTestCaseAndTestStepsToExtentReport("",extentReport);	
					RetryAnalyzer.setCountRetry(0);
				}else{
					if(threadName.contains("TestClassSetUp")||threadName.contains("TestClassTeardown")){
						takeScreenshot(driver, screenShotFile,format);						
						testCase.addTestStep(new TestStep("FAIL","Please refer to screenshot:",MediaEntityBuilder.createScreenCaptureFromPath(screenShotFileName).build()));
						debug("Screenshot of failed testcase is "+screenShotFile + "\\"+screenShotFileName);
						testCase.printTestCaseAndTestStepsToExtentReport("Test Configuration",extentReport);	
						RetryAnalyzer.setCountRetry(0);
					}else{
						takeScreenshot(driver, screenShotFile,format);
						debug("Screenshot of failed testcase is "+screenShotFile + "\\"+screenShotFileName);
					}
				}
				
				if(!message.equals("")){
					Assert.fail(message);
				}else{
					Assert.fail(failMessage);
				}
			}catch(Exception e){
				testCase.printTestCaseAndTestStepsToExtentReport("",extentReport);				
				Assert.fail(e.toString());				
			}
		}else{
			try {
				screenShotFile +="_Pass";					
				screenShotFileName = testCaseName+"_Pass" +"."+format;													
				takeScreenshot(driver, screenShotFile,format);											
				testCase.addTestStep(new TestStep("PASS","Please refer to screenshot:",MediaEntityBuilder.createScreenCaptureFromPath(screenShotFileName).build()));
				debug("Screenshot of testcase is "+screenShotFile + "\\"+screenShotFileName);								
				if(!message.equals("")){
					info(message);
				}
				infoStartEndTest("************ " +testCaseName+ " has PASSED ************");
				if(threadName.contains("TestClassSetUp")||threadName.contains("TestClassTeardown")){
					testCase.printTestCaseAndTestStepsToExtentReport("Test Configuration",extentReport);
				}else{
					testCase.printTestCaseAndTestStepsToExtentReport("",extentReport);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertTrue(status, message);
		}
	}	
	public static void takeScreenshotAndReport(WebDriver driver, String testCaseName) throws Exception{
		String screenShotFile = DTNConstant.SCREENSHOT_FOLDER+testCaseName;
		String screenShotFileName;
		String format = "png";					
		screenShotFile +="_"+getTimestamp() ;					
		screenShotFileName = testCaseName+"_"+getTimestamp() +"."+format;
		takeScreenshot(driver, screenShotFile,format);											
		testCase.addTestStep(new TestStep("PASS","Please refer to screenshot:",MediaEntityBuilder.createScreenCaptureFromPath(screenShotFileName).build()));		
	}
	public static void takeScreenshot(WebDriver driver, String screenShotName, String format) throws Exception{
		try{			
			String browserTest = Browser.getBrowser();
			String fileName;			
			if(browserTest.equalsIgnoreCase("IE")){
				Robot robot = new Robot();             
	            fileName = screenShotName+ "."+ format;	             
	            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
	            File screenshotFile = new File(screenShotName+"."+format);
	            if (screenshotFile.exists()){
					FileUtils.deleteQuietly(screenshotFile);
				}
	            ImageIO.write(screenFullImage, format, new File(fileName));
			}else{
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				File screenshotFile = new File(screenShotName+"."+format);
				if (screenshotFile.exists()){
					FileUtils.deleteQuietly(screenshotFile);
				}
				FileUtils.copyFile(scrFile,screenshotFile);	
			}
						
		} catch(UnhandledAlertException e1){
			try{
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}catch(Exception e){				
			}
		}catch (Exception e){		
			error("Class utilities | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.toString());
			throw new Exception();
		}
	}
	/*public static void takeScreenshot(WebDriver driver, String screenShotName, String format) throws Exception{
		try{			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
			//File screenshotFile = new File(DTNConstant.SCREENSHOT_FOLDER + sTestCaseName +".png");
			File screenshotFile = new File(screenShotName+"."+format);
			//String relativeScreenShotPath = DTNConstant.relativeSCREENSHOT_FOLDER + sTestCaseName +".png";
			if (screenshotFile.exists()){
				FileUtils.deleteQuietly(screenshotFile);
			}
			FileUtils.copyFile(scrFile,screenshotFile);				
		} catch(UnhandledAlertException e1){
			try{
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}catch(Exception e){				
			}
		}catch (Exception e){		
			error("Class utilities | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.toString());
			throw new Exception();
		}
	}*/
	/**
	 * Capture a screenshot (full screen)
	 * as an image which will be saved into a file.
	 * @author Thao Le
	 *
	 */
	public static void captureFullScreenshot(String screenShotFile, String format){	 
        try {
            Robot robot = new Robot();             
            String fileName = screenShotFile+ "."+ format;
             
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));                        
        } catch (AWTException | IOException e) {
        	error("Class ultilities | Method captureFullScreenshot | Exception occured while capturing ScreenShot : "+e.toString());			
        }	   
	}	
	public static String getTimestamp()
	{
		
		String timeStamp = new SimpleDateFormat("MM.dd.yyyy__hh.mm.ssa").format(new Date());
		
		return timeStamp;
	}
}