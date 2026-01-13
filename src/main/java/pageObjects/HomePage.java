package pageObjects;

import commons.BasePage;
import io.appium.java_client.android.AndroidDriver;
import pageUIs.HomePageUI;

public class HomePage extends BasePage {

    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isHomePageDisplayed() {
        return isElementDisplayed(HomePageUI.HOMEPAGE_TITLE);
    }

    public AppPage openViews() {
        clickToElement(HomePageUI.APP_MENU);
        return PageGenerator.getAppPage(driver);
    }
}
