package com.mobile.automation.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.integration.JiraConfig;

/**
 * RetryListener - Implementa retry automático para testes (Framework)
 * Reutiliza testes falhados conforme configurado em config.properties
 * 
 * Uso: @Test(retryAnalyzer = RetryListener.class)
 */
public class RetryListener implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 2; // Padrão: 2 retries

    /**
     * Determina se um teste falhado deve ser retentado
     */
    @Override
    public boolean retry(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        JiraConfig config = new JiraConfig();
        int maxRetry = config.getMaxRetry();

        if (!result.isSuccess() && retryCount < maxRetry) {
            retryCount++;
            LoggerUtil.warn("========== RETRY ==========");
            LoggerUtil.warn("Teste: " + testName + " FALHANDO");
            LoggerUtil.warn("Tentativa: " + retryCount + " de " + maxRetry);
            LoggerUtil.warn("Retentando...");
            LoggerUtil.warn("===========================");
            return true; // Retorna true para retentar
        }

        retryCount = 0; // Reset para próximo teste
        return false; // Não retenta mais
    }

    public int getRetryCount() {
        return retryCount;
    }
}

