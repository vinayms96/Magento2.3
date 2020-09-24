package com.magento.modules;

import com.magento.project_setup.TestNGBase;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.pickers.DatePicker;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Screenshot implements Constants {
    private static TakesScreenshot shot;
    private static String screenshotPath = SHOT_PATH + DatePicker.getDateTime() + "/";

    /**
     * Takes screenshot and convert it to Base64
     *
     * @return Base64 String
     */
    public static String getScreenshotBase64(WebDriver driver) {
        // Setting the Loggers
        Loggers.setLogger(Screenshot.class.getName());

        shot = (TakesScreenshot) driver;
        String screenShotBase64 = shot.getScreenshotAs(OutputType.BASE64);
        Loggers.getLogger().info("Base64 screenshot taken");
        return screenShotBase64;
    }

    /**
     * Takes screenshot and copying it to file and naming the image name to errorMessage.
     *
     * @param errorMessage
     */
    public static void getScreenshot(String errorMessage, WebDriver driver) {
        // Setting the Loggers
        Loggers.setLogger(Screenshot.class.getName());

        shot = (TakesScreenshot) driver;
        File srcScreenshot = shot.getScreenshotAs(OutputType.FILE);
        File destScreenshot = new File(screenshotPath + errorMessage + ".jpg");
        try {
            FileUtils.copyFile(srcScreenshot, destScreenshot);
            Loggers.getLogger().info("Screenshot taken and saved in destination file");
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }
}
