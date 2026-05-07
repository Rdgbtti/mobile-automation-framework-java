package com.mobile.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * WaitUtils - Classe para gerenciar esperas explícitas e implícitas
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

    /**
     * Define tempo de espera implícita
     */
    private void setImplicitWait() {
        int implicitWait = ConfigReader.getImplicitWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
    }

    /**
     * Espera por um elemento estar visível
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Espera por um elemento estar presente
     */
    public WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Espera por um elemento estar clicável
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Espera por um elemento com tempo customizado
     */
    public WebElement waitForElement(By locator, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Espera por um texto estar presente em um elemento
     */
    public boolean waitForTextToBePresentInElement(By locator, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Espera por URL conter valor
     */
    public boolean waitForUrlToBePresentInCurrentUrl(String urlPortion) {
        return wait.until(ExpectedConditions.urlContains(urlPortion));
    }

    /**
     * Espera genérica usando condição customizada
     */
    public void waitForCondition(long timeoutSeconds, long pollingIntervalMs, java.util.function.Predicate<WebDriver> condition) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        customWait.pollingEvery(Duration.ofMillis(pollingIntervalMs));
        customWait.until(d -> condition.test(d));
    }

    /**
     * Pausa de tempo fixo (usar com moderação)
     */
    public static void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.error("Erro na espera: " + e.getMessage());
        }
    }

    /**
     * Pausa de tempo fixo em segundos
     */
    public static void waitSeconds(int seconds) {
        hardWait(seconds * 1000L);
    }
}

