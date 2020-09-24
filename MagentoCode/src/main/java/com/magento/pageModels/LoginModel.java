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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LoginModel extends TestNGBase {

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
        // Creating Extent Node
        ExtentReport.createNode("Login Account: Enter the Login Details");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Click on Login link
        wait.until(ExpectedConditions.elementToBeClickable(login_link));
        login_link.click();

        // Entering the form details
        email_id.sendKeys(ExcelUtils.getDataMap().get("email_id"));
        Loggers.getLogger().info("Entered the Email id");
        ExtentReport.getExtentNode().pass("Entered the user Email id");

        password.sendKeys(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Entered the user Password");
        ExtentReport.getExtentNode().pass("Entered the user Password");

        MouseActions.moveClickEvent(driver, submit);
        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().pass("Clicked on Submit button");

        // Verifying if user is logged in
        wait.until(ExpectedConditions.visibilityOf(user_name));

    }

    public boolean verifyLogin() {
        // Creating Extent Node
        ExtentReport.createNode("Login Account: Verify the Login Details");

        String fullName = ExcelUtils.getDataMap().get("first_name") + " " +
                ExcelUtils.getDataMap().get("last_name");
        Loggers.getLogger().info("Username: " + fullName);
        ExtentReport.getExtentNode().info("Username: " + fullName);

        try {
            Thread.sleep(2000);
            Assert.assertTrue(user_name.getText().contains(fullName));
            Loggers.getLogger().info("User logged in Successfully");
            return true;
        } catch (Exception e) {
            Loggers.getLogger().error("User could not be logged in");
        }
        return false;
    }

}
