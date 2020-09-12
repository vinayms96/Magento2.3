package com.magento;

import com.magento.browser_setup.BrowserSetup;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.*;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.Test;

public class AddToCart extends BrowserSetup {

    @Test(description = "Setting up the Loggers and Extent Reports", priority = 1, groups = {"addCart.setLoggerExtent"})
    public void setLoggerExtent() {
        /*Setting the Loggers and Extent Reports*/
        Loggers.setLogger(AddToCart.class.getName());
        ExtentReport.createTest("Add to Cart");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Navigating to the listing page", priority = 2, groups = {"addCart.navListingPage"})
    public void navListingPage() {
        HomeModel homeModel = new HomeModel(driver);
        SearchModel searchModel = new SearchModel(driver);

//        homepageModel.selectMenus();
        searchModel.searchText();
    }

    @Test(description = "Navigate to PDP and Add to Cart", priority = 3, groups = {"addCart.addProductToCart"})
    public void addProductToCart() {
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CartModel cartModel = new CartModel(driver);

        listingModel.fetchProductDetails();
        listingModel.pickProduct();

        productModel.verifyProductDetails();
        productModel.addToCart();

        minicartModel.clickMiniCartPop();
        minicartModel.clickViewCart();

        cartModel.fetchProductDetails();
        cartModel.verifyProductDetails();
    }
}
