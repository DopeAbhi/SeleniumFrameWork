package StepDefinitions;

import PageObject.*;
import TestComponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;
public class StepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProdutCatalouge produtCatalouge;
    ConfirmationPage confirmationPage;

    @Given("I landed in the Ecommerce Website")
    public void i_landed_in_the_ecommerce_website() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged with Username \"([^\"]*)\" and Password \"([^\"]*)\"$")
    public void logged_with_username_and_password(String username, String password) {

        produtCatalouge = page.loginApplication(username, password);
    }

    @When("^I add the product \"([^\"]*)\" to Cart$")
    public void i_add_the_product_to_cart(String productName) {

        List<WebElement> products = produtCatalouge.getProducts();
        produtCatalouge.addProductToCart(productName);
    }

    @When("^ChecKout \"([^\"]*)\" from the cart$")
    public void checkout_product_from_cart(String productName) {

        CartPage cartPage = produtCatalouge.cartNavigation(); //Method is Declared inside the AbstractComponent
        Boolean match = cartPage.verifyCartItem(productName);
        Assert.assertTrue(match);


        //Payment Page Handling
        PaymentPage paymentPage = cartPage.cartCheckout();
        paymentPage.selectCountry("India");

        //Confirmation Page
        confirmationPage = paymentPage.orderPlace();
    }

    // Then Verifying the Confirmation Msg "THANKYOU FOR THE ORDER"
    @Then("Then Verifying the Confirmation Msg {string}")
    public void verifying_the_confirmation_msg(String str) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(str));
        driver.close();
    }


}
