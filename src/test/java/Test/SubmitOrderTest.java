package Test;

import PageObject.*;
import TestComponents.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SubmitOrderTest extends BaseTest {

@Test
    public void submitOrder() throws IOException {
        String productName = "ADIDAS ORIGINAL";
        String productName2 = "ZARA COAT 3";
        //  WebDriverManager.chromedriver().setup();  //We don't need this from selenium 4  //This downloads the driver for chrome while running

        //Login into the Application
        LandingPage page = launchApplication();

        ProdutCatalouge produtCatalouge = page.loginApplication("AbhayVerma@yopmail.com", "Test@123");

        //Adding Item in Cart

//        List<WebElement> products = produtCatalouge.getProducts();
        CartPage cartPage = produtCatalouge.addProductToCart(productName);
        //   produtCatalouge.addProductToCart(productName2);


        //Cart Page Handling

        cartPage.cartNavigation(); //Method is Declared inside the AbstractComponent
        Boolean match = cartPage.verifyCartItem(productName);
        Assert.assertTrue(match);


        //Payment Page Handling
        PaymentPage paymentPage = cartPage.cartCheckout();
        paymentPage.selectCountry("India");

        //Confirmation Page
        ConfirmationPage confirmationPage = paymentPage.orderPlace();

        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));


        driver.quit();
    }
}
