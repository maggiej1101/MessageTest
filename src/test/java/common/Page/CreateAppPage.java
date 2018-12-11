package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;
import dtn.automation.core.Page;
import dtn.automation.utilities.Frame;

public class CreateAppPage extends BaseTestSuite{
	public static final String PageName = "Create App Page";	
	public static Element tabCreateApp = new Element(By.id("PrepareApp"),"Create App");
	public static Element lstAsset = new Element(By.id("ddAsset"),"Asset dropdown list");
	public static Element lstLender = new Element(By.id("ddLenders"),"Lender dropdown list");
	public static Element lstProduct = new Element(By.id("ddProduct"),"Product dropdown list");
	public static Element lstPrimaryApplicantType = new Element(By.id("ddApplicantType"),"Primary Applicant Type dropdown list");
	public static Element btnCancel = new Element(By.id("btnCancel"),"Cancel button");

	public static Element btnContinue = new Element(By.id("btnSave"),"Continue button");

	/**
	 * To click on 'Create App' and select value at Create App page
	 * @param asset
	 * @param lender
	 * @param product
	 * @param PrimaryApplicantType
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void createApp(String lender) throws Exception{	 
		Frame.moveToChildFrame(driver,CommonObjects.parentFrame,CommonObjects.topFrame);
		CommonObjects.lnkCreateApp.click();
		Frame.moveToChildFrame(driver, CommonObjects.parentFrame,CommonObjects.mainFrame);
	    CreateAppPage.lstAsset.select(testData.getApplicantValue("ASSET"));
	    CreateAppPage.lstLender.select(lender);
	    CreateAppPage.lstProduct.select(testData.getApplicantValue("PRODUCT"));
	    CreateAppPage.lstPrimaryApplicantType.select(testData.getApplicantValue("APPLICANT_TYPE"));
		CreateAppPage.btnContinue.click();	
		Page.verifyPageWithElement(DealManagementPage.PageName, DealManagementPage.lblApplicationNumber,DealManagementPage.ddLender,DealManagementPage.ddProduct);
	}

	

}
