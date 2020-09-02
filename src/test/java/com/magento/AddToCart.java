package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.HomepageModel;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class AddToCart extends BrowserSetup {
    @Test
    public void selectCategoryPage() {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Add to Cart");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));

        HomepageModel homepageModel = new HomepageModel(driver);

        homepageModel.selectMenus();

    }
}
