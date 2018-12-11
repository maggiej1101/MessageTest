package dtn.automation.utilities;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;

import dtn.automation.listeners.RetryAnalyzer;

/**
 * @author Automation
 *
 */
public class TestCase {
	private List<TestStep> testSteps = new ArrayList<>();
	private String moduleName = null;
	private String testCaseName = null;
	private String testCaseDescription = null;		
			
	public TestCase(String moduleName, String testCaseName, String testCaseDescription) {				
		this.moduleName = moduleName;
		this.testCaseName = testCaseName;
		this.testCaseDescription = testCaseDescription;
	}
	public void setModuleName(String testModuleName){
		moduleName = testModuleName;
	}
	public String getModuleName(){
		return moduleName;
	}
	public void setTestCaseName(String testName){
		testCaseName = testName;
	}
	public void setTestCaseDescription(String testDescription){
		testCaseDescription = testDescription;
	}
	public String getTestCaseDescription(){		
		return testCaseDescription;
	}
	public String getTestCaseName(){
		return testCaseName;
	}
	public void addTestStep(TestStep testStep){
		testSteps.add(testStep);
	}	
	
	/**
	 * To print test steps to Extent Report
	 */
	public void printTestCaseAndTestStepsToExtentReport(String category,ExtentTest parentReport){			
		String testDescription;
		if(RetryAnalyzer.getCountRetry()>0){
			testDescription = "RETRY "+RetryAnalyzer.getCountRetry()+" TIMES DUE TO PREVIOUS TEST RUN IS FAILED - "+getTestCaseDescription();
		}else{
			testDescription = getTestCaseDescription();
		}		
		ExtentTest extentReport = ExtentTestManager.startNode(parentReport, testCaseName, testDescription);
		if(category!=""){
			extentReport.assignCategory(category);
		}else{
			extentReport.assignCategory("Test Cases");
		}
		for(TestStep testStep:testSteps){										
			if(testStep.getTestDescription()!=null && testStep.getMediaFile()!=null){
				printTestStep(category,extentReport,testStep.getStatus(),testStep.getTestDescription(),testStep.getMediaFile());
			}else{
				if(testStep.getException()!=null && testStep.getMediaFile()!=null){
					printTestStep(category,extentReport,testStep.getStatus(),testStep.getException(),testStep.getMediaFile());
				}else{
					if(testStep.getTestDescription()!=null){
						printTestStep(category,extentReport,testStep.getStatus(),testStep.getTestDescription());	
					}else if(testStep.getException()!=null){
						printTestStep(category,extentReport,testStep.getStatus(),testStep.getException());	
					}
				}
			}
			
				
		}		
	}
	/**
	 * To print into Extent report with Test Description
	 * @param extentReport
	 * @param status
	 * @param testDescription
	 */
	private void printTestStep(String category,ExtentTest extentReport,String status,String testDescription){
		/*if(category!=""){
			extentReport.assignCategory(category);
		}else{
			extentReport.assignCategory("Test Cases");
		}*/
		switch (status){
			case "PASS":	extentReport.pass(testDescription);
							break;		
			case "FAIL":	extentReport.fail(testDescription);
							break;
			case "FATAL":	extentReport.debug(testDescription);
							break;					
			case "INFO":	extentReport.info(testDescription);
							break;		
			case "WARNING":	extentReport.warning(testDescription);
							break;
			case "ERROR":	extentReport.error(testDescription);
							break;		
			case "SKIP":	extentReport.skip(testDescription);
							break;
			case "DEBUG":	extentReport.debug(testDescription);
							break;		

		}
	}
	/**
	 * * To print into Extent report with exception message
	 * @param extentReport
	 * @param status
	 * @param exception
	 */
	private void printTestStep(String category,ExtentTest extentReport,String status,Throwable exception){
		/*if(category!=""){
			extentReport.assignCategory(category);
		}else{
			extentReport.assignCategory("Test Cases");
		}*/
		switch (status){
			case "PASS":	extentReport.pass(exception);
							break;		
			case "FAIL":	extentReport.fail(exception);
							break;
			case "FATAL":	extentReport.debug(exception);
							break;	
			case "INFO":	extentReport.info(exception);
							break;		
			case "WARNING":	extentReport.warning(exception);
							break;
			case "ERROR":	extentReport.error(exception);
							break;		
			case "SKIP":	extentReport.skip(exception);
							break;
			case "DEBUG":	extentReport.debug(exception);
							break;		

		}
	}
	
	/**
	 * To print into Extent report with Test Description and screen shot
	 * @param extentReport
	 * @param status
	 * @param testDescription
	 * @param mediaFile
	 */
	private void printTestStep(String category,ExtentTest extentReport,String status, String testDescription, MediaEntityModelProvider mediaFile){
		/*if(category!=""){
			extentReport.assignCategory(category);
		}else{
			extentReport.assignCategory("Test Cases");
		}*/
		switch (status){
			case "PASS":	extentReport.pass(testDescription,mediaFile);
							break;		
			case "FAIL":	extentReport.fail(testDescription,mediaFile);
							break;
			case "FATAL":	extentReport.debug(testDescription,mediaFile);
							break;
			case "INFO":	extentReport.info(testDescription,mediaFile);
							break;		
			case "WARNING":	extentReport.warning(testDescription,mediaFile);
							break;
			case "ERROR":	extentReport.error(testDescription,mediaFile);
							break;		
			case "SKIP":	extentReport.skip(testDescription,mediaFile);
							break;
			case "DEBUG":	extentReport.debug(testDescription,mediaFile);
							break;				
		}
	}
	
	/**
	 * To print into Extent report with exception message and screen shot
	 * @param extentReport
	 * @param status
	 * @param exception
	 * @param mediaFile
	 */
	private void printTestStep(String category,ExtentTest extentReport,String status, Throwable exception, MediaEntityModelProvider mediaFile){
		/*if(category!=""){
			extentReport.assignCategory(category);
		}else{
			extentReport.assignCategory("Test Cases");
		}*/
		switch (status){
			case "PASS":	extentReport.pass(exception,mediaFile);
							break;		
			case "FAIL":	extentReport.fail(exception,mediaFile);
							break;
			case "FATAL":	extentReport.debug(exception,mediaFile);
							break;
			case "INFO":	extentReport.info(exception,mediaFile);
							break;		
			case "WARNING":	extentReport.warning(exception,mediaFile);
							break;
			case "ERROR":	extentReport.error(exception,mediaFile);
							break;		
			case "SKIP":	extentReport.skip(exception,mediaFile);
							break;
			case "DEBUG":	extentReport.debug(exception,mediaFile);
							break;				
		}
	}	
}
