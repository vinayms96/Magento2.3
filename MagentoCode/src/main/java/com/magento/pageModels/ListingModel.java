package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.pickers.RandomPicker;
import com.magento.project_setup.TestNGBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListingModel extends TestNGBase {
    private static String list_product_name;
    private static String list_product_final_price;
    private static String list_product_old_price;
    private static int product_id;

    @FindBy(css = ".product-item")
    private List<WebElement> product_list;
    @FindBy(css = ".product-item-link")
    private List<WebElement> product_item_link;
    @FindBy(xpath = "//span[@data-price-type='finalPrice']")
    private List<WebElement> final_price_list;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']")
    private List<WebElement> old_price_list;
    @FindBy(xpath = "//button[@type='submit']")
    private List<WebElement> submit_button;
    @FindBy(xpath = "//div[@data-ui-id='message-success']/div/a")
    private WebElement add_success;

    /**
     * @param driver - Webdriver element
     */
    public ListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @return - Get Product Name
     */
    public static String getList_product_name() {
        return list_product_name;
    }

    /**
     * @return - Get the Product Old price
     */
    public static String getList_product_old_price() {
        return list_product_old_price;
    }

    /**
     * @return - Get Product final price
     */
    public static String getList_product_final_price() {
        return list_product_final_price;
    }

    /**
     * Fetch the Product Name, Old Price(If present) and Final Price
     */
    public void fetchProductDetails() {
        // Setting ExtentReports
        ExtentReport.createNode("Fetch product details in Listing page");

        // Pick random product number
        product_id = RandomPicker.numberPicker(product_list.size());

        // Fetching the Product name
        list_product_name = product_item_link.get(product_id).getText();
        Loggers.getLogger().info("Product '" + list_product_name + "' is picked");
        ExtentReport.getExtentNode().pass("Product '" + list_product_name + "' is picked");

        // Fetching Old Price if present
        try {
            if (old_price_list.get(product_id).isDisplayed()) {
                // Fetching the Old Price in listing page
                list_product_old_price = old_price_list.get(product_id).findElement(By.className("price")).getText();
                Loggers.getLogger().info("Product Old Price: " + list_product_old_price);
                ExtentReport.getExtentNode().pass("Product Old Price: " + list_product_old_price);

                // Fetching the Final Price in listing page
                list_product_final_price = final_price_list.get(product_id).findElement(By.className("price")).getText();
                Loggers.getLogger().info("Product Final Price: " + list_product_final_price);
                ExtentReport.getExtentNode().pass("Product Final Price: " + list_product_final_price);
            }
        } catch (Exception e) {
            // Fetching Final Price if Old Price not present
            try {
                if (final_price_list.get(product_id).isDisplayed()) {
                    list_product_final_price = final_price_list.get(product_id).findElement(By.className("price")).getText();
                    Loggers.getLogger().info("Product Final Price: " + list_product_final_price);
                    ExtentReport.getExtentNode().pass("Product Final Price: " + list_product_final_price);
                }
            } catch (Exception er) {
                Loggers.getLogger().error("Could not fetch the Product Price in listing page");
                ExtentReport.getExtentNode().pass("Could not fetch the Product Price in listing page");
            }
        }
    }

    /**
     * Pick a product from Listing page randomly
     * Fetching the Product Name along with Final and Old Prices(if available)
     */
    public void pickProduct(WebDriver driver) {
        // Setting ExtentReports
        ExtentReport.createNode("Pick Product from Listing");

        // Clicking on the Product name
        MouseActions.moveClickEvent(driver, product_item_link.get(product_id));
        Loggers.getLogger().info("Clicked on '" + list_product_name + "' product");
        ExtentReport.getExtentNode().pass("Clicked on '" + list_product_name + "' product");
    }

    /**
     * Adding the Product randomly from listing page
     */
    public void addCartListing(WebDriver driver) {

        // Setting ExtentReports
        ExtentReport.createNode("Add to Cart from listing");

        // Moving over the Product
        MouseActions.jsScrollViewEvent(driver, product_list.get(product_id).findElement(By.tagName("img")));

        // Picking Random product
        WebElement select_product = product_list.get(product_id);
        List<WebElement> list_product_swatches = select_product.findElements(By.cssSelector(".swatch-attribute"));

        // Selecting the Random Config options
        for (WebElement swatches_per_product : list_product_swatches) {

            List<WebElement> every_swatch_options = swatches_per_product.findElements(By.cssSelector(".swatch-option"));

            // Picking random Swatch Option
            int swatch_option = RandomPicker.numberPicker(every_swatch_options.size());

            // Clicking on Swatch Options
            every_swatch_options.get(swatch_option).click();

            // Logging and Reporting
            Loggers.getLogger().info("Selected '" + every_swatch_options.get(swatch_option).getAttribute("option-label") + "' swatch option");
            ExtentReport.getExtentNode().pass("Selected '" + every_swatch_options.get(swatch_option).getAttribute("option-label") + "' swatch option");

        }

        // Clicking on Add to Cart button
        submit_button.get(product_id + 1).click();
        Loggers.getLogger().info("Clicked on the Add to Cart button");

        // Verifying the Success Message
        if (add_success.isDisplayed()) {
            Assert.assertEquals(add_success.getText(), "shopping cart");
            Loggers.getLogger().info("Product Successfully added to Cart");
            ExtentReport.getExtentNode().pass("Product Successfully added to Cart");
        }
    }

}
