package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Mail extends BaseMail{
    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(css = "button.ph-login.svelte-ttryjx")
    private WebElement loginButton;

    @FindBy(css = "iframe.ag-popup__frame__layout__iframe")
    private WebElement loginIframe;

    @FindBy(name = "username")
    private WebElement emailField;

    @FindBy(css = "button[data-test-id='next-button']")
    private WebElement nextButton;

    @FindBy(css = "div[data-test-id='phone-confirmation-step-container']")
    private WebElement captchaContainer;

    @FindBy(css = "input[data-test-id='phone-confirmation-step-container__input_6']")
    private WebElement captchaInput1;

    @FindBy(css = "input[data-test-id='phone-confirmation-step-container__input_7']")
    private WebElement captchaInput2;

    @FindBy(css = "button[data-test-id='submit-button']")
    private WebElement submitButton;

    @FindBy(css = "div[aria-label='testing20252025@mail.ru']")
    private WebElement userIcon;

    @FindBy(css = "div[data-testid='whiteline-account-exit']")
    private WebElement logoutButton;

    public Mail(WebDriver driver, final WebDriverWait wait) {
        super(driver);
    }

    public void searchMailRu(String query) {
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    public boolean clickMailRuLink(WebDriverWait wait) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id='search']//a"));
        for (WebElement result : searchResults) {
            String link = result.getDomAttribute("href");
            if (link != null && link.contains("mail.ru")) {
                result.click();
                return true;
            }
        }
        return false;
    }

    public void login(String email, String captcha1, String captcha2, WebDriverWait wait) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOf(loginIframe));
        driver.switchTo().frame(loginIframe);

        emailField.sendKeys(email);
        nextButton.click();

        wait.until(ExpectedConditions.visibilityOf(captchaContainer));
        captchaInput1.sendKeys(captcha1);
        captchaInput2.sendKeys(captcha2);

        Thread.sleep(15000);

        submitButton.click();
        driver.switchTo().defaultContent();
    }

    public void logout(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
}
