package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class BlogPostTest {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:/Users/patel/Desktop/Winter-2025/CIS-565/chromedriver-win64/chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:8201/api/blog-post");
    }

    @Test(priority = 1)
    public void testPageTitle() {
        String expectedTitle = "Professional Blog";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title doesn't match.");
    }

    @Test(priority = 2)
    public void testNavbarLinksPresent() {
        String[] navLinks = {"Home", "About", "Profile", "Logout"};
        for (String linkText : navLinks) {
            WebElement link = driver.findElement(By.linkText(linkText));
            Assert.assertTrue(link.isDisplayed(), linkText + " link should be visible.");
        }
    }

    @Test(priority = 3)
    public void testCategoryDropdownLoads() throws InterruptedException {
        WebElement dropdown = driver.findElement(By.id("category"));
        Thread.sleep(2000); // Better to replace this with WebDriverWait in production
        List<WebElement> options = dropdown.findElements(By.tagName("option"));
        Assert.assertTrue(options.size() > 1, "Dropdown should have more than one option.");
    }

    @Test(priority = 4)
    public void testSearchBlogPosts() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("search"));
        WebElement descriptionField = driver.findElement(By.id("search-box"));
        WebElement categoryDropdown = driver.findElement(By.id("category"));
        WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));

        emailField.clear();
        emailField.sendKeys("admin@example.com");
        descriptionField.clear();
        descriptionField.sendKeys("web");
        categoryDropdown.sendKeys("Technology");
        searchButton.click();

        Thread.sleep(2000); // Better to replace this with WebDriverWait in production
        List<WebElement> posts = driver.findElements(By.cssSelector(".blog-feed .post"));
        Assert.assertFalse(posts.isEmpty(), "At least one blog post should be displayed.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
