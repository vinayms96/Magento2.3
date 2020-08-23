package com.magento.modules;

import com.magento.browser_setup.BrowserSetup;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebdriverWait extends BrowserSetup {
    private static WebDriverWait webDriverWait;

    public static void waitTillVisibility(WebElement element) {
        webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitTillClickable(WebElement element) {
        webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
