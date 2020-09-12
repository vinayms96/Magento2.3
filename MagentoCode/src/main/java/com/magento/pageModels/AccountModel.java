package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class AccountModel {

    @FindBy(css = ".messages .success")
    private WebElement acc_success_message;
    @FindBy(css = ".messages .error")
    private WebElement acc_error_message;
    @FindBy(css = ".messages")
    private WebElement messages;

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

}
