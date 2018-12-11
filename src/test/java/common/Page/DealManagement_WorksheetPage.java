package common.Page;

import dtn.automation.core.Page;
import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Log;

import org.openqa.selenium.By;
import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;

public class DealManagement_WorksheetPage extends BaseTestSuite{
	public static final String PageName = "Deal Management Worksheet Page";
	public static Element tabWORKSHEET = new Element(By.id("ctl19_btnWORKSHEET"),"Worksheet tab");
	public static Element ddlVehicleCondition = new Element(By.id("ctl00_ddlVehicleCondition"),"Vehicle condition");
	public static Element ddlVehicleYear = new Element(By.id("ctl00_ddlVehicleYear"),"Vehicle Year");
	public static Element ddlVehicleMake = new Element(By.id("ctl00_ddlVehicleMake"),"Vehicle Make");
	public static Element ddlVehicleModel = new Element(By.id("ctl00_ddlVehicleModel"),"Vehicle Model");
	public static Element ddlVehicleSeries = new Element(By.id("ctl00_ddlVehicleSeries"),"Vehicle Series");
	public static Element ddlVehicleIncludes = new Element(By.id("ctl00_ddlVehicleIncludes"),"Vehicle Includes");
	public static Element txtCurrentKMs = new Element(By.id("ctl00_txtCurrentKMs"),"Current Kms");

	public static Element ddlProgram = new Element(By.xpath("//select[contains(@id,'_ddlProgram')]"),"Program");
	public static Element txtCashPrice = new Element(By.xpath("//input[contains(@id,'_txtCashPrice')]"),"Cash Price");
	public static Element txtYear = new Element(By.xpath("//input[contains(@id,'_txtYear')]"),"Trade In Year");
	
	public static Element txtMake = new Element(By.xpath("//input[contains(@id,'_txtMake')]"),"Trade In Make");
	public static Element txtModel = new Element(By.xpath("//input[contains(@id,'_txtModel')]"),"Trade In Make");
	public static Element txtBodyStyle = new Element(By.xpath("//input[contains(@id,'_txtBodyStyle')]"),"Trade In Body Style");
	public static Element txtMileage = new Element(By.xpath("//input[contains(@id,'_txtMileage')]"),"Mileage");
	public static Element txtAllowance = new Element(By.xpath("//input[contains(@id,'_txtAllowance')]"),"Trade In Allowance");
	
	public static Element txtLienAmount = new Element(By.xpath("//input[contains(@id,'_txtLienAmount')]"),"Lien Amount");
	public static Element txtBalanceOwedTo = new Element(By.xpath("//input[contains(@id,'_txtBalanceOwedTo')]"),"Balance Owed To");
	public static Element txtCashDownPayment = new Element(By.xpath("//input[contains(@id,'_txtCashDownPayment')]"),"Cash Down Payment");
	public static Element txtRebate = new Element(By.xpath("//input[contains(@id,'_txtRebate')]"),"Rebate");
	public static Element txtLicenseFee = new Element(By.xpath("//input[contains(@id,'_txtLicenseFee')]"),"License Fee");
	public static Element txtOtherTaxable = new Element(By.xpath("//input[contains(@id,'_txtOtherTaxable')]"),"OtherTaxable");
	public static Element txtOtherTaxableDesc = new Element(By.xpath("//input[contains(@id,'_txtOtherTaxableDesc')]"),"Other Taxable Desc");
	public static Element txtOtherNonTaxable = new Element(By.xpath("//input[contains(@id,'_txtOtherNonTaxable')]"),"Other Non-Taxable");
	public static Element txtOtherNonTaxableDesc = new Element(By.xpath("//input[contains(@id,'_txtOtherNonTaxableDesc')]"),"Other Non-Taxable Desc");
	public static Element txtExtendedWarranty = new Element(By.xpath("//input[contains(@id,'_txtExtendedWarranty')]"),"Extended Warranty");
	public static Element ddlExWarrantyTerm = new Element(By.xpath("//input[contains(@id,'_ddlExWarrantyTerm')]"),"Extended Warranty Term");
	public static Element txtLifeInsurance = new Element(By.xpath("//input[contains(@id,'_txtLifeInsurance')]"),"Life Insurance");
	public static Element txtAHInsurance = new Element(By.xpath("//input[contains(@id,'_txtAHInsurance')]"),"AH Insurance");
	public static Element txtReplacement = new Element(By.xpath("//input[contains(@id,'_txtReplacement')]"),"Replacement");
	public static Element CTermDropDown1 = new Element(By.xpath("//input[contains(@id,'_CTermDropDown1')]"),"Term");
	public static Element CAmortizationPeriodDropDown1 = new Element(By.xpath("//input[contains(@id,'_CAmortizationPeriodDropDown1')]"),"Amortization");	
	public static Element chkViewed = new Element(By.xpath("//input[contains(@id,'_chkViewed')]"),"View checkbox");
	public static Element CPaymentFrequencyDropDown1 = new Element(By.xpath("//input[contains(@id,'_CPaymentFrequencyDropDown1')]"),"Payment Frequency ");
	
	public static Element ddlTypeApp1a = new Element(By.xpath("//input[contains(@id,'_tabApplicant1_ddlTypeApp1a')]"),"");
	public static Element txtIDNoApp1a = new Element(By.xpath("//input[contains(@id,'_ajaxTabs_tabApplicant1_txtIDNoApp1a')]"),"");
	public static Element txtExpiryDateApp1a_MM = new Element(By.xpath("//input[contains(@id,'_ajaxTabs_tabApplicant1_txtExpiryDateApp1a_MM')]"),"");
	public static Element txtExpiryDateApp1a_DD = new Element(By.xpath("//input[contains(@id,'_ajaxTabs_tabApplicant1_txtExpiryDateApp1a_DD')]"),"");
	public static Element txtExpiryDateApp1a_YYYY = new Element(By.xpath("//input[contains(@id,'_ajaxTabs_tabApplicant1_txtExpiryDateApp1a_YYYY')]"),"");
	/**
		 * To enter all fields at Worksheet (Create App)
		 * @throws Exception
		 * @author Thao Le
		 */
		public static void enterWorksheet() throws Exception{
			Thread.sleep(2000);
			DealManagement_WorksheetPage.tabWORKSHEET.click();
			int maxRetry = 2;
			for(int retry=0;retry<maxRetry;retry++){
				if(retry==0){
					Log.info("****** Enter information for Worksheet ******");
				}else{
					if(retry>0 && retry<maxRetry){
						Log.info("****** Check if all fields are entered/selected correctly ******");
					}				
				}
				DealManagement_WorksheetPage.ddlVehicleCondition.select(testData.getVehicleValue("CONDITION"));
				DealManagement_WorksheetPage.ddlVehicleYear.select(testData.getVehicleValue("YEAR"));
				DealManagement_WorksheetPage.ddlVehicleMake.select(testData.getVehicleValue("MAKE"));
				DealManagement_WorksheetPage.ddlVehicleModel.select(testData.getVehicleValue("MODEL"));
				DealManagement_WorksheetPage.ddlVehicleSeries.select(testData.getVehicleValue("SERIES"));
				DealManagement_WorksheetPage.ddlVehicleIncludes.select(testData.getVehicleValue("INCLUDES"));
		
				DealManagement_WorksheetPage.txtCurrentKMs.enterWithRetry(testData.getVehicleValue("CURRENTKMS"));
				DealManagement_WorksheetPage.ddlProgram.select(testData.getWorksheetOtherInfoValue("PROGRAM"));
				DealManagement_WorksheetPage.txtCashPrice.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("CASH_PRICE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtYear.enterEachCharInString(testData.getWorksheetOtherInfoValue("TRADEIN_YEAR"));
				DealManagement_WorksheetPage.txtMake.enterEachCharInString(testData.getWorksheetOtherInfoValue("TRADEIN_MAKE"));
				DealManagement_WorksheetPage.txtModel.enterEachCharInString(testData.getWorksheetOtherInfoValue("TRADEIN_MODEL"));
				DealManagement_WorksheetPage.txtBodyStyle.enterEachCharInString(testData.getWorksheetOtherInfoValue("TRADEIN_BODY_STYLE"));
				DealManagement_WorksheetPage.txtMileage.enterEachCharInString(testData.getWorksheetOtherInfoValue("TRADEIN_ODOMETER"));
				DealManagement_WorksheetPage.txtAllowance.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("TRADEIN_ALLOWANCE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtLienAmount.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("LIEN_AMOUNT"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtBalanceOwedTo.enterEachCharInString(testData.getWorksheetOtherInfoValue("LIEN_BALANCED_OWED_TO"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtCashDownPayment.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("FEES_CASH_DOWN"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtRebate.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("FEES_REBATE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtLicenseFee.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("FEES_INSTALLATION_AND_DELIVERY"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtOtherTaxable.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("FEES_OTHER_TAXABLE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtOtherTaxableDesc.enterEachCharInString(testData.getWorksheetOtherInfoValue("FEES_OTHER_DESC"));
				DealManagement_WorksheetPage.txtOtherNonTaxable.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("FEES_OTHERNON_TAXABLE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtOtherNonTaxableDesc.enterEachCharInString(testData.getWorksheetOtherInfoValue("FEES_OTHERNON_TAXABLE_DESC"));
				DealManagement_WorksheetPage.txtExtendedWarranty.enterEachCharInString(testData.getWorksheetOtherInfoValue("AFTERMARKET_EXTENDED_SERVICE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.ddlExWarrantyTerm.select(testData.getWorksheetOtherInfoValue("AFTERMARKET_EXT_WARRANTY_TERM"));
				DealManagement_WorksheetPage.txtLifeInsurance.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("AFTERMARKET_LIFE_INSURANCE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtAHInsurance.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("AFTERMARKET_AH_INSURANCE"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);
				DealManagement_WorksheetPage.txtReplacement.enterEachCharInStringInCurrencyField(testData.getWorksheetOtherInfoValue("AFTERMARKET_REPLACEMENT"),DTNConstant.SLEEP_TIME_FOR_ENTER_CHAR);				
				DealManagement_WorksheetPage.CTermDropDown1.select(testData.getWorksheetOtherInfoValue("TERM"));
				DealManagement_WorksheetPage.CAmortizationPeriodDropDown1.select(testData.getWorksheetOtherInfoValue("AMORTIZATION"));
				DealManagement_WorksheetPage.CPaymentFrequencyDropDown1.select(testData.getWorksheetOtherInfoValue("PAYMENT_FREQUENCY"));
				if(retry==maxRetry-1){
					Log.info("****** Finish entering information for Worksheet ******");
				}
			}
		}

}
