package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class SearchModel {
    @FindBy(id = "search")
    private WebElement search_box;
    @FindBy(xpath = "//h1/span")
    private WebElement search_heading;

    public SearchModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void searchText() {
        String searchQuery = ExcelUtils.getDataMap().get("search_text");

        try {
            if (search_box.isDisplayed()) {
                search_box.sendKeys(searchQuery);
                search_box.sendKeys(Keys.ENTER);

                if (search_heading.isDisplayed()) {
                    Assert.assertTrue(search_heading.getText().contains(searchQuery));
                }

                Loggers.getLogger().info("Search result is displayed for '" + searchQuery + "'");
                ExtentReport.getExtentTest().pass("Search result is displayed for '" + searchQuery + "'");
            } else {
                Loggers.getLogger().error("Could not find the Search Box");
                ExtentReport.getExtentTest().fail("Could not find the Search Box");
            }
        } catch (AssertionError e) {
            Loggers.getLogger().error("The search query doesn't match the result");
            ExtentReport.getExtentTest().fail("The search query doesn't match the result");
        }

    }

}
