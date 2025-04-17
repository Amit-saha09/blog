package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ContactUsPageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://localhost:8201/api/contact-us";

    // Locators
    private final By subjectField = By.id("subject");
    private final By emailField = By.id("email");
    private final By contentField = By.id("content");
    private final By submitButton = By.xpath("//button[@type='submit']");

    @BeforeTest
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Windows\\chromedriver.exe"
        );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(baseUrl);
    }

    @Test(priority = 1)
    public void testPageTitleAndFieldsVisible() {
        Assert.assertEquals(driver.getTitle(), "Contact Us", "Page title mismatch.");

        Assert.assertTrue(driver.findElement(subjectField).isDisplayed(), "Subject field not visible.");
        Assert.assertTrue(driver.findElement(emailField).isDisplayed(), "Email field not visible.");
        Assert.assertTrue(driver.findElement(contentField).isDisplayed(), "Content field not visible.");
        Assert.assertTrue(driver.findElement(submitButton).isDisplayed(), "Submit button not visible.");
    }

    @Test(priority = 2)
    public void testSubmitValidForm() {
        String email = "contact" + System.currentTimeMillis() + "@example.com";

        fillForm("Inquiry about blog collaboration", email,
                "Hi team, I would like to discuss a blog partnership opportunity.");
        submitForm();

        verifyAlertContains("successfully submitted", "Success alert not present after submitting valid form.");
    }

    @Test(priority = 3)
    public void testSubmitInvalidEmail() {
        driver.navigate().refresh();

        fillForm("Invalid Email Test", "invalid-email.com", "Test message with invalid email.");
        submitForm();

        verifyAlertContains("valid information", "Expected alert for invalid email, but none appeared.");
    }

    @Test(priority = 4)
    public void testSubmitEmptyFields() {
        driver.navigate().refresh();
        driver.findElement(submitButton).click();

        verifyAlertContains("fill out all fields", "Expected alert for empty fields submission, but none appeared.");
    }

    private void fillForm(String subject, String email, String content) {
        driver.findElement(subjectField).sendKeys(subject);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(contentField).sendKeys(content);
    }

    private void submitForm() {
        driver.findElement(submitButton).click();
    }

    private void verifyAlertContains(String expectedText, String errorMessage) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertTrue(alert.getText().toLowerCase().contains(expectedText.toLowerCase()), errorMessage);
            alert.accept();
        } catch (TimeoutException e) {
            Assert.fail(errorMessage);
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}