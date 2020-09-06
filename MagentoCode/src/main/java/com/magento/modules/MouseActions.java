package com.magento.modules;

import com.magento.browser_setup.BrowserSetup;
import com.magento.loggers.Loggers;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;

import java.awt.*;

public class MouseActions extends BrowserSetup {
    private static Actions action = new Actions(driver);
    private static Robot robot;
    private static JavascriptExecutor jscript = (JavascriptExecutor) driver;
    private static Point position;

    public static void moveClickEvent(WebElement element) {
        action.moveToElement(element).click().build().perform();
    }

    public static void moveOverEvent(WebElement element) {
        action.moveToElement(element).build().perform();
    }

    public static void moveOverPosition(WebElement element) {
        position = element.getLocation();
        action.moveToElement(element).moveByOffset(position.getX(), position.getY()).build().perform();
    }

    public static void jsHoverEvent(WebElement element) {
        String hoverEvent = "if(document.createEvent) {" +
                "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initEvent('mouseover', true, false); " +
                "arguments[0].dispatchEvent(evObj);" +
                "} " +
                "else if(document.createEventObject) { " +
                "arguments[0].fireEvent('onmouseover');" +
                "}";
        jscript.executeScript(hoverEvent, element);
    }

    public static void jsClickEvent(WebElement element) {
        jscript.executeScript("arguments[0].click();", element);
    }

    public static void jsScrollViewEvent(WebElement element) {
        jscript.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void robotMoveOverPosition(WebElement element) {
        position = element.getLocation();
        try {
            robot = new Robot();
            robot.mouseMove(position.getX(),position.getY());
        } catch (AWTException e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

}
