package dtn.automation.utilities;

import com.aventstack.extentreports.MediaEntityModelProvider;

/**
 * @author Thao Le
 * This class to hold test step information
 * Test Step will display description/error or exception message and screen shot
 */
public class TestStep {
	private String status;
	private String testDescription=null;
	private MediaEntityModelProvider mediaFile=null; //mediaFile can be a screen-shot
	private Throwable exception=null;
	
	public TestStep(String status, String testDescription) {		
		this.status = status;
		this.testDescription = testDescription;
	}
	public TestStep(String status, Throwable exception) {		
		this.status = status;
		this.exception = exception;
	}
	public TestStep(String status, String testDescription, MediaEntityModelProvider mediaFile) {		
		this.status = status;
		this.testDescription = testDescription;
		this.mediaFile = mediaFile;
	}
	public TestStep(String status, Throwable exception, MediaEntityModelProvider mediaFile) {		
		this.status = status;
		this.exception = exception;
		this.mediaFile = mediaFile;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public MediaEntityModelProvider getMediaFile() {
		return mediaFile;
	}

	public void setMediaFile(MediaEntityModelProvider mediaFile) {
		this.mediaFile = mediaFile;
	}
	
}
