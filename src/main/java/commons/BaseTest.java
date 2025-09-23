package commons;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

public class BaseTest {
    protected WebDriver driver;

    @Parameters({"browser", "url"})
    @BeforeMethod
    public void beforeMethod(@Optional("chrome") String browserName,
                             @Optional("https://demo.nopcommerce.com") String url) {
        // Initialize WebDriver
        driver = getWebdriver(browserName,"https://demo.hyva.io/", true);
        driver.manage().window().maximize();

        // Initialize ScreenshotHelper
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        // Close browser
        if (driver != null) {
            driver.quit();
        }
    }

    private void createDownloadDirectory(String downloadDir) {
        if (downloadDir != null && !downloadDir.isEmpty()) {
            File dir = new File(downloadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
            }
        }
    }

    private void configureChromeDownload(ChromeOptions options, String downloadDir) {
        if (downloadDir != null && !downloadDir.isEmpty()) {
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("download.default_directory", downloadDir);
            chromePrefs.put("download.prompt_for_download", false);
            chromePrefs.put("download.directory_upgrade", true);
            options.setExperimentalOption("prefs", chromePrefs);
        }
    }

    protected WebDriver getWebdriver(String browserName, String url, boolean headless) {
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        switch (browserList) {
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case CHROME:
                ChromeOptions options = new ChromeOptions();

                // Tắt các thông báo không cần thiết
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-translate");
                options.addArguments("--disable-extensions");

                // Tối ưu cho screenshot
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");

                // Set window size lớn để tránh scroll bar
                options.addArguments("--start-maximized");

                // Enable DevTools cho CDP commands
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                // ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    // Headless mode
                    options.addArguments("--headless=new"); // Selenium 4 new headless mode
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--disable-gpu");

                    // Set large viewport for full page
                    options.addArguments("--force-device-scale-factor=1");
                }
                driver = new ChromeDriver(options);
                break;

            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Browser name is not valid");
        }

        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        return driver;
    }

    protected WebDriver getRemoteWebdriver(String browserName, String url, String osName, String ipAddress, String portNumber, boolean headless) {
        BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
        Platform platform;
        Capabilities capability = null;

        if (osName.toLowerCase().contains("windows")) {
            platform = Platform.WINDOWS;
        } else {
            platform = Platform.MAC;
        }

        switch (browserList) {
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capability = firefoxOptions;
                break;

            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                }
                chromeOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capability = chromeOptions;
                break;

            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu");
                }
                edgeOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capability = edgeOptions;
                break;

            case SAFARI:
                SafariOptions safariOptions = new SafariOptions();
                safariOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);
                capability = safariOptions;
                break;

            default:
                throw new RuntimeException("Browser name is not valid");
        }

        try {
            driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddress, portNumber)), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        return driver;
    }
}
