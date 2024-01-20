package com.eva.vtiger.salestestscripts;

import org.testng.annotations.Test;

import com.eva.vtiger.base.BaseTest;
import com.eva.vtiger.pages.sales.leads.MarketingLaedsNewCreateLead;
import com.eva.vtiger.pages.sales.leads.MarketingLeadsInformationPage;
import com.eva.vtiger.pages.sales.leads.MarketingLeadsLandingPage;
import com.eva.vtiger.utils.WebUtil;

public class SalesLeadsTestScripts extends BaseTest{
	BaseTest bsTst =new BaseTest();
	WebUtil webUtil=bsTst.getWebUtil();

	
	
	@Test(groups = {"Smoke"})
	public void verifyVT007SearchLeads() {
		webUtil.printMessage("====  verifyVT008SearchLeads starts from here====");
		getReuseCode().goToMarketingLeadsLink();
		getReuseCode().creatNewPluseButton();
		MarketingLaedsNewCreateLead creatLead=new MarketingLaedsNewCreateLead(webUtil);
		String expFirstName =creatLead.newCreatMarketingLeads();
		getReuseCode().saveButton();
		getReuseCode().goToMarketingLeadsLink();
		MarketingLeadsLandingPage malp=new MarketingLeadsLandingPage(webUtil);
		String searchedName=malp.innerTextOfSearchedElement(expFirstName, "firstname");
		webUtil.verifText(searchedName, expFirstName, " your data "+searchedName+" is matched that you are searching");
		webUtil.printMessage("====  verifyVT008SearchLeads starts from here====");
	}

	@Test
	public void verifyVT008CreateLeads() {
		webUtil.printMessage("====  verifyVT009CreateLeads ends from here====");
		getReuseCode().goToMarketingLeadsLink();
		getReuseCode().creatNewPluseButton();
		MarketingLaedsNewCreateLead creatLead=new MarketingLaedsNewCreateLead(webUtil);
		String expFirstName =creatLead.newCreatMarketingLeads();
		getReuseCode().saveButton();
		getReuseCode().goToMarketingLeadsLink();
		MarketingLeadsLandingPage mlLandingPage=new MarketingLeadsLandingPage(webUtil);
		String searchData=mlLandingPage.innerTextOfSearchedElement(expFirstName, "firstname");
		if (searchData.contains(expFirstName)) {
			webUtil.printMessage("your search data "+searchData+" is matched that you are searching");
			webUtil.printMessage("Passed !, "+searchData+" name is created successfully");
		} else {
			webUtil.printMessage("your search data data "+searchData+" is not  matched that you are searching");
			webUtil.printMessage("Failed !, "+searchData+" name is not created successfully");
		}
		webUtil.printMessage("====  verifyVT009CreateLeads ends from here====");
	}

	@Test
	public void verifyVT009DeleteLead() {
		webUtil.printMessage("====  verifyVT0010DeleteLead starts from here====");
		getReuseCode().goToMarketingLeadsLink();
		getReuseCode().creatNewPluseButton();
		MarketingLaedsNewCreateLead creatLead=new MarketingLaedsNewCreateLead(webUtil);
		creatLead.newCreatMarketingLeads();
		getReuseCode().saveButton();
		webUtil.myThread(3000);
		MarketingLeadsInformationPage mlInfoPage=new MarketingLeadsInformationPage(webUtil);
		String leadResult=mlInfoPage.innerTextsearchForLeadNumber();
		String expResult="No Lead Found !";
		webUtil.verifText(expResult, leadResult, "your created lead has been deleted successfully");
		webUtil.printMessage("====  verifyVT0010DeleteLead ends from here====");
	}

	@Test(groups = {"Smoke"})
	public void verifyVT010LeadsTableLinks() {
		webUtil.printMessage("===== verifyVT011LeadsTableLinks starts from here ======");
		getReuseCode().goToMarketingLeadsLink();
		getReuseCode().creatNewPluseButton();
		MarketingLaedsNewCreateLead creatLead=new MarketingLaedsNewCreateLead(webUtil);
		String expLeadsName=creatLead.newCreatMarketingLeads();
		getReuseCode().saveButton();
		webUtil.myThread(3000);
		getReuseCode().goToMarketingLeadsLink();
		MarketingLeadsLandingPage mlLandingPage=new MarketingLeadsLandingPage(webUtil);
		String firstNameBeforeClickText=mlLandingPage.innerTextOfSearchedElement(expLeadsName, "firstname");
		MarketingLeadsInformationPage mlip=new MarketingLeadsInformationPage(webUtil);
		String firstNameAfterClickText=mlip.firstName();

		if (firstNameBeforeClickText.equalsIgnoreCase(firstNameAfterClickText)) {
			webUtil.printMessage("Passed !,first name is same After clicking the first name link in marketing leads");
		} else {
			webUtil.printMessage("Passed !,first name is not same After clicking the first name link in marketing leads");
		}
		webUtil.printMessage("====  verifyVT011LeadsTableLinks ends from here====");

	}
}
