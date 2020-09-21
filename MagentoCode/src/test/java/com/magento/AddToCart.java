package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.*;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class AddToCart extends BrowserSetup {

    /**
     * @param extentTestName
     */
    public void setLoggerExtent(String extentTestName) {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(AddToCart.class.getName());
        ExtentReport.createTest(extentTestName);
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Navigate to PDP and Add to Cart", priority = 1, groups = {"addCart.addProductToCart"})
    public void addProductToCart() {
        SearchModel searchModel = new SearchModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CartModel cartModel = new CartModel(driver);

        setLoggerExtent("Add to Cart");

        searchModel.searchText();

        listingModel.fetchProductDetails();
        listingModel.pickProduct(driver);

        productModel.verifyProductDetails();
        productModel.addToCart(driver);

        minicartModel.clickMiniCartPop();
        minicartModel.clickViewCart(driver);

        cartModel.fetchProductDetails();
        cartModel.verifyProductDetails();
    }
}
