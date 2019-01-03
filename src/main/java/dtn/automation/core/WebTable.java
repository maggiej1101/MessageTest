package dtn.automation.core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dtn.automation.utilities.DTNConstant;
import dtn.automation.utilities.Log;


public class WebTable{
	private String tableName;
	private WebElement table;	
	private WebDriver driver;
	private By by;

	public WebTable(WebDriver driver, By by,String tableName) {		
		this.driver = driver;
		try{
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			this.table = driver.findElement(by);		
			this.tableName = tableName;
			this.by = by;
		}catch(Exception e){
			Log.fail("Cannot find '"+tableName, "Class: core.WebTable | Method: WebTable | Exception occured - "+e.toString() + " - locator - " + by +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}
	}
	public WebTable(By by,String tableName) {				
		this.tableName = tableName;
		this.by = by;
	}
	public WebTable(WebDriver driver, WebElement tableElement,String tableName) {
		this.driver = driver;
		this.table = tableElement;		
		this.tableName = tableName;		
		this.by = by;
	}	
	public int getBodyRowCount() throws Exception{
		int rows = 0;
		try{
			Log.debug("Get total rows of "+this.tableName);
			this.table = findElement();
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr"));
			rows = rowCollection.size();			
		}catch(Exception e){
			Log.fail("Fail to get total rows of "+this.tableName, "Exception occured - "+e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw (e);
		}
		return rows;
	}
	public int getHeaderRowCount() throws Exception{
		int rows = 0;
		try{
			this.table = findElement();
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|thead/tr"));
			rows = rowCollection.size();
		}catch(Exception e){
			Log.fail("Class: core.WebTable | Method: getHeaderRowCount | Exception occured - ",e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}
		return rows;
	}
	public int getBodyColumnCount() throws Exception{
		int cols = 0;
		try{
			Log.debug("In Class: core.WebTable | Method: getBodyColumnCount() for "+this.tableName+" is starting...");
			this.table = findElement();
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr"));			
			WebElement firstRow = rowCollection.get(0);
			List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));
			cols = columnCollection.size();	
		}catch(Exception e){
			Log.fail("Class: core.WebTable | Method: getBodyColumnCount | Exception occured - ",e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}
		return cols;
	}
	public int getHeaderColumnCount() throws Exception{
		int cols = 0;
		try{
			Log.debug("In Class: core.WebTable | Method: getHeaderColumnCount() for "+this.tableName+" is starting...");
			this.table = findElement();
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|thead/tr"));			
			WebElement firstRow = rowCollection.get(0);
			List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));
			cols = columnCollection.size();	
		}catch(Exception e){
			Log.fail("Class: core.WebTable | Method: getHeaderColumnCount | Exception occured - ",e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}
		return cols;
	}
	public String getCell(int rowIndex, int colIndex) throws Exception{
		String cellValue = null;
		try{
			Log.debug("In Class: core.WebTable | Method: getCell() for "+this.tableName+" is starting...");
			this.table = findElement();
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr"));				
			int i = 0;				//
			WebElement firstRow = rowCollection.get(rowIndex);
		    List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));
		    for(WebElement col:columnCollection){
		        i++;
		        if(colIndex==i){
		        	cellValue = col.getText();
		             break;
		        }
		    }
		}catch(Exception e){
			Log.fail("Class: core.WebTable | Method: getCell | Exception occured - ",e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}
		return cellValue;
	}
	public int getColIndex(String colName) throws Exception{		
		int j = 0;	
		String headerCol;
		try{
			Log.debug("In Class: core.WebTable | Method: getColIndex() for "+this.tableName+" is starting...");
			this.table = findElement();
			List<WebElement> headRowCollection = table.findElements(By.xpath("tr|thead/tr"));
			WebElement firstRow = headRowCollection.get(0);		
			    List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));		   
			    for(WebElement col:columnCollection){
			        j++;   
			        headerCol = col.getText();
			        if(colName.equals(headerCol)){    		             
			           break;
			        }
			  }	
		}catch(Exception e){
			Log.fail("Class: core.WebTable | Method: getColIndex | Exception occured - ",e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}
	    return j;
	}
	
	/**
	 * To get index of Text in table
	 * 
	 * @param text
	 * @return (row,col) location of text
	 * @throws Exception 
	 */
	public int[] getRowColIndexOfText(String text) throws Exception{	
		try{
			Log.debug("Get (row,column) location of '"+ text +"' in Webtable");
			this.table = findElement();
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr")); 
			int rows = rowCollection.size(); 
			int rowIndex = -1;    
			int colIndex = -1;
			int j;
		    for(int i=0;i<rows;i++){ 
		        WebElement row = rowCollection.get(i); 
		        List<WebElement> columnCollection = row.findElements(By.xpath("th|td")); 
		        j = 0;
		        for(WebElement col:columnCollection){   	            
		            if(col.getText().contains(text)){ 
		                Log.debug(text+" is Found at (row,col) = ("+ i +","+j+")");
		                rowIndex = i;
		                colIndex = j;
		                break; 
		            }             	                   
		            j++;                 
		        }         
		    } 
	    	int[] indexOfText = {rowIndex,colIndex};
	    	 return indexOfText;
		}catch(Exception e){
			Log.fail("Class: core.WebTable | Method: getRowColIndexOfText | Exception occured - ",e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw(e);
		}	   
	}
	/**
	 * Find an element on page using constant timeout. 
	 * @param by Element by value
	 * @return an element 
	 */
	public WebElement findElement() throws Exception{	
		try{
			Log.debug("In Class: core.WebTable | Method: findElement() for "+this.tableName+" is starting...");
			driver = BaseDriver.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, DTNConstant.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			table = driver.findElement(by);
			Log.debug("In Class: core.WebTable | Method: findElement() for "+this.tableName+" is finished successfully.");
		}catch(Exception e){
			Log.fail("In Class: core.WebTable | Method: findElement() for "+this.tableName+" is NOT finished successfully " +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return table;	
	}
}
