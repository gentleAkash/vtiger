
package com.eva.vtiger.inventorytestscripts;

import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

import com.eva.vtiger.base.BaseTest;
import com.eva.vtiger.pages.inventry.invoice.InventoryInvoiceNewCreateInvoice;
import com.eva.vtiger.utils.WebUtil;

public class InventryInvoiceTestScripts extends BaseTest {

	/// requirement Testing Logic TestCase TestScript
	/// TDD - Test Driven Development

	BaseTest bsTst = new BaseTest();
	WebUtil webUtil = bsTst.getWebUtil();

	@DataProvider()
	public Object[][] getTestCaseData() {

		String dataArray1[] = new String[8];
		dataArray1[0] = "Akash";
		dataArray1[1] = "Mansigh";
		dataArray1[2] = "Jaunpur";
		dataArray1[3] = "Badalpur Khurd";
		dataArray1[4] = "Badalpur";
		dataArray1[5] = "up";
		dataArray1[6] = "25888";
		dataArray1[7] = "India";

		String dataArray2[] = new String[8];
		dataArray2[0] = "Shailesh";
		dataArray2[1] = "Anil singh";
		dataArray2[2] = "bhadohi";
		dataArray2[3] = "Sant ravidas nagar bhadohi";
		dataArray2[4] = "Kausanbi";
		dataArray2[5] = "up";
		dataArray2[6] = "889617";
		dataArray2[7] = "India";

		String dataArray3[] = new String[8];
		dataArray3[0] = "Kiasn";
		dataArray3[1] = "RahulSir";
		dataArray3[2] = "bhdhohi";
		dataArray3[3] = "vankat";
		dataArray3[4] = "Sultanpur";
		dataArray3[5] = "up";
		dataArray3[6] = "657594";
		dataArray3[7] = "India";

		String[][] dim2Array2 = new String[3][8];

		dim2Array2[0] = dataArray1;
		dim2Array2[1] = dataArray2;
		dim2Array2[2] = dataArray3;

		return dim2Array2;

	}

	@Test(dataProvider = "getTestCaseData")

	public void verifyVT006AccountAndContactAddOnInvoiceForm(String firstName, String accountName, String address,
			String postofficeBox, String cityName, String StateName, String billCode, String country) {

		webUtil.printMessage("====  verifyVT006AccountAndContactAddOnInvoiceForm starts from here====");
		reuseCode.goToInventoryInvoiceLink();
		reuseCode.creatNewPluseButton();
		InventoryInvoiceNewCreateInvoice creatInvoice = new InventoryInvoiceNewCreateInvoice(webUtil);
		creatInvoice.fillUpInvoiceInformation(firstName, accountName, address, postofficeBox, cityName, StateName,
				billCode, country, "Test Account Name and Contact,country");
		webUtil.printMessage("====  verifyVT006AccountAndContactAddOnInvoiceForm ends from here====");

	}

}
/// cadric beust

///// TestNG JUnit

//// Cucumber - BDD
