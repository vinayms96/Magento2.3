package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.modules.WebdriverWait;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomepageModel {

    @FindBy(xpath = "//nav/ul/li")
    private static List<WebElement> menu_links;
    @FindBy(xpath = "/ul/li")
    private List<WebElement> sub_menu_links;
    @FindBy(xpath = "(//nav/ul/li)[3]/a/span[2]")
    private static WebElement main_menu;

    public HomepageModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void selectMenus() {
        /*Creating Extent Node*/
        ExtentReport.createNode("Hover and Select Menus");

        WebElement outer_nav = null;
        WebElement inner_nav;
        List<WebElement> nav_sub_menu;
        String main_category = ExcelUtils.getDataMap().get("main_category");
        String sub_category_1 = ExcelUtils.getDataMap().get("sub_category_1");

        try {
            for (int link_id = 0; link_id < menu_links.size(); link_id++) {
                outer_nav = menu_links.get(link_id);
                if (outer_nav.findElement(By.xpath("//a/span[2]")).isDisplayed()) {
                    WebElement menu_tag = outer_nav.findElement(By.xpath("//a/span[2]"));
                    if (menu_tag.getText().equalsIgnoreCase(main_category)) {
//                        MouseActions.moveOverEvent(outer_nav.findElement(By.tagName("a")));
//                        MouseActions.jsHoverEvent(outer_nav.findElement(By.tagName("a")));
                        WebdriverWait.waitTillVisibility(outer_nav.findElement(By.xpath("//a/span[1]")));
                        MouseActions.robotMoveOverPosition(outer_nav.findElement(By.xpath("//a/span[1]")));
                        break;
                    }
                }
            }
            Loggers.getLogger().info("Hovered over menu '" + main_category + "'");
        } catch (Exception e) {
            Loggers.getLogger().error("Could not find the Category to Hover");
            ExtentReport.getExtentNode().fail("Could not find the Category to Hover");
        }

        try {
            nav_sub_menu = outer_nav.findElements((By) sub_menu_links);
            for (int sub_link_id = 0; sub_link_id < nav_sub_menu.size(); sub_link_id++) {
                inner_nav = nav_sub_menu.get(sub_link_id);
                if (inner_nav.findElement(By.xpath("//a/span[2]")).isDisplayed()) {
                    WebElement sub_menu_tag = inner_nav.findElement(By.xpath("//a/span[2]"));
                    if (sub_menu_tag.getText().equalsIgnoreCase(sub_category_1)) {
//                        MouseActions.moveOverEvent(inner_nav.findElement(By.tagName("a")));
//                        MouseActions.jsHoverEvent(inner_nav.findElement(By.tagName("a")));
                        MouseActions.robotMoveOverPosition(inner_nav.findElement(By.tagName("a")));
                        clickMenus(inner_nav.findElement(By.tagName("a")),sub_category_1);
                        System.out.println("Came Here");
                        break;
                    }
                }
            }
            Loggers.getLogger().info("Hovered over menu '" + sub_category_1 + "'");
        } catch (Exception e) {
            Loggers.getLogger().error("Could not find the Sub Category to Hover");
            ExtentReport.getExtentNode().fail("Could not find the Sub Category to Hover");
        }
    }

    public void clickMenus(WebElement menu_element, String category_name) {
        MouseActions.moveClickEvent(menu_element);
        ExtentReport.getExtentNode().pass("Click on the " + category_name);
        Loggers.getLogger().info("Click on the " + category_name);
    }
}
