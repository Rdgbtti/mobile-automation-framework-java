package com.mobile.automation.listeners;

import org.testng.IReporter;
import org.testng.ITestContext;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.integration.JiraConnector;
import com.mobile.automation.integration.TestResult;
import io.qameta.allure.Allure;
import java.util.List;

/**
 * ReportListener - Listener para gerar relatórios com Allure e integrar com JIRA (Framework)
 * Captura informações de testes e envia para Allure + JIRA
 *
 * Uso: Adicionar em testng.xml:
 * <listeners>
 *   <listener class-name="com.mobile.automation.listeners.ReportListener"/>
 * </listeners>
 */
public class ReportListener implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        LoggerUtil.info("========== Gerando Relatório Final ==========");
        LoggerUtil.info("Diretório de saída: " + outputDirectory);

        for (ISuite suite : suites) {
            processTestResults(suite);
        }

        LoggerUtil.passStep("Relatório gerado com sucesso em: " + outputDirectory);
    }

    /**
     * Processa resultados de cada suite
     */
    private void processTestResults(ISuite suite) {
        LoggerUtil.info("Processando suite: " + suite.getName());

        suite.getResults().forEach((suiteName, context) -> {
            processSuiteResults(context);
        });
    }

    /**
     * Processa contexto de testes (sucessos, falhas, skipped)
     */
    private void processSuiteResults(ITestContext context) {
        // Processar sucessos
        context.getPassedTests().getAllResults().forEach(result -> {
            attachResultToAllure(result, "PASS");
        });

        // Processar falhas
        context.getFailedTests().getAllResults().forEach(result -> {
            attachResultToAllure(result, "FAIL");
        });

        // Processar skipped
        context.getSkippedTests().getAllResults().forEach(result -> {
            attachResultToAllure(result, "SKIP");
        });

        // Log resumo
        logTestSummary(context);
    }

    /**
     * Anexa resultado do teste ao Allure Report
     */
    private void attachResultToAllure(ITestResult result, String status) {
        String testName = result.getMethod().getMethodName();
        long executionTime = result.getEndMillis() - result.getStartMillis();

        try {
            // Adicionar informações ao Allure
            Allure.addAttachment("Status_Marcador", status);
            Allure.addAttachment("Tempo de Execução (ms)", String.valueOf(executionTime));

            if (result.getThrowable() != null) {
                Allure.addAttachment("Erro", result.getThrowable().getMessage());
            }

            LoggerUtil.debug("Resultado anexado ao Allure: " + testName + " [" + status + "]");
        } catch (Exception e) {
            LoggerUtil.warn("Erro ao anexar resultado ao Allure: " + e.getMessage());
        }
    }

    /**
     * Log resumo da execução
     */
    private void logTestSummary(ITestContext context) {
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = passed + failed + skipped;

        LoggerUtil.info("========== RESUMO DA EXECUÇÃO ==========");
        LoggerUtil.info("Total de testes: " + total);
        LoggerUtil.passStep("✓ Sucesso: " + passed);
        if (failed > 0) LoggerUtil.failStep("✗ Falhas: " + failed);
        if (skipped > 0) LoggerUtil.warn("⊘ Pulados: " + skipped);
        LoggerUtil.info("========================================");
    }
}

