package com.mobile.automation.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import com.mobile.automation.utils.ConfigReader;
import com.mobile.automation.utils.LoggerUtil;

/**
 * WebDriverFactory - Factory para criar e gerenciar drivers Web (Chrome, Firefox, Edge, Safari)
 * Framework reutilizável para testes web
 * Parte do framework HÍBRIDO (Mobile + Web)
 */
public class WebDriverFactory {

    /**
     * Cria WebDriver baseado no browser configurado
     * @return WebDriver configurado
     */
    public static WebDriver createWebDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        LoggerUtil.info("Criando WebDriver para browser: " + browser);

        return switch (browser) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            case "safari" -> createSafariDriver();
            default -> throw new IllegalArgumentException("Browser não suportado: " + browser);
        };
    }

    /**
     * Cria Chrome WebDriver com opções otimizadas
     */
    private static WebDriver createChromeDriver() {
        try {
            LoggerUtil.info("Inicializando ChromeDriver...");
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // Opções recomendadas para testes
            options.addArguments("--start-maximized");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-gpu");

            if (ConfigReader.getBooleanProperty("headless.mode")) {
                options.addArguments("--headless");
                LoggerUtil.debug("Chrome iniciado em modo headless");
            }

            if (ConfigReader.getBooleanProperty("incognito.mode")) {
                options.addArguments("--incognito");
                LoggerUtil.debug("Chrome iniciado em modo incógnito");
            }

            WebDriver driver = new ChromeDriver(options);
            LoggerUtil.passStep("ChromeDriver criado com sucesso");
            return driver;
        } catch (Exception e) {
            LoggerUtil.error("Erro ao criar ChromeDriver: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao criar ChromeDriver", e);
        }
    }

    /**
     * Cria Firefox WebDriver com opções otimizadas
     */
    private static WebDriver createFirefoxDriver() {
        try {
            LoggerUtil.info("Inicializando FirefoxDriver...");
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions options = new FirefoxOptions();

            options.addArguments("--width=1920");
            options.addArguments("--height=1080");

            if (ConfigReader.getBooleanProperty("headless.mode")) {
                options.addArguments("--headless");
                LoggerUtil.debug("Firefox iniciado em modo headless");
            }

            if (ConfigReader.getBooleanProperty("incognito.mode")) {
                options.addArguments("-private");
                LoggerUtil.debug("Firefox iniciado em modo privado");
            }

            WebDriver driver = new FirefoxDriver(options);
            LoggerUtil.passStep("FirefoxDriver criado com sucesso");
            return driver;
        } catch (Exception e) {
            LoggerUtil.error("Erro ao criar FirefoxDriver: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao criar FirefoxDriver", e);
        }
    }

    /**
     * Cria Edge WebDriver com opções otimizadas
     */
    private static WebDriver createEdgeDriver() {
        try {
            LoggerUtil.info("Inicializando EdgeDriver...");
            WebDriverManager.edgedriver().setup();

            EdgeOptions options = new EdgeOptions();

            options.addArguments("--start-maximized");
            options.addArguments("--disable-gpu");

            if (ConfigReader.getBooleanProperty("headless.mode")) {
                options.addArguments("--headless");
                LoggerUtil.debug("Edge iniciado em modo headless");
            }

            if (ConfigReader.getBooleanProperty("incognito.mode")) {
                options.addArguments("--inprivate");
                LoggerUtil.debug("Edge iniciado em modo privado");
            }

            WebDriver driver = new EdgeDriver(options);
            LoggerUtil.passStep("EdgeDriver criado com sucesso");
            return driver;
        } catch (Exception e) {
            LoggerUtil.error("Erro ao criar EdgeDriver: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao criar EdgeDriver", e);
        }
    }

    /**
     * Cria Safari WebDriver (apenas macOS)
     */
    private static WebDriver createSafariDriver() {
        try {
            LoggerUtil.info("Inicializando SafariDriver...");
            WebDriver driver = new SafariDriver();
            LoggerUtil.passStep("SafariDriver criado com sucesso");
            return driver;
        } catch (Exception e) {
            LoggerUtil.error("Erro ao criar SafariDriver: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao criar SafariDriver (apenas disponível em macOS)", e);
        }
    }

    /**
     * Fecha o WebDriver
     */
    public static void quitWebDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                LoggerUtil.info("WebDriver fechado com sucesso");
            } catch (Exception e) {
                LoggerUtil.warn("Erro ao fechar WebDriver: " + e.getMessage());
            }
        }
    }

    /**
     * Maximiza janela do navegador
     */
    public static void maximizeWindow(WebDriver driver) {
        try {
            driver.manage().window().maximize();
            LoggerUtil.debug("Janela maximizada");
        } catch (Exception e) {
            LoggerUtil.warn("Erro ao maximizar janela: " + e.getMessage());
        }
    }
}

