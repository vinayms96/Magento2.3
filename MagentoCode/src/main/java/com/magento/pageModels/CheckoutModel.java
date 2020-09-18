package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.modules.WebdriverWait;
import com.magento.pickers.RandomPicker;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckoutModel {
    private Select select;

    @FindBy(css = ".authentication-wrapper .action")
    private WebElement sign_in_link;
    @FindBy(id = "login-email")
    private WebElement email_field;
    @FindBy(id = "login-password")
    private WebElement password_field;
    @FindBy(xpath = "//input[@name='street[0]']")
    private WebElement street_address;
    @FindBy(xpath = "//input[@name='city']")
    private WebElement city;
    @FindBy(xpath = "//select[@name='region_id']")
    private WebElement state;
    @FindBy(xpath = "//select[@name='country_id']")
    private WebElement country;
    @FindBy(xpath = "//input[@name='postcode']")
    private WebElement post_code;
    @FindBy(xpath = "//input[@name='telephone']")
    private WebElement telephone;
    @FindBy(xpath = "(//div[@class='block-content'] //button)[1]")
    private WebElement login_submit;
    @FindBy(css = ".shipping-address-item.selected-item")
    private WebElement selected_ship;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> shipping_rates;
    @FindBy(css = "#shipping-method-buttons-container .button")
    private WebElement next_submit;
    @FindBy(xpath = "//td/input[1]")
    private By select_shipping;
    @FindBy(xpath = "//td[4]")
    private By ship_method_name;
    @FindBy(id = "billing-address-same-as-shipping-checkmo")
    private WebElement set_default_billing;
    @FindBy(css = ".primary .checkout")
    private WebElement place_order;

    /**
     * @param driver - WebDriver element
     */
    public CheckoutModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @param driver - Webdriver element
     *               Verifying the Checkout 1 redirection
     */
    public void verifyCheckout1(WebDriver driver) {
        /*Setting up Extent report*/
        ExtentReport.createNode("Verify Checkout Page 1 Redirect");

        WebdriverWait.waitTillVisibility(sign_in_link, 10);

        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#shipping"));
        Loggers.getLogger().info("Successfully redirected to Checkout page 1");
        ExtentReport.getExtentNode().pass("Successfully redirected to Checkout page 1");
    }

    /**
     * @param driver - Webdriver element
     *               Verifying the Checkout 2 redirection
     */
    public void verifyCheckout2(WebDriver driver) {
        /*Setting up Extent report*/
        ExtentReport.createNode("Verify Checkout Page 2 Redirect");

        WebdriverWait.waitTillVisibility(place_order, 10);

        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#payment"));
        Loggers.getLogger().info("Successfully redirected to Checkout page 2");
        ExtentReport.getExtentNode().pass("Successfully redirected to Checkout page 2");
    }

    /**
     * Signing in from Checkout page
     */
    public void checkoutSignIn(WebDriver driver) {
        /*Setting up Extent node*/
        ExtentReport.createNode("Checkout Sign in");

        MouseActions.moveClickEvent(driver, sign_in_link);

        WebdriverWait.waitTillVisibility(email_field, 10);
        email_field.sendKeys(ExcelUtils.getDataMap().get("email_id"));
        Loggers.getLogger().info("Entered email id in checkout");

        password_field.sendKeys(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Entered password in checkout");

        login_submit.click();
        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().pass("Logged into the User Account successfully");
    }

    /**
     * Select the Shipping Method and click on Next
     */
    public void selectShippingMethod(WebDriver driver) {
        /*Setting up Extent node*/
        ExtentReport.createNode("Select Shipping Method");

        /*Wait till the Shipping Address is present*/
        try {
            WebdriverWait.waitTillVisibility(selected_ship, 7);
        } catch (Exception e) {
            addShippingAddress();
        }

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            Loggers.getLogger().error("Shipping Method Could not be found");
            ExtentReport.getExtentNode().error("Shipping Method Could not be found");
        }

        int ship_count = shipping_rates.size();
        Loggers.getLogger().info(ship_count + " Shipping Method(s) are/is displayed");
        ExtentReport.getExtentNode().info(ship_count + " Shipping Method(s) are/is displayed");

        /*Selecting one of many options*/
        if (ship_count > 1) {
            int one_ship = RandomPicker.numberPicker(ship_count);
            WebElement ship_method = shipping_rates.get(one_ship);

            if (ship_method.findElement(select_shipping).isSelected() == false) {
                ship_method.findElement(select_shipping).click();
                Loggers.getLogger().info("Selected '" + ship_method
                        .findElement(ship_method_name).getText() + "' Shipping Method");
                ExtentReport.getExtentNode().pass("Selected '" + ship_method
                        .findElement(ship_method_name).getText() + "' Shipping Method");
            }
        }

        /*Clicking on Next button*/
        MouseActions.moveClickEvent(driver, next_submit);
        Loggers.getLogger().info("Clicked on Next button");
        ExtentReport.getExtentNode().pass("Clicked on Next button");
    }

    /**
     * Selecting billing address and click on Place Order
     */
    public void clickPlaceOrder(WebDriver driver) {
        /*Setting up Extent Node*/
        ExtentReport.createNode("Select Billing Address & click on Place Order");

        /*Clicking on Default billing if not checked*/
        if(set_default_billing.isSelected() == false) {
            set_default_billing.click();
            Loggers.getLogger().info("Clicked on Same as Shipping Address checkbox");
            ExtentReport.getExtentNode().pass("Clicked on Same as Shipping Address checkbox");
        }

        /*Click on Place Order button*/
        MouseActions.moveClickEvent(driver, place_order);
        Loggers.getLogger().info("Clicked on Place Order button");
        ExtentReport.getExtentNode().pass("Clicked on Place Order button");
    }

    public void addShippingAddress() {
        /*Setting up Extent Node*/
        ExtentReport.createNode("Filling the Shipping Address");

        select = new Select(country);
        select.selectByValue(ExcelUtils.getDataMap().get("country"));
        Loggers.getLogger().info("Entered the Country");
        ExtentReport.getExtentNode().pass("Entered the Country");

        select = new Select(state);
        select.selectByValue(ExcelUtils.getDataMap().get("state"));
        Loggers.getLogger().info("Entered the State");
        ExtentReport.getExtentNode().pass("Entered the State");

        street_address.sendKeys(ExcelUtils.getDataMap().get("street_address"));
        Loggers.getLogger().info("Entered the Street Address");
        ExtentReport.getExtentNode().pass("Entered the Street Address");

        city.sendKeys(ExcelUtils.getDataMap().get("city"));
        Loggers.getLogger().info("Entered the City");
        ExtentReport.getExtentNode().pass("Entered the City");

        post_code.sendKeys(ExcelUtils.getDataMap().get("post_code"));
        Loggers.getLogger().info("Entered the Zip Code");
        ExtentReport.getExtentNode().pass("Entered the Zip Code");

        telephone.sendKeys(ExcelUtils.getDataMap().get("mobile_number"));
        Loggers.getLogger().info("Entered the Mobile Number");
        ExtentReport.getExtentNode().pass("Entered the Mobile Number");
    }
}
