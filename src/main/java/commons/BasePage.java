package commons;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
    }

    // ========= NAVIGATION (MOBILE) =========
    public void pressBack() {
        driver.navigate().back();
    }

    public void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    // ========= WAIT =========
    protected WebElement waitForElementVisible(String locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locator)));
    }

    protected WebElement waitForElementVisible(String locator, String restParameter) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(castParameter(locator, restParameter))));
    }

    protected Boolean waitForElementSelected(String locator) {
        return wait.until(ExpectedConditions.elementToBeSelected(getByLocator(locator)));
    }

    protected WebElement waitClickable(String locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(getByLocator(locator)));
    }

    protected boolean waitInvisible(String locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locator)));
    }

    protected Alert waitAlertPresence(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptToAlert(WebDriver driver) {
        waitAlertPresence(driver).accept();
    }

    protected void cancelToAlert(WebDriver driver) {
        waitAlertPresence(driver).dismiss();
    }

    protected String getAlertText(WebDriver driver) {
        return waitAlertPresence(driver).getText();
    }

    protected void sendkeyToAlert(WebDriver driver, String keysToSend) {
        waitAlertPresence(driver).sendKeys(keysToSend);
    }

    protected WebElement getElement(String locator) {
        return driver.findElement(getByLocator(locator));
    }

    protected List<WebElement> getListElement(String locator) {
        return driver.findElements(getByLocator(locator));
    }

    private String castParameter(String locator, String... restParameter) {
        return String.format(locator, (Object[]) restParameter);
    }

    private By getByLocator(String prefixLocator) {
        String up = prefixLocator.toUpperCase();
        if (up.startsWith("ACCESSIBILITYID=")) {
            return AppiumBy.accessibilityId(prefixLocator.substring("ACCESSIBILITYID=".length()));
        }
        if (up.startsWith("ID=")) {
            return By.id(prefixLocator.substring("ID=".length()));
        }
        if (up.startsWith("CLASS=")) {
            return By.className(prefixLocator.substring("CLASS=".length()));
        }
        if (up.startsWith("XPATH=")) {
            return By.xpath(prefixLocator.substring("XPATH=".length()));
        }
        if (up.startsWith("ANDROIDUIAUTOMATOR=")) {
            return AppiumBy.androidUIAutomator(prefixLocator.substring("ANDROIDUIAUTOMATOR=".length()));
        }
        throw new IllegalArgumentException("Unsupported locator format: " + prefixLocator);
    }

    protected void clickToElement(String locator) {
        getElement(locator).click();
    }

    protected void clickToElement(String locator, String restParameter) {
//        waitForElementClickable(driver, locator, restParameter);
        getElement(castParameter(locator, restParameter)).click();
    }

    protected void sendKeyToElement(String locator, String keyToSend) {
        Keys key = null;
        if (GlobalConstants.OS_NAME.startsWith("Windows")) {
            key = Keys.CONTROL;
        } else {
            key = Keys.COMMAND;
        }
        getElement(locator).clear();
        sleepInSeconds(1);
        getElement(locator).sendKeys(keyToSend);

    }

    protected void sendKeyToElement(String locator, String keyToSend, String restParameter) {
        getElement(castParameter(locator, restParameter)).clear();
        getElement(castParameter(locator, restParameter)).sendKeys(keyToSend);
    }

    protected void selectItemDropdown(String locator, String textItem) {
        new Select(getElement(locator)).selectByVisibleText(textItem);
    }

    protected String getSelectedItemInDropdown(String locator) {
        return new Select(driver.findElement(By.xpath((locator)))).getFirstSelectedOption().getText();
    }

    protected void isDropdownMultiple(String locator) {
        new Select(getElement(locator)).isMultiple();
    }

    protected void sleepInSeconds(long timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getElementAttribute(String locator, String attributeName) {
        return getElement(locator).getAttribute(attributeName);
    }

    protected String getElementAttribute(String locator, String attributeName, String restParameter) {
        return getElement(castParameter(locator, restParameter)).getAttribute(attributeName);
    }

    protected Dimension getElementSize(String locator) {
        return getElement(locator).getSize();
    }

    protected String getElementText(String locator) {
        return getElement(locator).getText();
    }

    protected String getElementText(String locator, String restParameter) {
        return getElement(castParameter(locator, restParameter)).getText();
    }

    protected void getElementCssValue(String locator, String propertyName) {
        getElement(locator).getCssValue(propertyName);
    }

    protected String getHexaColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex().toUpperCase();
    }

    protected int getListElementNumber(String locator) {
        return getListElement(locator).size();
    }

    protected void checkToCheckboxRadio(String locator) {
        if (!getElement(locator).isSelected()) {
            getElement(locator).click();
        }
    }

    protected void checkToCheckboxRadio(String locator, String restParameter) {
        if (!getElement(castParameter(locator, restParameter)).isSelected()) {
            getElement(castParameter(locator, restParameter)).click();
        }
    }

    protected void uncheckToCheckbox(String locator) {
        if (getElement(locator).isSelected()) {
            getElement(locator).click();
        }
    }

    protected boolean isElementDisplayed(String locator) {
        waitForElementVisible(locator);
        return getElement(locator).isDisplayed();
    }

    protected boolean isElementDisplayed(String locator, String restParameter) {
        return getElement(castParameter(locator, restParameter)).isDisplayed();
    }

    protected boolean isElementEnable(String locator) {
        return getElement(locator).isEnabled();
    }

    protected boolean isElementSelected(String locator) {
        return getElement(locator).isSelected();
    }

    protected boolean isElementSelected(String locator, String restParameter) {
        return getElement(castParameter(locator, restParameter)).isSelected();
    }

    protected void clickAndHoldToElement(String locator) {
        new Actions(driver).clickAndHold(getElement(locator)).perform();
    }

    protected void dragAndDropElement(String sourceLocator, String targetLocator) {
        new Actions(driver).dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).perform();
    }

    public void scrollToElement(String locator) {
        new Actions(driver).scrollToElement(getElement(locator)).perform();
    }

    protected void highlightElement(String locator) {
        WebElement element = getElement(locator);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSeconds(2);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void scrollToElementOnTopByJS(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public void scrollToElementOnDownByJS(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
    }

    public void scrollToBottomPageByJS(AndroidDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", getElement(locator));
    }

    protected void removeAttributeInDOM(String locator, String attributeRemove) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
    }

    protected void sendkeyToElementByJS(String locator, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
    }

    protected String getAttributeInDOMByJS(String locator, String attributeName) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(locator));
    }

    protected String getElementValidationMessage(String locator) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getElement(locator));
    }

    protected boolean isImageLoaded(String locator) {
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete " +
                        "&& typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
                getElement(locator));
    }

}