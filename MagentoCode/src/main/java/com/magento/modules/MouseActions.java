package com.magento.modules;

import com.magento.loggers.Loggers;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;

public class MouseActions {
    private static Actions action;
    private static Robot robot;
    private static JavascriptExecutor jscript;
    private static Point position;

    /**
     * @param driver
     * @param element
     */
    public static void moveClickEvent(WebDriver driver, WebElement element) {
        action = new Actions(driver);
        action.moveToElement(element).click().build().perform();
    }

    /**
     * @param driver
     * @param element
     */
    public static void moveOverEvent(WebDriver driver, WebElement element) {
        action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    /**
     * @param driver
     * @param element
     */
    public static void moveOverPosition(WebDriver driver, WebElement element) {
        action = new Actions(driver);
        position = element.getLocation();
        action.moveToElement(element).moveByOffset(position.getX(), position.getY()).perform();
    }

    /**
     * @param driver
     * @param element
     */
    public static void jsHoverEvent(WebDriver driver, WebElement element) {
        jscript = (JavascriptExecutor) driver;
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

    /**
     * @param driver
     * @param element
     */
    public static void jsClickEvent(WebDriver driver, WebElement element) {
        jscript = (JavascriptExecutor) driver;
        jscript.executeScript("arguments[0].click();", element);
    }

    /**
     * @param driver
     * @param element
     */
    public static void jsScrollViewEvent(WebDriver driver, WebElement element) {
        jscript = (JavascriptExecutor) driver;
        jscript.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * @param element
     */
    public static void robotMoveOverPosition(WebElement element) {
        position = element.getLocation();
        try {
            robot = new Robot();
            robot.mouseMove(position.getX(), position.getY());
        } catch (AWTException e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

}
