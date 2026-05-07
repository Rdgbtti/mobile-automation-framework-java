package com.mobile.automation.base;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;

/**
 * BasePage - Classe base para todas as Page Objects
 * Contém métodos reutilizáveis para interações com elementos
 */
public class BasePage {
    protected AppiumDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(AppiumDriver driver, WaitUtils waitUtils) {
        this.driver = driver;
        this.waitUtils = waitUtils;
    }

    /**
     * Clica em um elemento
     */
    public void click(By locator) {
        try {
            WebElement element = waitUtils.waitForElementToBeClickable(locator);
            element.click();
            LoggerUtil.passStep("Clique realizado em: " + locator);
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao clicar em: " + locator + " - " + e.getMessage());
            throw e;
        }
    }

    /**
     * Preenche um campo de texto
     */
    public void sendKeys(By locator, String text) {
        try {
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            LoggerUtil.passStep("Texto inserido em: " + locator);
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao inserir texto em: " + locator + " - " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtém texto de um elemento
     */
    public String getText(By locator) {
        try {
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            String text = element.getText();
            LoggerUtil.info("Texto obtido de: " + locator + " - " + text);
            return text;
        } catch (Exception e) {
            LoggerUtil.error("Erro ao obter texto de: " + locator + " - " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifica se um elemento está visível
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            boolean displayed = element.isDisplayed();
            LoggerUtil.info("Elemento visível: " + locator + " - " + displayed);
            return displayed;
        } catch (Exception e) {
            LoggerUtil.info("Elemento não está visível: " + locator);
            return false;
        }
    }

    /**
     * Verifica se um elemento está presente no DOM
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se um elemento está habilitado
     */
    public boolean isElementEnabled(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o valor de um atributo
     */
    public String getAttribute(By locator, String attributeName) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao obter atributo: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Aguarda um elemento estar visível
     */
    public void waitForElement(By locator) {
        try {
            waitUtils.waitForElementToBeVisible(locator);
            LoggerUtil.info("Elemento aguardado com sucesso: " + locator);
        } catch (Exception e) {
            LoggerUtil.error("Elemento não apareceu no tempo esperado: " + locator);
            throw e;
        }
    }

    /**
     * Aguarda um elemento estar clicável
     */
    public void waitForElementClickable(By locator) {
        try {
            waitUtils.waitForElementToBeClickable(locator);
            LoggerUtil.info("Elemento clicável aguardado com sucesso: " + locator);
        } catch (Exception e) {
            LoggerUtil.error("Elemento não ficou clicável no tempo esperado: " + locator);
            throw e;
        }
    }

    /**
     * Scroll para um elemento
     */
    public void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            driver.executeScript("arguments[0].scrollIntoView(true);", element);
            LoggerUtil.info("Scroll para elemento realizado: " + locator);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao fazer scroll para elemento: " + e.getMessage());
        }
    }

    /**
     * Obtém o driver
     */
    public AppiumDriver getDriver() {
        return driver;
    }

    /**
     * Obtém a instância de WaitUtils
     */
    public WaitUtils getWaitUtils() {
        return waitUtils;
    }
}

