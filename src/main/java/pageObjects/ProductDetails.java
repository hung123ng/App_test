package pageObjects;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import commons.BasePage;
import io.qameta.allure.Step;
import pageUIs.HomePageUI;
import pageUIs.ProductDetailsPageUI;

public class ProductDetails extends BasePage {
    private WebDriver driver;

    public ProductDetails(WebDriver driver){ this.driver = driver; }

    public ProductDetails productDetailsPage_Check_Title(String expectedTitle ){
        System.out.println("ProductDetailsPage check page title");
        String actualTitle = getElementText(driver, ProductDetailsPageUI.productDetailsPage_Title);
        Assert.assertEquals(actualTitle, expectedTitle);
        return PageGenerator.getProductDetailsPage(driver);
    }
    @Step("productDetailsPage_AddToCart_WithInfo")
    public ProductDetails productDetailsPage_AddToCart_WithInfo(String size, String color, String qty) {
        System.out.println("Add to cart with info: " + size + " - " + color + " - " + qty);
        clickToElement(driver,ProductDetailsPageUI.productDetailsPage_Size, size);
        clickToElement(driver,ProductDetailsPageUI.productDetailsPage_Color, color);
        sendKeyToElement(driver,ProductDetailsPageUI.productDetailsPage_Qty,qty);
        clickToElement(driver,ProductDetailsPageUI.productDetailsPage_addToCart);
        return PageGenerator.getProductDetailsPage(driver);
    }
    @Step("productDetailsPage_Check_AddSucess")
    public ProductDetails productDetailsPage_Check_AddSucess(String expectedQty) {
        System.out.println("Check add to cart success with qty: " + expectedQty);
        isElementDisplayed(driver, ProductDetailsPageUI.productDetailsPage_messageSuccess);
        isElementDisplayed(driver, ProductDetailsPageUI.productDetailsPage_QtyInCart);
        String actualQty = getElementText(driver, ProductDetailsPageUI.productDetailsPage_QtyInCart);
        Assert.assertEquals(actualQty, expectedQty);
        return PageGenerator.getProductDetailsPage(driver);
    }
    public ProductCart header_Transit_CartPage() {
        System.out.println("Transit to CartPage");
        clickToElement(driver, HomePageUI.headerNavigationCard);
        return PageGenerator.getCartPage(driver);
    }
}
