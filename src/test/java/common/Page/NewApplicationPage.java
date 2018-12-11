package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

/**
 * @author QSONG
 * The partlet of Creating New Application
 */
public class NewApplicationPage {
	public static final String PageName = "Create New Application Page";	
	public static Element lbNewApplication = new Element(By.id("lblCreateNewApplication"),"Create New Application Page");
	public static Element lstAsset = new Element(By.id("ddAsset"),"Asset list");
	public static Element lstLenders = new Element(By.id("ddLenders"),"Lenders list");
	public static Element lstProduct = new Element(By.id("ddProduct"),"Product list");
	public static Element lstApplicantType = new Element(By.id("ddApplicantType"),"Applicant Type list");
	public static Element btnContinue = new Element(By.id("btnSave"),"Continue button");
}