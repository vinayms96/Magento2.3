package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.*;
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

    @Test(description = "Placing Order from Listing page (LoggedIn)", priority = 2, groups = {"checkout.placeOrder"})
    public void placeOrderMini() throws InterruptedException {
        SearchModel searchModel = new SearchModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CheckoutModel checkoutModel = new CheckoutModel(driver);
        OrderSuccessModel orderSuccessModel = new OrderSuccessModel(driver);

        searchModel.searchText();

        listingModel.fetchProductDetails();
        listingModel.addCartListing();

        minicartModel.clickMiniCartPop();
        minicartModel.getMiniProductDetails();
        minicartModel.goToCheckout();

        checkoutModel.verifyCheckout1(driver);
        checkoutModel.checkoutSignIn();
        checkoutModel.selectShippingMethod();
        checkoutModel.verifyCheckout2(driver);
        checkoutModel.clickPlaceOrder();

        orderSuccessModel.verifyOrderSuccess(driver);
        orderSuccessModel.fetchOrderNumber();
    }
}
