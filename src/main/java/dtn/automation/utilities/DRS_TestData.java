package dtn.automation.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DRS_TestData{
	private ReadTestData testcase;
	private ReadTestData Applicant;
	private ReadTestData CoApplicant;
	private ReadTestData Commercial_Applicant;		
	private ReadTestData Vehicle;
	private ReadTestData WorksheetOtherInfo;
	private ReadTestData PaymentAsset;
	private ReadTestData Convert;
	
	public DRS_TestData(String FilePath,String testCaseID){
		this.testcase = new ReadTestData(FilePath,"TestCases",testCaseID);
		testcase.readDataSheetAndReturnData("TESTCASE_ID","TestCases");
		this.Applicant = new ReadTestData(FilePath,"Applicant",testCaseID);
		this.CoApplicant = new ReadTestData(FilePath,"CoApplicant",testCaseID);
		this.Commercial_Applicant = new ReadTestData(FilePath,"Commercial_Applicant",testCaseID);		
		this.Vehicle = new ReadTestData(FilePath,"Vehicle",testCaseID);
		this.WorksheetOtherInfo = new ReadTestData(FilePath,"WorksheetOtherInfo",testCaseID);
		this.PaymentAsset = new ReadTestData(FilePath,"PaymentAsset",testCaseID);		
		this.Convert = new ReadTestData(FilePath,"Convert",testCaseID);
		
		if(testcase.getTestData().get("APP_FLAG")!=null && (testcase.getTestData().get("APP_FLAG")!="")){
			this.Applicant.readDataSheetAndReturnData("TESTCASE_ID","Applicant");			
		}
		if(testcase.getTestData().get("COAPP_FLAG")!=null && testcase.getTestData().get("COAPP_FLAG")!=""){
			this.CoApplicant.readDataSheetAndReturnData("TESTCASE_ID","CoApplicant");		
		}
		if(testcase.getTestData().get("COMMERCIAL_COAPP_FLAG")!=null && testcase.getTestData().get("COMMERCIAL_COAPP_FLAG")!=""){
			this.Commercial_Applicant.readDataSheetAndReturnData("TESTCASE_ID","Commercial_Applicant");		
		}
		if(testcase.getTestData().get("VEH_FLAG")!=null && testcase.getTestData().get("VEH_FLAG")!=""){
			this.Vehicle.readDataSheetAndReturnData("TESTCASE_ID","Vehicle");		
		}
		if(testcase.getTestData().get("WORKSHEET_OTHER_INFO")!=null && testcase.getTestData().get("WORKSHEET_OTHER_INFO")!=""){
			this.WorksheetOtherInfo.readDataSheetAndReturnData("TESTCASE_ID","WorksheetOtherInfo");		
		}		
		if(testcase.getTestData().get("PAYMENT_ASSET_FLAG")!=null && testcase.getTestData().get("PAYMENT_ASSET_FLAG")!=""){
			this.PaymentAsset.readDataSheetAndReturnData("TESTCASE_ID","PaymentAsset");		
		}
		if(testcase.getTestData().get("CONVERT_FLAG")!=null && testcase.getTestData().get("CONVERT_FLAG")!=""){
			this.Convert.readDataSheetAndReturnData("TESTCASE_ID","Convert");		
		}
	}	
	public String getTestCaseValue(String Key){
		return this.testcase.getTestData().get(Key);
	}
	public String getApplicantValue(String Key){
		return this.Applicant.getTestData().get(Key);
	}
	public String getCoApplicantValue(String Key){
		return this.CoApplicant.getTestData().get(Key);
	}	
	public String getPaymentAssetValue(String Key){
		return this.PaymentAsset.getTestData().get(Key);
	}	
	public ReadTestData getTestcase() {
		return testcase;
	}

	public void setTestcase(ReadTestData testcase) {
		this.testcase = testcase;
	}

	public ReadTestData getApplicant() {
		return Applicant;
	}
	
	public ReadTestData getCoApplicant() {
		return CoApplicant;
	}
	public ReadTestData getPaymentAsset() {
		return PaymentAsset;
	}
	public void setCoApplicant(ReadTestData coApplicant) {
		CoApplicant = coApplicant;
	}

	public ReadTestData getCommercial_Applicant() {
		return Commercial_Applicant;
	}

	public void setCommercial_Applicant(ReadTestData commercial_Applicant) {
		Commercial_Applicant = commercial_Applicant;
	}

	public ReadTestData getVehicle() {
		return Vehicle;
	}

	public void setVehicle(ReadTestData vehicle) {
		Vehicle = vehicle;
	}

	public ReadTestData getWorksheetOtherInfo() {
		return WorksheetOtherInfo;
	}

	public void setWorksheetOtherInfo(ReadTestData worksheetOtherInfo) {
		WorksheetOtherInfo = worksheetOtherInfo;
	}
	public ReadTestData getConvert() {
		return Convert;
	}
	
	public String getConvertValue(String Key){
		return this.Convert.getTestData().get(Key);
	}
	public class ReadTestData {
		private Excel TestCasesSheet;
		private Map<String,String> testData = new HashMap<String, String>();
		private String testCaseID;
		
		public ReadTestData(String FilePath,String SheetName, String testCaseID){						
			TestCasesSheet = new Excel(FilePath,SheetName);
			this.testCaseID = testCaseID;					
		}		
		public void readDataSheetAndReturnData(String colName,String SheetName){		
			String KEY = "";
			String VALUE = "";
			int rowNum;
			TestCasesSheet.ReinitializeWorkSheet(SheetName);
			rowNum = TestCasesSheet.findRowOfCellValue(colName, testCaseID);
			if(rowNum>0){				
				for(int i=0;i<TestCasesSheet.ColCount();i++){
					KEY = TestCasesSheet.getCellData(0, i);
					VALUE = TestCasesSheet.getCellData(rowNum,i);
					//remove all spaces from value:
					//VALUE = VALUE.replaceAll("\\s+","");	
					VALUE = VALUE.trim();
					KEY = KEY.trim();
					testData.put(KEY,VALUE); //add new KEY, VALUE
					
				}	
				System.out.println("for testing");
			}
		}

		public Map<String, String> getTestData() {
			return testData;
		}
	
		public void setTestData(Map<String, String> testData) {
			this.testData = testData;
		}
				
	}
}