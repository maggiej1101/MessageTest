package smokeTest.UAT;

import org.openqa.selenium.Alert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static common.Page.CommonObjects.*;
import static common.TestMethods.CommonTestMethods.*;

import common.Page.CommonObjects;
import common.Page.CreateAppPage;
import common.Page.CreditBureauPage;
import common.Page.DealManagementPage;
import common.Page.HomePage;
import common.Page.StatusPage;
import common.TestSuite.BaseTestSuite;
import dtn.automation.utilities.TestEnvironment;
import dtn.automation.core.Page;
import dtn.automation.listeners.RetryAnalyzer;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;

/**
 * Smoke test POC on UAT DTN web app 
 * @since March 26, 2018
 * @author thao.le
 *
 */
public class UATSmokeTest_TestSuite extends BaseTestSuite {
	private String URLTest = "UAT_DealertrackCanada";	
	
	TestEnvironment testClassEnvironment = new TestEnvironment();
	@BeforeClass	
	@Parameters({"browserTest"})
	public void TestClassSetUp(@Optional("CHROME") String browserTest){			
		DTNConstant.EXPLICIT_WAITTIME = 150;
		DTNConstant.LOADING_POPUP_TIMEOUT = 240;
		super.TestDataPath = DTNConstant.workingDir + "\\src\\test\\java\\smokeTest\\UAT\\SmokeTest_Testdata_QA.xlsx";
		super.ClassSetUp(browserTest,testClassEnvironment,URLTest);
	}
	@AfterClass
	public void TestClassTeardown() throws Exception {
		super.ClassTeardown();
	}
	@AfterMethod
	public void TestMethodTeardown(){		
		try {	
			RetryAnalyzer.setRetryMaximum(DTNConstant.MAX_RETRY_FAILED_TESTCASE);
			try{
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}catch(Exception e){				
			}
			Frame.moveToChildFrame(driver,parentFrame, topFrame);
			HomePage.lnkHome.clickAndIgnoreAlert();
		} catch (Exception e) {				
			e.printStackTrace();
		}		
	}
	@Test
	public void TC02(){	
		  try{				  					
				Log.startTestCase(testName);	
				String dealerShip = testData.getMainValue("DEALERSHIP");
				String dealNo = testData.getMainValue("DEAL_NO");				
				switchDealership(dealerShip);	
				Frame.moveToFrame(driver,topFrame);
			    HomePage.lnkStatus.click(popupModal);				    
			    Frame.moveToFrame(driver, mainFrame);
			    StatusPage.lstAsset.select("Automotive",popupModal);		    
			    StatusPage.txtSearch.enter(dealNo); //Test Deal#
			    StatusPage.btnSearch.click(popupModal);			    
			    Page.verifyPageWithElement(HomePage.PageName, StatusPage.imgDealStatus);
			    StatusPage.imgDealStatus.click();
			    Frame.moveToFrame(driver, mainFrame);
			    Page.verifyPageWithElement(DealManagementPage.PageName,DealManagementPage.txtAppNumber,DealManagementPage.tabApplicants,DealManagementPage.tabWorksheet,DealManagementPage.tabDocuments, DealManagementPage.tabMessages);			   
			    Thread.sleep(2000);
			    DealManagementPage.tabWorksheet.click(popupModal);
			    Page.verifyPageWithElement(DealManagementPage.PageName, DealManagementPage.verifyWorksheet);
			    DealManagementPage.tabDocuments.click(popupModal);
			    Page.verifyPageWithElement(DealManagementPage.PageName, DealManagementPage.verifyDocuments);
			    DealManagementPage.tabMessages.click(popupModal);
			    Page.verifyPageWithElement(DealManagementPage.PageName, DealManagementPage.verifyMessages);			    
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	/**
	 * 
	 * @author Thao Le
	 */
	@Test 
	public void TC04() {
		try {
			Log.startTestCase(testName);
			RetryAnalyzer.setRetryMaximum(0);
			String firstName = testData.getCreditBureauValue("CB_FIRSTNAME");
			String lastName = testData.getCreditBureauValue("CB_LASTNAME");
			String dealerShip = testData.getMainValue("DEALERSHIP");
			String consumerName = "MARTEN" + ", "+firstName;	
			boolean requiredFieldDisplayed = false;
			switchDealership(dealerShip);	
			Frame.moveToFrame(driver,topFrame);			
		    CreditBureauPage.tabCreditBureau.click();		    
		    Frame.moveToFrame(driver, mainFrame);
		    Page.verifyPageWithElement(CreditBureauPage.PageName,CreditBureauPage.lblCreditBureau);	
		    for(int retry=0;retry<=DTNConstant.MAX_RETRY_ENTER_REQUIRED_FIELD;retry++){
		    	requiredFieldDisplayed = false;
		    	CreditBureauPage.enterCreditBureauInfor(firstName, lastName);			    
			    CreditBureauPage.btnPullReport.click(CreditBureauPage.creditBureauLoadingPopup);	
			    try{
			    	requiredFieldDisplayed = CreditBureauPage.requiredFieldSymbol.isDisplayed(3);				    	
			    }catch(Exception e){				    	
			    }			    
			    if(requiredFieldDisplayed){
			    	Log.info("***** Some required fields are missing. RETRY to enter required fields: *****");
			    }else{
			    	Thread.sleep(4000);
				    Frame.moveToChildFrame(driver,parentFrame,mainFrame,dtcModalPopupFrame);		    
				    CreditBureauPage.btnContinue.click(CreditBureauPage.creditBureauLoadingPopup);
				    break;
			    }
		    }
		    Thread.sleep(4000);
		    Frame.moveToChildFrame(driver, parentFrame,mainFrame,dtcModalPopupFrame);			    
		    CreditBureauPage.tabHTMLReport.click();				    		    
		    Page.verifyPageDisplayedWithText(driver, CreditBureauPage.PageName, consumerName.toUpperCase());	
			Log.takeScreenshotAndReport(driver, testName);
		    CreditBureauPage.btnCloseTop.click();	
		    Log.endTestCase(testName, true, "", driver);
		} catch (Exception e) {
			Log.endTestCase(testName, false, "", driver);
		}
	}	
	/**
	 * 
	 * @author Thao Le
	 */
	@Test 
	public void TC07() {
		try {			
			Log.startTestCase(testName);			
			String firstName = testData.getApplicantValue("APP_FIRST_NAME");
			String lastName = testData.getApplicantValue("APP_LAST_NAME");	
			String applicationNumber;
			switchDealership(testData.getMainValue("DEALERSHIP"));
			//Create Deal
			CreateAppPage.createApp(testData.getApplicantValue("LENDER"));			
			applicationNumber = enterAllDealsInfoAndSubmit(firstName, lastName);
			StatusPage.checkDealNumberInStatusTable(applicationNumber);		
			//Copy Deal			
			String lender = "TDAF";
			String copyLender = "BNS";	
			StatusPage.imgDealsStatus.click();
			Frame.moveToFrame(driver,CommonObjects.mainFrame);
		    Page.verifyPageWithElement(DealManagementPage.PageName,DealManagementPage.ddLender,DealManagementPage.ddProduct);
			DealManagementPage.copyDeal(applicationNumber, lender, copyLender);						
		    Log.endTestCase(testName, true, "", driver);
		} catch (Exception e) {
			Log.endTestCase(testName, false, "", driver);
		}
	}		
}
