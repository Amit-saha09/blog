package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class BlogPostViewTest {

    private WebDriver driver;

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

        // Load the Blog Post view HTML page (update path if needed)
        driver.get("file:///C:/Users/YourUsername/Desktop/blogview.html");
    }

    @Test(priority = 1)
    public void testPageTitle() {
        String expectedTitle = "Blog Post: How AngularJS Makes Web Development Easier";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match expected value.");
    }

    @Test(priority = 2)
    public void testNavbarLinks() {
        List<WebElement> navLinks = driver.findElements(By.cssSelector(".nav-links li a"));
        Assert.assertEquals(navLinks.size(), 4, "There should be 4 navigation links.");

        Assert.assertEquals(navLinks.get(0).getText(), "Home");
        Assert.assertEquals(navLinks.get(1).getText(), "About");
        Assert.assertEquals(navLinks.get(2).getText(), "Profile");
        Assert.assertEquals(navLinks.get(3).getText(), "Logout");
    }

    @Test(priority = 3)
    public void testBlogPostDetails() {
        WebElement authorInitials = driver.findElement(By.className("name-poster"));
        Assert.assertEquals(authorInitials.getText(), "AS", "Author initials should be AS.");

        WebElement authorName = driver.findElement(By.className("author-name"));
        Assert.assertEquals(authorName.getText(), "Amit Saha");

        WebElement postDate = driver.findElement(By.className("post-date"));
        Assert.assertTrue(
                postDate.getText().contains("December 3, 2024"),
                "Post date is incorrect or missing."
        );

        WebElement postTitle = driver.findElement(By.className("post-title"));
        Assert.assertEquals(postTitle.getText(), "How AngularJS Makes Web Development Easier");

        WebElement postBody = driver.findElement(By.className("post-body"));
        Assert.assertTrue(
                postBody.getText().contains("AngularJS simplifies web development"),
                "Post body should contain expected content."
        );
    }

    @Test(priority = 4)
    public void testFooterContentAndLinks() {
        WebElement footer = driver.findElement(By.className("footer"));
        Assert.assertTrue(footer.isDisplayed(), "Footer should be visible.");

        WebElement footerText = footer.findElement(By.tagName("p"));
        Assert.assertTrue(
                footerText.getText().contains("© 2024 BlogPost. All rights reserved."),
                "Footer text is incorrect or missing."
        );

        List<WebElement> footerLinks = footer.findElements(By.tagName("a"));
        Assert.assertEquals(footerLinks.size(), 3, "There should be 3 footer links.");

        Assert.assertEquals(footerLinks.get(0).getText(), "Privacy");
        Assert.assertEquals(footerLinks.get(1).getText(), "Terms");
        Assert.assertEquals(footerLinks.get(2).getText(), "Contact Us");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
