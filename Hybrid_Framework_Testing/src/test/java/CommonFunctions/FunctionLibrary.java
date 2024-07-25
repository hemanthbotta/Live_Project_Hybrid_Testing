package CommonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	
	public static Properties conpro;
	public static WebDriver driver;
	
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
			
		}
		else
		{
			Reporter.log("The Browser value is not matching, true");
		}
		return driver;
		
	}
	
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	
	// method for waiting for element(s)
	public static void waitForElement(String LocatorType, String LocatorValue, String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
	}
	
	public static void typeAction(String Locatortype, String Locatorvalue, String Testdata)
	{
		if(Locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locatorvalue)).clear();
			driver.findElement(By.id(Locatorvalue)).sendKeys(Testdata);
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locatorvalue)).clear();
			driver.findElement(By.name(Locatorvalue)).sendKeys(Testdata);
		}
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locatorvalue)).clear();
			driver.findElement(By.xpath(Locatorvalue)).sendKeys(Testdata);
		}
	}
	
	public static void clickAction(String locatorType, String locatorValue, String testData)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).sendKeys(Keys.ENTER);
		}
		if(locatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorValue)).click();
		}
		if(locatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorValue)).click();
		}
	}
	
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title,Expected_Title, "Title is not Matching");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void closeBrowser()
	{
		driver.quit();
	}
	
	public static void dropDownAction(String locatortype, String locatorvalue, String testdata)
	{
		if(locatortype.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(testdata);
			Select element = new Select(driver.findElement(By.id(locatorvalue)));
			element.selectByIndex(value);
		}
		
		if(locatortype.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(testdata);
			Select element = new Select(driver.findElement(By.name(locatorvalue)));
			element.selectByIndex(value);
		}
		
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(testdata);
			Select element = new Select(driver.findElement(By.xpath(locatorvalue)));
			element.selectByIndex(value);
		}
	}
	//method for capturing Stock Number in Notepad
	public static void captureStock(String locatortype, String locatorvalue) throws Throwable
	{
		String stockNum ="";
		if(locatortype.equalsIgnoreCase("id"))
		{
			stockNum = driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		
		if(locatortype.equalsIgnoreCase("name"))
		{
			stockNum = driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			stockNum = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
		}
		//create notepad in CaptureData folder
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush(); // primarily used to force any buffered data to be written immediately without closing the FileWriter.
		bw.close();
	}
	
	//method for verify captured Stock Number from Notpad in the Stock table
	public static void stockTable() throws Throwable
	{
			FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
			BufferedReader br = new BufferedReader(fr);
			String Exp_Data = br.readLine();
			//if search textbox is not displayed click on search panel icon, if search textbox is displayed don't click on search panel
			if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			{
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			}
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(3000);
			String Act_Data = driver.findElement(By.xpath("//table[@id = 'tbl_a_stock_itemslist']//tr[1]//td[8]/div/span/span")).getText();
			Reporter.log(Act_Data+"   "+Exp_Data,true);
			try {
				Assert.assertEquals(Act_Data, Exp_Data, "Stock number is not matching");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
	}
	//method for capturing Supplier Number in Notepad
	public static void captureSupplierNumber(String locator_type, String locatar_value, String Test_Data)throws Throwable
	{
		String suppNum = "";
		if(locator_type.equalsIgnoreCase("id"))
		{
			suppNum = driver.findElement(By.id(locatar_value)).getAttribute("value");
		}
		if(locator_type.equalsIgnoreCase("name"))
		{
			suppNum = driver.findElement(By.name(locatar_value)).getAttribute("value");
		}
		if(locator_type.equalsIgnoreCase("xpath"))
		{
			suppNum = driver.findElement(By.xpath(locatar_value)).getAttribute("value");
		}
		//create notepad in CaptureData folder
		FileWriter fw = new FileWriter("./CaptureData/SupplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(suppNum);
		bw.flush(); // primarily used to force any buffered data to be written immediately without closing the FileWriter.
		bw.close();
	}
	
	//method for verify captured Supplier Number from Notepad in the Supplier table
	public static void supplierTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/SupplierNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		//if search textbox is not displayed click on search panel icon, if search textbox is displayed don't click on search panel
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("(//span[last()])[74]")).getText();
		Reporter.log(Act_Data+"   "+Exp_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data, "Stock number is not matching");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
	}

	
	//method for capturing Customer Number in Notepad
	public static void captureCustomerNumber(String Locator_type, String Locator_value)throws Throwable
	{
		String custNum = "";
		if(Locator_type.equalsIgnoreCase("id"))
		{
			custNum = driver.findElement(By.id(Locator_value)).getAttribute("value");
		}
		if(Locator_type.equalsIgnoreCase("name"))
		{
			custNum = driver.findElement(By.name(Locator_value)).getAttribute("value");
		}
		if(Locator_type.equalsIgnoreCase("xpath"))
		{
			custNum = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
		}
		//create notepad in CaptureData folder
		FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(custNum);
		bw.flush(); // primarily used to force any buffered data to be written immediately without closing the FileWriter.
		bw.close();
	}
	
	//method for verify captured Customer Number from Notepad in the Customer table
	public static void customerTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		//if search textbox is not displayed click on search panel icon, if search textbox is displayed don't click on search panel
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("(//span[last()])[67]")).getText();
		Reporter.log(Act_Data+"   "+Exp_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data, "Customer number is not matching");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
	}



}
