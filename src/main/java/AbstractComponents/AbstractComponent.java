package AbstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractComponent {
    WebDriver driver;

    public AbstractComponent(WebDriver driver) {

    this.driver = driver;
    PageFactory.initElements(driver,this);

    }


//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3"))); This is Called By Locator

    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    WebElement cartButton;


    public void cartNavigation()
    {
        cartButton.click();
    }


public void WaitForLocator(By FindBy)
{
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOfElementLocated(FindBy));
}

public void WaitforLocatorDisappear(By FindBy)
{
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(FindBy)));
}
}
