package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomepageModel {
    public HomepageModel(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }
}
