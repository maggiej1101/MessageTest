package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

public class DealerSearchAndSwitchPage {
	public static final String PageName = "Dealer Search And Switch Page";	
	public static Element txtDealerID = new Element(By.id("TxtSwitchDealer"),"Dealer ID textbox");
	public static Element btnSwitch = new Element(By.id("BtnSwitch"),"Switch button");
}
