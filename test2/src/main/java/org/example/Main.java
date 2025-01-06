package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.time.Duration;
import java.util.function.Function;

public class Main extends Setup{
    private static Document pdfDocument;
    private static FileOutputStream pdfOutputStream;

    public static void main(String[] args) {

        try {

            initializePDF("Test_Report.pdf");

            Main test = new Main();
            test.testMail();
            test.runTest();
            test.testTo();
            test.testThree();
            test.testFour();
            test.testActions();
            test.testSelect("3");


            finalizePDF();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void testMail(){
        setup();
        Mail mailRuPage = new Mail(driver,wait);


        try {
            driver.get("https://www.google.com");


            mailRuPage.searchMailRu("Mail ru");

            if (mailRuPage.clickMailRuLink(wait)) {
                System.out.println("Test Passed: Navigated to 'mail.ru'.");
            } else {
                System.out.println("Test Failed: 'mail.ru' not found in search results.");
                return;
            }

            mailRuPage.login("testing20252025@mail.ru", "1", "0", wait);
            System.out.println("Login successful!");
            takeScreenshot("testMail_Login");
            Thread.sleep(3000);

            mailRuPage.logout(wait);
            System.out.println("Logged out successfully!");
            takeScreenshot("testMail_Logout");
        } catch (Exception e) {
            System.err.println("An error occurred during the test: " + e.getMessage());
        } finally {
           tearDown();
        }
    }


    public void runTest() {
        setup();

        try {
            Booking homePage = new Booking(driver);
            DateSelection dateSelectionPage = new DateSelection(driver);


            driver.get("https://www.aviasales.ru/");
            Thread.sleep(2000);


            homePage.inputFromCity("Астана");




            homePage.openDatePicker();
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("document.querySelector('div.s__y2gjNiIS0o1RA_5Ebnkw').style.display = 'none';");
            dateSelectionPage.select1Date();
            dateSelectionPage.select2Date();

            homePage.inputToCity("Алматы");


            WebElement showAllTicketsButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[data-test-id='show-all-tickets-button']")
            ));
            System.out.println("The 'Show all tickets' button was found!");
            takeScreenshot("runTest_ShowAllTicketsButton");


            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showAllTicketsButton);



            Thread.sleep(5000);
            System.out.println("Tickets displayed.");
            takeScreenshot("runTest_ShowAllTicketsButton");

            System.out.println("Test Passed: Successfully navigated to ticket results.");
            Thread.sleep(5000);

        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }

    private static void initializePDF(String fileName) throws Exception {
        pdfDocument = new Document();
        pdfOutputStream = new FileOutputStream(fileName);
        PdfWriter.getInstance(pdfDocument, pdfOutputStream);
        pdfDocument.open();
        pdfDocument.add(new Paragraph("Test Report"));
        pdfDocument.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now()));
        pdfDocument.add(new Paragraph("\n"));
    }
    private void takeScreenshot(String testName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "screenshots/" + testName + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
            addScreenshotToPDF(testName, screenshotPath);
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    private void addScreenshotToPDF(String testName, String screenshotPath) {
        try {
            pdfDocument.add(new Paragraph("Test: " + testName));
            pdfDocument.add(Image.getInstance(screenshotPath));
            pdfDocument.add(new Paragraph("\n"));
        } catch (Exception e) {
            System.err.println("Failed to add screenshot to PDF: " + e.getMessage());
        }
    }
    private static void finalizePDF() throws Exception {
        if (pdfDocument != null) {
            pdfDocument.close();
        }
        if (pdfOutputStream != null) {
            pdfOutputStream.close();
        }
    }

    public void testTo() {
        setup();

        try {


            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


            driver.get("https://shop.kz/");


            WebElement searchBox = driver.findElement(By.cssSelector(".search-hover__field"));


            searchBox.sendKeys("Laptops");


            WebElement searchButton = driver.findElement(By.cssSelector(".search-hover__submit"));
            searchButton.click();
            System.out.println("Test Passed: Searched for laptops.");
            takeScreenshot("testTo_SearchLaptops");
        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
    public void testThree() {
        setup();

        try {

            driver.get("https://shop.kz/");


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".search-hover__field")));


            searchBox.sendKeys("ssd");

            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".search-hover__submit")));
            searchButton.click();


            System.out.println("Test Passed: Successfully entered search query and clicked the search button.");

            takeScreenshot("testThree_SearchSSD");
        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
    public void testFour() {
        setup();

        try {

            driver.get("https://shop.kz/");


            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);


            WebElement searchBox = fluentWait.until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    WebElement element = driver.findElement(By.cssSelector(".search-hover__field"));
                    if (element.isDisplayed() && element.isEnabled()) {
                        return element;
                    }
                    return null;
                }
            });


            searchBox.sendKeys("планшет");


            WebElement searchButton = fluentWait.until(driver -> driver.findElement(By.cssSelector(".search-hover__submit")));
            searchButton.click();


            System.out.println("Test Passed: Successfully entered search query and clicked the search button.");
            takeScreenshot("testFour_SearchПланшет");
        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
    public void testActions() {
        setup();

        try {

            driver.get("https://shop.kz/");


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement catalogButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".bx-top-nav-button[data-role='bx-menu-button']")
            ));


            Actions actions = new Actions(driver);
            actions.moveToElement(catalogButton).click().perform();
            Thread.sleep(3000);

            System.out.println("Test Passed: Successfully clicked the 'Каталог' button.");
            takeScreenshot("testActions_Click");
        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
    public void testSelect(String cityId) {
        setup();

        try {

            driver.get("https://shop.kz/");


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement regionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#region-selector .rs-btn")
            ));
            regionButton.click();


            WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".city-default-list")
            ));


            WebElement cityOption = dropdownMenu.findElement(
                    By.xpath(".//a[@data-city-id='" + cityId + "']")
            );
            cityOption.click();

            System.out.println("Test Passed: Successfully selected the city with ID " + cityId);
            Thread.sleep(3000);
            takeScreenshot("testSelect_Click");
        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
}
