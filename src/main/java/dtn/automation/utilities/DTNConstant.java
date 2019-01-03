package dtn.automation.utilities;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

/**
 * This class to keep all constants using for DTN
 * @author Maggie Jiang
 *
 */
public class DTNConstant {

    public static String workingDir = System.getProperty("user.dir");
    public static final String  relativeSCREENSHOT_FOLDER =workingDir + "\\reportLogs\\ExtentReports\\";
    public static final String  SCREENSHOT_FOLDER = workingDir + "\\reportLogs\\ExtentReports\\";
    public static final String  UPLOADEXEFILE = workingDir + "\\Uploadfile.exe";
    public static int EXPLICIT_WAITTIME = 60; 
    public static int LOADING_POPUP_TIMEOUT = 60;
    public static final int SLEEP_TIME_FOR_ENTER_CHAR = 500;
    public static String LOCALE = "US";
    public static int MAX_RETRY_ENTER_REQUIRED_FIELD = 1;
    public static int MAX_FAIL_RETRY = 2;
    
    // ------- EXTENT REPORT -----------------------
    public static String projectName=null;
    public static int MAX_RETRY_FAILED_TESTCASE = 0;
    public boolean isMAX_RETRY_FAILED_TESTCASE = false;
  //*** DRIVERS PATH FOR BROWSERS 
  	public static final String DriverPath=workingDir + "/Drivers/";  	
  	public static Element DTNPopupModal = new Element(By.xpath("//div[@id='DTC$ModalPopup$Content' and (contains(text(), 'Loading') or contains(text(), 'Initializing') or contains(text(), 'Loading Deals...') or contains(text(), 'please wait'))]"),"Loading Popup");
  	
    public static final int MAX_Length_80=80;
    public static final int MAX_LENGTH_4000=4000;

}
