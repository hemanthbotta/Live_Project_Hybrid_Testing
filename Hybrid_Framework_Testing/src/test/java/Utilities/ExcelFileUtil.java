package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	XSSFWorkbook wb;
	//Constructor for reading excel path
	public ExcelFileUtil(String Excelpath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = new XSSFWorkbook(fi);
	}
	// reading excel data of rows in a sheet
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
		
	}
	//reading cell data in a row
	public String getCellData(String sheetname, int row, int column)
	{
		String data;
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType() == CellType.NUMERIC)
		{
			int celldata = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	
	//Method for writing data results
	public void setCellData(String sheetname, int row, int column, String status, String writeexcel) throws Throwable
	{
		
		for(int i=0;i<=row;i++)
		{
			wb.getSheet(sheetname).getRow(row).createCell(column).setCellValue(status);
			
			if (status.equalsIgnoreCase("Pass"))
			{
				XSSFCellStyle style = wb.createCellStyle();
				XSSFFont font = wb.createFont();
				font.setColor(IndexedColors.GREEN.getIndex());
				font.setBold(true);
				style.setFont(font);
				wb.getSheet(sheetname).getRow(row).getCell(column).setCellStyle(style);
			}
			else if (status.equalsIgnoreCase("Fail"))
			{
				XSSFCellStyle style = wb.createCellStyle();
				XSSFFont font = wb.createFont();
				font.setColor(IndexedColors.RED.getIndex());
				font.setBold(true);
				style.setFont(font);
				wb.getSheet(sheetname).getRow(row).getCell(column).setCellStyle(style);
			}
			else if (status.equalsIgnoreCase("Blocked"))
			{
				XSSFCellStyle style = wb.createCellStyle();
				XSSFFont font = wb.createFont();
				font.setColor(IndexedColors.BLUE.getIndex());
				font.setBold(true);
				style.setFont(font);
				wb.getSheet(sheetname).getRow(row).getCell(column).setCellStyle(style);
			}
			
		}
		
		FileOutputStream fo = new FileOutputStream(writeexcel);
		wb.write(fo);
		fo.close();
		
	}
	
	public static void main (String[] args) throws Throwable
	{
		ExcelFileUtil xl = new ExcelFileUtil("D://Sample.xlsx");
		int rc = xl.rowCount("Emp");
		for (int i=1; i<=rc;i++)
		{
			String fname = xl.getCellData("Emp", i, 0);
			String mname = xl.getCellData("Emp", i, 1);
			String lname = xl.getCellData("Emp", i, 2);
			String eid = xl.getCellData("Emp", i, 3);
			System.out.println(fname+"   "+mname+"   "+lname+"   "+eid);
			//write data & formatting
			xl.setCellData("Emp", i, 4, "Blocked", "D://SampleReport.xlsx");
		}
		
		
	}

}
