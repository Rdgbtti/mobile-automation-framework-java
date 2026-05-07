package com.mobile.automation.integration;

/**
 * JiraConfig - Carrega configurações do JIRA (Framework)
 */
public class JiraConfig {
    private String url;
    private String user;
    private String token;
    private String project;
    private boolean enabled;

    public JiraConfig() {
        this.url = com.mobile.automation.utils.ConfigReader.getJiraUrl();
        this.user = com.mobile.automation.utils.ConfigReader.getJiraUser();
        this.token = com.mobile.automation.utils.ConfigReader.getJiraToken();
        this.project = com.mobile.automation.utils.ConfigReader.getJiraProject();
        this.enabled = com.mobile.automation.utils.ConfigReader.isJiraEnabled();
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getProject() {
        return project;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isConfigured() {
        return enabled && !url.isEmpty() && !user.isEmpty() && !token.isEmpty();
    }
}

