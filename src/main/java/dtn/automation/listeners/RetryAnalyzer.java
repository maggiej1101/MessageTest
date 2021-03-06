package dtn.automation.listeners;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Log;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int countRetry = 0;
	public static int startRetry = 0;
	private static int retryMaximum = DTNConstant.MAX_RETRY_FAILED_TESTCASE;
	private static boolean maxRetryFailedTestCase= startRetry==retryMaximum?true:false;
	
	@Override
	public boolean retry(ITestResult result) {		
		if(countRetry<retryMaximum){
			countRetry++;		
			Log.debug("Try to re-run test case again, count retry = "+ countRetry);
			startRetry = countRetry;
			if(countRetry==retryMaximum){
				maxRetryFailedTestCase = true;
			}else
				maxRetryFailedTestCase = false;
			return true;
		}
		return false;
	}
	public static int getRetryMaximum() {
		return retryMaximum;
	}
	public static void setRetryMaximum(int retryMaximum) {
		RetryAnalyzer.retryMaximum = retryMaximum;
		maxRetryFailedTestCase= startRetry==retryMaximum?true:false;
	}
	public static void setCountRetry(int startCount){
		startRetry = startCount;
		if(startRetry==retryMaximum)
			maxRetryFailedTestCase=true;
		else
			maxRetryFailedTestCase = false;
	}	
	public static boolean isMaxRetryFailedTestCase(){		
		return maxRetryFailedTestCase;		
	}
	public static int getRetryMaxminum(){
		return retryMaximum;
	}
	public static int getCountRetry(){
		return startRetry;
	}
}