package common.Page;

import org.openqa.selenium.By;
import dtn.automation.core.Element;

import dtn.automation.core.WebTable;

public class EquifaxConsumerReportPage {
	public static final String PageName = "Equifax Consumer Report Page";	
	public static WebTable tblEquifaxConsumerReport = new WebTable(By.xpath("//table[@class='data1']"),"Equifax Consumer Report");
	public static Element lblFirstName = new Element(By.xpath("//table[@class='data1']//table[2]//td[contains(text(),'ABA')]"),"Firstname on Equifax report");
}
