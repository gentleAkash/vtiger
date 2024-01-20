package com.eva.vtiger.pages.marketing.accounts;

import com.eva.vtiger.pages.marketing.accountsOr.MarketingAccountsNewCreateAccountOr;
import com.eva.vtiger.utils.WebUtil;

public class MarketingAccountsNewCreateAccount extends MarketingAccountsNewCreateAccountOr {

	private WebUtil webtl;
	public MarketingAccountsNewCreateAccount(WebUtil webtl) {
		super(webtl);
		this.webtl=webtl;
	}
	
	
	public String fillUpBasicInformation() {
		String ranName=webtl.getRandomName(10);
		webtl.sendKeys(getAccountNameTB(),ranName);
		webtl.sendKeys(getWebsiteTB(), ""+ranName+".er@gmail.com");
		webtl.sendKeys(getPhoneTB(), "8947863734");
		webtl.sendKeys(getTickerSymbolTB(),"plus");
		webtl.sendKeys(getFaxNameTB(),"A fax called telecopying");
		webtl.sendKeys(getPhonenumberTB(),""+ranName+".evs@gmail.com");
		webtl.sendKeys(getBillCityTB(),"kaushambi");
		webtl.sendKeys(getBillStateTB() ,"utter pradesh");
		webtl.sendKeys(getBillCodeTB() ,"68536");
		webtl.sendKeys(getBillCountryTB(),"india");
		webtl.click(getCopyBillingAddressRB());
		webtl.sendKeys(getDescriptionTB(),"Do work hard until you do");
		return ranName;
	}
	
	public void fillUpMoreInformation(String chooseIndustryValueAttribute) {
		webtl.sendKeys(getOtherphoneTB(),"9876573434");
		webtl.sendKeys(getEmployeesTB(),"5345");
		webtl.sendKeys(getOtherEmailTB(),"8947863734");
		webtl.sendKeys(getOwnershipTB(),"72.27%");
		webtl.selectByValueAttribute(getIndustryTB(),chooseIndustryValueAttribute );
	}
	
}
