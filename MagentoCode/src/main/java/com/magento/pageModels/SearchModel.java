package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class SearchModel extends TestNGBase {
    @FindBy(id = "search")
    private WebElement search_box;
    @FindBy(xpath = "//h1/span")
    private WebElement search_heading;

    /**
     * @param driver - Webdriver element
     */
    public SearchModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Entering search text in search field and Searching
     */
    public void searchText() {
        // Setting the extent reports
        ExtentReport.createNode("Search for product");

        // Getting the Search query
        String searchQuery = ExcelUtils.getDataMap().get("search_text");

        if (search_box.isDisplayed()) {
            search_box.sendKeys(searchQuery);
            search_box.sendKeys(Keys.ENTER);

            // Verifying the Search page heading
            if (search_heading.isDisplayed()) {
                Assert.assertTrue(search_heading.getText().contains(searchQuery));
            }

            // Logging and Reporting
            Loggers.getLogger().info("Search result is displayed for '" + searchQuery + "'");
            ExtentReport.getExtentNode().pass("Search result is displayed for '" + searchQuery + "'");

        } else {

            Loggers.getLogger().error("Could not find the Search Box");
            ExtentReport.getExtentNode().fail("Could not find the Search Box");

        }
    }
}
