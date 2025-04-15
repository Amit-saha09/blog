package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LogoutTest {

    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Windows\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:8201/api/log-out"); // Adjust this if it's not the actual logout route

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void verifyLogoutPageTitleAndMessage() {
        Assert.assertEquals(driver.getTitle(), "Logout", "Page title should be 'Logout'.");

        WebElement message = driver.findElement(By.tagName("h1"));
        Assert.assertTrue(message.isDisplayed(), "Logout confirmation heading should be visible.");
        Assert.assertTrue(
                message.getText().contains("You've Logged Out"),
                "Expected logout message not found."
        );
    }

    @Test(priority = 2)
    public void verifyLocalStorageAndSessionStorageAreCleared() {
        // Set dummy auth data
        js.executeScript("localStorage.setItem('authToken', 'dummyToken');");
        js.executeScript("sessionStorage.setItem('userEmail', 'user@example.com');");

        // Reload to trigger logout logic
        driver.navigate().refresh();

        // Dummy wait to ensure JS executes (replace with real element wait if possible)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dummy")));

        long localStorageSize = (Long) js.executeScript("return localStorage.length;");
        long sessionStorageSize = (Long) js.executeScript("return sessionStorage.length;");

        Assert.assertEquals(localStorageSize, 0, "localStorage should be cleared after logout.");
        Assert.assertEquals(sessionStorageSize, 0, "sessionStorage should be cleared after logout.");
    }

    @Test(priority = 3)
    public void verifyLoginAgainButtonRedirects() {
        WebElement loginAgainButton = driver.findElement(By.className("button-primary"));
        loginAgainButton.click();

        wait.until(ExpectedConditions.urlContains("/login"));

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("/login"), "User should be redirected to the login page.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
