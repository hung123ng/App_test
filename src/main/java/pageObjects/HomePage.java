package pageObjects;

import commons.BasePage;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageUIs.HomePageUI;

public class HomePage extends BasePage {
    AndroidDriver driver;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
    }

    // chuyển từ UI → action
    public boolean isHomePageDisplayed() {
        WebElement title = driver.findElement(By.xpath(HomePageUI.HOMEPAGE_TITLE));
        return title.isDisplayed();
    }

    public void openViews() {
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='App']")).click();
    }

}
