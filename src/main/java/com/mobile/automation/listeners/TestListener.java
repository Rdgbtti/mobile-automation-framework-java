package com.mobile.automation.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.ScreenshotUtil;
import com.mobile.automation.integration.JiraConnector;
import com.mobile.automation.integration.TestResult;
import io.appium.java_client.AppiumDriver;

/**
 * TestListener - Listener TestNG para capturar eventos de teste (Framework)
 * Captura: SUCESSO, FALHA, SKIPPED
 */
public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        LoggerUtil.info("========== Iniciando Suite: " + context.getName() + " ==========");
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerUtil.info("========== Suite Finalizada: " + context.getName() + " ==========");
        LoggerUtil.info("Total de testes: " + context.getAllTestMethods().length);
        LoggerUtil.info("Sucesso: " + context.getPassedTests().size());
        LoggerUtil.info("Falha: " + context.getFailedTests().size());
        LoggerUtil.info("Skipped: " + context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtil.testInfo(result.getMethod().getMethodName(), "Iniciando teste...");
    }

    /**
     * Captura sucesso do teste
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        long executionTime = result.getEndMillis() - result.getStartMillis();

        LoggerUtil.passStep("Teste " + testName + " executado com SUCESSO em " + executionTime + "ms");

        // Criar e registrar resultado
        TestResult testResult = new TestResult(testName);
        testResult.setStatus("PASS");
        testResult.setExecutionTime(executionTime);
        testResult.setMessage("Teste passou com sucesso");

        // Atualizar JIRA se configurado
        updateJiraIfNeeded(result, testResult);
    }

    /**
     * Captura falha do teste
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        long executionTime = result.getEndMillis() - result.getStartMillis();
        Throwable exception = result.getThrowable();

        LoggerUtil.failStep("Teste " + testName + " FALHOU em " + executionTime + "ms");
        LoggerUtil.error("Erro: " + exception.getMessage(), exception);

        // Tentar capturar screenshot
        String screenshotPath = captureScreenshot(result, testName);

        // Criar e registrar resultado
        TestResult testResult = new TestResult(testName);
        testResult.setStatus("FAIL");
        testResult.setExecutionTime(executionTime);
        testResult.setMessage(exception.getMessage());
        testResult.setErrorDetails(exception.toString());
        if (screenshotPath != null) {
            testResult.setScreenshotPath(screenshotPath);
        }

        // Atualizar JIRA se configurado
        updateJiraIfNeeded(result, testResult);
    }

    /**
     * Captura teste skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        long executionTime = result.getEndMillis() - result.getStartMillis();
        String skipReason = result.getSkipCausedBy() != null ?
            result.getSkipCausedBy().toString() : "Skipped sem motivo especificado";

        LoggerUtil.warn("Teste " + testName + " foi PULADO: " + skipReason);

        // Criar e registrar resultado
        TestResult testResult = new TestResult(testName);
        testResult.setStatus("SKIP");
        testResult.setExecutionTime(executionTime);
        testResult.setMessage(skipReason);

        // Atualizar JIRA se configurado
        updateJiraIfNeeded(result, testResult);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LoggerUtil.warn("Teste " + result.getMethod().getMethodName() +
            " falhou mas dentro da porcentagem de sucesso configurada");
    }

    /**
     * Captura screenshot em caso de falha
     */
    private String captureScreenshot(ITestResult result, String testName) {
        try {
            Object testInstance = result.getInstance();
            if (testInstance != null && testInstance instanceof com.mobile.automation.base.BaseTest) {
                com.mobile.automation.base.BaseTest baseTest = (com.mobile.automation.base.BaseTest) testInstance;
                AppiumDriver driver = baseTest.getDriver();
                if (driver != null) {
                    return ScreenshotUtil.takeScreenshot(driver, testName);
                }
            }
        } catch (Exception e) {
            LoggerUtil.warn("Erro ao capturar screenshot: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza JIRA se configurado
     */
    private void updateJiraIfNeeded(ITestResult result, TestResult testResult) {
        try {
            JiraConnector jira = JiraConnector.getInstance();
            if (jira.isConfigured()) {
                // Procura por annotation @JiraIssue se existir
                String issueKey = extractJiraIssueKey(result);
                if (issueKey != null && !issueKey.isEmpty()) {
                    jira.linkTestResult(issueKey, testResult);
                }
            }
        } catch (Exception e) {
            LoggerUtil.warn("Não foi possível atualizar JIRA: " + e.getMessage());
        }
    }

    /**
     * Extrai chave de issue do JIRA da annotation (se existir)
     */
    private String extractJiraIssueKey(ITestResult result) {
        // Aqui seria feita busca de annotation customizada @JiraIssue
        // Por enquanto, retorna null
        return null;
    }
}

