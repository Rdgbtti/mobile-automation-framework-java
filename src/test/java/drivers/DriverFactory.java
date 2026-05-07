package com.mobile.automation.drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.mobile.automation.utils.ConfigReader;
import com.mobile.automation.utils.LoggerUtil;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * DriverFactory - Factory para criar e gerenciar drivers (Appium)
 */
public class DriverFactory {
    private static final String APPIUM_SERVER_URL = ConfigReader.getAppiumServerUrl();
    private static AppiumDriverLocalService service;

    /**
     * Inicia o servidor Appium localmente (se necessário)
     */
    public static void startAppiumServer() {
        try {
            LoggerUtil.info("Iniciando servidor Appium...");
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
            serviceBuilder.withPort(4723);
            service = serviceBuilder.build();
            service.start();
            LoggerUtil.info("Servidor Appium iniciado com sucesso em " + APPIUM_SERVER_URL);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao iniciar servidor Appium: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao iniciar Appium Server", e);
        }
    }

    /**
     * Para o servidor Appium
     */
    public static void stopAppiumServer() {
        if (service != null && service.isRunning()) {
            LoggerUtil.info("Parando servidor Appium...");
            service.stop();
            LoggerUtil.info("Servidor Appium parado");
        }
    }

    /**
     * Cria driver Android
     */
    public static AndroidDriver createAndroidDriver() {
        try {
            LoggerUtil.info("Criando driver Android...");
            DesiredCapabilities caps = getAndroidCapabilities();
            AndroidDriver driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), caps);
            LoggerUtil.info("Driver Android criado com sucesso");
            return driver;
        } catch (MalformedURLException e) {
            LoggerUtil.error("URL do Appium Server inválida: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao criar driver Android", e);
        }
    }

    /**
     * Cria driver iOS
     */
    public static IOSDriver createIOSDriver() {
        try {
            LoggerUtil.info("Criando driver iOS...");
            DesiredCapabilities caps = getIOSCapabilities();
            IOSDriver driver = new IOSDriver(new URL(APPIUM_SERVER_URL), caps);
            LoggerUtil.info("Driver iOS criado com sucesso");
            return driver;
        } catch (MalformedURLException e) {
            LoggerUtil.error("URL do Appium Server inválida: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao criar driver iOS", e);
        }
    }

    /**
     * Obtém desired capabilities para Android
     */
    private static DesiredCapabilities getAndroidCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", ConfigReader.getAndroidPlatformVersion());
        caps.setCapability("deviceName", ConfigReader.getAndroidDeviceUDID());
        caps.setCapability("app", ConfigReader.getAndroidAppPath());
        caps.setCapability("appPackage", ConfigReader.getAndroidPackage());
        caps.setCapability("appActivity", ConfigReader.getAndroidActivity());
        caps.setCapability("autoGrantPermissions", ConfigReader.getBooleanProperty("android.auto.grant.permissions"));
        caps.setCapability("newCommandTimeout", 300);
        caps.setCapability("automationName", "UiAutomator2");

        LoggerUtil.debug("Android Capabilities: " + caps);
        return caps;
    }

    /**
     * Obtém desired capabilities para iOS
     */
    private static DesiredCapabilities getIOSCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", ConfigReader.getIOSPlatformVersion());
        caps.setCapability("deviceName", ConfigReader.getProperty("ios.device.name"));
        caps.setCapability("bundleId", ConfigReader.getIOSBundleId());
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("newCommandTimeout", 300);

        LoggerUtil.debug("iOS Capabilities: " + caps);
        return caps;
    }

    /**
     * Cria driver baseado na plataforma configurada
     */
    public static Object createDriver() {
        String platform = ConfigReader.getPlatform().toLowerCase();

        return switch (platform) {
            case "android" -> createAndroidDriver();
            case "ios" -> createIOSDriver();
            default -> throw new IllegalArgumentException("Plataforma não suportada: " + platform);
        };
    }

    /**
     * Fecha o driver
     */
    public static void quitDriver(Object driver) {
        if (driver instanceof AndroidDriver androidDriver) {
            androidDriver.quit();
            LoggerUtil.info("Driver Android fechado");
        } else if (driver instanceof IOSDriver iosDriver) {
            iosDriver.quit();
            LoggerUtil.info("Driver iOS fechado");
        }
    }
}

