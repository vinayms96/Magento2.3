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

    @Test(description = "Setting up the Loggers and Extent Reports", priority = 0, groups = {"userAccounts.setLoggerExtent"})
    public void setLoggerExtent() {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("User Accounts");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Creating the User Account", priority = 1, groups = {"userAccounts.accountCreate"})
    public void accountCreate() {
        /*PageModel object*/
        SignupModel signupModel = new SignupModel(driver);
        AccountModel accountModel = new AccountModel(driver);

        signupModel.clickCreateAccountLink();
        signupModel.fillCustomerForm();

        accountModel.accountCreateVerify();
    }

    @Test(description = "Logging into the User Account", priority = 2, groups = {"userAccounts.accountLogin"})
    public void accountLogin() {
        /*PageModel object*/
        LoginModel loginModel = new LoginModel(driver);

        loginModel.fillLoginForm();
    }
}
