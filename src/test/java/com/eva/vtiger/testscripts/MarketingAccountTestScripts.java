package com.eva.vtiger.testscripts;

import org.testng.annotations.Test;

import com.eva.vtiger.pages.common.CommonReusableCodes;
import com.eva.vtiger.pages.marketing.accounts.MarketingAccountLandingPage;
import com.eva.vtiger.pages.marketing.accounts.MarketingAccountsNewCreateAccount;
import com.eva.vtiger.utils.WebUtil;

public class MarketingAccountTestScripts extends BaseTest{
	BaseTest bsTst =new BaseTest();
	 WebUtil webUtil=bsTst.getWebUtil();
	///  @BeforeClass  @AfterClass
	
	
	@Test(priority = 1, groups = {"Smoke", "Regression"})
	public void verifyVT001CreateAccount() throws InterruptedException {
		webUtil.printMessage("====verifyVT001CreateAccount starts from here====");
		getReuseCode().goToMarketingAccountsLink();
		getReuseCode().creatNewPluseButton();
		MarketingAccountsNewCreateAccount malp= new MarketingAccountsNewCreateAccount(webUtil);
		String expAccountName =malp.fillUpBasicInformation();		
		getReuseCode().saveButton();
		getReuseCode().goToMarketing();
		getReuseCode().goToMarketingAccountsLink();
		MarketingAccountLandingPage acLandingPage= new MarketingAccountLandingPage(webUtil);
		String actAccountName=acLandingPage.innerTextOfSearchedElement(expAccountName, "accountname");
		if (actAccountName.equalsIgnoreCase(expAccountName)) {
			webUtil.printMessage("Passed !,new Account is created successfully");
		} else {
			webUtil.printMessage("Failed !,new Account is not created successfully");
		}
		webUtil.printMessage("====  verifyVT001CreateAccount ends from here====");

	}



	@Test(priority=3, alwaysRun = true, groups = {"Smoke"})
//If set to true, this test method will always be run even if it depends on a method 
//that failed.This attribute will be ignored if this test doesn't depend on any method or group.
//	Default value is false.

	public void verifyVT002DeleteAccount() throws InterruptedException {
		webUtil.printMessage("====  verifyVT002DeleteAccount starts from here====");
		getReuseCode().goToMarketingAccountsLink();
		getReuseCode().creatNewPluseButton();
		MarketingAccountsNewCreateAccount malp= new MarketingAccountsNewCreateAccount(webUtil);
		String actAccountName =malp.fillUpBasicInformation();		
		getReuseCode().saveButton();
		MarketingAccountLandingPage acLandingPage= new MarketingAccountLandingPage(webUtil);
		acLandingPage.deleteAndSearchAccount(actAccountName, "accountname");
		webUtil.printMessage("====  verifyVT002DeleteAccount ends from here====");
	}
////  sorting

	@Test(priority = 2)
	public void verifyVT003SearchAccount() throws InterruptedException {
		webUtil.printMessage("====  verifyVT003SearchAccount Starts from here====");
		getReuseCode().goToMarketingAccountsLink();
		getReuseCode().creatNewPluseButton();
		MarketingAccountsNewCreateAccount malp= new MarketingAccountsNewCreateAccount(webUtil);
		String expAccountName =malp.fillUpBasicInformation();		
		getReuseCode().saveButton();
		getReuseCode().goToMarketing();
		getReuseCode().goToMarketingAccountsLink();
		MarketingAccountLandingPage acLandingPage= new MarketingAccountLandingPage(webUtil);
		String actAccountName=acLandingPage.innerTextOfSearchedElement(expAccountName, "accountname");
		if (actAccountName.equalsIgnoreCase(expAccountName)) {
			webUtil.printMessage("Passed!,your expected name is searched successfully");
		} else {
			webUtil.printMessage("failed!,your expected name is not searched successfully");
		}
		webUtil.printMessage("====  verifyVT003SearchAccount ends from here====");
	}

	@Test(groups = {"Regression"})
	public void verifyVT004DuplicateAccount() {
		webUtil.printMessage("======= verifyVT004DuplicateAccount Starts from here ======");
		getReuseCode().goToMarketingAccountsLink();
		getReuseCode().creatNewPluseButton();
		MarketingAccountsNewCreateAccount malp= new MarketingAccountsNewCreateAccount(webUtil);
		malp.fillUpBasicInformation();		
		getReuseCode().saveButton();
		MarketingAccountLandingPage acLandingPage= new MarketingAccountLandingPage(webUtil);
		acLandingPage.verifyDuplicateAndEdit();
		webUtil.printMessage("======= verifyVT004DuplicateAccount ends from here ======");

	}

	@Test(priority=-2)
	public void verifyVT005AccountEditable()  {
		webUtil.printMessage("===== verifyVT005AccountEditable starts from here ======");
		getReuseCode().goToMarketingAccountsLink();
		getReuseCode().creatNewPluseButton();
		MarketingAccountsNewCreateAccount malp= new MarketingAccountsNewCreateAccount(webUtil);
		String acNameBeforeEdit=malp.fillUpBasicInformation();		
		getReuseCode().saveButton();
		MarketingAccountLandingPage acLandingPage= new MarketingAccountLandingPage(webUtil);
		String acNameAfterEdit=acLandingPage.editAccountInformation();
		if (!acNameBeforeEdit.equalsIgnoreCase(acNameAfterEdit)) {  ///  !...not equalsIgnoreCase
			System.out.println("Passed !,your Account name is editable because your before edit A/C name ="+acNameBeforeEdit+" "
					+ "and after edit A/C name ="+acNameAfterEdit+" is different");

		} else {
			System.out.println("Failed !,your Account name is editable because your before edit A/C name ="+acNameBeforeEdit+" "
					+ "and after edit A/C name ="+acNameAfterEdit+" is same");
		}
		webUtil.printMessage("===== verifyVT005AccountEditable ends from here ======");
	}
}