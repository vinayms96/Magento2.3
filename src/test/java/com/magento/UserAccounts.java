package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.LoginModel;
import com.magento.pageModels.SignupModel;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class UserAccounts extends BrowserSetup {

    @Test
    public void setLoggerExtent() {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("User Accounts");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test
    public void accountCreate() {
        /*PageModel object*/
        SignupModel signupModel = new SignupModel(driver);

        signupModel.clickCreateAccountLink();
        signupModel.fillCustomerForm();
    }

    @Test
    public void accountLogin() {
        /*PageModel object*/
        LoginModel loginModel = new LoginModel(driver);

        loginModel.fillLoginForm();
    }
}
