package common.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;
import dtn.automation.core.WebTable;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;


public class StatusPage extends BaseTestSuite{	
	public static String PageName = "Status Page";
	public static Element btnSearch = new Element(By.id("btnSearch"),"Search button");
	public static Element lstAsset = new Element(By.id("ddlAssets2"),"Asset list");
	public static Element txtSearch = new Element(By.id("tbSearchCriteria"),"Search textbox");
	public static WebTable tblDealStatus = new WebTable(By.id("divView3_datagrid"), "Deals Status table");	
	public static Element imgDealsStatus = new Element(By.xpath(".//*[@id='divView3_0']/td[9]/table/tbody/tr/td/table/tbody/tr/td[1]/img"), "Deal in Status table");
	public static Element bntOK = new Element(By.id("DTC$ModalPopup$OK"), "OK button on Dealer Switch Required popup");	
	public static Element lstSearchType = new Element(By.id("ddlSearchType"), "Deal Search type");	
	public static Element imgDealStatus = new Element(By.xpath("//img[contains(@src,'/brands/dealertrack/images/icons/status_icons/en-CA')]"), "Deal Status image");
	/**
	 * To search for a deal no and check if it is in status table
	 * @param DealNo
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void checkDealNumberInStatusTable(String DealNo) throws Exception{	
		 try{	
			 Log.info("Check if deal no:"+DealNo+" is in Deal Status table");
			 Frame.moveToChildFrame(driver, CommonObjects.parentFrame, CommonObjects.mainFrame);
			 StatusPage.lstAsset.select("Automotive");		    
		     StatusPage.txtSearch.enter(DealNo); //Test Deal#
		     StatusPage.btnSearch.click(CommonObjects.popupModal);		     
			 int DealColumnIndex = StatusPage.tblDealStatus.getColIndex("Deal#");
			 String DealNoInStatusTable; 
			 DealNoInStatusTable = StatusPage.tblDealStatus.getCell(0, DealColumnIndex);
			 if(DealNoInStatusTable.equals(DealNo)){
				 Log.pass("*** Actual result: deal no:"+DealNo+" is displayed in Deal Status table.");					 
			 }else{
				 Log.fail("*** Actual result: deal no:"+DealNo+" is NOT displayed in Deal Status table.");
				 throw new Exception("Deal is not in status table");
			 }
		 }catch(Exception e){	
			 Log.fail("*** Actual result: deal no:"+DealNo+" is NOT displayed in Deal Status table.", "Exception occured - "+e.getMessage() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());			 
			 throw(e);
		 }		 		 
	 }
	
	
}
