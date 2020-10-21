package stepDefinitions;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import junit.framework.Assert;

public class commonStepDef {
	
	public WebDriver driver;
	public Properties prop;
	public WebDriverWait wait;
	
	public String amount;
	
	@Given("^User initializes the driver and browser$")
	public void user_initializes_the_driver_and_browser() throws Throwable {
		prop = new Properties();
		String dataLoc = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\data.properties";
		FileInputStream fis = new FileInputStream(dataLoc);
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		if (browserName.equals("chrome")) {
			String chrDriverloc = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chrDriverloc);
			driver = new ChromeDriver();
		} 
		else if (browserName.equals("firefox")) {

			String ffoxDriverloc = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", ffoxDriverloc);
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	    
	}

	@Given("^User hits the URL of the application$")
	public void user_hits_the_URL_of_the_application() throws Throwable {
		String Appurl = prop.getProperty("url");
		driver.get(Appurl);
	    
	}

	@Given("^User navigates to \"([^\"]*)\" category to \"([^\"]*)\" Product and adds it to the cart$")
	public void user_navigates_to_category_to_Product_and_adds_it_to_the_cart(String category, String product) throws Throwable {
	    driver.findElement(By.xpath("//a[text()='CATEGORIES']/following-sibling::a[text()='"+category+"']")).click();
	  
	    driver.findElement(By.xpath("//a[contains(@href,'prod') and contains(text(),'"+product+"')]")).click();
	    
	    driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
	    wait = new WebDriverWait(driver, 2);
	    wait.until(ExpectedConditions.alertIsPresent());
	    Alert alert = driver.switchTo().alert();
	    alert.accept();
	    
	}
	

@Given("^User navigates to the \"([^\"]*)\" from the navigation menu$")
public void user_navigates_to_the_from_the_navigation_menu(String menu) throws Throwable {
	driver.findElement(By.xpath("//a[@class='nav-link' and contains(text(),'"+menu+"')]")).click();
}

	@Given("^User Deletes \"([^\"]*)\" Product from the cart$")
	public void user_Deletes_Product_from_the_cart(String product) throws Throwable {
	    driver.findElement(By.xpath("//td[text()='"+product+"']/..//a[text()='Delete']")).click();
	}

	@Given("^User Clicks on Place Order Button$")
	public void user_Clicks_on_Place_Order_Buttpn() throws Throwable {
		driver.findElement(By.xpath("//button[@type='button' and text()='Place Order']")).click();
	}
	
	@Given("^User Fills in all the form details and Clicks On the Purchase Button$")
	public void user_Fills_in_all_the_form_details_and_Clicks_On_the_Purchase_Button(DataTable details) throws Throwable {
		driver.switchTo().activeElement();
		List<List<String>> data = details.raw();
		//input[@id='name']
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		WebElement nametext = driver.findElement(By.xpath("//input[@id='name']"));
		
		executor.executeScript("arguments[0].value='"+data.get(0).get(1)+"'", nametext); 
		 
//		driver.findElement(By.xpath("//input[@id='country']")).sendKeys(data.get(1).get(1));
		
		WebElement countrytext = driver.findElement(By.xpath("//input[@id='country']"));
		executor.executeScript("arguments[0].value='"+data.get(1).get(1)+"'", countrytext);
		 
//		driver.findElement(By.xpath("//input[@id='city']")).sendKeys(data.get(2).get(1));
		
		WebElement citytext = driver.findElement(By.xpath("//input[@id='city']"));
		executor.executeScript("arguments[0].value='"+data.get(2).get(1)+"'", citytext);
		
		 
//		driver.findElement(By.xpath("//input[@id='card']")).sendKeys(data.get(3).get(1));
		
		WebElement cardtext = driver.findElement(By.xpath("//input[@id='card']"));
		executor.executeScript("arguments[0].value='"+data.get(3).get(1)+"'", cardtext);
		 
//		driver.findElement(By.xpath("//input[@id='month']")).sendKeys(data.get(4).get(1));
		
		WebElement monthtext = driver.findElement(By.xpath("//input[@id='month']"));
		executor.executeScript("arguments[0].value='"+data.get(4).get(1)+"'", monthtext);
		 
//		driver.findElement(By.xpath("//input[@id='year']")).sendKeys(data.get(5).get(1));
		
		WebElement yeartext = driver.findElement(By.xpath("//input[@id='year']"));
		executor.executeScript("arguments[0].value='"+data.get(4).get(1)+"'", yeartext);
		 
		WebElement element = driver.findElement(By.cssSelector("button[onclick='purchaseOrder()']"));
		
		executor.executeScript("arguments[0].click();", element);
		
//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='sweet-alert  showSweetAlert visible']"))));
		
	 
	}

	@Given("^User captures purchase Id and Amount$")
	public void user_captures_purchase_Id_and_Amount() throws Throwable {
		
		String text = driver.findElement(By.xpath("//p[@class='lead text-muted ']")).getText();
//		 String s = text.substring(text.indexOf("Id"), text.indexOf(" "));
		System.out.println(text);
		 String[] lines = text.split("\n");
		 String line1 = lines[0];
//		 System.out.println(secondLine);
		 String purchaseID = line1.split(":")[1];
		 System.out.println("purchaseId = "+purchaseID);
		 String line2 = lines[1];
//		 System.out.println(secondLine);
		 amount = line2.split(":")[1].split(" ")[1];
		 System.out.println("AmountInUSD = "+amount);
	    
	}
	

	@Then("^User verifies that purchase amount is \"([^\"]*)\"$")
	public void user_verifies_that_purchase_amount_is(String amtExpected) throws Throwable {
	    Assert.assertEquals(amtExpected, amount);
	    
	}

	@Then("^User terminates the browser$")
	public void user_terminates_the_browser() throws Throwable {
	    driver.quit();
	    
	}


}
