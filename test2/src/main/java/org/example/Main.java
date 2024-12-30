package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        // Set the path for the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\chromedriver.exe");

        // Set up ChromeDriver options (optional, for headless or custom settings)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-web-security");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open Google homepage
            driver.get("https://www.google.com");

            // Accept cookies if prompted (this depends on region and browser state)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Accept all') or contains(text(), 'I agree')]")));
                acceptButton.click();
            } catch (Exception e) {
                System.out.println("No cookie prompt displayed.");
            }

            // Locate the search bar and enter the search query
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("mail.ru");
            searchBox.submit();

            // Wait for the results to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));

            // Verify if 'mail.ru' is present in the search results and click it
            List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id='search']//a"));
            boolean isMailRuPresent = false;

            for (WebElement result : searchResults) {
                String link = result.getDomAttribute("href");
                if (link != null && link.contains("mail.ru")) {
                    isMailRuPresent = true;
                    System.out.println("Found mail.ru link: " + link);

                    // Click the link to navigate to the Mail.ru website
                    result.click();
                    break;
                }
            }

            // Output the test result
            if (isMailRuPresent) {
                System.out.println("Test Passed: Navigated to 'mail.ru'.");
            } else {
                System.out.println("Test Failed: 'mail.ru' not found in search results.");
                return;
            }

            acceptCookiesIfPresent(driver, wait, "//button[contains(text(), 'Accept all') or contains(text(), 'Accept')]");

            // Navigate to the login page
            WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-nav-link")));
            loginLink.click();
            System.out.println("Clicked login link.");

            acceptCookiesIfPresent(driver, wait, "//button[contains(text(), 'Accept all') or contains(text(), 'Accept')]");

            // Wait for the login page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-container")));
            System.out.println("Login modal loaded successfully!");

            // Enter email/username
            WebElement emailField = driver.findElement(By.cssSelector("input#sso-email"));
            emailField.sendKeys("2018nightmare19@gmail.com"); // Replace with actual username

            // Enter password
            WebElement passwordField = driver.findElement(By.cssSelector("input#sso-password"));
            passwordField.sendKeys("SayMyName2003"); // Replace with actual password

            // Click the login button
            WebDriverWait waitLog = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement loginButton = waitLog.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
            loginButton.click();
            Thread.sleep(10000);

            // Additional logic for handling post-login verification can be added here

        } catch (Exception e) {
            System.err.println("An error occurred during the test: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }

    }
    private static void acceptCookiesIfPresent(WebDriver driver, WebDriverWait wait, String xpath) {
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            cookieButton.click();
            System.out.println("Cookies accepted.");
        } catch (Exception e) {
            System.out.println("No cookie prompt displayed.");
        }
    }}
