package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class CreateBlogPostTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        // Set the path to ChromeDriver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Windows\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("http://localhost:8201/api/create-post");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @Test(priority = 1)
    public void testPageTitleAndUIElements() {
        Assert.assertEquals(driver.getTitle(), "Create New Post", "Page title mismatch.");

        WebElement heading = driver.findElement(By.className("form-title"));
        Assert.assertTrue(heading.isDisplayed(), "Form heading should be visible.");
        Assert.assertEquals(heading.getText(), "Create New BlogPost");

        // Form fields
        Assert.assertTrue(driver.findElement(By.id("title")).isDisplayed(), "Title field should be visible.");
        Assert.assertTrue(driver.findElement(By.id("category")).isDisplayed(), "Category dropdown should be visible.");
        Assert.assertTrue(driver.findElement(By.id("content")).isDisplayed(), "Content field should be visible.");
        Assert.assertTrue(driver.findElement(By.id("status")).isDisplayed(), "Status field should be visible.");

        // Action buttons
        Assert.assertTrue(driver.findElement(By.cssSelector(".btn.btn-primary")).isDisplayed(), "Submit button should be visible.");
        Assert.assertTrue(driver.findElement(By.cssSelector(".btn.btn-secondary")).isDisplayed(), "Cancel button should be visible.");
    }

    @Test(priority = 2)
    public void testCategoryDropdownPopulation() throws InterruptedException {
        // Simulate login by injecting a dummy token
        js.executeScript("localStorage.setItem('authToken', 'dummyAuthToken');");

        // Refresh to trigger category fetch
        driver.navigate().refresh();

        WebElement categoryDropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("category"))
        );
        Select categorySelect = new Select(categoryDropdown);

        Thread.sleep(3000); // ⚠️ Replace with dynamic wait if API timing varies

        Assert.assertTrue(
                categorySelect.getOptions().size() > 1,
                "Category dropdown should have more than one option."
        );
    }

    @Test(priority = 3)
    public void testFormSubmissionWithoutLogin() {
        // Remove token to simulate unauthenticated user
        js.executeScript("localStorage.removeItem('authToken');");

        // Fill out the form
        driver.findElement(By.id("title")).sendKeys("Unauthorized Test Post");
        driver.findElement(By.id("content")).sendKeys("Test content.");

        Select categorySelect = new Select(driver.findElement(By.id("category")));
        if (categorySelect.getOptions().size() > 1) {
            categorySelect.selectByIndex(1);
        }

        // Submit the form
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();

        // Wait for login redirection
        wait.until(ExpectedConditions.urlContains("/login"));
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("/login"),
                "Unauthenticated user should be redirected to login page."
        );
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
