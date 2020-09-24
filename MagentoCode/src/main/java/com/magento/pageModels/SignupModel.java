package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SignupModel extends TestNGBase {

    @FindBy(css = ".header .links")
    private static WebElement header_links;
    @FindBy(xpath = "//ul[@class='header links']/li[3]/a")
    private static WebElement create_account_link;
    @FindBy(xpath = "//div/input[@id='firstname']")
    private static WebElement first_name;
    @FindBy(xpath = "//div/input[@id='lastname']")
    private static WebElement last_name;
    @FindBy(xpath = "//div/input[@id='is_subscribed']")
    private static WebElement is_subscribed;
    @FindBy(xpath = "//div/input[@id='email_address']")
    private static WebElement email_address;
    @FindBy(xpath = "//div/input[@id='password']")
    private static WebElement password;
    @FindBy(xpath = "//div/input[@id='password-confirmation']")
    private static WebElement password_confirmation;
    @FindBy(css = ".action.submit.primary")
    private static WebElement submit;
    @FindBy(xpath = "//div[@data-ui-id='message-error']/div")
    private static WebElement messages;

    /**
     * @param driver - Webdriver element
     */
    public SignupModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Click on Create Account link
     */
    public void clickCreateAccountLink(WebDriver driver) {
        // Creating Extent Node
        ExtentReport.createNode("Create Account: Click Create Account Link");

        // Clicking on link
        MouseActions.moveClickEvent(driver, create_account_link);

        // Logging and Reporting
        Loggers.getLogger().info("Create account link clicked");
        ExtentReport.getExtentNode().pass("Create account link clicked");
    }

    /**
     * Fill the Create account form and hit Submit
     */
    public void fillCustomerForm(WebDriver driver) {
        // Creating Extent Node
        ExtentReport.createNode("Create Account: Fill Customer Form");

        // Filling the form fields
        first_name.sendKeys(ExcelUtils.getDataMap().get("first_name"));
        Loggers.getLogger().info("Entered the Firstname");
        ExtentReport.getExtentNode().pass("Entered the Firstname");

        last_name.sendKeys(ExcelUtils.getDataMap().get("last_name"));
        Loggers.getLogger().info("Entered the Lastname");
        ExtentReport.getExtentNode().pass("Entered the Lastname");

//        if (Property.getProperty("is_subscribed").equals(1)) {
//            is_subscribed.click();
//            ExtentReport.getExtentSubNode().pass("Check the Subscriber Newsletter");
//        }

        email_address.sendKeys(ExcelUtils.getDataMap().get("email_id"));
        Loggers.getLogger().info("Entered the Email id");
        ExtentReport.getExtentNode().pass("Entered the Email id");

        password.sendKeys(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Entered the Password");
        ExtentReport.getExtentNode().pass("Entered the Password");

        password_confirmation.sendKeys(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Entered the Confirmation Password");
        ExtentReport.getExtentNode().pass("Entered the Confirmation Password");

        MouseActions.moveClickEvent(driver, submit);
        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().pass("Clicked on Submit button");

    }
}
