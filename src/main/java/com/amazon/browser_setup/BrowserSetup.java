package com.amazon.browser_setup;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;

public class BrowserSetup {
    @BeforeClass
    public void setup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);

        ChromeOptions ch_options = new ChromeOptions();
        ch_options.merge(capabilities);

        FirefoxOptions ff_options = new FirefoxOptions();
        ff_options.merge(capabilities);



    }
}
