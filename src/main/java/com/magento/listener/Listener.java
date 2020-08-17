package com.magento.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {
    Logger listenerLog = LogManager.getLogger(Listener.class);

    @Override
    public void onTestStart(ITestResult result) {
        listenerLog.info("Test '"+result.getMethod().getMethodName()+"' execution Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        listenerLog.info("Test has run successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        listenerLog.info("Method '"+result.getMethod().getMethodName()+"' execution is Skipped");
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
