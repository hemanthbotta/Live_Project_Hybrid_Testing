package DriverFactory;

import CommonFunctions.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript extends FunctionLibrary{
	
	String input = "./FileInput/ControllerExcelFile.xlsx";
	String output = "./FileOutput/ControllerExcelFileReport.xlsx";
	String TCSheet = "MasterTestCases";
	
	public void startTest() throws Throwable 
	{
		String ModuleStatus="";
		String ModuleNew="";
		
		ExcelFileUtil xl = new ExcelFileUtil(input);
		
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule = xl.getCellData(TCSheet, i, 1);
				
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String objType = xl.getCellData(TCModule, j, 1);
					String LType = xl.getCellData(TCModule, j, 2);
					String LValue = xl.getCellData(TCModule, j, 3);
					String TData = xl.getCellData(TCModule, j, 4);
										
					try {
										 
					if(objType.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibrary.startBrowser();
					}
					if(objType.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
					}
					if(objType.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(LType, LValue, TData);
					}
					if(objType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(LType, LValue, TData);
					}
					if(objType.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(LType, LValue, TData);
					}
					if(objType.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(TData);
					}
					if(objType.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
					}
					
					if(objType.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(LType, LValue, TData);
					}
					
					if(objType.equalsIgnoreCase("captureStock"))
					{
						FunctionLibrary.captureStock(LType, LValue);
					}
					
					if(objType.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable();
					}
					
					if(objType.equalsIgnoreCase("captureSupplierNumber"))
					{
						FunctionLibrary.captureSupplierNumber(LType, LValue, TData);
					}
					
					if(objType.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable();
					}
					
					if(objType.equalsIgnoreCase("captureCustomerNumber"))
					{
						FunctionLibrary.captureCustomerNumber(LType, LValue);
					}
					
					if(objType.equalsIgnoreCase("customerTable"))
					{
						FunctionLibrary.customerTable();
					}
					
					xl.setCellData(TCModule, j, 5, "Pass", output);
					ModuleStatus="true";
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						//write as fail into status cell
						xl.setCellData(TCModule, j, 5,"Fail", output);
						ModuleNew = "false";
						
					}
					if(ModuleStatus.equalsIgnoreCase("True"))
					{
						xl.setCellData(TCSheet, i, 3, "Pass", output);
					}
					if(ModuleNew.equalsIgnoreCase("False"))
					{
						xl.setCellData(TCSheet, i, 3, "Fail", output);
					}
				}
			}
			
			else
			{
				xl.setCellData(TCSheet, i, 3, "Blocked", output);
			}
		}
	}
	
	

}
