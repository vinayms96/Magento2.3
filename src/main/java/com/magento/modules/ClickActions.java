package com.magento.modules;

import com.magento.browser_setup.BrowserSetup;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ClickActions {
    private static BrowserSetup browserSetup = new BrowserSetup();
    private static Actions action = new Actions(browserSetup.driver);
    ;

    public static void moveClick(WebElement element) {
        action.moveToElement(element).click().build().perform();
    }

    public static void moveOver(WebElement element) {
        action.moveToElement(element).build().perform();
    }
}
