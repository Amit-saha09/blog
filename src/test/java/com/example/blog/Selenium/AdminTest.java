package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class AdminTest {

    public static void main(String[] args) {
        // Set path to ChromeDriver
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe");

        // Initialize ChromeOptions and WebDriver
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Load the admin dashboard page
            driver.get("http://localhost:5500/admin-dashboard.html"); // Adjust if needed

            // Inject localStorage auth token and user type
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("localStorage.setItem('authToken', 'your_valid_jwt_token_here');");
            js.executeScript("localStorage.setItem('userType', 'blog-admin');");

            // Refresh page to apply localStorage
            driver.navigate().refresh();

            // Check if Admin Dashboard header is present
            WebElement heading = driver.findElement(By.tagName("h1"));
            if ("Admin Dashboard".equals(heading.getText())) {
                System.out.println("✅ Admin Dashboard page loaded.");
            }

            // Click "View All Users"
            WebElement viewAllBtn = driver.findElement(By.cssSelector(".view-all-btn"));
            viewAllBtn.click();
            Thread.sleep(3000); // Wait for users to load (can be replaced by explicit waits)

            // Search for a specific user
            WebElement searchInput = driver.findElement(By.id("searchInput"));
            searchInput.sendKeys("test@example.com");

            WebElement searchBtn = driver.findElement(By.cssSelector(".search-btn"));
            searchBtn.click();
            Thread.sleep(3000); // Wait for search to complete

            // Check if user table has rows
            WebElement userTable = driver.findElement(By.id("userTable"));
            int rowCount = userTable.findElements(By.tagName("tr")).size();

            if (rowCount > 1) {
                System.out.println("✅ Users loaded into the table.");
            } else {
                System.out.println("⚠️ No users found or table is empty.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close browser after delay
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
            driver.quit();
        }
    }
}
