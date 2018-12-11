package common.TestSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;

import common.Page.LoginPage;
import common.TestMethods.CommonTestMethods;
import dtn.automation.core.BaseDriver;
import dtn.automation.core.Browser;
import dtn.automation.listeners.RetryAnalyzer;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.DTN_TestData;
import dtn.automation.utilities.ExtentManager;
import dtn.automation.utilities.ExtentTestManager;
import dtn.automation.utilities.Log;
import dtn.automation.utilities.Log.ConsoleLogLevel;
import dtn.automation.utilities.ObjectLocator;
import dtn.automation.utilities.TestCase;
import dtn.automation.utilities.TestEnvironment;

/**
 * Base test suite to run before start Test Class
 * @author thao.le
 *
 */
public class BaseTestSuite{		
	public String className;
	public String userName;
	public String password;
	public ExtentTest parentTest;	
	public String TestDataPath;
	public static Browser browser;
	public static WebDriver driver;
	public String testName;
	public TestCase testCase;
	public static DTN_TestData testData;
	
	@BeforeSuite	
	@Parameters({"consoleLogLevel","userName","password","retryFailedTestCase"})
	public void TestSuiteSetUp(			
			@Optional("DEBUG_STEP") String consoleLogLevel,
			@Optional("mjianq") String UserName,
			@Optional("Winter2018") String Password,			
			@Optional String retryFailedTestCase) throws IOException{
		BasicConfigurator.configure();			
		PropertyConfigurator.configure("src/test/resources/Properties/log4j.properties");
		String extentReportFolder = DTNConstant.workingDir + "/reportLogs/ExtentReports";
		String extentReportFile = "reportLogs/ExtentReports/extentReport.html";
		
		if(retryFailedTestCase!=null){
			DTNConstant.MAX_RETRY_FAILED_TESTCASE = Integer.parseInt(retryFailedTestCase);
			RetryAnalyzer.setRetryMaximum(DTNConstant.MAX_RETRY_FAILED_TESTCASE);
		}
		FileUtils.deleteDirectory(new File(extentReportFolder));
		new File(extentReportFolder).mkdir();
		this.className = this.getClass().getSimpleName();		
		ExtentManager.getReporter(extentReportFile);
	    parentTest = ExtentTestManager.startModule(this.className);	
	    Log.setExtentReport(parentTest);
		switch (consoleLogLevel){
		case "NO_STEP":
			Log.setCustomLogLevel(Level.DEBUG, ConsoleLogLevel.NO_STEP);
			break;
		case "TESTCASE_STEP":
			Log.setCustomLogLevel(Level.DEBUG, ConsoleLogLevel.TESTCASE_STEP);
			break;
		case "DEBUG_STEP":
			Log.setCustomLogLevel(Level.DEBUG, ConsoleLogLevel.DEBUG_STEP);
			break;
		default :
			Log.setCustomLogLevel(Level.DEBUG, ConsoleLogLevel.DEBUG_STEP);
			break;
		}		
		  	    
		userName = UserName;
		password = Password;			

	}
	
	@AfterSuite(alwaysRun=true)
	public void TestSuiteTearDown(){		
		try {
			browser.closeBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@BeforeMethod	
	public void TestMethodSetUp(Method method) throws Exception {		
		testName = method.getName();		
		String threadName = className + "_" + testName;
		Thread.currentThread().setName(threadName);		
		testData = new DTN_TestData(TestDataPath, testName);
		testCase = new TestCase(this.className, testName, testData.getMainValue("TEST_DESCRIPTION"));
		Log.setTestCase(testCase);			
	}	
	
	public void ClassSetUp(String browserUnderTest,TestEnvironment testClassEnvironment,String URLTest){	
		try{						
			ObjectLocator URL = new ObjectLocator(DTNConstant.workingDir+"\\src\\test\\resources\\Properties\\URL.properties");
			//TestDataPath = DTNConstant.workingDir + "\\src\\test\\java\\smokeTest\\UAT\\SmokeTest_Testdata_QA.xlsx";
			//this.TestDataPath= testDataPath;
			testClassEnvironment.setBrowserUnderTest(browserUnderTest);
			//new Object(){}.getClass().getEnclosingMethod().getName()			
			testName = "TestClassSetUp";
			
			Log.startTestCase(testName);
			String threadName = className + "_" + testName;
			Thread.currentThread().setName(threadName);		
			testData = new DTN_TestData(TestDataPath, testName);
			testCase = new TestCase(this.className, testName, " Login DTN");
			Log.setTestCase(testCase);
			browser = new Browser(testClassEnvironment.getBrowserUnderTest());
			driver = browser.getDriver();
			testClassEnvironment.setTestEnvironment(driver);
			ExtentTestManager.reportEnvironment(this.getClass().getSimpleName(), testClassEnvironment.getBrowserUnderTest()
					,testClassEnvironment.getBrowserVersion(),testClassEnvironment.getDriverVersion()
					,testClassEnvironment.getOperatingSystem());
			new BaseDriver(driver);			

			LoginPage.logIn(URL.getPropertyValue(URLTest),userName,password);
			Log.endTestCase(testName,true,"",driver);
		} catch(Exception e){		
			Log.endTestCase(testName,false,"Login is FAILED",driver);
			try {
				browser.closeBrowser();
			} catch (Exception e1) {
				Log.endTestCase(testName,false,"Login is FAILED",driver);
			}
		}
	}	
	public void ClassTeardown(){
		try{							
			testName = "TestClassTeardown";	
			Log.startTestCase(testName);
			String threadName = className + "_" + testName;
			Thread.currentThread().setName(threadName);		
			testData = new DTN_TestData(TestDataPath, testName);
			testCase = new TestCase(this.className, testName, "Logout DTN");
			Log.setTestCase(testCase);
			//CommonTestMethods.logOut();						
			Log.endTestCase(testName,true,"",driver);
			browser.closeBrowser();
		} catch (Exception e) {
			Log.endTestCase(testName,false,"Logout is FAILED",driver);
		}
	}
}
