package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Booking extends BaseBooking {
    private By fromInput = By.id("avia_form_origin-input");
    private By toInput = By.id("avia_form_destination-input");
    private By datePicker = By.cssSelector("div[data-test-id='start-date-value']");




    public Booking(final WebDriver driver) {
        super(driver);
    }

    public void inputFromCity(String city) {
        sendKeysToElement(fromInput, city);
    }

    public void inputToCity(String city) {
        sendKeysToElement(toInput, city);
    }

    public void openDatePicker() {
        clickElement(datePicker);
    }


}
