package PageObject;

import AbstractComponents.AbstractComponent;
import com.epam.healenium.SelfHealingDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class CartPage extends AbstractComponent {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='cartSection']/h3")
    List<WebElement> cartItem;


    @FindBy(css="[class='totalRow'] button")
    WebElement CheckoutButton;


    public Boolean verifyCartItem(String productName)
    {
        Boolean match=  cartItem.stream().anyMatch(product -> product.getText().equals(productName));
        return match;
    }

    public PaymentPage cartCheckout()
    {
        WebElement element = new SelfHealingDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(CheckoutButton));

        element.click();
        return new PaymentPage(driver);

    }


}
