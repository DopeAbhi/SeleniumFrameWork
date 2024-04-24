package PageObject;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProdutCatalouge  extends AbstractComponent {

    WebDriver driver;
    public ProdutCatalouge(WebDriver driver)
    {
        super(driver);
        //Initializing Class Instance Driver Variable with the Local Variable
        this.driver = driver;
        PageFactory.initElements(driver,this);  //This method is responsible for converting @findBy format into Normal Format

    }

//    List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
//    WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText()
//            .equals(productName)).findFirst().orElse(null); //Stream Implementation
//
//        prod.findElement(By.cssSelector(".card-body button:last-child")).click();

    //PageFactory Pattern

    @FindBy(css=".mb-3")
    List<WebElement> products;
    By productsBy=By.cssSelector(".mb-3");

    public List<WebElement> getProducts()
    {
     WaitForLocator(productsBy);
     return products;
    }

    public void goTo()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }


}

