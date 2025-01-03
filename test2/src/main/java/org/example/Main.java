package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;

public class Main extends Setup{

    public static void main(String[] args) {



        Main test = new Main();
        test.testMail();
        test.runTest();

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

            mailRuPage.logout(wait);
            System.out.println("Logged out successfully!");
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


            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showAllTicketsButton);



            Thread.sleep(5000);
            System.out.println("Tickets displayed.");

            System.out.println("Test Passed: Successfully navigated to ticket results.");
            Thread.sleep(5000);

        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
}
