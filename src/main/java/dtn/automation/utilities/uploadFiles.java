package dtn.automation.utilities;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;

public class uploadFiles {
	static WebDriver driver;
	public static void UploadFileUsingSendKeys()
			throws InterruptedException {

		driver = new FirefoxDriver();
		String workingDir = System.getProperty("user.dir");
		String filepath = workingDir + "\\SeleniumWebdriverUploadFile.html";
		driver.get(filepath);

		WebElement fileInput = driver.findElement(By.name("uploadfile"));
		fileInput.sendKeys(filepath);

		// Added a wait to make you notice the difference.
		Thread.sleep(1000);

		driver.findElement(By.id("uploadfile")).sendKeys(
				"C:\\path\\to\\fileToUpload.txt");

		// Added sleep to make you see the difference.
		Thread.sleep(1000);

		fileInput.sendKeys(filepath);
	}
	public void UploadFileUsingAutoIt()
			throws InterruptedException, IOException {

		String workingDir = System.getProperty("user.dir");
		String autoitscriptpath = workingDir + "\\"
				+ "File_upload_selenium_webdriver.au";

		driver = new FirefoxDriver();
		String filepath = workingDir + "\\SeleniumWebdriverUploadFile.html";
		driver.get(filepath);

		// Added a wait to make you notice the difference.
		Thread.sleep(1000);

		driver.findElement(By.id("uploadfile")).click();

		// Added sleep to make you see the difference.
		Thread.sleep(1000);

		Runtime.getRuntime().exec(
				"cmd.exe /c Start AutoIt3.exe " + autoitscriptpath + " \""
						+ filepath + "\"");
	}
	
	public void UploadFileUsingJacobDll()
			throws InterruptedException {

		String workingDir = System.getProperty("user.dir");

		final String jacobdllarch = System.getProperty("sun.arch.data.model")
				.contains("32") ? "jacob-1.18-x86.dll" : "jacob-1.18-x64.dll";
		String jacobdllpath = workingDir + "\\" + jacobdllarch;

		File filejacob = new File(jacobdllpath);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH,
				filejacob.getAbsolutePath());
		AutoItX uploadWindow = new AutoItX();

		driver = new FirefoxDriver();
		String filepath = workingDir + "\\SeleniumWebdriverUploadFile.html";
		driver.get(filepath);

		Thread.sleep(1000);

		driver.findElement(By.id("uploadfile")).click();

		Thread.sleep(1000);

		if (uploadWindow.winWaitActive("File Upload", "", 5)) {
			if (uploadWindow.winExists("File Upload")) {
				uploadWindow.sleep(100);
				uploadWindow.send(filepath);
				uploadWindow.controlClick("File Upload", "", "&Open");

			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1.instantiate the object
		try {
			UploadFileUsingSendKeys();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
