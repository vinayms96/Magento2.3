package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.modules.WebdriverWait;
import com.magento.utilities.Property;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AccountModel {

    @FindBy(css = ".messages .success")
    private WebElement acc_success_message;
    @FindBy(css = ".messages .error")
    private WebElement acc_error_message;
    @FindBy(css = ".messages")
    private WebElement messages;
    @FindBy(css = ".action.switch")
    private WebElement account_dropdown;
    @FindBy(xpath = "//div[@class='panel header'] //div[@class='customer-menu']/ul/li/a")
    private List<WebElement> account_drop_list;
    @FindBy(css = ".base")
    private WebElement logout_success;

    public AccountModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void accountCreateVerify() {
        /*Setting Extent reports*/
        ExtentReport.createNode("Account Creation Verification");

        try {

            /*Verifying the account is created*/
            Assert.assertTrue(acc_success_message.findElement(By.xpath("//div"))
                    .getAttribute("innerHTML").contains("Thank you for registering"));
            Loggers.getLogger().info("Account is created Successfully");
            ExtentReport.getExtentNode().pass("Account is created Successfully");

        } catch (Exception er) {

            /*Checking if the email id already exists*/
            Assert.assertTrue(acc_error_message.findElement(By.xpath("//div"))
                    .getAttribute("innerHTML").contains("There is already an account with this email address."));
            Loggers.getLogger().error("There is already an account with this email address.");
            ExtentReport.getExtentNode().fail("There is already an account with this email address.");

        } catch (AssertionError assertionError) {

            Loggers.getLogger().error("Success message was not displayed.");
            ExtentReport.getExtentNode().fail("Success message was not displayed.");

        }
    }

    public void clickAccountDropdown(WebDriver driver) {
        ExtentReport.createNode("Select Account dropdown options");

        MouseActions.moveClickEvent(driver, account_dropdown);
        Loggers.getLogger().info("Clicked on Account Dropdown");
        ExtentReport.getExtentNode().pass("Clicked on Account Dropdown");
    }

    public void selectDropOptions(WebDriver driver, String dropdownOption) {
        Iterator<WebElement> options = account_drop_list.iterator();
        while (options.hasNext()) {
            WebElement element = options.next();
            Loggers.getLogger().error(element.getAttribute("href"));
            if (element.getText().contains(dropdownOption)) {
                clickAccountDropdown(driver);
                driver.get(element.getAttribute("href"));
            }
        }
        Loggers.getLogger().info("Clicked on '" + dropdownOption + "' button");
        ExtentReport.getExtentNode().pass("Clicked on '" + dropdownOption + "' button");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebdriverWait.waitTillPagRefreshed(logout_success, 10);

        /*Verifying the Logout*/
        Assert.assertEquals(driver.getCurrentUrl(), (Property.getProperty("url") + "/customer/account/logoutSuccess/"));
        Assert.assertEquals(logout_success.getText(), "You are signed out");

        /*Logging and Reporting*/
        Loggers.getLogger().info("User logged out successfully");
        ExtentReport.getExtentNode().pass("User logged out successfully");
    }

}
