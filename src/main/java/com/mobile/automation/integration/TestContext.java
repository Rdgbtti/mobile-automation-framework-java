package com.mobile.automation.integration;

import io.appium.java_client.AppiumDriver;
import com.mobile.automation.utils.WaitUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * TestContext - Armazena contexto global do teste (Framework)
 */
public class TestContext {
    private AppiumDriver driver;
    private WaitUtils waitUtils;
    private TestResult currentTestResult;
    private Map<String, Object> contextData;

    public TestContext() {
        this.contextData = new HashMap<>();
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }

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

    public void cleanUp() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignored
            }
        }
        contextData.clear();
    }
}

