package common.TestMethods;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

import common.Page.CommonObjects;
import common.Page.DealManagementPage;
import common.Page.DealManagement_ApplicantsPage;
import common.Page.DealManagement_DocumentsPage;
import common.Page.DealManagement_WorksheetPage;
import common.Page.DealerSearchAndSwitchPage;
import common.Page.LoginPage;
import common.TestSuite.BaseTestSuite;
import dtn.automation.core.Page;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;

public class CommonTestMethods extends BaseTestSuite{
	
	/**
	 * To switch dealership. Check if Dealership is not expected dealer, 
	 * then switch to expected dealer, if 'Dealer Switch' title is presented, 
	 * then select expected dealer by clicking on Switch button. If 'Dealer Switch' 
	 * title is not presented, then select expected dealer by entering dealer# into textbox.
	 * @param dealershipNo
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void switchDealership(String dealershipNo) throws Exception{	
		String currentDealership;		
		//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.bottomFrame);
		Frame.moveToFrame(driver, CommonObjects.bottomFrame);
		currentDealership = CommonObjects.lblDealerInforAtBottomPage.getText();
		if(!currentDealership.contains(dealershipNo)){
			Log.info("*** Switch to '"+dealershipNo+"' dealership");
			//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.topFrame);
			Frame.moveToFrame(driver, CommonObjects.topFrame);
			CommonObjects.lnkSwitch.clickAndIgnoreAlert();
			//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.mainFrame);
			Frame.moveToFrame(driver, CommonObjects.mainFrame);
			Page.waitForPageLoad(driver);
			if(Page.verifyTextOnPage(driver, "Dealer Switch", "Dealer Search and Switch")){				
				// Line added by Quinn
				//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.mainFrame);
				Frame.moveToFrame(driver, CommonObjects.mainFrame);
				DealerSearchAndSwitchPage.txtDealerID.enter(dealershipNo);
				//Element btnSwitch = new Element(By.xpath("//input[contains(@onclick,\'" + dealershipNo + "\')]"),"Switch button for "+dealershipNo);
				Element btnSwitch = new Element(By.name("BtnSwitch"),"Switch button");
				// Retry to click on switch button again due to clicking on Switch button sometime not working
				for (int retry = 0;retry<DTNConstant.MAX_FAIL_RETRY; retry++) {
					try {
						btnSwitch.clickAndIgnoreAlert();
						//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.mainFrame);
						Frame.moveToFrame(driver, CommonObjects.mainFrame);
						Page.waitForPageLoad(driver);
						//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.bottomFrame);
						Frame.moveToFrame(driver, CommonObjects.bottomFrame);
						currentDealership = CommonObjects.lblDealerInforAtBottomPage.getText();
						Log.debug("Dealership after switch: "+currentDealership);
						if(currentDealership.contains(dealershipNo)){
							Log.pass("*** Actual result: switch to "+ dealershipNo +" dealership successfully.");
							break;
						}else{
							if(retry==DTNConstant.MAX_FAIL_RETRY){
								Log.fail("*** Actual result: user can NOT be able to switch to '"+dealershipNo+"' dealership");
								throw new Exception("Switch dealership is failed.");
							}else {
								//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.topFrame);
								Frame.moveToFrame(driver, CommonObjects.topFrame);
							}
						}
					}
					catch (Exception e2) {
						if(retry==DTNConstant.MAX_FAIL_RETRY){
							Log.fail("*** Actual result: user can NOT be able to switch to '"+dealershipNo+"' dealership"
									+" - Exception at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber(),e2.toString());
				    		throw (e2);
				    	}else {
							//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.topFrame);
							Frame.moveToFrame(driver, CommonObjects.topFrame);
						}
						Thread.sleep(1000);
					}
				}
					
			}else{
				for (int retry = 0;retry<=DTNConstant.MAX_FAIL_RETRY; retry++) {
					try {
						//switch dealership using Dealer Search And Switch method
						//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.mainFrame);
						Frame.moveToFrame(driver, CommonObjects.mainFrame);
						DealerSearchAndSwitchPage.txtDealerID.sendTextWithRetry(dealershipNo);
						DealerSearchAndSwitchPage.btnSwitch.click();
						Page.waitUntilPopupDisappear(driver, CommonObjects.popupModal.getBy());
						Frame.moveToFrame(driver, CommonObjects.bottomFrame);
						//Frame.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.bottomFrame);
						currentDealership = CommonObjects.lblDealerInforAtBottomPage.getText();
						Log.debug("Dealership after switch: "+currentDealership);
						if(currentDealership.contains(dealershipNo)){
							Log.pass("*** Actual result: switch to "+ dealershipNo +" dealership successfully.");
							break;
						}else{
							if(retry==DTNConstant.MAX_FAIL_RETRY){
								//Log.fail("*** Actual result: failed switching to '"+dealershipNo+"' dealership");
								throw new Exception("Switch dealership is failed.");
							}
						}
					}
					catch (Exception e2) {
						Log.debug(e2.toString());
						if(retry==DTNConstant.MAX_FAIL_RETRY){
							//Log.fail("*** Actual result: failed switching to '"+dealershipNo+"' dealership"
							//		+" - Exception at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber(),e2.toString());
				    		throw (e2);
				    	}
					}
				}
			}
		}
	}
	/**
	 * To enter value for Create App (Applicants, Worksheet, Document)
	 * If submit is failed, it will try to re-enter/select Applicant/Worksheet again
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public static String enterAllDealsInfoAndSubmit(String firstName, String lastName) throws Exception{
		String applicationNumber = null;
		boolean retryStatus = false;
		for(int retry=0;retry<=DTNConstant.MAX_RETRY_ENTER_REQUIRED_FIELD;retry++){	
			DealManagement_ApplicantsPage.enterPrimaryApplicants(firstName,lastName);			
			DealManagement_WorksheetPage.enterWorksheet();			
			DealManagement_DocumentsPage.enterDocument(firstName, lastName);
			//Save Deal
			applicationNumber = DealManagementPage.saveDeal();
			retryStatus = DealManagementPage.submitDeal(applicationNumber); 
			if(!retryStatus){
				break;
			}else{
				Log.info("***** Validation error(s) have occurred. RETRY to enter required fields: *****");
			}
		}
		if(retryStatus){
			Log.fail("*** Actual result: submit deal is failed!");
			throw new Exception("Submit deal is failed");
		}
		return applicationNumber;
	}
	/**
	 * To log out DTN web app
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void logOut() throws Exception{					
		Frame.moveToChildFrame(driver, CommonObjects.parentFrame, CommonObjects.bottomFrame);
		CommonObjects.lnkLogOut.clickAndIgnoreAlert();
		Page.verifyPageWithElement(LoginPage.PageName, LoginPage.btnLogin);			
	}
}
