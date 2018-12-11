package common.Page;

import org.openqa.selenium.By;
import dtn.automation.core.Element;

import common.TestSuite.BaseTestSuite;

public class HomePage extends BaseTestSuite{
	
	public static final String PageName = "Home Page";	
	public static Element lnkStatus = new Element(By.id("Status"),"Status tab");
	public static Element lnkHome = new Element(By.id("Home"),"Home tab");
	
}
