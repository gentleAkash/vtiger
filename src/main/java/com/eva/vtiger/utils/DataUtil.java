package com.eva.vtiger.utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataUtil {
	public static void main(String[] args) {
		
		DataUtil util = new DataUtil();
		util.getTestCaseData("VT006");

	}
	public Map<String, String> getTestCaseData(String tcID) {
		Workbook wbook = null;
		try {
			InputStream fis = new FileInputStream("C:\\Users\\pc\\Downloads\\vtiger\\src\\test\\resources\\TestData.xlsx");
			wbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheetObj = wbook.getSheet("Sheet1");
		int tcRowNumber = getRowNumberByTestCaseID(sheetObj, tcID);

		Row rowTcObj = sheetObj.getRow(tcRowNumber);
		int cellCount = rowTcObj.getLastCellNum();
		Map<String, String> dataMap = new HashMap<String, String>();
		for (int i = 3; i <= cellCount - 1; i = i + 2) {
			String cellDataKey = getCellData(rowTcObj, i);
			String cellDataValue = getCellData(rowTcObj, i + 1);
			dataMap.put(cellDataKey, cellDataValue);
		}
		return dataMap;
	}

	// this method will read data from excel on the basis of row object and cell
	// number
	// it will manage data whether it is in string or integer
	public String getCellData(Row rowObj, int cellNumber) {
		String data = null;
		Cell cellObj = rowObj.getCell(cellNumber, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		if (cellObj.getCellType() == CellType.STRING) {
			data = cellObj.getStringCellValue();
		} else if (cellObj.getCellType() == CellType.NUMERIC) {
			Double dbl = cellObj.getNumericCellValue();
			Integer intData = dbl.intValue();
			data = intData.toString();
		}

		return data;
	}

	public int getRowNumberByTestCaseID(Sheet sheetObj, String tcID) {
		/// below lines will search for testcase id in data sheet
		/// and will give row number where data exist
		int tcRowNumber = -1;
		int rowCount = sheetObj.getLastRowNum();
		for (int i = 1; i <= rowCount; i++) {
			Row rowObj = sheetObj.getRow(i);
			Cell cellObj = rowObj.getCell(2);
			String cellData = cellObj.getStringCellValue();
			System.out.println(cellData);
			if (cellData.trim().equalsIgnoreCase(tcID)) {
				tcRowNumber = i;
				break;
			}
		}
		return tcRowNumber;
	}

}
