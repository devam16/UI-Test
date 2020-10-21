Feature: automated checks over our DEMO ONLINE Shop
Scenario: vkhkclc
Given User initializes the driver and browser
And User hits the URL of the application
And User navigates to "Laptops" category to "Sony vaio i5" Product and adds it to the cart
And User navigates to the "Home" from the navigation menu
And User navigates to "Laptops" category to "Dell i7 8gb" Product and adds it to the cart
And User navigates to the "Cart" from the navigation menu
And User Deletes "Dell i7 8gb" Product from the cart
And User Clicks on Place Order Button
And User Fills in all the form details and Clicks On the Purchase Button
|Name	|Devam		|
|Country|India		|
|City	|Noida		|
|Card	|5555-6666	|
|Month	|October	|
|Year	|2020		|
And User captures purchase Id and Amount
Then User verifies that purchase amount is "1490"
And User terminates the browser

