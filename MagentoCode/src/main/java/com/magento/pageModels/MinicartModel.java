package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MinicartModel {
    private static String mini_product_name;
    private static String swatch_one;
    private static String swatch_two;
    private static String mini_product_qty;
    private static String mini_product_price;

    @FindBy(xpath = "//span[@class='counter qty']")
    private WebElement mini_cart_pop;
    @FindBy(css = "#mini-cart .product-item")
    private List<WebElement> mini_products_list;
    @FindBy(xpath = "//strong[@class='product-item-name']/a")
    private WebElement product_name;
    @FindBy(css = ".product.options .toggle")
    private WebElement see_details;
    @FindBy(xpath = "//dd[@class='values']/span")
    private List<WebElement> swatch_options;
    @FindBy(css = ".price-wrapper .minicart-price .price")
    private WebElement product_price;
    @FindBy(css = ".cart-item-qty")
    private WebElement quantity;
    @FindBy(css = ".viewcart")
    private WebElement view_cart;

    public MinicartModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void getMiniProductDetails() {
        ExtentReport.createNode("Get Minicart Product Details");

        if (mini_cart_pop.isDisplayed()) {
            mini_cart_pop.click();
            mini_product_name = product_name.getText();
            try {
                if (see_details.isDisplayed()) {
                    see_details.click();
                    if (swatch_options.size() == 2) {
                        swatch_one = swatch_options.get(0).getText();
                        swatch_two = swatch_options.get(1).getText();
                        Loggers.getLogger().info("Swatches '" + swatch_one + "' and '" + swatch_two + "' fetched");
                        ExtentReport.getExtentNode().pass("Swatches '" + swatch_one + "' and '" + swatch_two + "' fetched");
                    } else if (swatch_options.size() == 1) {
                        swatch_one = swatch_options.get(0).getText();
                        Loggers.getLogger().info("Swatch '" + swatch_one + "' fetched");
                        ExtentReport.getExtentNode().pass("Swatch '" + swatch_one + "' fetched");
                    }
                }
            } catch (Exception e) {
                Loggers.getLogger().info("The product '" + mini_product_name + "' is a simple product");
                ExtentReport.getExtentNode().pass("The product '" + mini_product_name + "' is a simple product");
            }
            mini_product_price = product_price.getText();
            Loggers.getLogger().info("Minicart product price '" + mini_product_price + "' is fetched");
            ExtentReport.getExtentNode().pass("Minicart product price '" + mini_product_price + "' is fetched");

            mini_product_qty = quantity.getAttribute("data-item-qty");
            Loggers.getLogger().info("Minicart product quantity '" + mini_product_qty + "' is fetched");
            ExtentReport.getExtentNode().pass("Minicart product quantity '" + mini_product_qty + "' is fetched");
        }

    }

}
