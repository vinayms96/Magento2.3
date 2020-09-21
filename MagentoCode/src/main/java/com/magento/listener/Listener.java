package com.magento.listener;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.Screenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Loggers.getLogger().info("Test '" + result.getMethod().getMethodName() + "' execution Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Loggers.getLogger().info("Test has run successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Loggers.getLogger().error(result.getThrowable().getMessage());
        for (int er = 0; er < result.getThrowable().getStackTrace().length; er++) {
            Loggers.getLogger().error(result.getThrowable().getStackTrace()[er]);
        }
        ExtentReport.getExtentNode().fail(result.getThrowable().getMessage());

        try {
            ExtentReport.getExtentNode().fail(result.getTestName(), MediaEntityBuilder
                    .createScreenCaptureFromBase64String(Screenshot.getScreenshotBase64()).build());
        } catch (IOException e) {
            Loggers.getLogger().error(e.getMessage());
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Loggers.getLogger().info("Method '" + result.getMethod().getMethodName() + "' execution is Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
