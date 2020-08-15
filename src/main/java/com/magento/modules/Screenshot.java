package com.magento.modules;

import com.magento.browser_setup.BrowserSetup;
import com.magento.loggers.Loggers;
import com.magento.path.Constants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

public class Screenshot implements Constants {
    private static BrowserSetup webDriver = new BrowserSetup();
    private static TakesScreenshot shot;
    private static String screenshotPath = shot_path + DatePicker.getDateTime() + "/";

    public static String getScreenshotBase64() {
        shot = (TakesScreenshot) webDriver.driver;
        String screenShotBase64 = shot.getScreenshotAs(OutputType.BASE64);
        return screenShotBase64;
    }

    public static void getScreenshot(String errorMessage) {
        shot = (TakesScreenshot) webDriver.driver;
        File srcScreenshot = shot.getScreenshotAs(OutputType.FILE);
        File destScreenshot = new File(screenshotPath + errorMessage + ".jpg");
        try {
            FileUtils.copyFile(srcScreenshot, destScreenshot);
        } catch (Exception e) {
            Loggers.getLogger().error(e.getCause());
        }
    }
}
