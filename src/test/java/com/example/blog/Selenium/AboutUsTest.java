package com.example.blog.Selenium;



import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class AboutUsTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Windows\\chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:8201/api/about-us"); // Adjust if your URL differs
    }

    @Test(priority = 1)
    public void testPageTitleAndHeroSection() {
        Assert.assertEquals(driver.getTitle(), "About Us - Blog Application", "Page title mismatch.");

        WebElement heroHeading = driver.findElement(By.cssSelector(".hero-text h1"));
        Assert.assertEquals(heroHeading.getText(), "ABOUT US", "Hero section title mismatch.");

        WebElement heroParagraph = driver.findElement(By.cssSelector(".hero-text p"));
        Assert.assertTrue(heroParagraph.getText().toLowerCase().contains("storytelling"), "Hero paragraph should mention storytelling.");
    }

    @Test(priority = 2)
    public void testHeaderNavigationLinks() {
        WebElement nav = driver.findElement(By.tagName("nav"));
        List<WebElement> links = nav.findElements(By.tagName("a"));

        Assert.assertTrue(links.stream().anyMatch(link -> link.getText().contains("Home")), "Home link should be visible.");
        Assert.assertTrue(links.stream().anyMatch(link -> link.getText().contains("FAQ")), "FAQ link should be visible.");
        Assert.assertTrue(links.stream().anyMatch(link -> link.getText().contains("Contact Us")), "Contact Us link should be visible.");
    }

    @Test(priority = 3)
    public void testWhoWeAreSection() {
        WebElement section = driver.findElement(By.cssSelector(".about-us h2"));
        Assert.assertEquals(section.getText(), "Who We Are", "Missing or incorrect 'Who We Are' heading.");

        WebElement aboutImg = driver.findElement(By.cssSelector(".about-img"));
        Assert.assertTrue(aboutImg.isDisplayed(), "Team image should be visible.");
    }

    @Test(priority = 4)
    public void testMissionSection() {
        WebElement missionTitle = driver.findElement(By.cssSelector(".mission h2"));
        Assert.assertEquals(missionTitle.getText(), "Our Mission");

        WebElement missionText = driver.findElement(By.cssSelector(".mission p"));
        Assert.assertTrue(missionText.getText().length() > 30, "Mission text should not be empty.");

        WebElement missionImg = driver.findElement(By.cssSelector(".decorative-img"));
        Assert.assertTrue(missionImg.isDisplayed(), "Mission image should be visible.");
    }

    @Test(priority = 5)
    public void testTeamSection() {
        WebElement teamTitle = driver.findElement(By.cssSelector(".team h2"));
        Assert.assertEquals(teamTitle.getText(), "Meet Our Team");

        List<WebElement> members = driver.findElements(By.className("team-member"));
        Assert.assertEquals(members.size(), 3, "There should be 3 team members.");

        for (WebElement member : members) {
            WebElement initials = member.findElement(By.className("team-member-initials"));
            WebElement name = member.findElement(By.tagName("h3"));
            WebElement role = member.findElement(By.tagName("p"));

            Assert.assertFalse(name.getText().isEmpty(), "Team member name should be visible.");
            Assert.assertFalse(role.getText().isEmpty(), "Team member role should be visible.");
        }
    }

    @Test(priority = 6)
    public void testFooterContent() {
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assert.assertTrue(footer.isDisplayed(), "Footer should be visible.");

        WebElement copyright =
                footer.findElement(By.tagName("p"));
        Assert.assertTrue(
                copyright.getText().contains("© 2024 BlogPost"),
                "Footer should display © notice."
        );

        List<WebElement> footerLinks = footer.findElements(By.tagName("a"));
        Assert.assertEquals(footerLinks.size(), 3, "Footer should have 3 links.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}