package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class SearchPost {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Windows\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("http://localhost:8201/api/blog-post");

        // OPTIONAL: Inject authToken if your search API requires it
        js.executeScript("localStorage.setItem('authToken', 'your-valid-jwt-token');");
        driver.navigate().refresh();
    }

    @Test(priority = 1)
    public void testValidSearchWithAllInputs() {
        WebElement emailField = driver.findElement(By.id("search"));
        WebElement descriptionField = driver.findElement(By.id("search-box"));
        WebElement categoryDropdown = driver.findElement(By.id("category"));
        WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));

        emailField.clear();
        emailField.sendKeys("amit@gmail.com");

        descriptionField.clear();
        descriptionField.sendKeys("Angular");

        wait.until(driver -> categoryDropdown.findElements(By.tagName("option")).size() > 1);
        new Select(categoryDropdown).selectByIndex(1);

        searchButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("blog")));
        List<WebElement> posts = driver.findElements(By.cssSelector(".blog-feed .post"));
        System.out.println("Total posts found: " + posts.size());

        Assert.assertTrue(posts.size() > 0, "Expected results with valid inputs.");
    }

    @Test(priority = 2)
    public void testEmptySearchDefaults() {
        driver.navigate().refresh();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Search']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("blog")));

        List<WebElement> posts = driver.findElements(By.cssSelector(".blog-feed .post"));
        System.out.println("Default posts: " + posts.size());

        Assert.assertTrue(posts.size() > 0, "Expected default blog posts with empty search.");
    }

    @Test(priority = 3)
    public void testInvalidEmailSearch() {
        driver.navigate().refresh();

        driver.findElement(By.id("search")).sendKeys("invalid@unknown.com");
        driver.findElement(By.xpath("//button[text()='Search']")).click();

        waitUntilBlogContainerStable();

        List<WebElement> posts = driver.findElements(By.cssSelector(".blog-feed .post"));
        System.out.println("Posts for invalid email: " + posts.size());

        Assert.assertEquals(posts.size(), 0, "No posts should be returned for invalid email.");
    }

    @Test(priority = 4)
    public void testGibberishSearch() {
        driver.navigate().refresh();

        driver.findElement(By.id("search-box")).sendKeys("asf9a8s7d6g5fd");
        driver.findElement(By.xpath("//button[text()='Search']")).click();

        waitUntilBlogContainerStable();

        List<WebElement> posts = driver.findElements(By.cssSelector(".blog-feed .post"));
        System.out.println("Posts for gibberish text: " + posts.size());

        Assert.assertEquals(posts.size(), 0, "No results should match gibberish text.");
    }

    @Test(priority = 5)
    public void testCategoryOnlySearch() {
        driver.navigate().refresh();

        WebElement categoryDropdown = driver.findElement(By.id("category"));
        wait.until(driver -> categoryDropdown.findElements(By.tagName("option")).size() > 1);

        new Select(categoryDropdown).selectByIndex(1);
        driver.findElement(By.xpath("//button[text()='Search']")).click();

        waitUntilBlogContainerStable();

        List<WebElement> posts = driver.findElements(By.cssSelector(".blog-feed .post"));
        System.out.println("Posts for category-only search: " + posts.size());

        Assert.assertTrue(posts.size() >= 0, "Category-only search should not crash.");
    }

    private void waitUntilBlogContainerStable() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("blog")));
        try {
            Thread.sleep(1500); // Allow JS to render results (you can replace this with smarter waits)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}