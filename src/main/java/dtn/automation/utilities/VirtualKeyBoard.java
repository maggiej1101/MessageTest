package dtn.automation.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * This class to handle all action on keyboard
 * @author thao.le
 *
 */
public class VirtualKeyBoard{
	/**
	 * To move scroll bar up to top of screen
	 * @param driver
	 * @return
	 * @author Thao Le
	 */
	public static boolean moveScrollbarUpToTop(WebDriver driver){
		Log.debug("+ Move scroll bar to top of page.");
		try{
			((JavascriptExecutor)driver).executeScript("scroll(document.body.scrollHeight,0)");
			return true;
		}catch(Exception e){
			Log.fail("FAILED : cannot move scroll bar to top of page","Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}		
	}
}
