package com.mobile.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * WaitUtils - Classe para gerenciar esperas explícitas e implícitas (Framework)
 */
public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;
    private int explicitWaitTime;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.explicitWaitTime = ConfigReader.getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
        setImplicitWait();
    }

    private void setImplicitWait() {
        int implicitWait = ConfigReader.getImplicitWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElement(By locator, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean waitForTextToBePresentInElement(By locator, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForUrlToBePresentInCurrentUrl(String urlPortion) {
        return wait.until(ExpectedConditions.urlContains(urlPortion));
    }

    public void waitForCondition(long timeoutSeconds, long pollingIntervalMs, java.util.function.Predicate<WebDriver> condition) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        customWait.pollingEvery(Duration.ofMillis(pollingIntervalMs));
        customWait.until(d -> condition.test(d));
    }

    public static void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.error("Erro na espera: " + e.getMessage());
        }
    }

    public static void waitSeconds(int seconds) {
        hardWait(seconds * 1000L);
    }
}

