package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.project_setup.TestNGBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartModel extends TestNGBase {

    private String cart_product_name;
    private ArrayList<String> cart_swatch;
    private String cart_final_price;
    private String cart_subtotal_price;
    private String cart_product_qty;

    @FindBy(css = ".cart.item .item-info")
    private List<WebElement> product_list;
    @FindBy(xpath = "//tbody[@class='cart item'] //strong/a")
    private List<WebElement> product_name;
    @FindBy(xpath = "//td[@class='col item']")
    private List<WebElement> swatch_options;
    @FindBy(css = ".price .cart-price .price")
    private List<WebElement> product_final_price;
    @FindBy(css = ".subtotal .cart-price .price")
    private List<WebElement> product_subtotal_price;
    @FindBy(css = ".control.qty .qty")
    private List<WebElement> product_qty;

    /**
     * @param driver - Webdriver Element
     */
    public CartModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Fetching the Cart product details
     */
    public void fetchProductDetails(WebDriver driver) {
        /*Setting up Extent Node*/
        ExtentReport.createNode("Fetch Cart Product Details");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOfAllElements(product_list));
        cart_swatch = new ArrayList<>(10);

        for (WebElement element :
                product_list) {

            /*Fetching the Product name*/
            for (WebElement product_object :
                    product_name) {
                Loggers.getLogger().info(product_object.getAttribute("innerHTML"));
                cart_product_name = product_object.getText();
                Loggers.getLogger().info("Fetched the Product Name");
                ExtentReport.getExtentNode().pass("Fetched the Product Name");
            }

            /*Fetching the Product Swatch options*/
            for (WebElement swatch :
                    swatch_options) {
                String swatch_text = (String) ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].text;", swatch);
                cart_swatch.add(swatch_text);
//                cart_swatch.add(swatch.findElement(By.xpath("//dl/dd")).getText());
            }
            Loggers.getLogger().info("Fetched the Product swatches");
            ExtentReport.getExtentNode().pass("Fetched the product swatches");

            /*Fetching Product Final Price*/
            for (WebElement final_price :
                    product_final_price) {
                cart_final_price = final_price.getText();
            }
            Loggers.getLogger().info("Fetched the product final price");
            ExtentReport.getExtentNode().pass("Fetched the product final price");

            /*Fetching the Product Subtotal*/
            for (WebElement subtotal :
                    product_subtotal_price) {
                cart_subtotal_price = subtotal.getText();
            }
            Loggers.getLogger().info("Fetched the product subtotal");
            ExtentReport.getExtentNode().pass("Fetched the product subtotal");

            /*Fetching the Product qty*/
            for (WebElement qty :
                    product_qty) {
                cart_product_qty = qty.getAttribute("value");
            }
            Loggers.getLogger().info("Fetched the product qty added to cart");
            ExtentReport.getExtentNode().pass("Fetched the product qty added to cart");

        }

    }

    /**
     * Verifying the Product details of products added to cart
     */
    public void verifyProductDetails() {
        /*Setting up Extent Node*/
        ExtentReport.createNode("Verify Cart Product Details");

        /*Verifying product details*/
        Assert.assertEquals(ListingModel.getList_product_name(), cart_product_name);
        Assert.assertEquals(ProductModel.getProduct_final_price(), cart_final_price);
        Assert.assertEquals(ProductModel.getProduct_quantity(), cart_product_qty);
//        for (int swatch = 0; swatch < cart_swatch.size(); swatch++) {
//            Assert.assertEquals(cart_swatch.get(swatch), ProductModel.getProduct_swatches().get(swatch));
//        }

        /*Logging and Extent Reports*/
        Loggers.getLogger().info("Verified the Product details added to cart");
        ExtentReport.getExtentNode().pass("Verified the Product details added to cart");
    }

}
