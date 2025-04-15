package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.List;

public class HomePageTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        // Set the path to ChromeDriver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8201/api/home");
    }

    @Test(priority = 1)
    public void testPageTitle() {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Welcome to Blog Application", "Page title mismatch!");
    }

    @Test(priority = 2)
    public void testHeaderLinks() {
        Assert.assertTrue(driver.findElement(By.linkText("About Us")).isDisplayed(), "'About Us' link should be visible.");
        Assert.assertTrue(driver.findElement(By.linkText("FAQ")).isDisplayed(), "'FAQ' link should be visible.");
        Assert.assertTrue(driver.findElement(By.linkText("Login")).isDisplayed(), "'Login' link should be visible.");
        Assert.assertTrue(driver.findElement(By.linkText("Sign-up")).isDisplayed(), "'Sign-up' link should be visible.");
    }

    @Test(priority = 3)
    public void testExplorePostsButton() {
        WebElement exploreBtn = driver.findElement(By.linkText("Explore Posts"));
        Assert.assertTrue(exploreBtn.isDisplayed(), "'Explore Posts' button should be visible.");
        Assert.assertEquals(exploreBtn.getAttribute("href"), "http://localhost:8201/api/blog-post", "Explore button URL mismatch.");
    }

    @Test(priority = 4)
    public void testRecentBlogPosts() {
        List<WebElement> posts = driver.findElements(By.className("blog-post"));
        Assert.assertEquals(posts.size(), 3, "There should be exactly 3 blog posts.");

        for (WebElement post : posts) {
            WebElement title = post.findElement(By.tagName("h3"));
            WebElement link = post.findElement(By.tagName("a"));

            Assert.assertTrue(title.isDisplayed(), "Blog post title should be visible.");
            Assert.assertTrue(link.getAttribute("href").contains("post"), "Blog post link should be valid.");
        }
    }

    @Test(priority = 5)
    public void testAboutSectionContent() {
        WebElement missionHeading = driver.findElement(By.xpath("//h2[text()='Our Mission']"));
        WebElement valuesHeading = driver.findElement(By.xpath("//h2[text()='Our Values']"));

        Assert.assertTrue(missionHeading.isDisplayed(), "'Our Mission' section should be visible.");
        Assert.assertTrue(valuesHeading.isDisplayed(), "'Our Values' section should be visible.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
