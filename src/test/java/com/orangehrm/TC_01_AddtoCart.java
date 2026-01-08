package com.orangehrm;

import core.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.HomePage;

public class TC_01_AddtoCart {
    private AndroidDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void beforeTest(){
        driver = DriverFactory.initDriver();
        homePage = new HomePage(driver);
    }
    @Test
    public void TC1(){
        Assert.assertTrue(homePage.isHomePageDisplayed());
        homePage.openViews();
    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        driver.quit();
    }
}
