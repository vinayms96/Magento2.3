package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.pickers.RandomPicker;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductModel {
    private static String product_old_price;
    private static String product_final_price;
    private static String product_quantity;
    private static ArrayList<String> product_swatches;

    @FindBy(xpath = "//h1/span")
    private WebElement product_name;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']/span")
    private WebElement pdp_old_price;
    @FindBy(xpath = "(//span[@data-price-type='finalPrice'])[1]/span")
    private WebElement pdp_final_price;
    @FindBy(id = "product-options-wrapper")
    private WebElement product_options;
    @FindBy(xpath = "//div[@class='swatch-attribute size']/div/div")
    private List<WebElement> swatches_size_list;
    @FindBy(xpath = "//div[@class='swatch-attribute color']/div/div")
    private List<WebElement> swatches_color_list;
    @FindBy(id = "qty")
    private WebElement quantity;
    @FindBy(id = "product-addtocart-button")
    private WebElement add_cart_button;
    @FindBy(xpath = "//div[@data-ui-id='message-success']/div/a")
    private WebElement add_success;

    /**
     * @param driver - Webdriver element
     */
    public ProductModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @return - product_old_price
     */
    public static String getProduct_old_price() {
        return product_old_price;
    }

    /**
     * @return - product_final_price
     */
    public static String getProduct_final_price() {
        return product_final_price;
    }

    /**
     * @return - product_qty
     */
    public static String getProduct_quantity() {
        return product_quantity;
    }

    /**
     * @return - product_swatches
     */
    public static ArrayList<String> getProduct_swatches() {
        return product_swatches;
    }

    /**
     * Verifying the Product details with the listing page details
     */
    public void verifyProductDetails() {
        /*Setting up the Extent Report*/
        ExtentReport.createNode("Verify Product Details");

        /*Verifying Product name*/
        Assert.assertEquals(ListingModel.getList_product_name(), product_name.getText());
        Loggers.getLogger().info("Product name verified successfully");
        ExtentReport.getExtentNode().pass("Product name verified successfully");

        /*Fetching the Final price*/
        product_final_price = pdp_final_price.getText();

        /*Verifying Product prices*/
        if (ListingModel.getList_product_old_price() != "" && ListingModel.getList_product_old_price() != null) {
            /*Fetching the Old price*/
            product_old_price = pdp_old_price.getText();

            Assert.assertEquals(ListingModel.getList_product_old_price(), product_old_price);
            Assert.assertEquals(ListingModel.getList_product_final_price(), product_final_price);
            Loggers.getLogger().info("Product old price and final price verified successfully");
            ExtentReport.getExtentNode().pass("Product old price and final price verified successfully");
        } else {
            Assert.assertEquals(ListingModel.getList_product_final_price(), product_final_price);
            Loggers.getLogger().info("Product final price verified successfully");
            ExtentReport.getExtentNode().pass("Product final price verified successfully");
        }
    }

    /**
     * Adding the Product to cart
     */
    public void addToCart() {
        /*Setting up the Extent Report*/
        ExtentReport.createNode("Add to Cart");

        try {
            if (product_options.isDisplayed()) {
                /*Logging and Reporting*/
                Loggers.getLogger().info("Selected a Configurable Product");
                ExtentReport.getExtentNode().info("Selected a Configurable Product");

                product_swatches = new ArrayList<>(10);

                if (swatches_size_list.get(0).isDisplayed()) {
                    int size_option = RandomPicker.numberPicker(swatches_size_list.size());
                    WebElement size_element = swatches_size_list.get(size_option);

                    /*Selecting the Swatches*/
                    MouseActions.moveClickEvent(size_element);
                    product_swatches.add(size_element.getAttribute("option-label"));

                    /*Logging and Reporting*/
                    Loggers.getLogger().info("Swatch '" + product_swatches.get(0) + "' is selected");
                    ExtentReport.getExtentNode().info("Swatch '" + product_swatches.get(0) + "' is selected");
                }

                if (swatches_color_list.get(0).isDisplayed()) {
                    int color_option = RandomPicker.numberPicker(swatches_color_list.size());
                    WebElement color_element = swatches_color_list.get(color_option);

                    /*Selecting the Swatches*/
                    MouseActions.moveClickEvent(color_element);
                    product_swatches.add(color_element.getAttribute("option-label"));

                    /*Logging and Reporting*/
                    Loggers.getLogger().info("Swatch '" + product_swatches.get(1) + "' is selected");
                    ExtentReport.getExtentNode().info("Swatch '" + product_swatches.get(1) + "' is selected");
                }
            }
        } catch (Exception e) {
            Loggers.getLogger().info("Selected a Simple Product");
            ExtentReport.getExtentNode().info("Selected a Simple Product");
        }

        /*Selecting the qty to add*/
        product_quantity = ExcelUtils.getDataMap().get("qty");
        quantity.sendKeys(Keys.DELETE);
        quantity.sendKeys(product_quantity);

        /*Click on Add to Cart button*/
        MouseActions.moveClickEvent(add_cart_button);

        /*Verifying the Success Message*/
        if (add_success.isDisplayed()) {
            Assert.assertEquals(add_success.getText(), "shopping cart");
            Loggers.getLogger().info("Product Successfully added to Cart");
            ExtentReport.getExtentNode().pass("Product Successfully added to Cart");
        }
    }

}
