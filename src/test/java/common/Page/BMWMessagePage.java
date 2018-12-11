package common.Page;

import static common.Page.CommonObjects.mainFrame;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


import dtn.automation.core.Element;
import dtn.automation.core.Page;
import common.TestSuite.BaseTestSuite;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;

public class BMWMessagePage extends BaseTestSuite {

	public static final String PageName = "BMW Message Page";	
	 //------------ Message tab -------------------------------
	
	public static Element tabMessage = new Element( By.cssSelector("[id$=_btnMESSAGES]"),"Message tab");
	
	public static Element txtSubject = new Element(By.cssSelector("[id$=messagesubject]"),"Subject Box");
	public static Element txtMessage = new Element(By.cssSelector("[id$=messagetext]"),"Message Box");
	//public static Element btnSend = new Element(By.xpath("/html/body/lendercomm-root/messages/div/div[2]/div[2]/form/div[3]/button[2]"),"Send Message Button");
	//public static Element btnSend = new Element(By.cssSelector("button[type='submit']"),"Send Message Button");
	public static Element btnSend = new Element(By.cssSelector(".actions > button:nth-child(3)"),"Send Message Button");
	public static Element btnAttach = new Element(By.cssSelector("button[type='button']"),"Attach Button");
	public static Element txtFile = new Element(By.cssSelector(".column-right .attachment"),"File Name Box");
	public static Element btnCancelFile = new Element(By.cssSelector(".column-right .attachment i.close"),"Cancel sending File");
    public static Element lblSubjectValMessage=new Element(By.cssSelector(".validation-message >span"),"Validation Message for Subject");
    public static Element lblNotesValMessage=new Element(By.cssSelector(".form-group:nth-child(2) div.validation-message"),"Validation Message for Notes");
    public static Element lblAttachValMessage=new Element(By.cssSelector(".actions > div.validation-message"),"Validation Message for oversized Attachfile");
    //#idp > lendercomm-root > messages > div > div.column.column-right > div.message-compose > form > div.actions > div.validation-message
	public static Element txtFirstMSGSubject = new Element(By.cssSelector(".column-left .message:first-child div.message-subject"),"First message Subject");
	public static Element txtFirstMSGNote = new Element(By.cssSelector(".column-left .message:first-child div.message-content"),"First message Content");
	public static Element txtFirstMSGFile = new Element(By.cssSelector(".column-left .message:first-child div.attachment"),"First message file linked");
	public static Element btnPlus = new Element(By.cssSelector(".column-left .message:first-child i.far.fa-plus-square"),"Plus Button");
	public static Element btnMinus = new Element(By.cssSelector(".column-left .message:first-child i.far.fa-minus-square"),"Plus Button");
	
	static String strExpectedValSubject="Subject is required.";
    static String strExpectedValMessage="Message is required.";
    static String strExpectedValAttach="Max file size (in MB) is 25.";
	
	/**
	 * To enter a new message
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static void sendMessage(String strSubject, String strMessage)  throws Exception{
		//BMWMessagePage.tabMessage.click();
				
		for(int retry=0;retry< DTNConstant.MAX_FAIL_RETRY;retry++){
			if(retry==0){
				Log.info("****** Enter Message ******");
			}else{
				if(retry>0 && retry< DTNConstant.MAX_FAIL_RETRY){
					Log.info("****** Check if all fields are entered/selected correctly ******");
				}				
			}
			try {
				BMWMessagePage.txtSubject.enter(strSubject);
				BMWMessagePage.txtMessage.enter(strMessage);
				BMWMessagePage.btnSend.clickAndIgnoreAlert();
				Page.waitForPageLoad(driver);
				BMWMessagePage.btnPlus.click();
				Thread.sleep(1000);
				Page.waitForPageLoad(driver);
				if (checkSubjectInTable(strSubject)&&checkMessageInTable(strMessage)) {
					Log.pass("****** Enter Message Successfully******");
					break;
				}else Log.fail("****** Message sent failed *****");;
			}catch(Exception e){
				throw(e);
			}
			if(retry== DTNConstant.MAX_FAIL_RETRY-1){
				Log.info("****** Finish entering Message ******");
			}
		}
	}
	
	
	/**
	 * To enter a new message
	 * @param File path to attach
	 * @param Finle name to attach
	 * @param message subject
	 * @param message information
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static void sendMessage(String strSubject, String strMessage, String strFilename, String strPath)  throws Exception{
		// get current window
		String mainWindowHandle = driver.getWindowHandles().iterator().next();

		for(int retry=0;retry< DTNConstant.MAX_FAIL_RETRY;retry++){
			if(retry==0){
				Log.info("****** Enter Message ******");
			}else{
				if(retry>0 && retry< DTNConstant.MAX_FAIL_RETRY){
					Log.info("****** Check if all fields are entered/selected correctly ******");
				}				
			}

			try {
				BMWMessagePage.txtSubject.enter(strSubject);
				BMWMessagePage.txtMessage.enter(strMessage);
				BMWMessagePage.btnAttach.clickAndIgnoreAlert();
				//To call the AutoIt script
				
				Runtime.getRuntime().exec(DTNConstant.UPLOADEXEFILE+" "+strPath+" "+ strFilename);
				
				Thread.sleep(3000);
				driver.switchTo().window(mainWindowHandle);
				if (checkAttachment(strFilename)) {
					if (BMWMessagePage.btnAttach.isEnabled()) {
						Log.fail("****** Attach button should be set to Disabled******");
					}else {
						Log.pass("****** Attach button is to Disabled******");
					}
					
					Log.info("****** Sending Message ******");
					BMWMessagePage.btnSend.click();
					Thread.sleep(1000);
					Page.waitForPageLoad(driver);
					BMWMessagePage.btnPlus.click();
					Thread.sleep(1000);
					Page.waitForPageLoad(driver);
					if (checkSubjectInTable(strSubject)) {
						if (checkFirstAttachment(strFilename)) {
							Log.pass("****** Sent Message with attachment Successfully******");
							break;
						}
					};
				}
			}catch(Exception e){
				throw(e);
			}
			if(retry== DTNConstant.MAX_FAIL_RETRY-1){
				Log.info("****** Finish entering Message ******");
			}
		}

	}
	
	/**
	 * To Cancel sending an Attachement
	 * @param File path to attach
	 * @param Finle name to attach
	 * @param message subject
	 * @param message information
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static void cancelSendMessage(String strSubject, String strMessage, String strFilename, String strPath)  throws Exception{
		// get current window
		String mainWindowHandle = driver.getWindowHandles().iterator().next();
		// do what you want in other window
		// ...
		// switch back
		for(int retry=0;retry< DTNConstant.MAX_FAIL_RETRY;retry++){
			if(retry==0){
				Log.info("****** Enter Message ******");
			}else{
				if(retry>0 && retry< DTNConstant.MAX_FAIL_RETRY){
					Log.info("****** Check if all fields are entered/selected correctly ******");
				}				
			}

			try {
				BMWMessagePage.txtSubject.enter(strSubject);
				BMWMessagePage.txtMessage.enter(strMessage);
				BMWMessagePage.btnAttach.clickAndIgnoreAlert();
				//To call the AutoIt script
				
				Runtime.getRuntime().exec(DTNConstant.UPLOADEXEFILE+" "+strPath+" "+ strFilename);
				
				Thread.sleep(3000);
				driver.switchTo().window(mainWindowHandle);
				if (checkAttachment(strFilename)) {
					if (BMWMessagePage.btnAttach.isEnabled()) {
						Log.fail("****** Attach button should be set to Disabled******");
					}else {
						Log.pass("****** Attach button is to Disabled******");
					}
					

					BMWMessagePage.btnCancelFile.click();

					Thread.sleep(5000);
					if (BMWMessagePage.btnAttach.isEnabled()) {
						Log.pass("****** Cancel attachement Successfully******");
						break;
					};
				}
			}catch(Exception e){
				throw(e);
			}
			if(retry== DTNConstant.MAX_FAIL_RETRY-1){
				Log.info("****** Finish entering Message ******");
			}
		}
	}
	
	
	/**
	 * To verify the message subject sent is displayed in the list of left
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static boolean checkSubjectInTable(String expectedSubject) throws Exception{
		try{	
			Log.info("Check if subject:"+expectedSubject+"----- is in Message table");
			Frame.moveToFrame(driver, CommonObjects.mainFrame);
			String SubjectInTable; 
			expectedSubject=expectedSubject.trim().replace("\n", " ");
			if (expectedSubject.length()>DTNConstant.MAX_Length_80) {
				expectedSubject=expectedSubject.substring(0, DTNConstant.MAX_Length_80-1);
			}
			SubjectInTable = txtFirstMSGSubject.getText().trim();
			if(SubjectInTable.equals(expectedSubject)){
				Log.pass("*** Actual result: Subject "+SubjectInTable+" is sent successfully.");
				return true;
			}else{
				 Log.fail("*** Actual result: Subject "+SubjectInTable+" is NOT displayed successfully.");
				 throw new Exception("Subject is not displayed in the list.");
			 }
		 }catch(Exception e){	
			 Log.fail("*** Actual result: Subject:"+expectedSubject+" is NOT displayed.", "Exception occured - "+e.getMessage() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			 
			 throw(e);
			 
		 }
	 }
	
	
	/**
	 * To verify the message sent is displayed in the list of left
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static boolean checkMessageInTable(String expectedMessage) throws Exception{
		try{	
			Log.info("Check if subject:"+expectedMessage+"----- is in Message table");
			Frame.moveToFrame(driver, CommonObjects.mainFrame);
			String MessageInTable; 
			expectedMessage=expectedMessage.trim().replace("\n", " ");
			if (expectedMessage.length()>DTNConstant.MAX_LENGTH_4000) {
				expectedMessage=expectedMessage.substring(0, DTNConstant.MAX_LENGTH_4000);
			}
			MessageInTable = BMWMessagePage.txtFirstMSGNote.getText().trim();
			if(MessageInTable.equals(expectedMessage)){
				Log.pass("*** Actual result: Message "+MessageInTable+" is sent successfully.");
				return true;
			}else{
				 Log.fail("*** Actual result: Message "+MessageInTable+" is NOT displayed successfully.");
				 throw new Exception("Subject is not displayed in the list.");
			 }
		 }catch(Exception e){	
			 Log.fail("*** Actual result: Message:"+expectedMessage+" is NOT displayed.", "Exception occured - "+e.getMessage() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			 
			 throw(e);
			 
		 }
	 }
	
	/**
	 * To verify file is attached and displayed in the field, and the attach button is set to disable. 
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static boolean checkAttachment(String expectedFile) throws Exception{
		
		 try{	
			Log.info("Check if File: "+expectedFile+"----- is uploaded successfully");
			Frame.moveToFrame(driver, CommonObjects.mainFrame);
			String fileAttached; 
			expectedFile=expectedFile.trim();
			fileAttached = BMWMessagePage.txtFile.getText().trim();
			if(fileAttached.equals(expectedFile)){
				 //Log.pass("*** Actual result: deal no:"+message+" is displayed in Deal Status table.");
				 //System.out.println("*** Actual result: File:----"+fileAttached+"  is attached successfully." + expectedFile);
				 return true;
			 }else{
				 //Log.fail("*** Actual result: deal no:"+message+" is NOT displayed in Deal Status table.");
				 //System.out.println("*** Actual result: File:----"+fileAttached+"  is NOT attached successfully." + expectedFile);
				 throw new Exception("Deal is not in status table");
				 
			 }
		 }catch(Exception e){	
			 Log.fail("*** Actual result: File "+expectedFile+" is NOT attached successfully.", "Exception occured - "+e.getMessage() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			 
			 throw(e);
			 
		 }
	 }	
	/**
	 * To verify file is attached and displayed in the field, and the attach button is set to disable. 
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static boolean checkFirstAttachment(String expectedFile) throws Exception{
		
		 try{	
			Log.info("Check if File: "+expectedFile+"----- is uploaded successfully");
			Frame.moveToFrame(driver, CommonObjects.mainFrame);
			String fileAttached; 
			expectedFile=expectedFile.trim();
			fileAttached = BMWMessagePage.txtFirstMSGFile.getText().trim();
			if(fileAttached.equals(expectedFile)){
				 Log.pass("*** Actual result: deal no:"+expectedFile+" is displayed in Deal Status table.");
				 //System.out.println("*** Actual result: File:----"+fileAttached+"  is sent successfully." + expectedFile);
				 return true;
			 }else{
				 Log.fail("*** Actual result: deal no:"+expectedFile+" is NOT displayed in Deal Status table.");
				 //System.out.println("*** Actual result: File:----"+fileAttached+"  is NOT sent successfully." + expectedFile);
				 throw new Exception("Deal is not in status table");
				 
			 }
		 }catch(Exception e){	
			 Log.fail("*** Actual result: File "+expectedFile+" is NOT sent successfully.", "Exception occured - "+e.getMessage() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			 
			 throw(e);
			 
		 }
	 }	
	
    public static void mandatoryValidation(String strSubject,String strMessage) throws Exception{

    	String actualSubMessage;
        String actualNotesMessage;
        try {
        	btnSend.getText();		
        	BMWMessagePage.txtSubject.enter(strSubject);
        	BMWMessagePage.txtMessage.enter(strMessage);
        	
        	if (btnSend.isEnabled()){
        		Log.pass("**********Passed: Send button is set to enabled when all mandatory fields are input. **************");
        	}else {
        		Log.fail("******Fail:Send button is not set to enable when all mandatory fields are input*****");
        		throw new Exception("******Fail:Send button is not set to enable when all mandatory fields are input*****");
            }
        	Log.info("Empty subject...");
            txtSubject.sendKeys(Keys.CONTROL+"a");
            txtSubject.enterKeyBoard(Keys.DELETE);
           Thread.sleep(2000);
           if (BMWMessagePage.lblSubjectValMessage.isDisplayed()) {
        	   actualSubMessage=BMWMessagePage.lblSubjectValMessage.getText().trim();
               if (actualSubMessage.equals(strExpectedValSubject)) {
            	   	if (BMWMessagePage.btnSend.isEnabled()==false){
                        Log.pass("**********Subject Validation Passed**************");
            	   	}else {
            	   		Log.fail("******Fail--Send Button is not disabled when Subject is empty*****");
            	   		throw new Exception("******Fail--Send Button is not disabled when Subject is empty*****");
            	   	}
               }
           }else {
            	   Log.fail("******Fail--Validation Message for Subject not displayed*****");
            	   throw new Exception("******Fail--Validation Message for Subject not displayed*****");
           }
            	   
         Log.info("Empty Message...");
         txtMessage.sendKeys(Keys.CONTROL+"a");
         txtMessage.enterKeyBoard(Keys.DELETE);
         Thread.sleep(2000);
         if (BMWMessagePage.lblNotesValMessage.isDisplayed()) { 
               actualNotesMessage=BMWMessagePage.lblNotesValMessage.getText().trim();
               if (actualNotesMessage.equals(strExpectedValMessage)) {
            	   	if (BMWMessagePage.btnSend.isEnabled()==false){
                        Log.pass("**********Subject Validation Passed**************");
            	   	}else {
            	   		Log.fail("******Fail--Send Button is not disabled when Subject is empty*****");
            	   		throw new Exception("******Fail--Send Button is not disabled when Subject is empty*****");
            	   		
            	   	}
               }
          }else { Log.fail("******Fail--Validation Message for Subject not displayed*****");
          		throw new Exception("******Fail--Validation Message for Subject not displayed*****");
         }

        }catch(Exception e){ 
               Log.fail("****Mondatary verification failed*********");                 
                throw(e);
               
        }
        
    }
    
	/**
	 * To verify file over 25MB can't be attached and error message is displayed. 
	 * @throws Exception
	 * @author Maggie.jiang
	 */
    
    public static void maxAttachmentValidation(String strSubject, String strMessage, String strPath, String strFilename) throws Exception{
		// get current window
		String mainWindowHandle = driver.getWindowHandles().iterator().next();
	
		for(int retry=0;retry< DTNConstant.MAX_FAIL_RETRY;retry++){
			if(retry==0){
				Log.info("****** Enter Message ******");
			}else{
				if(retry>0 && retry< DTNConstant.MAX_FAIL_RETRY){
					Log.info("****** Check if all fields are entered/selected correctly ******");
				}				
			}

			try {
	        	BMWMessagePage.txtSubject.enter(strSubject);
	        	BMWMessagePage.txtMessage.enter(strMessage);
				BMWMessagePage.btnAttach.clickAndIgnoreAlert();
				//To call the AutoIt script
				Runtime.getRuntime().exec(DTNConstant.UPLOADEXEFILE+" "+strPath+" "+ strFilename);
				Thread.sleep(3000);
				driver.switchTo().window(mainWindowHandle);
				Frame.moveToFrame(driver, mainFrame);
				String strActual = BMWMessagePage.lblAttachValMessage.getText().trim();
				
				if (BMWMessagePage.lblAttachValMessage.isDisplayed()) {
					if (strActual.equals(strExpectedValAttach)) {
						if (BMWMessagePage.btnAttach.isEnabled()) {
							Log.pass("****** Attach file over 25M test passed. ******");
							break;
						}
				}else {
							Log.fail("****** Attach file over 25M test failed******");
							throw new Exception("****** Attach file over 25M test failed******");
						}
				}
				
			}catch(Exception e){
				Log.fail("****** Attach file over 25M test failed******");
				throw(e);
			}
			if(retry== DTNConstant.MAX_FAIL_RETRY-1){
				Log.info("****** Finish entering Message ******");
			}
		}
        
    }     
}