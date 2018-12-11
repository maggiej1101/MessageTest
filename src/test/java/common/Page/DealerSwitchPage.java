package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.WebTable;

public class DealerSwitchPage {	
	public static final String PageName = "Dealer Switch Page";	
	public static WebTable tblDealerShipDetails = new WebTable(By.xpath("//table[@id='GridViewSearchResult']"),"Dealership Details table");
	
}
