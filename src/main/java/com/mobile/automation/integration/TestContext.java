package com.mobile.automation.integration;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import com.mobile.automation.utils.WaitUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * TestContext - Armazena contexto global do teste (Framework HÍBRIDO - Mobile + Web)
 */
public class TestContext {
    private AppiumDriver driver;           // Para testes MOBILE
    private WebDriver webDriver;          // Para testes WEB
    private WaitUtils waitUtils;
    private TestResult currentTestResult;
    private Map<String, Object> contextData;
    private String testType;              // "mobile" ou "web"

    public TestContext() {
        this.contextData = new HashMap<>();
        this.testType = "mobile"; // Padrão
    }

    // ===== MOBILE METHODS =====
    public AppiumDriver getDriver() {
        return driver;
    }

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }

    // ===== WEB METHODS =====
    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    // ===== COMMON METHODS =====
    public WaitUtils getWaitUtils() {
        return waitUtils;
    }

    public void setWaitUtils(WaitUtils waitUtils) {
        this.waitUtils = waitUtils;
    }

    public TestResult getCurrentTestResult() {
        return currentTestResult;
    }

    public void setCurrentTestResult(TestResult testResult) {
        this.currentTestResult = testResult;
    }

    public void putContextData(String key, Object value) {
        contextData.put(key, value);
    }

    public Object getContextData(String key) {
        return contextData.get(key);
    }

    public Map<String, Object> getAllContextData() {
        return new HashMap<>(contextData);
    }

    public void clearContextData() {
        contextData.clear();
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public boolean isMobileTest() {
        return "mobile".equalsIgnoreCase(testType);
    }

    public boolean isWebTest() {
        return "web".equalsIgnoreCase(testType);
    }

    public void cleanUp() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignored
            }
        }
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                // Ignored
            }
        }
        contextData.clear();
    }
}



