package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ContactUsPageTest {

    private WebDriver driver;
    private final String baseUrl = "http://localhost:8201/api/contact-us";

    @BeforeTest
    public void setUp() {
        // Set the path to ChromeDriver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(baseUrl);
    }

    @Test(priority = 1)
    public void testPageTitleAndFieldsVisible() {
        Assert.assertEquals(driver.getTitle(), "Contact Us", "Page title mismatch.");

        // Locate form fields and submit button
        WebElement subjectField = driver.findElement(By.id("subject"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement contentField = driver.findElement(By.id("content"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        // Verify field and button visibility
        Assert.assertTrue(subjectField.isDisplayed(), "Subject field not visible.");
        Assert.assertTrue(emailField.isDisplayed(), "Email field not visible.");
        Assert.assertTrue(contentField.isDisplayed(), "Content field not visible.");
        Assert.assertTrue(submitButton.isDisplayed(), "Submit button not visible.");
    }

    @Test(priority = 2)
    public void testSubmitValidForm() throws InterruptedException {
        // Use dynamic email to avoid duplication
        String email = "contact" + System.currentTimeMillis() + "@example.com";

        driver.findElement(By.id("subject")).sendKeys("Inquiry about blog collaboration");
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("content")).sendKeys("Hi team, I would like to discuss a blog partnership opportunity.");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Wait for alert and verify message
        Thread.sleep(2000); // Use WebDriverWait in real projects
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(
                alert.getText().toLowerCase().contains("successfully submitted"),
                "Expected success alert after submission."
        );
        alert.accept();
    }

    @Test(priority = 3)
    public void testSubmitInvalidEmail() throws InterruptedException {
        driver.navigate().refresh();

        driver.findElement(By.id("subject")).sendKeys("Invalid Email Test");
        driver.findElement(By.id("email")).sendKeys("invalid-email.com");
        driver.findElement(By.id("content")).sendKeys("Test message with invalid email.");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(1000); // Use WebDriverWait in real projects
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(
                alert.getText().toLowerCase().contains("valid information"),
                "Expected validation alert for invalid email."
        );
        alert.accept();
    }

    @Test(priority = 4)
    public void testSubmitEmptyFields() throws InterruptedException {
        driver.navigate().refresh();

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(1000); // Use WebDriverWait in real projects
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(
                alert.getText().toLowerCase().contains("fill out all fields"),
                "Expected alert for empty form submission."
        );
        alert.accept();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
