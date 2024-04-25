package Test;

import PageObject.CartPage;
import PageObject.LandingPage;
import PageObject.PaymentPage;
import PageObject.ProdutCatalouge;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SubmitOrderTest {

    public static void main(String[] args) throws InterruptedException {

        String productName = "ADIDAS ORIGINAL";
        String productName2 = "ZARA COAT 3";
        //  WebDriverManager.chromedriver().setup();  //We don't need this from selenium 4  //This downloads the driver for chrome while running
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Login into the Application
        LandingPage page = new LandingPage(driver);
        page.goTo();
        ProdutCatalouge produtCatalouge = page.loginApplication("AbhayVerma@yopmail.com", "Test@123");

        //Adding Item in Cart

        List<WebElement> products = produtCatalouge.getProducts();
        CartPage cartPage = produtCatalouge.addProductToCart(productName);
        //   produtCatalouge.addProductToCart(productName2);


        //Cart Page Handling

        cartPage.cartNavigation(); //Method is Declared inside the AbstractComponent
        Boolean match = cartPage.verifyCartItem(productName);
        Assert.assertTrue(match);

        PaymentPage paymentPage = cartPage.cartCheckout();


        //Navigating to the Cart


//Verifying Added Items from the Cart
//        List<WebElement> cartlist = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
//        Boolean match = cartlist.stream().anyMatch(product -> product.getText().equals(productName));


//Payment Page Handling

        paymentPage.autoSuggestiveDropDown();
        paymentPage.orderPlace();

        List<WebElement> orderID = driver.findElements(By.cssSelector("label[class='ng-star-inserted']"));
        for (WebElement order : orderID) {
            System.out.println(order.getText());
        }


        driver.quit();
    }
}
