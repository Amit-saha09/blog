package com.example.blog.Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class UserListTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\patel\\Desktop\\Winter-2025\\CIS-565\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // TODO: Replace with actual file path or hosted URL
        driver.get("file:///C:/Users/YourUsername/Desktop/userlist.html");
    }

    @Test(priority = 1)
    public void testPageTitleAndHeaders() {
        Assert.assertEquals(driver.getTitle(), "Active Users", "Page title should be 'Active Users'.");

        WebElement mainHeader = driver.findElement(By.tagName("h1"));
        Assert.assertTrue(mainHeader.isDisplayed(), "Main header should be visible.");
        Assert.assertEquals(mainHeader.getText(), "ACTIVE USERS", "Main header text mismatch.");

        WebElement subHeader = driver.findElement(By.tagName("h2"));
        Assert.assertTrue(
                subHeader.getText().contains("All Active Users"),
                "Subheading should contain 'All Active Users'."
        );
    }

    @Test(priority = 2)
    public void testTableStructureAndContent() {
        WebElement table = driver.findElement(By.tagName("table"));
        Assert.assertTrue(table.isDisplayed(), "User table should be visible.");

        List<WebElement> headers = table.findElements(By.tagName("th"));
        String[] expectedHeaders = {"User ID", "Username", "Email", "Status"};

        for (int i = 0; i < expectedHeaders.length; i++) {
            Assert.assertEquals(headers.get(i).getText(), expectedHeaders[i], "Table header mismatch at index " + i);
        }

        List<WebElement> rows = table.findElements(By.tagName("tr"));
        Assert.assertTrue(rows.size() >= 3, "Expected at least 2 data rows plus 1 header row.");

        List<WebElement> statusCells = driver.findElements(By.className("status"));
        for (WebElement status : statusCells) {
            Assert.assertEquals(status.getText().trim().toLowerCase(), "active", "User status should be 'Active'.");
        }
    }

    @Test(priority = 3)
    public void testSearchAndNavigation() {
        WebElement searchBox = driver.findElement(By.className("search-box"));
        WebElement searchButton = driver.findElement(By.className("search-button"));

        Assert.assertTrue(searchBox.isDisplayed(), "Search input should be visible.");
        Assert.assertTrue(searchButton.isDisplayed(), "Search button should be visible.");

        searchBox.sendKeys("john");
        searchButton.click();
        // Note: Add assertions based on actual search result behavior if available.

        WebElement backButton = driver.findElement(By.className("btn"));
        Assert.assertTrue(backButton.isDisplayed(), "Back to Dashboard button should be visible.");
        Assert.assertEquals(backButton.getText(), "Back to Dashboard", "Back button label mismatch.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
