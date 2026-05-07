package com.mobile.automation.base;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.mobile.automation.drivers.DriverFactory;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;
import com.mobile.automation.integration.TestContext;

/**
 * BaseTest - Classe base para todos os testes (Framework)
 * Gerencia inicialização e finalização do driver Appium
 * Pode ser estendida por projetos de testes externos
 */
public class BaseTest {
    protected AppiumDriver driver;
    protected WaitUtils waitUtils;
    protected TestContext testContext;

    /**
     * Setup inicial da classe de teste (executado uma vez por classe)
     */
    @BeforeClass
    public void setUpClass() {
        LoggerUtil.info("========== Iniciando Suite de Testes ==========");
        testContext = new TestContext();
        // Comentado para não iniciar automaticamente
        // DriverFactory.startAppiumServer();
    }

    /**
     * Setup antes de cada teste
     */
    @BeforeMethod
    public void setUp() {
        try {
            LoggerUtil.info("Configurando driver para o teste...");
            Object driverObject = DriverFactory.createDriver();

            if (driverObject instanceof AppiumDriver) {
                driver = (AppiumDriver) driverObject;
                waitUtils = new WaitUtils(driver);
                testContext.setDriver(driver);
                testContext.setWaitUtils(waitUtils);
                LoggerUtil.info("Driver inicializado com sucesso");
            } else {
                throw new RuntimeException("Driver não é do tipo AppiumDriver");
            }
        } catch (Exception e) {
            LoggerUtil.error("Erro ao initializar driver: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Teardown após cada teste
     */
    @AfterMethod
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
                LoggerUtil.info("Driver finalizado com sucesso");
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
}

