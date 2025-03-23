Feature: Purchase the Order from the Ecommerce Website
Background:
Given I landed in the Ecommerce Website
  Scenario Outline: Positive Case to Purchase the Order
    Given Logged with Username <name>  and Password <password>
    When I add the product <productName> to Cart
    And ChecKout <productName> from the cart
    Then Verifying the Confirmation Msg "THANKYOU FOR THE ORDER"

    Examples:
      | name                  | password   | productName |
      | rahulshetty@gmail.com | Iamking@00 | ZARACOAT3   |