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

public class LoginModel {

    @FindBy(xpath = "(//ul[@class='header links']/li/a)[1]")
    private static WebElement login_link;
    @FindBy(xpath = "//div/input[@id='email']")
    private static WebElement email_id;
    @FindBy(xpath = "(//div/input[@id='pass'])[1]")
    private static WebElement password;
    @FindBy(xpath = "(//div/button[@id='send2'])[1]")
    private static WebElement submit;
    @FindBy(xpath = "//h1[@class='page-title']/span")
    private static WebElement page_title;


    public LoginModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void fillLoginForm() {
        /*Creating Extent Node*/
        ExtentReport.createNode("Enter the Login Details");

        /*Click on Login link*/
        WebdriverWait.waitTillClickable(login_link, 5);
        login_link.click();

        /*Entering the form details*/
        email_id.sendKeys(ExcelUtils.getDataMap().get("email_id"));
        password.sendKeys(ExcelUtils.getDataMap().get("password"));
        MouseActions.moveClickEvent(submit);

        /*Verifying if user is logged in*/
        WebdriverWait.waitTillVisibility(page_title, 5);
        if (page_title.getText().equals("My Account")) {
            Loggers.getLogger().info("User logged in Successfully");
            ExtentReport.getExtentNode().pass("User logged in Successfully");
        } else {
            Loggers.getLogger().error("User could not be logged in");
            ExtentReport.getExtentNode().fail("User could not be logged in");
        }
    }

}
