package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageUIs.CartPageUI;
import pageUIs.ProductDetailsPageUI;

public class ProductCart extends BasePage {
    private WebDriver driver;

    public ProductCart(WebDriver driver){ this.driver = driver; }

    public ProductCart miniCartPopup_Check_Title(){
        System.out.println("MiniCartPopup check page title");
        isElementDisplayed(driver, CartPageUI.CartPage_Title);
        return PageGenerator.getCartPage(driver);
    }
    public ProductCart miniCartPopup_Check_InProduct(String productName, String size, String color, String qty){
        System.out.println("MiniCartPopup check in product: " + productName + " - " + size + " - " + color + " - " + qty);
        isElementDisplayed(driver, CartPageUI.CartPage_QtyInCart);
        String actualQty = getElementText(driver, CartPageUI.CartPage_QtyInCart);
        Assert.assertEquals(actualQty, qty);

        String actualProductName = getElementText(driver, CartPageUI.CartPage_ProductNameInCart);
        Assert.assertEquals(actualProductName, productName);

        String actualColor = getElementText(driver, CartPageUI.CartPage_ColorInCart);
        Assert.assertEquals(actualColor, color);

        String actualSize = getElementText(driver, CartPageUI.CartPage_SizeInCart);
        Assert.assertEquals(actualSize, size);

        return PageGenerator.getCartPage(driver);
    }
}
