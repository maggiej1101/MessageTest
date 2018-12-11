package common.Page;

import org.openqa.selenium.By;
import dtn.automation.core.Element;
import dtn.automation.core.Page;
import dtn.automation.core.WebTable;
import common.TestSuite.BaseTestSuite;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;

public class DTNMessagePage extends BaseTestSuite {

	public static final String PageName = "Deal Management Message Page";	
	 //------------ Message tab -------------------------------
	
	public static Element tabMessage = new Element( By.cssSelector("[id$=_btnMESSAGES]"),"Message tab");
	public static Element txtMessage = new Element(By.cssSelector("[id$=_txtNewNote]"),"Message Box");
	public static Element btnSend = new Element(By.cssSelector("[id$=_btnNotesSend]"),"Send Message Button");
	public static Element txtFirstMSG = new Element(By.xpath("//*[contains(@id,'_tableRow0\')]/td[3]/span/b"),"First message");
    public static Element imgExpand = new Element(By.cssSelector("table.data2 tr:nth-child(2) input[type='image']"),"Expansion Twisty");
    public static Element txtFullMessage=new Element(By.cssSelector("td.noteStyle:nth-child(3)"),"Full Message");
    public static Element txtTimeStamp=new Element(By.cssSelector("[id$=_tableRow0]> td:nth-child(5) > span"),"Time Stamp"); 
    public static Element txtFrom=new Element(By.cssSelector("[id$=_tableRow0]> td:nth-child(6) > span"),"From");  
    public static Element txtValMessage=new Element(By.cssSelector("[id$=_lblErrMsg"),"Validation Message");
  	//By.xpath("//input[contains(@id,'_txtAHInsurance')]")
	//public static Element txtMessage = new Element(By.id("ctl19_ctl18_ctl00_txtNewNote"),"Message Box");
	//public static Element btnSend = new Element(By.id("ctl19_ctl18_ctl00_btnNotesSend"),"Send Message Button");
	//public static Element txtFirstMSG = new Element(By.xpath("//*[@id=\"ctl19_ctl18_ctl00_tableRow0\"]/td[3]/span/b"),"First message");
	
	/**
	 * To enter a new message
	 * @throws Exception
	 * @author Maggie.jiang
	 */
	public static void sendMessage(String strMessage)  throws Exception{
		//BMWMessagePage.tabMessage.click();
		
		for(int retry=0;retry< DTNConstant.MAX_FAIL_RETRY;retry++){
			if(retry==0){
				Log.info("****** Enter Message ******");
			}else{
				if(retry>0 && retry< DTNConstant.MAX_FAIL_RETRY){
					Log.info("****** Check if all fields are entered/selected correctly ******");
				}				
			}
			//driver.findElement(By.id("ctl19_ctl18_ctl00_txtNewNote")).sendKeys("your text to enter");
			try {
				DTNMessagePage.txtMessage.enter(strMessage);
				DTNMessagePage.btnSend.click();
				Thread.sleep(2000);
				Page.waitForPageLoad(driver);
				if (checkMeessageInTable(strMessage)) {
					Log.pass("****** Enter Message Successfully******");
					break;
				};
			}catch(Exception e){
				throw(e);
			}
			if(retry== DTNConstant.MAX_FAIL_RETRY-1){
				Log.info("****** Finish entering Message ******");
			}
		}
	}
	
	public static boolean checkMeessageInTable(String expectedMessage) throws Exception{
		try{	
			String MSGInTable; 
			Log.info("Check if message:"+expectedMessage+"----- is in Message table");
			Frame.moveToFrame(driver, CommonObjects.mainFrame);
			DTNMessagePage.imgExpand.click();
			 
			Thread.sleep(600);

			expectedMessage=expectedMessage.replace("\n", " ").trim();
			MSGInTable = DTNMessagePage.txtFullMessage.getText().replace("\n", " ").trim();
			if (expectedMessage.length()>4000) {
				expectedMessage=expectedMessage.substring(0, 3999);
			}
			if(MSGInTable.equals(expectedMessage)){
				Log.pass("*** Actual result: "+MSGInTable+" is displayed in Deal Status table.");
				return true;
			}else{
				Log.fail("*** Actual result: "+MSGInTable+" is NOT displayed in Deal Status table.");
				throw new Exception("Message is not displayed in the list.");
			}
		 }catch(Exception e){	
			 Log.fail("*** Actual result: message:"+expectedMessage+" is NOT displayed in message table.", "Exception occured - "+e.getMessage() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			 
			 throw(e);
		 }
	 }
	
	public static void verifyManditory() throws Exception{
		String expectedValMessage="Please provide some text for the message.";
		try{	
			 Log.info("---- Verify Manditory fields ---");
			 Frame.moveToFrame(driver, CommonObjects.mainFrame);
			 DTNMessagePage.btnSend.click();
             if(DTNMessagePage.txtValMessage.getText().trim().equals(expectedValMessage)) {
                 Log.pass("****** Mandatory Validation passed******");
             }else{
            	 Log.fail("**Validation failed*** ");
            	 throw new Exception("Validation message not displayed");
             }
			}catch(AssertionError e){
				Log.fail("******Validation failed**************");
				throw(e);
			}
	 }
}
