package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import org.testng.Assert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--remote-allow-origins=*",
                "--ignore-certificate-errors",
                "--disable-web-security",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-extensions",
                "--disable-infobars",
                "--start-maximized",
                "--disable-blink-features=AutomationControlled",
                "--disable-notifications"
        );

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        waitForAppToBeReady("http://localhost:8201/api/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void waitForAppToBeReady(String url) {
        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();

                driver.get(url);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Unable to connect after " + maxAttempts + " attempts. Check if the application is running.");
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Test(priority = 1)
    public void verifyLoginPageLoads() {
        Assert.assertEquals(driver.getTitle(), "Login - BlogPost", "Incorrect page title.");

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.className("login-btn"));

        Assert.assertTrue(email.isDisplayed(), "Email field should be visible.");
        Assert.assertTrue(password.isDisplayed(), "Password field should be visible.");
        Assert.assertTrue(loginBtn.isDisplayed(), "Login button should be visible.");
    }

    @Test(priority = 2)
    public void testSuccessfulLogin() {
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.className("login-btn"));

        email.clear();
        email.sendKeys("admin@example.com");

        password.clear();
        password.sendKeys("password123");

        loginBtn.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/home"),
                ExpectedConditions.urlContains("/userBlogList")
        ));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("/home") || currentUrl.contains("/userBlogList"),
                "Expected redirection after successful login. Current URL: " + currentUrl
        );
    }

    @Test(priority = 3)
    public void testInvalidLogin() {
        driver.get("http://localhost:8201/api/login");

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.className("login-btn"));

        email.clear();
        email.sendKeys("wronguser@example.com");

        password.clear();
        password.sendKeys("wrongpassword");

        loginBtn.click();

        WebElement responseMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("responseMessage"))
        );

        Assert.assertTrue(
                responseMsg.getText().toLowerCase().contains("error"),
                "Expected error message on invalid login."
        );
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
