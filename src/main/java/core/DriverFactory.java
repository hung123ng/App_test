package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    public static AndroidDriver driver;

    public static AndroidDriver initDriver() {
        try {
//            caps.setCapability("app", System.getProperty("user.dir") + "/src/main/resources/apps/ApiDemos.apk");

            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("emulator-5554")
                    .setAutomationName("UiAutomator2")
                    .setAppPackage("io.appium.android.apis")
                    .setAppActivity(".ApiDemos")
                    .setNoReset(true)
                    .setNewCommandTimeout(Duration.ofSeconds(300))
                    .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(120))
                    .setUiautomator2ServerInstallTimeout(Duration.ofSeconds(120));
            URL appiumServerUrl = URI.create("http://127.0.0.1:4723/").toURL();
            driver = new AndroidDriver(appiumServerUrl, options);
            System.out.println("Session ID: " + driver.getSessionId());
            driver.getPageSource();

            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot init driver", e);
        }
    }
}

