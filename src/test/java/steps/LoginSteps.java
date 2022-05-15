package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

public class LoginSteps {
    WebDriver driver;

    @Given("Navigate to Home Page")
    public void navigateToLoginPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://contacts-app.tobbymarshall815.vercel.app");
    }

    @When("Click on Login tab")
    public void clickOnLoginTab() {
        click(By.xpath("//a[.='LOGIN']"));
    }

    @Then("Appear Login Registration form")
    public void isLoginRegistrationFormPresent() {
        Assert.assertTrue(isElementPresent(By.cssSelector(".login_login__3EHKB")));
        driver.quit();
    }

    @And("Enter valid email and password")
    public void enterValidData(DataTable table) {

        List<Map<String,String>> dataTable= table.asMaps();
        String email = dataTable.get(0).get("email");
        String password = dataTable.get(0).get("password");

        type(By.cssSelector("[placeholder='Email']"), email);
        type(By.cssSelector("[placeholder='Password']"),password);
    }

    @And("Click on Login button")
    public void clickOnLoginButton() {
        click(By.xpath("//button[.=' Login']"));
    }

    @Then("SignOut button displayed")
    public void isSignOutButtonDisplayed() {
        pause(2000);
        Assert.assertTrue(isElementPresent(By.xpath("//button[.='Sign Out']")));
        driver.quit();
    }

    @And("Enter a valid email and an invalid password")
    public void enterValidEmailAndInvalidPassword(DataTable table) {

        List<Map<String,String>> dataTable = table.asMaps();
        String email = dataTable.get(0).get("email");
        String password = dataTable.get(0).get("password");

        type(By.cssSelector("[placeholder='Email']"),email);
        type(By.cssSelector("[placeholder='Password']"),password);
    }

    @Then("Alert appeared")
    public void isAlertDisplayed() {
        pause(2000);
        Assert.assertTrue(isAlertPresent());

    }
    @And("Accept alert")
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    @And("Check Warning")
    public void isWarningDisplayed(){
        pause(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='login_login__3EHKB']/div")).getText(),"Login Failed with code 400");

    }

    @And("Message registration failed appeared")
    public void isMessageFailedDisplayed(){
        pause(2000);

        String resultMessage = driver.findElement(By.xpath("//div[@class='login_login__3EHKB']/div")).getText();

        if (resultMessage.contains("409")) {
            Assert.assertEquals(resultMessage,"Registration failed with code 409");
        } else if (resultMessage.endsWith("400")){
            Assert.assertEquals(resultMessage,"Registration failed with code 400");
        }
    }

    @And("Message Login Failed with code 409 appeared")
    public void isMessage409Displayed(){
        pause(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='login_login__3EHKB']/div")).getText(),"Registration failed with code 409");
    }

    @And("Enter an invalid email and a valid password")
    public void enterInvalidEmailAndValidPassword(DataTable table) {

        List<Map<String, String>> dataTable = table.asMaps();
        String email = dataTable.get(0).get("email");
        String password = dataTable.get(0).get("password");

        type(By.cssSelector("[placeholder='Email']"), email);
        type(By.cssSelector("[placeholder='Password']"), password);
    }

    @And("Click on Registration button")
    public void clickOnRegistrationButton() {
        click(By.xpath("//button[.=' Registration']"));
    }

    @And("Browser closed")
    public void tierDown(){
        driver.quit();
    }

    private boolean isAlertPresent() {
        Alert alert = new WebDriverWait(driver,10).until((ExpectedConditions.alertIsPresent()));
        if(alert == null) {
            return false;
        } else {
            driver.switchTo().alert();
            alert.accept();
            return true;
        }
    }

    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void type(By locator, String text) {
        if (text != null) {
            driver.findElement(locator).click();
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        }
    }

    private boolean isElementPresent(By locator) {
        return driver.findElements(locator).size()>0;
    }

    private void click(By locator) {
        driver.findElement(locator).click();
    }
}