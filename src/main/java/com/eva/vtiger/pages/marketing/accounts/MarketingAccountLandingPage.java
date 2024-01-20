package com.eva.vtiger.pages.marketing.accounts;

import com.eva.vtiger.pages.common.CommonReusableCodes;
import com.eva.vtiger.pages.marketing.accountsOr.MarketingAccountLandingPageOr;
import com.eva.vtiger.utils.WebUtil;

public class MarketingAccountLandingPage extends MarketingAccountLandingPageOr{

	private WebUtil webtl;
	public MarketingAccountLandingPage(WebUtil webtl) {
		super(webtl);
			this.webtl=webtl;

			
	}

	public String innerTextOfSearchedElement(String searchName,String SearchTypeAttributValue) {
		CommonReusableCodes cc=new CommonReusableCodes(webtl);
		cc.searchForElement(searchName,SearchTypeAttributValue );
		webtl.myThread(4000);
		String actAccountName=webtl.myInnerText(getAccountNameTB());
		return actAccountName;
	}
	public String clickOfSearchedElement(String searchName,String SearchTypeAttributValue) {
		CommonReusableCodes cc=new CommonReusableCodes(webtl);
		cc.searchForElement(searchName,SearchTypeAttributValue );
		webtl.click(getAccountNameTB());
		String actAccountName=webtl.myInnerText(getAccountNameTB());
		return actAccountName;

	}

	public String deleteAndSearchAccount(String searchName,String SearchTypeAttributValue) {
		webtl.click(getAccountDelete());
		webtl.popUpAccept();
		CommonReusableCodes cc=new CommonReusableCodes(webtl);
		cc.searchForElement(searchName,SearchTypeAttributValue );
		String actResult=webtl.myInnerText(getSearchResult());
		return actResult;
	}
	public void verifyDuplicateAndEdit() {
		String emailBeforeDuplicate=webtl.myInnerText(getEmailTb());
		webtl.click(getDuplicateBT());
		webtl.myClear(getAcName1());
		String accountName=webtl.getRandomName(10);
		webtl.sendKeys(getAcName1(),accountName);
		CommonReusableCodes cc=new CommonReusableCodes(webtl);
		cc.saveButton();
		String emailAfterDuplicate=webtl.myInnerText(getEmailTb());
		webtl.verifText(emailBeforeDuplicate, emailAfterDuplicate, "the privious data "+emailBeforeDuplicate+" and current data "+emailAfterDuplicate+" is matched successfully");
	}

	public String editAccountInformation() {
		webtl.click(getEditBT());
		webtl.myClear(getAcName1());
		String acNameAfrterEdit=webtl.getRandomName(10);
		webtl.sendKeys(getAcName1(),acNameAfrterEdit);
		CommonReusableCodes cc=new CommonReusableCodes(webtl);
		cc.saveButton();
		String acNameAfterEdit=webtl.myInnerText(getAcName2());
		return acNameAfterEdit;
	}
}
