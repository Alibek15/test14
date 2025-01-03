package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DateSelection extends BaseBooking {
    private By date1 = By.cssSelector("div[data-test-id='date-12.01.2025']");
    private By date2 = By.cssSelector("div[data-test-id='date-31.01.2025']");

    public DateSelection(WebDriver driver) {
        super(driver);
    }

    public void select1Date() {
        clickElement(date1);
    }

    public void select2Date() {
        clickElement(date2);
    }
}
