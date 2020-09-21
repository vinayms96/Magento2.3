package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.*;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class Checkout extends BrowserSetup {

    /**
     * @param extentTestName
     */
    public void setLoggerExtent(String extentTestName) {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(Checkout.class.getName());
        ExtentReport.createTest(extentTestName);
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Placing Order from Listing page (LoggedIn)", priority = 2, groups = {"checkout.placeOrder"})
    public void placeOrderMini() throws InterruptedException {
        SearchModel searchModel = new SearchModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CheckoutModel checkoutModel = new CheckoutModel(driver);
        OrderSuccessModel orderSuccessModel = new OrderSuccessModel(driver);

        setLoggerExtent("Place Order");

        searchModel.searchText();

        listingModel.fetchProductDetails();
        listingModel.addCartListing(driver);

        minicartModel.clickMiniCartPop();
        minicartModel.getMiniProductDetails();
        minicartModel.goToCheckout(driver);

        checkoutModel.verifyCheckout1(driver);
        checkoutModel.checkoutSignIn(driver);
        checkoutModel.selectShippingMethod(driver);
        checkoutModel.verifyCheckout2(driver);
        checkoutModel.clickPlaceOrder(driver);

        orderSuccessModel.verifyOrderSuccess(driver);
        orderSuccessModel.fetchOrderNumber(driver);
    }
}
