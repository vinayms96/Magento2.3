package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.modules.WebdriverWait;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SignupModel {

    /**
     * Page Element references
     */
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
     * Constructor
     *
     * @param driver
     */
    public SignupModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Click on Create Account link
     */
    public void clickCreateAccountLink() {
        /*Creating Extent Node*/
        ExtentReport.createNode("Click Create Account Link");

        /*Clicking on link*/
        MouseActions.moveClickEvent(create_account_link);

        /*Logging and Reporting*/
        Loggers.getLogger().info("Create account link clicked");
        ExtentReport.getExtentNode().pass("Create account link clicked");
    }

    public void fillCustomerForm() {
        /*Creating Extent Node*/
        ExtentReport.createNode("Fill Customer Form");

        /*Filling the form fields*/
        first_name.sendKeys(ExcelUtils.getDataMap().get("first_name"));
        ExtentReport.getExtentNode().pass("Entered the Firstname");
        last_name.sendKeys(ExcelUtils.getDataMap().get("last_name"));
        ExtentReport.getExtentNode().pass("Entered the Lastname");
//        if (Property.getProperty("is_subscribed").equals(1)) {
//            is_subscribed.click();
//            ExtentReport.getExtentNode().pass("Check the Subscriber Newsletter");
//        }
        email_address.sendKeys(ExcelUtils.getDataMap().get("email_id"));
        ExtentReport.getExtentNode().pass("Entered the Email id");
        password.sendKeys(ExcelUtils.getDataMap().get("password"));
        ExtentReport.getExtentNode().pass("Entered the Password");
        password_confirmation.sendKeys(ExcelUtils.getDataMap().get("password"));
        ExtentReport.getExtentNode().pass("Entered the Confirmation Password");
        MouseActions.moveClickEvent(submit);

        /*Checking if the email id already exists*/
        WebdriverWait.waitTillVisibility(messages);
        if (messages.getAttribute("innerHTML").contains("There is already an account with this email address.")) {
            Loggers.getLogger().error("There is already an account with this email address.");
            ExtentReport.getExtentNode().fail("There is already an account with this email address.");
        } else {
            ExtentReport.getExtentNode().pass("User account successfully created");
        }
    }
}
