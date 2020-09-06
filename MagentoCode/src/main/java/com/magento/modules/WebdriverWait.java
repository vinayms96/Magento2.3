package com.magento.modules;

import com.magento.browser_setup.BrowserSetup;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebdriverWait extends BrowserSetup {
    private static WebDriverWait webDriverWait;

    public static void waitTillVisibility(WebElement element, int timeOutInSeconds) {
        webDriverWait = new WebDriverWait(driver, timeOutInSeconds);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitTillClickable(WebElement element, int timeOutInSeconds) {
        webDriverWait = new WebDriverWait(driver, timeOutInSeconds);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitTillListVisible(List<WebElement> element, int timeOutInSeconds) {
        webDriverWait = new WebDriverWait(driver, timeOutInSeconds);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(element));
    }
}
