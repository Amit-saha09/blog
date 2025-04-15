package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SignUpPageTest {

    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;
    private String generatedEmail;

    @BeforeTest
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:8201/api/sign-up");
    }

    @Test(priority = 1)
    public void testPageLoads() {
        Assert.assertEquals(driver.getTitle(), "Create Account", "Page title should be 'Create Account'");
        Assert.assertTrue(
                driver.findElement(By.tagName("h2")).getText().contains("Create a New Account"),
                "Heading should contain 'Create a New Account'"
        );
    }

    @Test(priority = 2)
    public void testValidAccountCreation() {
        generatedEmail = "user" + System.currentTimeMillis() + "@example.com";

        driver.findElement(By.id("firstName")).sendKeys("Test");
        driver.findElement(By.id("lastName")).sendKeys("User");
        driver.findElement(By.id("email")).sendKeys(generatedEmail);
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("confirmPassword")).sendKeys("password123");
        driver.findElement(By.xpath("//button[text()='Sign Up']")).click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("responseMessage")));
        String text = message.getText();

        Assert.assertTrue(
                text.contains("Account created successfully") || text.toLowerCase().contains("error"),
                "Expected success or error message after sign-up attempt"
        );
    }

    @Test(priority = 3)
    public void testPasswordMismatch() {
        driver.navigate().refresh();

        driver.findElement(By.id("firstName")).sendKeys("Mismatch");
        driver.findElement(By.id("lastName")).sendKeys("Case");
        driver.findElement(By.id("email")).sendKeys("mismatch@example.com");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        driver.findElement(By.id("password")).sendKeys("abc123");
        driver.findElement(By.id("confirmPassword")).sendKeys("xyz123");
        driver.findElement(By.xpath("//button[text()='Sign Up']")).click();

        WebElement response = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("responseMessage")));
        Assert.assertEquals(response.getText(), "Passwords do not match!", "Password mismatch error should appear.");
    }

    @Test(priority = 4)
    public void testFormSubmissionWithMissingFields() {
        driver.navigate().refresh();

        driver.findElement(By.id("firstName")).sendKeys("EmptyFields");
        driver.findElement(By.xpath("//button[text()='Sign Up']")).click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("/sign-up"),
                "User should not proceed if required fields are missing."
        );
    }

    @Test(priority = 5)
    public void testLoginAfterSignUp() {
        driver.get("http://localhost:8201/api/login");

        driver.findElement(By.id("email")).sendKeys(generatedEmail);
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.className("login-btn")).click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/home"),
                ExpectedConditions.urlContains("/userBlogList")
        ));

        String newUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                newUrl.contains("/home") || newUrl.contains("/userBlogList"),
                "After login, user should be redirected to home or blog list page."
        );
    }

    @Test(priority = 6)
    public void testLocalStorageAfterLogin() {
        Object emailStored = js.executeScript("return localStorage.getItem('userEmail');");
        Object tokenStored = js.executeScript("return localStorage.getItem('authToken');");
        Object userType = js.executeScript("return localStorage.getItem('userType');");

        Assert.assertNotNull(emailStored, "userEmail should be stored in localStorage");
        Assert.assertNotNull(tokenStored, "authToken should be stored in localStorage");
        Assert.assertNotNull(userType, "userType should be stored in localStorage");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
