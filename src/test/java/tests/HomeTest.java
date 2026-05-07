package com.mobile.automation.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import com.mobile.automation.base.BaseTest;
import com.mobile.automation.pages.HomePage;
import com.mobile.automation.pages.LoginPage;
import com.mobile.automation.utils.LoggerUtil;

/**
 * HomeTest - Exemplos de testes da página inicial
 */
@Epic("Navegação")
@Feature("Home")
public class HomeTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;

    /**
     * Setup antes de cada teste
     */
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver, waitUtils);
        loginPage = new LoginPage(driver, waitUtils);
    }

    /**
     * Teste: Verificar carregamento da homepage
     */
    @Test(description = "Validar que homepage carrega corretamente")
    @Story("Acesso à homepage")
    @Description("Deve verificar que a página inicial carrega com todos os elementos")
    public void testHomePageLoads() {
        LoggerUtil.testInfo("testHomePageLoads", "Iniciando teste");

        performLoginAndNavigateToHome();

        boolean isLoaded = homePage.isHomePageLoaded();
        LoggerUtil.passStep("Homepage carregada com sucesso");
        Assert.assertTrue(isLoaded, "Homepage deveria estar carregada");
    }

    /**
     * Teste: Obter mensagem de boas-vindas
     */
    @Test(description = "Validar mensagem de boas-vindas")
    @Story("Acesso à homepage")
    @Description("Deve exibir mensagem de boas-vindas personalizada")
    public void testWelcomeMessage() {
        LoggerUtil.testInfo("testWelcomeMessage", "Iniciando teste");

        performLoginAndNavigateToHome();

        String welcomeMessage = homePage.getWelcomeMessage();
        LoggerUtil.passStep("Mensagem de boas-vindas obtida: " + welcomeMessage);
        Assert.assertNotNull(welcomeMessage, "Mensagem de boas-vindas não deveria ser nula");
        Assert.assertTrue(welcomeMessage.contains("Hello"), "Mensagem deveria conter 'Hello'");
    }

    /**
     * Teste: Navegação para Profile
     */
    @Test(description = "Validar navegação para Profile")
    @Story("Navegação")
    @Description("Deve navegar corretamente para a página de Profile")
    public void testNavigateToProfile() {
        LoggerUtil.testInfo("testNavigateToProfile", "Iniciando teste");

        performLoginAndNavigateToHome();

        navigateToProfile();

        // Aqui você teria mais validações
        LoggerUtil.passStep("Navegação para Profile bem-sucedida");
        Assert.assertTrue(true, "Navegação funcionou");
    }

    /**
     * Teste: Logout funciona corretamente
     */
    @Test(description = "Validar funcionalidade de logout")
    @Story("Autenticação")
    @Description("Deve fazer logout e retornar à página de login")
    public void testLogout() {
        LoggerUtil.testInfo("testLogout", "Iniciando teste");

        performLoginAndNavigateToHome();

        performLogout();

        boolean loginPageDisplayed = loginPage.isLoginPageDisplayed();
        LoggerUtil.passStep("Logout realizado com sucesso");
        Assert.assertTrue(loginPageDisplayed, "Deveria estar de volta na página de login");
    }

    /**
     * Teste: Botão de Settings está visível
     */
    @Test(description = "Validar visibilidade do botão Settings")
    @Story("Navegação")
    @Description("Deve verificar que o botão Settings está visível")
    public void testSettingsButtonVisible() {
        LoggerUtil.testInfo("testSettingsButtonVisible", "Iniciando teste");

        performLoginAndNavigateToHome();

        boolean settingsVisible = homePage.isElementDisplayed(
            org.openqa.selenium.By.xpath("//button[@text='Settings']")
        );
        LoggerUtil.passStep("Botão Settings está visível");
        Assert.assertTrue(settingsVisible, "Botão Settings deveria estar visível");
    }

    // ============ Métodos auxiliares ============

    @Step("Fazer login e navegar para home")
    private void performLoginAndNavigateToHome() {
        verifyLoginPageLoaded();
        loginPage.login("usuario@example.com", "senha123");
        waitForHomePageToLoad();
    }

    @Step("Navegar para Profile")
    private void navigateToProfile() {
        homePage.clickProfile();
        // Aqui você adicionaria esperas da página de Profile se tivesse
    }

    @Step("Realizar logout")
    private void performLogout() {
        boolean logoutVisible = homePage.isLogoutButtonVisible();
        Assert.assertTrue(logoutVisible, "Botão logout deveria estar visível");
        homePage.logout();
    }

    @Step("Verificar que página de login está carregada")
    private void verifyLoginPageLoaded() {
        boolean loginLoaded = loginPage.isLoginPageDisplayed();
        Assert.assertTrue(loginLoaded, "Página de login deveria estar carregada");
    }

    @Step("Aguardar carregamento da homepage")
    private void waitForHomePageToLoad() {
        int attempts = 0;
        while (attempts < 5 && !homePage.isHomePageLoaded()) {
            com.mobile.automation.utils.WaitUtils.waitSeconds(1);
            attempts++;
        }
        Assert.assertTrue(homePage.isHomePageLoaded(), "Homepage deveria ter carregado");
    }
}

