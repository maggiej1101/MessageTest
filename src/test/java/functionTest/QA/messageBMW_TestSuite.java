package functionTest.QA;

import static common.Page.CommonObjects.mainFrame;
import static common.Page.CommonObjects.popupModal;
import static common.Page.CommonObjects.topFrame;
import static common.TestMethods.CommonTestMethods.switchDealership;

import org.openqa.selenium.Alert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.Page.BMWMessagePage;
import common.Page.DTNMessagePage;
import common.Page.DealManagementPage;
import common.Page.HomePage;
import common.Page.StatusPage;
import common.TestSuite.BaseTestSuite;
import dtn.automation.core.Page;
import dtn.automation.listeners.RetryAnalyzer;
import dtn.automation.utilities.DTNConstant;

import dtn.automation.utilities.Frame;
import dtn.automation.utilities.Log;
import dtn.automation.utilities.TestEnvironment;


/**
 * Function0 test - message sending and verification 
 * @since Nov 23, 2018
 * @author Maggie.Jiang
 *
 */

public class messageBMW_TestSuite extends BaseTestSuite {
	
	//private String URLTest = "UAT_DealertrackCanada";
	//FOr Dev testing
	private String URLTest = "DEV_Dealertrack";
	String dealerShip;
	String dealNo;
	String strSubject;
	String strNotes;
	String strAppNumber;
    String strExpectedSubject;
    String strExpectedMessage;
	TestEnvironment testClassEnvironment = new TestEnvironment();

	@BeforeClass	
	@Parameters({"browserTest"})
	public void TestClassSetUp(@Optional("CHROME") String browserTest){			
		DTNConstant.EXPLICIT_WAITTIME = 150;
		DTNConstant.LOADING_POPUP_TIMEOUT = 240;
		
		super.TestDataPath = DTNConstant.workingDir + "\\src\\test\\java\\functionTest\\QA\\BMWMessageTest_Testdata_QA.xlsx";
		super.ClassSetUp(browserTest,testClassEnvironment,URLTest);

	}
	@AfterClass
	public void TestClassTeardown() throws Exception {
		super.ClassTeardown();
	}
	
	@AfterMethod
	public void TestMethodTeardown(){		
		try {	
			RetryAnalyzer.setRetryMaximum(2);
			try{
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}catch(Exception e){				
			}	
			//commented on Nov 7 by M.J.
			//Frame.moveToFrame(driver,mainFrame);
			//HomePage.lnkHome.clickAndIgnoreAlert();
			//Add on Nov 7 by M.J.
			//BMWMessagePage.tabMessage.clickAndIgnoreAlert();
			//DealManagementPage.tabMessages.click(popupModal);
			
		} catch (Exception e) {				
			e.printStackTrace();
		}		
	}
	
	//DTN Regression test 1: 
	//Send a new message. 
	@Test
	public void TC02(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				moveToMessageTab(dealerShip,dealNo);
			    DTNMessagePage.sendMessage(strNotes);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	//DTN Regression test 1: 
	//Send a new message with multi lines 
	@Test
	public void TC03(){	
		  try{		
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
		    if (!isSameDeal(dealNo)) {
			    	moveToMessageTab(dealerShip,dealNo);
			    }
			    
			    DTNMessagePage.sendMessage(strNotes);
			    Log.endTestCase(testName,true,"",driver);
			    
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	//DTN Regression test 1: 
	//Send a new message with multi lines 
	@Test
	public void TC04(){	
		  try{		
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
				if (!isSameDeal(dealNo)) {
			    	moveToMessageTab(dealerShip,dealNo);
			    }
			    DTNMessagePage.verifyManditory();
			    Log.endTestCase(testName,true,"",driver);
			    
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	
	//DTN Regression test 1: 
	//Send a new message with special characters 
	@Test
	public void TC05(){	
		  try{				  					
				Log.startTestCase(testName);	
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strNotes = testData.getMessageValue("Notes");
				
				Frame.moveToFrame(driver, mainFrame);
				
			    if (!isSameDeal(dealNo)) {
			    	moveToMessageTab(dealerShip,dealNo);
			    }
			    
			    DTNMessagePage.sendMessage(strNotes);
			    Log.endTestCase(testName,true,"",driver);

		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}

	//DTN Regression test 1: 
	//Send a new message with maximum length
	@Test
	public void TC06(){	
		  try{				  					
				Log.startTestCase(testName);	
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strNotes = testData.getMessageValue("Notes");
				
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
			    
				    DTNMessagePage.sendMessage(strNotes);
				    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}

	//BMW Notes: Send a simple messge, and verify the subject of the message is displayed at left list. 
	@Test
	public void TC07(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				
				
				BMWMessagePage.sendMessage(strSubject, strNotes);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	//BMW Notes:  Send a message with attachement, verify the attach button gray out.
	
	@Test
	public void TC08(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				String strFilePath = testData.getMessageValue("FilePath");
				String strFileName = testData.getMessageValue("Attachment");
				
				Frame.moveToFrame(driver, mainFrame);
				
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				//-------System.out.println("Applition Number -------  "+DealManagementPage.lblApplicationNumber.getValue());
				
				BMWMessagePage.sendMessage(strSubject, strNotes, strFileName, strFilePath);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}

	//BMW Notes:  Cancel the file sending out. 
	
	
	@Test
	public void TC09(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				String strFilePath = testData.getMessageValue("FilePath");
				String strFileName = testData.getMessageValue("Attachment");

				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				//-------System.out.println("Applition Number -------  "+DealManagementPage.lblApplicationNumber.getValue());
				
				BMWMessagePage.cancelSendMessage(strSubject, strNotes, strFileName, strFilePath);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	//BMW Notes: Send a simple messge, and verify the subject of the message is displayed at left list. 
	@Test
	public void TC10(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				BMWMessagePage.sendMessage(strSubject, strNotes);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}

	//BMW Notes: mandatory fields validation. 
	@Test
    public void TC12(){
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");


				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				BMWMessagePage.mandatoryValidation(strSubject, strNotes);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	   
    }

	
	@Test
	public void TC11(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				BMWMessagePage.sendMessage(strSubject, strNotes);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	//BMW Notes: Send a simple messge, and verify the subject of the message is displayed at left list. 
	@Test
	public void TC13(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
				
				
				BMWMessagePage.sendMessage(strSubject, strNotes);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	
	//BMW Notes: Attache a file over 25MB, expecting warning message and file won't be uploaded. 
	@Test
	public void TC14(){	
		  try{			
			  	Log.startTestCase(testName);
				dealerShip = testData.getMainValue("DEALERSHIP");
				dealNo = testData.getMainValue("DEAL_NO");
				strSubject = testData.getMessageValue("Subject");
				strNotes = testData.getMessageValue("Notes");
				String strFilePath = testData.getMessageValue("FilePath");
				String strFileName = testData.getMessageValue("Attachment");
				Frame.moveToFrame(driver, mainFrame);
				//New Deal to open... 
			    if (!isSameDeal(dealNo)) {
				    	moveToMessageTab(dealerShip,dealNo);
				    }
			
				BMWMessagePage.maxAttachmentValidation(strSubject,strNotes,strFilePath, strFileName);
			    Log.endTestCase(testName,true,"",driver);
		  }catch(Exception e){		  
			  Log.endTestCase(testName,false,"",driver);	
		  }	
	}
	/**
	 * Open a deal and navigate to Message Tab.
	 * @param Dealership  
	 * @param Deal No
	 * @author Maggie.jiang
	 */
	private void moveToMessageTab(String dealerShip, String dealNo) {
		try {
			switchDealership(dealerShip);

			Frame.moveToFrame(driver,topFrame);
			HomePage.lnkStatus.clickAndIgnoreAlert();
		    Frame.moveToFrame(driver, mainFrame);
		    StatusPage.lstAsset.select("Automotive",popupModal);		    
		    StatusPage.txtSearch.enter(dealNo); //Test Deal#
		    StatusPage.btnSearch.click(popupModal);			    
		    Page.verifyPageWithElement(HomePage.PageName, StatusPage.imgDealStatus);
		    StatusPage.imgDealStatus.click();
		    Frame.moveToFrame(driver, mainFrame);
		    Page.verifyPageWithElement(DealManagementPage.PageName,DealManagementPage.txtAppNumber,DealManagementPage.tabMessages);			   
		    Thread.sleep(2000);
		    DealManagementPage.tabMessages.click(popupModal);
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if the next test case is using same Deal.
	 * @param Deal No 
	 * @author Maggie.jiang
	 */
	private boolean isSameDeal(String dealNo) {
		boolean isSame = false;
		try {
			if (DealManagementPage.lblApplicationNumber.isDisplayed(DealManagementPage.lblApplicationNumber.getBy())) {
				if(DealManagementPage.lblApplicationNumber.getValue().contains(dealNo.trim())) {
					isSame = true;
				}
			} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			isSame = false;
			e.printStackTrace();
		}
		return isSame;
	}
}
