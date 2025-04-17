package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UserProfileTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://localhost:8201/api/user-profile";

    // Locators
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By phoneField = By.id("phone");
    private final By saveButton = By.cssSelector(".primary-btn");

    @BeforeTest
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Windows\\chromedriver.exe"
        );
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(baseUrl);
        setLocalStorage("userEmail", "test@example.com");
        setLocalStorage("authToken", "your-valid-jwt-token");

        driver.navigate().refresh();
    }

    @Test
    public void testLoadAndEditUserProfile() {
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        WebElement lastNameInput = driver.findElement(lastNameField);
        WebElement phoneInput = driver.findElement(phoneField);
        WebElement submitButton = driver.findElement(saveButton);

        Assert.assertFalse(firstNameInput.getAttribute("value").isEmpty(), "First name field is empty.");
        Assert.assertFalse(lastNameInput.getAttribute("value").isEmpty(), "Last name field is empty.");

        updateInputField(firstNameInput, "UpdatedFirstName");
        updateInputField(lastNameInput, "UpdatedLastName");
        updateInputField(phoneInput, "1234567890");

        submitButton.click();

        String expectedUrl = "http://localhost:8201/api/home";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "URL mismatch after saving profile.");
    }

    private void setLocalStorage(String key, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("localStorage.setItem('%s', '%s');", key, value));
    }

    private void updateInputField(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}