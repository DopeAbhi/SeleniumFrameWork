package Framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) throws InterruptedException {

        String productName = "ADIDAS ORIGINAL";
        //  WebDriverManager.chromedriver().setup();  //We don't need this from selenium 4  //This downloads the driver for chrome while running
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/client");

        //Login into the Application
        driver.findElement(By.id("userEmail")).sendKeys("AbhayVerma@yopmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Test@123");
        driver.findElement(By.id("login")).click();

        //Adding Item in Cart
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText()
                .equals(productName)).findFirst().orElse(null); //Stream Implementation

        prod.findElement(By.cssSelector(".card-body button:last-child")).click();


        //        for(WebElement element: products) My Implementation
//        {
//            if(element.getText().equals("ADIDAS ORIGINAL"))
//                System.out.println(element.getText());
//
//        }

//Explicit Wait for Toast Message

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

//Class for the Loader=  ng-animating
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //Checking loader is getting disppeared or not
        System.out.println(driver.findElement(By.cssSelector("#toast-container")).getText());

        //Adding Second Product in the Cart
        prod=products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText()
                .equals("ZARA COAT 3")).findFirst().orElse(null);

        prod.findElement(By.cssSelector(".card-body button:last-child")).click();

        //Explicit Wait for Toast Message

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        //Class for the Loader=  ng-animating
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //Checking loader is getting disppeared or not
        System.out.println(driver.findElement(By.cssSelector("#toast-container")).getText());

        //Navigating to the Cart
        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();



//Verifying Added Items from the Cart
        List<WebElement> cartlist=driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
       Boolean match= cartlist.stream().anyMatch(product->product.getText().equals(productName));
       Assert.assertTrue(match);



        driver.quit();
    }
}
