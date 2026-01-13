package pageObjects;

import io.appium.java_client.android.AndroidDriver;

public class PageGenerator {

    public static HomePage getHomePage(AndroidDriver driver){
        return new HomePage(driver);
    }

    public static AppPage getAppPage(AndroidDriver driver){
        return new AppPage(driver);
    }

}
