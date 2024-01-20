package com.eva.vtiger.pages.inventry.invoice;

import com.eva.vtiger.pages.inventry.invoiceOr.InventoryInvoiceNewCreateInvoiceOr;
import com.eva.vtiger.utils.WebUtil;

public class InventoryInvoiceNewCreateInvoice extends InventoryInvoiceNewCreateInvoiceOr {

	// TB--TextBox
	// BT--Button
	// RB--Radio Button
	// LK--Link
	private WebUtil wt;

	public InventoryInvoiceNewCreateInvoice(WebUtil wu) {
		super(wu);
		this.wt = wu;
	}

	public String fillUpInvoiceInformation( String firstName, String accountName,String address,String postofficeBox,String cityName,String StateName,String billCode,String country ,String expSubjectName) {
		wt.sendKeys(getExpSubjectNameTB(), expSubjectName);
		wt.sendKeys(getCustomerNumberTB(), "" + expSubjectName + ".er@gmail.com");
		InventoryInvoiceNewCreateInvoiceCommonAdd commonAdd = new InventoryInvoiceNewCreateInvoiceCommonAdd(wt);
		wt.click(getSelectContactBT());
		commonAdd.addElementName(firstName, "firstname");
		wt.sendKeys(getPurchaseOrderTB(), "Contact list");
		wt.click(getAccountNameTB());
		wt.myThread(2000);
		commonAdd.addElementName(accountName, "accountname");
		wt.myClear(getBillingAddressTB());
		wt.sendKeys(getBillingAddressTB(), address);
		wt.myClear(getBillPoBoxTB());
		wt.sendKeys(getBillPoBoxTB(), postofficeBox);
		wt.myClear(getBillCityTB());
		wt.sendKeys(getBillCityTB(), cityName);
		wt.myClear(getBillStateTB());
		wt.sendKeys(getBillStateTB(),StateName);
		wt.myClear(getBillCodeTB());
		wt.sendKeys(getBillCodeTB(), billCode);
		wt.myClear(getBillCountryTB());
		wt.sendKeys(getBillCountryTB(), country);
		wt.click(getCopyBillingAddressRB());
		wt.click(getProductLNK());
		return expSubjectName;
	}

}
