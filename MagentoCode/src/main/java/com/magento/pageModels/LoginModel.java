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
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LoginModel {

    @FindBy(xpath = "(//ul[@class='header links']/li/a)[1]")
    private static WebElement login_link;
    @FindBy(xpath = "//div/input[@id='email']")
    private static WebElement email_id;
    @FindBy(xpath = "(//div/input[@id='pass'])[1]")
    private static WebElement password;
    @FindBy(xpath = "(//div/button[@id='send2'])[1]")
    private static WebElement submit;
    @FindBy(css = ".greet.welcome .logged-in")
    private static WebElement user_name;

    /**
     * @param driver - Webdriver element
     */
    public LoginModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Fill login form and Login
     */
    public void fillLoginForm(WebDriver driver) {
        /*Creating Extent Node*/
        ExtentReport.createNode("Enter the Login Details");

        /*Click on Login link*/
        WebdriverWait.waitTillClickable(login_link, 5);
        login_link.click();

        /*Entering the form details*/
        email_id.sendKeys(ExcelUtils.getDataMap().get("email_id"));
        Loggers.getLogger().info("Entered the Email id");
        ExtentReport.getExtentNode().pass("Entered the user Email id");

        password.sendKeys(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Entered the user Password");
        ExtentReport.getExtentNode().pass("Entered the user Password");

        MouseActions.moveClickEvent(driver, submit);
        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().pass("Clicked on Submit button");

        /*Verifying if user is logged in*/
        WebdriverWait.waitTillVisibility(user_name, 5);

        String fullName = ExcelUtils.getDataMap().get("first_name") + " " +
                ExcelUtils.getDataMap().get("last_name");

        try {
            Assert.assertTrue(user_name.getText().contains(fullName));
            Loggers.getLogger().info("User logged in Successfully");
            ExtentReport.getExtentNode().pass("User logged in Successfully");
        } catch (Exception e) {
            Loggers.getLogger().error("User could not be logged in");
            ExtentReport.getExtentNode().fail("User could not be logged in");
        }
    }

}
