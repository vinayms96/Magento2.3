package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.*;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddToCart extends TestNGBase {
    public WebDriver driver;

    /**
     * Setting up Loggers and Extent reports
     */
    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(AddToCart.class.getName());
        ExtentReport.createTest("Add to Cart");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    @Test(description = "Navigate to PDP and Add to Cart", priority = 1, groups = {"addCart.addProductToCart"})
    public void addProductToCart() {
        SearchModel searchModel = new SearchModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CartModel cartModel = new CartModel(driver);

        searchModel.searchText();

        listingModel.fetchProductDetails();
        listingModel.pickProduct(driver);

        productModel.verifyProductDetails();
        productModel.addToCart(driver);

        minicartModel.clickMiniCartPop();
        minicartModel.clickViewCart(driver);

        cartModel.fetchProductDetails(driver);
        cartModel.verifyProductDetails();
    }

}
