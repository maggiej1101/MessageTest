package common.Page;
import org.openqa.selenium.By;
import dtn.automation.core.Element;
import common.TestSuite.BaseTestSuite;
import dtn.automation.utilities.Log;

public class DealManagement_ApplicantsPage extends BaseTestSuite{
	public static final String PageName = "Deal Management Applicants Page";	
	//------------ Applicant tab -------------------------------
	
		public static Element tabApplicants = new Element(By.id("ctl19_btnAPPLICANT"),"Applicants tab");
		public static Element ddlSalutation = new Element(By.id("ctl19_ctl20_ctl00_ddlSalutation"),"Salutation dropdown list");
		public static Element txtFirstName = new Element(By.id("ctl19_ctl20_ctl00_txtFirstName"),"First Name textbox");
		public static Element txtMiddleName = new Element(By.id("ctl19_ctl20_ctl00_txtMiddleName"),"Middle Name");
		public static Element txtLastName = new Element(By.id("ctl19_ctl20_ctl00_txtLastName"),"Last Name");
		public static Element txtPhone = new Element(By.id("ctl19_ctl20_ctl00_txtPhone"),"Phone");
		public static Element txtDateofBirthMM = new Element(By.id("ctl19_ctl20_ctl00_txtDateofBirth_MM"),"Date of Birth MM");
		public static Element txtDateofBirth_DD = new Element(By.id("ctl19_ctl20_ctl00_txtDateofBirth_DD"),"Date of Birth DD");
		public static Element txtDateofBirth_YYYY = new Element(By.id("ctl19_ctl20_ctl00_txtDateofBirth_YYYY"),"Date of Birth YYYY");
		public static Element ddlMaritalStatus = new Element(By.id("ctl19_ctl20_ctl00_ddlMaritalStatus"),"Marital Status");
		public static Element txtEmail = new Element(By.id("ctl19_ctl20_ctl00_txtEmail"),"Email");
		public static Element txtPostalCode = new Element(By.id("ctl19_ctl21_ctl00_txtPostalCode"),"Postal Code");
		public static Element ddlAddressType = new Element(By.id("ctl19_ctl21_ctl00_ddlAddressType"),"Address Type");
		public static Element txtStreetNumber = new Element(By.id("ctl19_ctl21_ctl00_txtStreetNumber"),"Street Number");
		public static Element txtStreetName = new Element(By.id("ctl19_ctl21_ctl00_txtStreetName"),"Street Name");
		public static Element ddlStreetType = new Element(By.id("ctl19_ctl21_ctl00_ddlStreetType"),"Street Type");
		public static Element ddlDirection = new Element(By.id("ctl19_ctl21_ctl00_ddlDirection"),"Direction");
		public static Element txtCity = new Element(By.id("ctl19_ctl21_ctl00_txtCity"),"City");
		public static Element CDurationCurrentAddress_Y = new Element(By.id("ctl19_ctl21_ctl00_CDurationCurrentAddress_Y"),"Duration Current Address Y");
		public static Element ddlHome = new Element(By.id("ctl19_ctl23_ctl00_ddlHome"),"Home");
		public static Element txtmarketValue = new Element(By.id("ctl19_ctl23_ctl00_txtmarketValue"),"Market Value");
		public static Element txtMortgageAmount = new Element(By.id("ctl19_ctl23_ctl00_txtMortgageAmount"),"Mortgage Amount");
		public static Element txtMortgageHolder = new Element(By.id("ctl19_ctl23_ctl00_txtMortgageHolder"),"Mortgage Holder");
		public static Element txtMonthlyPayment = new Element(By.id("ctl19_ctl23_ctl00_txtMonthlyPayment"),"Monthly Payment");
		public static Element ddlTypeCurEmp = new Element(By.id("ctl19_ctl24_ctl00_ddlTypeCurEmp"),"Current Employment - Type");
		public static Element txtEmployerCurEmp = new Element(By.id("ctl19_ctl24_ctl00_txtEmployerCurEmp"),"Current Employment - Employer");
		public static Element ddlStatusCurEmp = new Element(By.id("ctl19_ctl24_ctl00_ddlStatusCurEmp"),"Current Employment - Status");
		public static Element txtOccupationCurEmp = new Element(By.id("ctl19_ctl24_ctl00_txtOccupationCurEmp"),"Current Employment - Occupation");
		public static Element CDurationCurrentEmployerAddress_Y = new Element(By.id("ctl19_ctl24_ctl00_CDurationCurrentEmployerAddress_Y"),"Current Employment - Duration Y");
		public static Element CDurationCurrentEmployerAddress_M = new Element(By.id("ctl19_ctl24_ctl00_CDurationCurrentEmployerAddress_M"),"Current Employment - Duration M");
		public static Element ddlAddressTypeCurEmp = new Element(By.id("ctl19_ctl24_ctl00_ddlAddressTypeCurEmp"),"Current Employment - Address Type");
		public static Element txtStreetNumberCurEmp = new Element(By.id("ctl19_ctl24_ctl00_txtStreetNumberCurEmp"),"Current Employment -Address No");
		public static Element txtStreetNameCurEmp = new Element(By.id("ctl19_ctl24_ctl00_txtStreetNameCurEmp"),"Current Employment - Street Name");
		public static Element ddlStreetTypeCurEmp = new Element(By.id("ctl19_ctl24_ctl00_ddlStreetTypeCurEmp"),"Current Employment - Street Type");
		public static Element ddlDirectionCurEmp = new Element(By.id("ctl19_ctl24_ctl00_ddlDirectionCurEmp"),"Current Employment - Direction");
		public static Element txtTelephoneCurEmp = new Element(By.id("ctl19_ctl24_ctl00_txtTelephoneCurEmp"),"Current Employment - Telephone");
		public static Element txtGrossIncome = new Element(By.id("ctl19_ctl26_ctl00_txtGrossIncome"),"Income Detail - Gross Income");
		public static Element ddlIncomeDetailPer = new Element(By.id("ctl19_ctl26_ctl00_ddlIncomeBasis"),"Income Detail - per");
		
		/**
		 * To enter all fields for Primary Applicants
		 * @throws Exception
		 * @author Thao Le
		 */
		public static void enterPrimaryApplicants(String firstName, String lastName) throws Exception{
			DealManagement_ApplicantsPage.tabApplicants.click();
			int maxRetry = 2;
			for(int retry=0;retry<maxRetry;retry++){
				if(retry==0){
					Log.info("****** Enter information for primary Applicant ******");
				}else{
					if(retry>0 && retry<maxRetry){
						Log.info("****** Check if all fields are entered/selected correctly ******");
					}				
				}
				DealManagement_ApplicantsPage.ddlSalutation.select(testData.getApplicantValue("APP_SALUTATION"));
				DealManagement_ApplicantsPage.txtFirstName.enter(firstName);
				DealManagement_ApplicantsPage.txtMiddleName.enter(testData.getApplicantValue("APP_MIDDLE_NAME"));
				DealManagement_ApplicantsPage.txtLastName.enterEachCharInString(lastName);
				DealManagement_ApplicantsPage.txtPhone.enter(testData.getApplicantValue("APP_PHONE"));
				DealManagement_ApplicantsPage.txtDateofBirthMM.enter(testData.getApplicantValue("APP_BIRTH_MM"));
				DealManagement_ApplicantsPage.txtDateofBirth_DD.enter(testData.getApplicantValue("APP_BIRTH_DD"));
				DealManagement_ApplicantsPage.txtDateofBirth_YYYY.enter(testData.getApplicantValue("APP_BIRTH_YYYY"));
				DealManagement_ApplicantsPage.ddlMaritalStatus.select(testData.getApplicantValue("APP_MARITAL_STATUS"));
				DealManagement_ApplicantsPage.txtEmail.enter(testData.getApplicantValue("APP_EMAIL"));
				DealManagement_ApplicantsPage.txtPostalCode.enter(testData.getApplicantValue("APP_ZIP_CODE"));
				DealManagement_ApplicantsPage.ddlAddressType.select(testData.getApplicantValue("APP_ADD1"));
				DealManagement_ApplicantsPage.txtStreetNumber.enter(testData.getApplicantValue("APP_ADD2"));
				DealManagement_ApplicantsPage.txtStreetName.enter(testData.getApplicantValue("APP_STREET_NAME"));
				DealManagement_ApplicantsPage.ddlStreetType.select(testData.getApplicantValue("APP_STREET_TYPE"));
				DealManagement_ApplicantsPage.ddlDirection.select(testData.getApplicantValue("APP_DIRECTION"));
				DealManagement_ApplicantsPage.txtCity.enter(testData.getApplicantValue("APP_CITY"));
				DealManagement_ApplicantsPage.CDurationCurrentAddress_Y.enter(testData.getApplicantValue("APP_DURATION_YR"));
				DealManagement_ApplicantsPage.ddlHome.select(testData.getApplicantValue("APP_HOME"));
				DealManagement_ApplicantsPage.txtmarketValue.enter(testData.getApplicantValue("APP_MARKETVALUE"));
				DealManagement_ApplicantsPage.txtMortgageAmount.enter(testData.getApplicantValue("APP_MORTGAGE_AMT"));
				DealManagement_ApplicantsPage.txtMortgageHolder.enter(testData.getApplicantValue("APP_MORTGAGE_HOLDER"));
				DealManagement_ApplicantsPage.txtMonthlyPayment.enter(testData.getApplicantValue("APP_MONTHLY_PAY"));
				DealManagement_ApplicantsPage.ddlTypeCurEmp.select(testData.getApplicantValue("APP_CUR_EMP_TYPE"));
				DealManagement_ApplicantsPage.txtEmployerCurEmp.enter(testData.getApplicantValue("APP_CUR_EMPLOYER"));
				DealManagement_ApplicantsPage.ddlStatusCurEmp.select(testData.getApplicantValue("APP_CUR_STATUS"));
				DealManagement_ApplicantsPage.txtOccupationCurEmp.enter(testData.getApplicantValue("APP_CUR_OCCUPATION"));
				DealManagement_ApplicantsPage.CDurationCurrentEmployerAddress_Y.enter(testData.getApplicantValue("APP_CUR_DUR_YR"));
				DealManagement_ApplicantsPage.ddlAddressTypeCurEmp.select(testData.getApplicantValue("APP_CUR_ADD_TYPE"));
				DealManagement_ApplicantsPage.txtStreetNameCurEmp.enter(testData.getApplicantValue("APP_CUR_STREETNAME"));
				DealManagement_ApplicantsPage.txtStreetNumberCurEmp.enter(testData.getApplicantValue("APP_CUR_ADD_NO"));
				DealManagement_ApplicantsPage.ddlStreetTypeCurEmp.select(testData.getApplicantValue("APP_STREETTYPE"));
				DealManagement_ApplicantsPage.ddlDirectionCurEmp.select(testData.getApplicantValue("APP_CUR_DIRECTION"));
				DealManagement_ApplicantsPage.txtTelephoneCurEmp.enter(testData.getApplicantValue("APP_CUR_TEL"));
				DealManagement_ApplicantsPage.txtGrossIncome.enterInCurrencyField(testData.getApplicantValue("APP_GROSS_INCOME"));
				DealManagement_ApplicantsPage.ddlIncomeDetailPer.select(testData.getApplicantValue("APP_GROSS_INCOME_PER"));
				
				if(retry==maxRetry-1){
					Log.info("****** Finish entering information for primary Applicant ******");
				}
			}
		}

		public static void enterCoApplicants(){
			
		}

		public static void enterCommercialApplicants(){
			
		}
	
}
