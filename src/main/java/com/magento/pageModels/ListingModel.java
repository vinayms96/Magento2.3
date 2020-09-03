package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.modules.WebdriverWait;
import com.magento.pickers.RandomPicker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListingModel {
    private static String list_product_name;
    private static String list_product_final_price;
    private static String list_product_old_price;

    @FindBy(css = ".product-item")
    private List<WebElement> product_list;
    @FindBy(css = ".product-item-link")
    private List<WebElement> product_item_link;
    @FindBy(xpath = "//span[@data-price-type='finalPrice']")
    private List<WebElement> final_price_list;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']")
    private List<WebElement> old_price_list;

    /**
     * Constructor
     *
     * @param driver
     */
    public ListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Pick a product from Listing page randomly
     * Fetching the Product Name along with Final and Old Prices(if available)
     */
    public void pickProduct() {
        /*Setting ExtentReports*/
        ExtentReport.createNode("Pick Product from listing");

        int product_id = RandomPicker.numberPicker(product_list.size());

        list_product_name = product_item_link.get(product_id).getText();
        Loggers.getLogger().info("Product '" + list_product_name + "' is picked");
        ExtentReport.getExtentNode().pass("Product '" + list_product_name + "' is picked");

        try {
            if (old_price_list.get(product_id).isDisplayed()) {
                list_product_old_price = old_price_list.get(product_id).findElement(By.className("price")).getText();
                Loggers.getLogger().info("Fetched the Product old price");
                ExtentReport.getExtentNode().pass("Fetched the Product old price");
            }
        } catch (Exception e) {
            if (final_price_list.get(product_id).isDisplayed()) {
                list_product_final_price = final_price_list.get(product_id).findElement(By.className("price")).getText();
                Loggers.getLogger().info("Fetched the Product final price");
                ExtentReport.getExtentNode().pass("Fetched the Product final price");
            }
        }

        MouseActions.moveClickEvent(product_item_link.get(product_id));
        Loggers.getLogger().info("Clicked on '" + list_product_name + "' product");
        ExtentReport.getExtentNode().pass("Clicked on '" + list_product_name + "' product");
    }

    public static String getList_product_name() {
        return list_product_name;
    }

    public static String getList_product_old_price() {
        return list_product_old_price;
    }

    public static String getList_product_final_price() {
        return list_product_final_price;
    }

}
