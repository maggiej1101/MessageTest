package dtn.automation.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class to read all data for each test case from TestData excel file in DTN Automation.
 * The TestData format must be correct so that this class can return correct information.
 * DTN_TestData excel file format must have: Main worksheet. From Main worksheet to read 
 * another worksheet, a flag of another worksheet must be set in Main worksheet. 
 * 
 * For example: in TestData, there is two worksheet: Main and Applicant. 
 * This class will read data from Main, then if 'APP_FLAG' is not empty in Main, 
 * this class will read data from Applicant. 
 * @author Maggie J
 *
 */
public class DTN_TestData{
	private ReadTestData Main;
	private ReadTestData Applicant;
	private ReadTestData CoApplicant;
	private ReadTestData Commercial_Applicant;		
	private ReadTestData Vehicle;
	private ReadTestData WorksheetOtherInfo;
	private ReadTestData CreditBureau;
	private ReadTestData AfterMarket;
	private ReadTestData Message;
	
	/**
	 * To initial DTN TestData from TestData excel file using APP_TestCaseID
	 * For example: DTN_TestData("SmokeTest_TestData.xls", "TC01")
	 * @param FilePath
	 * @param testCaseID
	 * @author Maggie J
	 */
	public DTN_TestData(String FilePath,String testCaseID){
		this.Main = new ReadTestData(FilePath,"Main",testCaseID);
		Main.readDataSheetAndReturnData("TESTCASE_ID","Main");
		this.Applicant = new ReadTestData(FilePath,"Applicant",testCaseID);
		this.CoApplicant = new ReadTestData(FilePath,"CoApplicant",testCaseID);
		this.Commercial_Applicant = new ReadTestData(FilePath,"Commercial_Applicant",testCaseID);		
		this.Vehicle = new ReadTestData(FilePath,"Vehicle",testCaseID);
		this.WorksheetOtherInfo = new ReadTestData(FilePath,"WorksheetOtherInfo",testCaseID);		
		this.CreditBureau = new ReadTestData(FilePath,"CreditBureau",testCaseID);		
		this.AfterMarket = new ReadTestData(FilePath,"AfterMarket",testCaseID);
		this.Message = new ReadTestData(FilePath,"Message",testCaseID);
	
		
		if(Main.getTestData().get("APP_FLAG")!=null && (Main.getTestData().get("APP_FLAG")!="")){
			this.Applicant.readDataSheetAndReturnData("APP_TestCaseID","Applicant");			
		}
		if(Main.getTestData().get("COAPP_FLAG")!=null && Main.getTestData().get("COAPP_FLAG")!=""){
			this.CoApplicant.readDataSheetAndReturnData("APP_TestCaseID","CoApplicant");		
		}
		if(Main.getTestData().get("COMMERCIAL_COAPP_FLAG")!=null && Main.getTestData().get("COMMERCIAL_COAPP_FLAG")!=""){
			this.Commercial_Applicant.readDataSheetAndReturnData("APP_TestCaseID","Commercial_Applicant");		
		}
		if(Main.getTestData().get("VEH_FLAG")!=null && Main.getTestData().get("VEH_FLAG")!=""){
			this.Vehicle.readDataSheetAndReturnData("APP_TestCaseID","Vehicle");		
		}
		if(Main.getTestData().get("WORKSHEET_OTHER_INFO")!=null && Main.getTestData().get("WORKSHEET_OTHER_INFO")!=""){
			this.WorksheetOtherInfo.readDataSheetAndReturnData("APP_TestCaseID","WorksheetOtherInfo");		
		}		
		if(Main.getTestData().get("CB_FLAG")!=null && Main.getTestData().get("CB_FLAG")!=""){
			this.CreditBureau.readDataSheetAndReturnData("APP_TestCaseID","CreditBureau");		
		}
		if(Main.getTestData().get("AfterMarket_FLAG")!=null && Main.getTestData().get("AfterMarket_FLAG")!=""){
			this.AfterMarket.readDataSheetAndReturnData("APP_TestCaseID","AfterMarket");		
		}
		if(Main.getTestData().get("Message_FLAG")!=null && Main.getTestData().get("Message_FLAG")!=""){
			this.Message.readDataSheetAndReturnData("APP_TestCaseID","Message");		
		}
	}	
	/**
	 * To get value from 'Main' worksheet based on Column name
	 * @param Key column header name 
	 * @return value of column 
	 * @author Maggie J
	 */
	public String getMainValue(String Key){
		return this.Main.getTestData().get(Key);
	}
	/**
	 * To get value from 'Applicant' worksheet based on Column name
	 * @param Key
	 * @return
	 * @author Maggie J
	 */
	public String getApplicantValue(String Key){
		return this.Applicant.getTestData().get(Key);
	}
	/**
	 * To get value from Worksheet Other Info worksheet based on Column name
	 * @param Key
	 * @return
	 * @author Maggie J
	 */
	public String getWorksheetOtherInfoValue(String Key){
		return this.WorksheetOtherInfo.getTestData().get(Key);
	}
	/**
	 * To get value from Vehicle worksheet based on Column name
	 * @param Key
	 * @return
	 * @author Maggie J
	 */
	public String getVehicleValue(String Key){
		return this.Vehicle.getTestData().get(Key);
	}
	/**
	 * To get value from 'CoApplicant' worksheet based on Column name
	 * @param Key
	 * @return
	 * @author Maggie J
	 */
	public String getCoApplicantValue(String Key){
		return this.CoApplicant.getTestData().get(Key);
	}
	
	/**
	 * To get value from 'Credit Bureau' worksheet based on Column name
	 * @param Key
	 * @return
	 * @author Maggie J
	 */
	public String getCreditBureauValue(String Key){
		return this.CreditBureau.getTestData().get(Key);
	}	
		
	
	/**
	 * To get value from 'Applicant' worksheet based on Column name
	 * @param Key
	 * @return
	 * @author maggie.jiang
	 */
	public String getMessageValue(String Key){
		return this.Message.getTestData().get(Key);
	}

	/**
	 * To read all data in worksheet using TestCase ID
	 * @author Maggie J
	 *
	 */
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
			int colcount = TestCasesSheet.ColCount();
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
