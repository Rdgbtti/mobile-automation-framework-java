package com.mobile.automation.integration;

/**
 * TestResult - Armazena resultado de um teste (Framework)
 */
public class TestResult {
    private String testName;
    private String status; // PASS, FAIL, SKIP
    private long executionTime;
    private String message;
    private String errorDetails;
    private String screenshotPath;

    public TestResult(String testName) {
        this.testName = testName;
        this.status = "PENDING";
        this.executionTime = 0;
        this.message = "";
        this.errorDetails = "";
        this.screenshotPath = "";
    }

    // Getters e Setters
    public String getTestName() {
        return testName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }

    public boolean isPassed() {
        return "PASS".equalsIgnoreCase(status);
    }

    public boolean isFailed() {
        return "FAIL".equalsIgnoreCase(status);
    }

    public boolean isSkipped() {
        return "SKIP".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "testName='" + testName + '\'' +
                ", status='" + status + '\'' +
                ", executionTime=" + executionTime +
                ", message='" + message + '\'' +
                '}';
    }
}

