package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.AccountModel;
import com.magento.pageModels.LoginModel;
import com.magento.pageModels.SignupModel;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class UserAccounts extends BrowserSetup {

    /**
     * @param extentTestName
     */
    public void setLoggerExtent(String extentTestName) {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest(extentTestName);
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Creating the User Account", priority = 1, groups = {"userAccounts.accountCreate"})
    public void accountCreate() {
        /*PageModel object*/
        SignupModel signupModel = new SignupModel(driver);
        AccountModel accountModel = new AccountModel(driver);

        setLoggerExtent("Create New User Account");

        signupModel.clickCreateAccountLink(driver);
        signupModel.fillCustomerForm(driver);

        accountModel.accountCreateVerify();
//        accountModel.clickAccountDropdown(driver);
//        accountModel.selectDropOptions(driver, "sign out");
    }

    @Test(description = "Logging into the User Account", priority = 2, groups = {"userAccounts.accountLogin"})
    public void accountLogin() {
        /*PageModel object*/
        LoginModel loginModel = new LoginModel(driver);

        setLoggerExtent("Login to User Account");

        loginModel.fillLoginForm(driver);
    }
}
