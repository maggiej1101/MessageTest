package common.Page;

import org.openqa.selenium.By;



import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;
import dtn.automation.core.CheckBox;
import dtn.automation.core.RadioButton;
import dtn.automation.utilities.Log;

public class CreditBureauPage extends BaseTestSuite{
	public static final String PageName = "Credit Bureau Page";	
	public static Element creditBureauLoadingPopup = new Element(By.xpath("//div[@id='DTC$ModalPopup$Content' and (contains(text(), 'Loading, please wait...'))]"),"Loading Popup");
	public static Element requiredFieldSymbol = new Element(By.xpath("//*[contains(@id,'_$$EP')]"),"Required field symbol");
	public static Element lblCreditBureau = new Element(By.xpath(".//*[@id='Label1' and contains(text(),'Credit Bureau')]"),"Credit Bureau label");
	public static Element tabCreditBureau = new Element(By.id("CreditBureau"),"Credit Bureau tab");	
	public static RadioButton rdoEquifax = new RadioButton(By.id("rdoEquifax"),"Equifax radio");
	public static RadioButton rdoIndividual = new RadioButton(By.id("rdoIndividual"),"Consumer");
	public static Element txtAppFirstName = new Element(By.id("txtAppFirstName"),"App First Name");
	public static Element txtAppLastName = new Element(By.id("txtAppLastName"),"App Last Name");
	public static Element txtAppDateofBirth_MM = new Element(By.id("txtAppDateofBirth_MM"),"App Date of Birth MM");	
	public static Element txtAppDateofBirth_DD = new Element(By.id("txtAppDateofBirth_DD"),"App Date of Birth DD");	
	public static Element txtAppDateofBirth_YYYY = new Element(By.id("txtAppDateofBirth_YYYY"),"App Date of Birth YYYY");
	public static Element txtPostalCode = new Element(By.id("ctl07_ctl00_txtPostalCode"),"Postal Code");	
	public static Element ddlAddressType = new Element(By.id("ctl11_ctl00_ddlAddressType"),"Address Type");
	public static Element txtAddressNo = new Element(By.id("ctl11_ctl00_txtStreetNumber"),"Street Number");	
	public static Element txtStreetName = new Element(By.id("ctl11_ctl00_txtStreetName"),"Street Name");

	public static Element ddlStreetType = new Element(By.id("ctl11_ctl00_ddlStreetType"),"Street Type");
	public static Element txtCity = new Element(By.id("ctl09_ctl00_txtCity"),"City");
	public static Element ddlProvince = new Element(By.id("ctl09_ctl00_ddlProvince"),"Province");
	public static CheckBox ckbViewConsent = new CheckBox(By.id("ckbViewConsent"),"View consent");
	public static Element btnPullReport = new Element(By.id("btnPullReport"),"Pull Report");
	public static Element btnContinue = new Element(By.id("btnContinue"),"Continue button");
	public static Element tabHTMLReport = new Element(By.id("__tab_ajaxTabs_tabHTMLReport"),"HTML report tab");
	public static Element btnCloseTop = new Element(By.id("btnCloseTop"),"Close button in Equifax report");
	
	public static void enterCreditBureauInfor(String firstName,String lastName) throws Exception{
		//Retry=2 to check if the values are entered/selected correct
		int maxRetry = 2;
		for(int retry=0;retry<maxRetry;retry++){
			if(retry==0){
				Log.info("****** Enter information for Credit Bureau ******");
			}else{
				if(retry>0 && retry<maxRetry){
					Log.info("****** Check if all fields are entered/selected correctly ******");
				}				
			}
			rdoEquifax.checked();
			rdoIndividual.checked();
		    txtAppFirstName.enter(firstName);		    
		    txtAppLastName.enter(lastName);
		    txtAppDateofBirth_MM.enter(testData.getCreditBureauValue("CB_BIRTHMM"));
		    txtAppDateofBirth_DD.enter(testData.getCreditBureauValue("CB_BIRTHDD"));
		    txtAppDateofBirth_YYYY.enter(testData.getCreditBureauValue("CB_BIRTHYEAR"));
		    txtPostalCode.enter(testData.getCreditBureauValue("CB_POSTAL"));
		    ddlAddressType.select(testData.getCreditBureauValue("CB_ADDRESSTYPE"));		    
		    txtAddressNo.enter(testData.getCreditBureauValue("CB_ADDRESSNO"));
		    txtStreetName.enter(testData.getCreditBureauValue("CB_STREETNAME"));
		    ddlStreetType.select(testData.getCreditBureauValue("CB_STREETTYPE"));
		    txtCity.enter(testData.getCreditBureauValue("CB_CITY"));
		    ddlProvince.select(testData.getCreditBureauValue("CB_PROVINCE"));
		    ckbViewConsent.checked();		
		    if(retry==maxRetry-1){
				Log.info("****** Finish entering information for Credit Bureau ******");
			}
		}
	}
}
