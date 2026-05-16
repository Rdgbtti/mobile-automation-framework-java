package com.mobile.automation.base;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.mobile.automation.drivers.DriverFactory;
import com.mobile.automation.drivers.WebDriverFactory;
import com.mobile.automation.drivers.RestApiClient;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;
import com.mobile.automation.utils.ConfigReader;
import com.mobile.automation.integration.TestContext;

/**
 * BaseTest - Classe base para testes MOBILE, WEB E API (Framework TRIPLE HYBRID)
 * Gerencia inicialização e finalização de drivers (Appium, Selenium, RestAssured)
 * Pode ser estendida por projetos de testes externos
 */
public class BaseTest {
    // Mobile (Appium)
    protected AppiumDriver driver;
    
    // Web (Selenium WebDriver)
    protected WebDriver webDriver;
    
    // API (REST Assured)
    protected RestApiClient apiClient;
    
    protected WaitUtils waitUtils;
    protected TestContext testContext;

    /**
     * Setup inicial da classe de teste (executado uma vez por classe)
     */
    @BeforeClass
    public void setUpClass() {
        LoggerUtil.info("========== Iniciando Suite de Testes ==========");
        testContext = new TestContext();
    }

    /**
     * Setup antes de cada teste - Inicializa driver baseado na plataforma
     */
    @BeforeMethod
    public void setUp() {
        try {
            String testType = ConfigReader.getProperty("test.type", "mobile").toLowerCase();
            
            if ("web".equals(testType)) {
                setUpWeb();
            } else if ("mobile".equals(testType)) {
                setUpMobile();
            } else if ("api".equals(testType)) {
                setUpApi();
            } else {
                throw new IllegalArgumentException("test.type inválido. Use 'mobile', 'web' ou 'api'");
            }
        } catch (Exception e) {
            LoggerUtil.error("Erro ao inicializar driver: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Setup para testes MÓVEL (Appium)
     */
    private void setUpMobile() {
        try {
            LoggerUtil.info("Configurando driver MOBILE (Appium)...");
            Object driverObject = DriverFactory.createDriver();

            if (driverObject instanceof AppiumDriver) {
                driver = (AppiumDriver) driverObject;
                waitUtils = new WaitUtils(driver);
                testContext.setDriver(driver);
                testContext.setWaitUtils(waitUtils);
                testContext.setTestType("mobile");
                LoggerUtil.info("Driver MOBILE inicializado com sucesso");
            } else {
                throw new RuntimeException("Driver não é do tipo AppiumDriver");
            }
        } catch (Exception e) {
            LoggerUtil.error("Erro ao inicializar driver MOBILE: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Setup para testes WEB (Selenium)
     */
    private void setUpWeb() {
        try {
            LoggerUtil.info("Configurando driver WEB (Selenium)...");
            webDriver = WebDriverFactory.createWebDriver();
            WebDriverFactory.maximizeWindow(webDriver);
            
            // Navegar para URL base
            String baseUrl = ConfigReader.getWebBaseUrl();
            webDriver.navigate().to(baseUrl);
            LoggerUtil.info("Navegando para: " + baseUrl);
            
            waitUtils = new WaitUtils(webDriver);
            testContext.setWebDriver(webDriver);
            testContext.setWaitUtils(waitUtils);
            testContext.setTestType("web");
            LoggerUtil.info("Driver WEB inicializado com sucesso");
        } catch (Exception e) {
            LoggerUtil.error("Erro ao inicializar driver WEB: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Setup para testes API (REST Assured)
     */
    private void setUpApi() {
        try {
            LoggerUtil.info("Configurando cliente API (REST Assured)...");
            apiClient = new RestApiClient();
            
            // Adicionar autenticação se necessário
            if (ConfigReader.isApiAuthRequired()) {
                String authType = ConfigReader.getProperty("api.auth.type", "bearer");
                
                if ("bearer".equalsIgnoreCase(authType)) {
                    String token = ConfigReader.getApiAuthToken();
                    apiClient.addAuthToken(token);
                    LoggerUtil.debug("Autenticação Bearer adicionada");
                } else if ("basic".equalsIgnoreCase(authType)) {
                    String username = ConfigReader.getApiAuthUsername();
                    String password = ConfigReader.getApiAuthPassword();
                    apiClient.addBasicAuth(username, password);
                    LoggerUtil.debug("Autenticação Basic adicionada");
                }
            }
            
            testContext.setApiClient(apiClient);
            testContext.setTestType("api");
            LoggerUtil.info("Cliente API inicializado com sucesso");
        } catch (Exception e) {
            LoggerUtil.error("Erro ao inicializar cliente API: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Teardown após cada teste
     */
    @AfterMethod
    public void tearDown() {
        try {
            // Fechar Mobile Driver
            if (driver != null) {
                driver.quit();
                LoggerUtil.info("Driver MOBILE finalizado com sucesso");
            }
            
            // Fechar Web Driver
            if (webDriver != null) {
                WebDriverFactory.quitWebDriver(webDriver);
                LoggerUtil.info("Driver WEB finalizado com sucesso");
            }
            
            // Resetar API Client (não há conexão a fechar, apenas reset)
            if (apiClient != null) {
                apiClient.reset();
                LoggerUtil.info("Cliente API resetado com sucesso");
            }
        } catch (Exception e) {
            LoggerUtil.error("Erro ao fechar driver: " + e.getMessage(), e);
        }
    }

    /**
     * Teardown final da classe de teste
     */
    @AfterClass
    public void tearDownClass() {
        try {
            LoggerUtil.info("========== Suite de Testes Finalizada ==========");
        } catch (Exception e) {
            LoggerUtil.error("Erro ao finalizar suite: " + e.getMessage(), e);
        }
    }

    /**
     * Obtém o TestContext
     */
    public TestContext getTestContext() {
        return testContext;
    }

    /**
     * Verifica se é teste mobile
     */
    protected boolean isMobileTest() {
        return driver != null;
    }

    /**
     * Verifica se é teste web
     */
    protected boolean isWebTest() {
        return webDriver != null;
    }

    /**
     * Verifica se é teste API
     */
    protected boolean isApiTest() {
        return apiClient != null;
    }
}


