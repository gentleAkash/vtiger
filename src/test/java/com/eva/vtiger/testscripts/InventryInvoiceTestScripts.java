
package com.eva.vtiger.testscripts;

import org.testng.annotations.Test;

import com.eva.vtiger.pages.common.CommonReusableCodes;
import com.eva.vtiger.pages.inventry.invoice.InventoryInvoiceNewCreateInvoice;
import com.eva.vtiger.utils.WebUtil;

public class InventryInvoiceTestScripts extends BaseTest{
	
	///  requirement   Testing Logic  TestCase TestScript
	///  TDD -  Test Driven Development
	 BaseTest bsTst =new BaseTest();
	 WebUtil webUtil=bsTst.getWebUtil();


	@Test
	public void verifyVT006AccountAndContactAddOnInvoiceForm( String firstName, String accountName,String address,String postofficeBox,String cityName,String StateName,String billCode,String country ,String expSubjectName) {
		webUtil.printMessage("====  verifyVT0012AccountAndContactAddOnInvoiceForm starts from here====");
		
		getReuseCode().goToInventoryInvoiceLink();
		getReuseCode().creatNewPluseButton();
		InventoryInvoiceNewCreateInvoice creatInvoice= new InventoryInvoiceNewCreateInvoice(webUtil);
		creatInvoice.fillUpInvoiceInformation(firstName,accountName,address,postofficeBox,cityName,StateName,billCode,country,"Test Account Name And Contact Name Addition");
		webUtil.printMessage("====  verifyVT0012AccountAndContactAddOnInvoiceForm ends from here====");

	}
   ///  cadric beust 
	
	    /////   TestNG  JUnit
	
	   ////  Cucumber   -   BDD 
	
	
	
	
	
	
	
}
