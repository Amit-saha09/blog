package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserProfileTest {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("about:blank");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('userEmail', 'test@example.com');");
        js.executeScript("localStorage.setItem('authToken', 'your-valid-jwt-token');");

        String baseUrl = "http://localhost:8201/api/user-profile";
        driver.navigate().to(baseUrl);
    }

    @Test
    public void testLoadAndEditUserProfile() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        WebElement phoneInput = driver.findElement(By.id("phone"));
        WebElement saveButton = driver.findElement(By.cssSelector(".primary-btn"));

        Assert.assertFalse(firstNameInput.getAttribute("value").isEmpty());
        Assert.assertFalse(lastNameInput.getAttribute("value").isEmpty());

        firstNameInput.clear();
        firstNameInput.sendKeys("UpdatedFirstName");

        lastNameInput.clear();
        lastNameInput.sendKeys("UpdatedLastName");

        phoneInput.clear();
        phoneInput.sendKeys("1234567890");

        saveButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8201/api/home"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8201/api/home");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
