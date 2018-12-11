package common.Page;
import org.openqa.selenium.By;

import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;
import dtn.automation.core.Page;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;
import dtn.automation.utilities.ModalPopup;

public class DealManagementPage extends BaseTestSuite{
	public static final String PageName = "Deal Management Page";	
	public static Element txtAppNumber = new Element(By.id("txtApplicationNumber"), "Application Number");
	public static Element lblApplicationNumber = new Element(By.id("txtApplicationNumber"), "Application# label");
	public static Element ddLender = new Element(By.id("ddLender"), "Lender dropdown list");
	public static Element ddProduct = new Element(By.id("ddProduct"), "Product dropdown list");
	public static Element tabApplicants = new Element(By.id("ctl19_btnAPPLICANT"),"APPLICANTS Tab");
	public static Element tabWorksheet = new Element(By.id("ctl19_btnWORKSHEET"),"WORKSHEET Tab");
	public static Element tabDocuments = new Element(By.id("ctl19_btnDOCUMENTS"),"DOCUMENTS Tab");
	public static Element tabDecision = new Element(By.id("ctl19_btnAPPROVALCONDITIONS"),"DECISION Tab");
	public static Element tabMessages = new Element( By.cssSelector("[id$=_btnMESSAGES]"),"Message tab");
	public static Element verifyWorksheet = new Element(By.xpath("//input[@id='ctl00_txtMileage' or contains(@id,'txtCurrentKMs')]"),"WORKSHEET Current KMs");
	public static Element verifyDocuments = new Element(By.id("ctl00_chkViewed"),"DOCUMENTS Consent Form Checkbox");
	public static Element verifyMessages = new Element(By.xpath("//input[contains(@id,'btnNotesSend')]"),"MESSAGES Send New Message Button");
	public static Element btnSave = new Element(By.id("btnSave"),"Save button");
	public static Element btnOK = new Element(By.id("btnOK"),"OK button");
	public static Element btnSubmit = new Element(By.id("btnSubmit"),"Submit button");
	/**
	 * To save a Deal (click on Save button and check for application number after saving
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public static String saveDeal() throws Exception{
		DealManagementPage.btnSave.click();						
		ModalPopup.switchToFrameOfPopup(driver, CommonObjects.parentFrame,CommonObjects.mainFrame);
		ModalPopup.containText(CommonObjects.popupContent,"Your changes have been saved successfully.");
		ModalPopup.clickOnButton(CommonObjects.popupOKbutton);
		Frame.moveToChildFrame(driver,CommonObjects.parentFrame,CommonObjects.mainFrame);
		String temp = DealManagementPage.txtAppNumber.getAttribute("value");
		String[] ApplicationNo = temp.split("-");
		String applicationNumber = null;
		for(String appNo: ApplicationNo){
			applicationNumber = appNo;
			break;
		}
		Log.info("Deal Application has been created. Application#: "+applicationNumber);
		return applicationNumber;
	}
	/**
	 * To submit a deal (click on submit button, check for popup and make sure Deal 
	 * application number is in Status table after submitting
	 * @param applicationNumber
	 * @throws Exception
	 * @author Thao Le
	 */
	public static boolean submitDeal(String applicationNumber) throws Exception{
		boolean retry = false;
		boolean status = false;
		DealManagementPage.btnSubmit.click();
		ModalPopup.switchToFrameOfPopup(driver, CommonObjects.parentFrame,CommonObjects.mainFrame);						
		status = ModalPopup.containTextOnPopup(CommonObjects.popupContent,"There are application alerts. Do you wish to continue?");
		if(!status){
			retry = ModalPopup.containTextOnPopup(CommonObjects.popupContent,"Validation error(s) have occurred. Please review the application.");
			ModalPopup.clickOnButton(CommonObjects.popupOKbutton);
			return retry;
		}		
		ModalPopup.clickOnButton(CommonObjects.popupOKbutton);
		ModalPopup.containText(CommonObjects.popupContent,"Your application has been submitted.");
		ModalPopup.clickOnButton(CommonObjects.popupOKbutton);
		Page.waitUntilPopupDisappear(driver,CommonObjects.popupModal.getBy());		
	    return retry;
	}
	/**
	 * To copy a deal to different lender
	 * @param applicationNumber
	 * @param lender
	 * @param copyLender
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void copyDeal(String applicationNumber,String lender,String copyLender) throws Exception{
		DealManagementPage.ddLender.select(testData.getMainValue("COPYDEAL_LENDER"));
	    Frame.moveToChildFrame(driver, CommonObjects.parentFrame, CommonObjects.mainFrame,CommonObjects.dtcModalPopupFrame);
	    DealManagementPage.ddProduct.select(testData.getMainValue("COPYDEAL_PRODUCT"));
	    DealManagementPage.btnOK.click();
	    Frame.moveToChildFrame(driver, CommonObjects.parentFrame, CommonObjects.mainFrame);
	    DealManagementPage.btnSave.click();
	    ModalPopup.switchToFrameOfPopup(driver, CommonObjects.parentFrame,CommonObjects.mainFrame);
		ModalPopup.containText(CommonObjects.popupContent,"Your changes have been saved successfully.");
		ModalPopup.clickOnButton(CommonObjects.popupOKbutton);
		Frame.moveToChildFrame(driver, CommonObjects.parentFrame, CommonObjects.topFrame);
		CommonObjects.lnkStatus.click();			
		StatusPage.checkDealNumberInStatusTable(applicationNumber);			
		int colDeals = StatusPage.tblDealStatus.getColIndex("Deals");						
		String Deals = StatusPage.tblDealStatus.getCell(0, colDeals);		
		if(Deals.contains(copyLender)){
			Log.pass("*** Actual result: "+applicationNumber+" deal is displayed with "+copyLender + " lender.");
		}else{
			Log.fail("*** Actual result: "+applicationNumber+" deal is NOT displayed with "+copyLender + " lender.");
			throw new Exception();
		}
		if(Deals.contains(lender)){
			Log.pass("*** Actual result: "+applicationNumber+" deal is displayed with "+lender + " lender.");
		}else{
			Log.fail("*** Actual result: "+applicationNumber+" deal is NOT displayed with "+lender + " lender.");
			throw new Exception();
		}
	}
	
}
