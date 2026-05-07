package com.mobile.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Classe para ler propriedades do arquivo config.properties (Framework)
 */
public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        try {
            loadProperties();
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo de configuração: " + e.getMessage());
        }
    }

    private static void loadProperties() throws IOException {
        properties = new Properties();
        FileInputStream fis = new FileInputStream(CONFIG_PATH);
        properties.load(fis);
        fis.close();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static String getEnvironment() {
        return getProperty("environment", "local");
    }

    public static String getPlatform() {
        return getProperty("platform", "Android");
    }

    public static String getAppiumServerUrl() {
        return getProperty("appium.server.url", "http://127.0.0.1:4723");
    }

    public static int getImplicitWait() {
        return getIntProperty("implicit.wait");
    }

    public static int getExplicitWait() {
        return getIntProperty("explicit.wait");
    }

    public static String getAndroidAppPath() {
        return getProperty("android.app.path");
    }

    public static String getAndroidPackage() {
        return getProperty("android.app.package");
    }

    public static String getAndroidActivity() {
        return getProperty("android.app.activity");
    }

    public static String getAndroidDeviceUDID() {
        return getProperty("android.device.udid");
    }

    public static String getAndroidPlatformVersion() {
        return getProperty("android.platform.version");
    }

    public static String getIOSBundleId() {
        return getProperty("ios.bundle.id");
    }

    public static String getIOSPlatformVersion() {
        return getProperty("ios.platform.version");
    }

    // JIRA Configuration
    public static String getJiraUrl() {
        return getProperty("jira.url", "");
    }

    public static String getJiraUser() {
        return getProperty("jira.user", "");
    }

    public static String getJiraToken() {
        return getProperty("jira.token", "");
    }

    public static String getJiraProject() {
        return getProperty("jira.project", "");
    }

    public static boolean isJiraEnabled() {
        return getBooleanProperty("jira.enabled");
    }
}

