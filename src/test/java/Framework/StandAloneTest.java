package Framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {
      //  WebDriverManager.chromedriver().setup();  //We don't need this from selenium 4  //This downloads the driver for chrome while running
        WebDriver driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/client");

        //Login into the Application
        driver.findElement(By.id("userEmail")).sendKeys("AbhayVerma@yopmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Test@123");
        driver.findElement(By.id("login")).click();

        //Adding Item in Cart

    List <WebElement> products=    driver.findElements(By.cssSelector(".mb-3"));
    WebElement prod=products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText()
            .equals("ADIDAS ORIGINAL")).findFirst().orElse(null) ;  //Stream Implementation
        prod.findElement(By.cssSelector(".card-body button:last-child")).click();

//        for(WebElement element: products) My Implementation
//        {
//            if(element.getText().equals("ADIDAS ORIGINAL"))
//                System.out.println(element.getText());
//
//        }

        driver.quit();
    }
}
