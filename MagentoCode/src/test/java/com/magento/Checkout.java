package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.ListingModel;
import com.magento.pageModels.MinicartModel;
import com.magento.pageModels.SearchModel;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class Checkout extends BrowserSetup {
    @Test(description = "Setting up the Loggers and Extent Reports", priority = 1, groups = {"checkout.setLoggerExtent"})
    public void setLoggerExtent() {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(Checkout.class.getName());
        ExtentReport.createTest("Place Order");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Placing Order by adding product from Listing page", priority = 2, groups = {"checkout.placeOrder"})
    public void placeOrder() {
        SearchModel searchModel = new SearchModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);

        searchModel.searchText();
        listingModel.fetchProductDetails();
        listingModel.addCartListing();
        minicartModel.getMiniProductDetails();
    }
}
