package common.Page;

import org.openqa.selenium.By;

import dtn.automation.core.Element;

public class CommonObjects{		
	public static String PageName = "Common Page Objects";
	//----------------------- Common objects  ----------------------
	public static String jsModalPopupOK = "window.document.getElementById('DTC$ModalPopup$OK').click();"; 
	 public static String jsGetAppNumber= "return document.getElementById('txtApplicationNumber').value";
	 
	//----------------------- iFrame  ----------------------	
	 public static String topFrame = "nav";
	 public static String mainFrame = "main";
	 public static String bottomFrame = "bottom";
	 public static String parentFrame = "iFrm";
	 public static String dtcModalPopupFrame = "DTC$ModalPopup$Frame";
	 public static String drsPaymentDriverFrame = "drsPaymentDriverAdminFrame";  
	 public static String drsTradeDriveFrame = "drsTradeDrvierAdminFrame";
	 
	 //----------------------- Modal Popup  ----------------------	   	 
	 public static Element popupModal = new Element(By.id("DTC$ModalPopup$Content"),"Loading Popup");
	 public static Element popupSwitch = new Element(By.id("lbLoading"),"Switch Dealer Popup");
	 public static Element popupContent = new Element(By.id("DTC$ModalPopup$Content"),"Content Popup");
	 public static Element popupOKbutton = new Element(By.id("DTC$ModalPopup$OK"),"OK button on popup");
	 public static Element popupYesbutton = new Element(By.id("DTC$ModalPopup$Yes"),"Yes button  on popup");
	 public static Element popupNobutton = new Element(By.id("DTC$ModalPopup$No"),"No button on popup");
	 public static Element popupCancelbutton = new Element(By.id("DTC$ModalPopup$Cancel"),"Cancel button on popup");
	 	 
	 //----------------------- top frame ----------------------
	 public static Element lnkSwitch = new Element(By.xpath(".//a[contains(text(),'Switch')]"),"Switch dealer link");
	 
	 public static Element lnkStatus = new Element(By.id("Status"),"Status tab");
	 public static Element lnkHome = new Element(By.id("Home"),"Home tab");
	 public static Element lnkCreateApp = new Element(By.id("PrepareApp"),"Create App tab");
	//----------------------- bottom frame ----------------------	 
	 public static Element lnkLogOut = new Element(By.linkText("Log Out"),"Log Out");
	 public static Element lblDealerInforAtBottomPage = new Element(By.xpath("//font[@class='lastlogon']"),"Dealership information at the bottom page");
}
