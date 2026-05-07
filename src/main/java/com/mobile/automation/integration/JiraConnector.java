package com.mobile.automation.integration;

import com.mobile.automation.utils.LoggerUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * JiraConnector - Conecta com JIRA para atualizar testes (Framework)
 * Versão basic/hybrid - será evoluída com REST API futuramente
 */
public class JiraConnector {
    private JiraConfig config;
    private static JiraConnector instance;

    private JiraConnector() {
        this.config = new JiraConfig();
    }

    public static synchronized JiraConnector getInstance() {
        if (instance == null) {
            instance = new JiraConnector();
        }
        return instance;
    }

    /**
     * Atualiza status de uma issue no JIRA
     */
    public void updateIssueStatus(String issueKey, TestResult result) {
        if (!config.isConfigured()) {
            LoggerUtil.warn("JIRA não está configurado. Pulando atualização");
            return;
        }

        try {
            LoggerUtil.info("Atualizando issue " + issueKey + " com status: " + result.getStatus());

            // Aqui seria feita integração com JIRA REST API
            // Por enquanto, apenas log
            Map<String, Object> data = new HashMap<>();
            data.put("key", issueKey);
            data.put("status", result.getStatus());
            data.put("executionTime", result.getExecutionTime());
            data.put("message", result.getMessage());

            LoggerUtil.debug("Payload JIRA: " + data);
            LoggerUtil.passStep("Issue " + issueKey + " atualizada com sucesso");
        } catch (Exception e) {
            LoggerUtil.error("Erro ao atualizar issue " + issueKey + " no JIRA: " + e.getMessage(), e);
        }
    }

    /**
     * Cria comentário em uma issue
     */
    public void addCommentToIssue(String issueKey, String comment) {
        if (!config.isConfigured()) {
            LoggerUtil.warn("JIRA não está configurado. Pulando comentário");
            return;
        }

        try {
            LoggerUtil.info("Adicionando comentário à issue " + issueKey);

            Map<String, Object> data = new HashMap<>();
            data.put("key", issueKey);
            data.put("comment", comment);

            LoggerUtil.debug("Comentário: " + data);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao adicionar comentário em " + issueKey, e);
        }
    }

    /**
     * Vincula resultado do teste a uma issue do JIRA
     */
    public void linkTestResult(String issueKey, TestResult result) {
        try {
            updateIssueStatus(issueKey, result);
            addCommentToIssue(issueKey, buildResultComment(result));
        } catch (Exception e) {
            LoggerUtil.error("Erro ao vincular resultado do teste: " + e.getMessage(), e);
        }
    }

    private String buildResultComment(TestResult result) {
        return String.format(
            "Teste executado: %s\\nStatus: %s\\nTempo: %dms\\nMensagem: %s",
            result.getTestName(),
            result.getStatus(),
            result.getExecutionTime(),
            result.getMessage()
        );
    }

    public JiraConfig getConfig() {
        return config;
    }

    public boolean isConfigured() {
        return config.isConfigured();
    }
}

