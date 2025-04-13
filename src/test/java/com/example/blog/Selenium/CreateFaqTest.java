package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class CreateFaqTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        // Set the path to ChromeDriver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;

        driver.get("http://localhost:8201/api/create-faq"); // Update this URL if necessary
    }

    @Test(priority = 1)
    public void testPageUIElements() {
        Assert.assertEquals(driver.getTitle(), "Create FAQ - Blog Application", "Incorrect page title.");

        WebElement heading = driver.findElement(By.className("header-title"));
        Assert.assertTrue(heading.isDisplayed(), "Main heading is not visible.");
        Assert.assertEquals(heading.getText(), "Create a New FAQ");

        Assert.assertTrue(driver.findElement(By.id("questionTitle")).isDisplayed(), "Question input should be visible.");
        Assert.assertTrue(driver.findElement(By.id("answerDetails")).isDisplayed(), "Answer input should be visible.");
        Assert.assertTrue(driver.findElement(By.id("faqStatus")).isDisplayed(), "Status checkbox should be visible.");
        Assert.assertTrue(driver.findElement(By.className("submit-btn")).isDisplayed(), "Submit button should be visible.");
    }

    @Test(priority = 2)
    public void testWithoutLoginRedirectsToLogin() {
        // Simulate logged-out user
        js.executeScript("localStorage.removeItem('authToken');");

        driver.findElement(By.id("questionTitle")).sendKeys("Unauth FAQ");
        driver.findElement(By.id("answerDetails")).sendKeys("Should not allow unauthenticated submission.");
        driver.findElement(By.className("submit-btn")).click();

        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Unauthenticated user should be redirected to the login page."
        );
    }

    @Test(priority = 3)
    public void testAdminOnlyAccessValidation() {
        // Simulate non-admin user
        js.executeScript("localStorage.setItem('authToken', 'dummyToken');");
        js.executeScript("localStorage.setItem('userType', 'regular-user');");

        driver.navigate().refresh();

        driver.findElement(By.id("questionTitle")).sendKeys("Non-admin FAQ");
        driver.findElement(By.id("answerDetails")).sendKeys("User should be redirected.");
        driver.findElement(By.className("submit-btn")).click();

        wait.until(ExpectedConditions.urlContains("/home"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/home"),
                "Non-admin user should be redirected to the home page."
        );
    }

    @Test(priority = 4)
    public void testSuccessfulFaqSubmissionForAdmin() {
        // Simulate admin user
        js.executeScript("localStorage.setItem('authToken', 'dummyToken');");
        js.executeScript("localStorage.setItem('userType', 'blog-admin');");

        driver.navigate().refresh();

        // Fill out the form
        WebElement questionField = driver.findElement(By.id("questionTitle"));
        WebElement answerField = driver.findElement(By.id("answerDetails"));

        questionField.clear();
        answerField.clear();

        questionField.sendKeys("Admin FAQ");
        answerField.sendKeys("This is a test FAQ created by admin.");
        driver.findElement(By.id("faqStatus")).click(); // Mark FAQ as active

        driver.findElement(By.className("submit-btn")).click();

        // Wait for redirect or success message
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/manage-faq"),
                ExpectedConditions.presenceOfElementLocated(By.id("responseMessage"))
        ));

        boolean redirected = driver.getCurrentUrl().contains("/manage-faq");
        boolean successMessageShown = driver.findElement(By.id("responseMessage"))
                .getText().toLowerCase().contains("faq created");

        Assert.assertTrue(
                redirected || successMessageShown,
                "Should redirect to manage page or display success message."
        );
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
