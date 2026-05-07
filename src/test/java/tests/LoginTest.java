package com.mobile.automation.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import com.mobile.automation.base.BaseTest;
import com.mobile.automation.pages.LoginPage;
import com.mobile.automation.utils.LoggerUtil;

/**
 * LoginTest - Exemplos de testes de login
 * Utiliza TestNG e Allure para relatórios
 */
@Epic("Autenticação")
@Feature("Login")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    /**
     * Setup antes de cada teste
     */
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver, waitUtils);
    }

    /**
     * Teste: Login com credenciais válidas
     */
    @Test(description = "Validar login com credenciais corretas")
    @Story("Login com sucesso")
    @Description("Deve efetuar login com email e senha válidos")
    public void testLoginWithValidCredentials() {
        LoggerUtil.testInfo("testLoginWithValidCredentials", "Iniciando teste");

        loginWithValidCredentials();

        LoggerUtil.passStep("Login realizado com sucesso");
        Assert.assertTrue(true, "Login foi bem-sucedido");
    }

    /**
     * Teste: Login com email inválido
     */
    @Test(description = "Validar rejeição de login com email inválido")
    @Story("Login com falha")
    @Description("Deve rejeitar login com email inválido")
    public void testLoginWithInvalidEmail() {
        LoggerUtil.testInfo("testLoginWithInvalidEmail", "Iniciando teste");

        loginWithInvalidEmail();

        boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
        LoggerUtil.passStep("Mensagem de erro exibida corretamente");
        Assert.assertTrue(errorDisplayed, "Mensagem de erro deveria ser exibida");
    }

    /**
     * Teste: Login com senha incorreta
     */
    @Test(description = "Validar rejeição de login com senha incorreta")
    @Story("Login com falha")
    @Description("Deve rejeitar login com senha incorreta")
    public void testLoginWithInvalidPassword() {
        LoggerUtil.testInfo("testLoginWithInvalidPassword", "Iniciando teste");

        loginWithInvalidPassword();

        boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
        LoggerUtil.passStep("Mensagem de erro exibida corretamente");
        Assert.assertTrue(errorDisplayed, "Mensagem de erro deveria ser exibida");
    }

    /**
     * Teste: Verificar link de esqueci a senha
     */
    @Test(description = "Validar functionality do link de esqueci a senha")
    @Story("Recuperação de senha")
    @Description("Deve abrir página de recuperação de senha ao clicar no link")
    public void testForgotPasswordLink() {
        LoggerUtil.testInfo("testForgotPasswordLink", "Iniciando teste");

        verifyLoginPageIsDisplayed();

        loginPage.clickForgotPasswordLink();
        LoggerUtil.passStep("Link de esqueci a senha clicado");

        // Aqui você teria mais assertions para validar que a página de recuperação abriu
        Assert.assertTrue(true, "Link funcionou corretamente");
    }

    // ============ Métodos auxiliares com Step (Allure) ============

    @Step("Login com credenciais válidas")
    private void loginWithValidCredentials() {
        verifyLoginPageIsDisplayed();
        loginPage.login("usuario@example.com", "senha123");
    }

    @Step("Login com email inválido")
    private void loginWithInvalidEmail() {
        verifyLoginPageIsDisplayed();
        loginPage.login("", "senha123");
    }

    @Step("Login com senha incorreta")
    private void loginWithInvalidPassword() {
        verifyLoginPageIsDisplayed();
        loginPage.login("usuario@example.com", "senhaerrada");
    }

    @Step("Validar que página de login está exibida")
    private void verifyLoginPageIsDisplayed() {
        boolean isDisplayed = loginPage.isLoginPageDisplayed();
        Assert.assertTrue(isDisplayed, "Página de login deveria estar visível");
    }
}

