package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;
import dtn.automation.core.CheckBox;
import dtn.automation.core.Page;

public class DealManagement_DocumentsPage extends BaseTestSuite{
	
	public static final String PageName = "Deal Management Documents Page";	
	public static Element lblApplicantName = new Element(By.xpath("//ctl00_lblApplicant0"),"Applicant Name");
	public static CheckBox chkTheApplicantHasReadConsentForm = new CheckBox(By.id("ctl00_chkViewed"),"Checkbox to check 'The applicant(s) has(have) read and signed a printed copy of the above consent form(s).'");	
	public static CheckBox chkNoTheLoanBeingTaken = new CheckBox(By.id("ctl19_ctl22_ctl00_chkYesNo_1"),"'No' checkbox to check 'Is the loan being taken for the benefit of someone other than the Applicant or Co-Applicant?'");
	/**
	 * To select requied fields in Document tab at Create App
	 * @param firstName
	 * @param lastName
	 * @throws Exception
	 * @author Thao Le
	 */
	public static void enterDocument(String firstName, String lastName) throws Exception{
		String applicantName = lastName + ", "+ firstName;
		DealManagementPage.tabDocuments.click();
//		Page.verifyPageDisplayedWithText(driver, DealManagement_DocumentsPage.PageName, applicantName);		
		DealManagement_DocumentsPage.chkTheApplicantHasReadConsentForm.checked();
		DealManagement_DocumentsPage.chkNoTheLoanBeingTaken.checked();			
	}
	
}
