package com.mobile.automation.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * AssertionUtil - Utilitário para assertions customizadas
 */
public class AssertionUtil {

    /**
     * Asserção que um elemento está visível
     */
    public static void assertElementIsVisible(AppiumDriver driver, By locator, String message) {
        try {
            boolean visible = driver.findElements(locator).size() > 0;
            Assert.assertTrue(visible, message);
            LoggerUtil.passStep("Elemento visível: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Elemento não está visível: " + message);
            throw e;
        }
    }

    /**
     * Asserção que um elemento não está visível
     */
    public static void assertElementIsNotVisible(AppiumDriver driver, By locator, String message) {
        try {
            boolean notVisible = driver.findElements(locator).isEmpty();
            Assert.assertTrue(notVisible, message);
            LoggerUtil.passStep("Elemento não está visível: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Elemento está visível quando não deveria: " + message);
            throw e;
        }
    }

    /**
     * Asserção que um texto está presente
     */
    public static void assertTextIsPresent(String actualText, String expectedText, String message) {
        try {
            Assert.assertTrue(actualText.contains(expectedText), message);
            LoggerUtil.passStep("Texto presente: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Texto não encontrado: " + message);
            throw e;
        }
    }

    /**
     * Asserção que um valor é igual
     */
    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            LoggerUtil.passStep("Valores iguais: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Valores diferentes: " + message);
            throw e;
        }
    }

    /**
     * Asserção que um valor não é null
     */
    public static void assertNotNull(Object value, String message) {
        try {
            Assert.assertNotNull(value, message);
            LoggerUtil.passStep("Valor não é null: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Valor é null: " + message);
            throw e;
        }
    }

    /**
     * Asserção que uma condição é verdadeira
     */
    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            LoggerUtil.passStep("Condição verdadeira: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Condição falsa: " + message);
            throw e;
        }
    }

    /**
     * Asserção que uma condição é falsa
     */
    public static void assertFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition, message);
            LoggerUtil.passStep("Condição falsa: " + message);
        } catch (Exception e) {
            LoggerUtil.failStep("Condição verdadeira: " + message);
            throw e;
        }
    }
}

