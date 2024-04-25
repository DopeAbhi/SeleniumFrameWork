package PageObject;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.bidi.browsingcontext.Locator.css;

public class PaymentPage extends AbstractComponent {
    WebDriver driver;
    public PaymentPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }


   //  driver.findElement(By.cssSelector("[class='totalRow'] button")).click();


    @FindBy(xpath = "//input[@placeholder='Select Country']")
    WebElement autoSuggestiveDropdown;

    @FindBy(xpath = "(//button[contains(@class,'ta-item')]) [2]")
    WebElement optionSelection;

    @FindBy(css = ".action__submit")
    WebElement completePayment;





    public void autoSuggestiveDropDown()
    {

        Actions a = new Actions(driver);
        a.sendKeys( autoSuggestiveDropdown,"India").build().perform();
        WaitForLocator(By.cssSelector(".ta-results"));
        optionSelection.click();
    }

    public void orderPlace()
    {
        completePayment.click();
    }



}
