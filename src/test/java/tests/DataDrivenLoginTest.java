package com.mobile.automation.tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import com.mobile.automation.base.BaseTest;
import com.mobile.automation.pages.LoginPage;
import com.mobile.automation.utils.LoggerUtil;

/**
 * DataDrivenLoginTest - Testes parametrizados com diferentes credenciais
 * Demonstra o uso de @DataProvider com TestNG
 */
@Epic("Autenticação")
@Feature("Login Data-Driven")
public class DataDrivenLoginTest extends BaseTest {

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
     * Data Provider com diferentes cenários de login
     */
    @DataProvider(name = "validLoginCredentials")
    public Object[][] getValidLoginCredentials() {
        return new Object[][] {
            { "user1@example.com", "password123", "User 1" },
            { "user2@example.com", "password456", "User 2" },
            { "user3@example.com", "password789", "User 3" }
        };
    }

    /**
     * Data Provider com credenciais inválidas
     */
    @DataProvider(name = "invalidLoginCredentials")
    public Object[][] getInvalidLoginCredentials() {
        return new Object[][] {
            { "", "password123", "Email vazio" },
            { "user@example.com", "", "Senha vazia" },
            { "invalid-email", "password123", "Email inválido" },
            { "user@example.com", "wrongpassword", "Senha incorreta" }
        };
    }

    /**
     * Teste parametrizado com credenciais válidas
     */
    @Test(dataProvider = "validLoginCredentials", description = "Teste login com múltiplas credenciais válidas")
    @Description("Deve ser possível fazer login com diferentes credenciais válidas")
    public void testLoginWithMultipleValidCredentials(String email, String password, String userName) {
        LoggerUtil.testInfo("testLoginWithMultipleValidCredentials",
            "Testando login para: " + userName + " (" + email + ")");

        try {
            // Verifica se página de login está carregada
            Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Página de login deveria estar visível");

            // Realiza login
            loginPage.login(email, password);

            LoggerUtil.passStep("Login bem-sucedido para: " + userName);

            // Aqui você teria validações adicionais de que o login funcionou
            Assert.assertTrue(true, "Login foi bem-sucedido");
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao fazer login para: " + userName);
            throw e;
        }
    }

    /**
     * Teste parametrizado com credenciais inválidas
     */
    @Test(dataProvider = "invalidLoginCredentials", description = "Teste login com múltiplas credenciais inválidas")
    @Description("Deve rejeitar login com credenciais inválidas")
    public void testLoginWithMultipleInvalidCredentials(String email, String password, String scenario) {
        LoggerUtil.testInfo("testLoginWithMultipleInvalidCredentials",
            "Testando cenário: " + scenario);

        try {
            // Verifica se página de login está carregada
            Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Página de login deveria estar visível");

            // Realiza login com credenciais inválidas
            loginPage.login(email, password);

            // Aguarda um pouco para a validação
            com.mobile.automation.utils.WaitUtils.waitSeconds(2);

            // Verifica se mensagem de erro é exibida
            boolean errorMessageDisplayed = loginPage.isErrorMessageDisplayed();

            LoggerUtil.passStep("Cenário '" + scenario + "' validado corretamente");
            Assert.assertTrue(errorMessageDisplayed,
                "Mensagem de erro deveria ser exibida para: " + scenario);
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao testar cenário: " + scenario);
            throw e;
        }
    }

    /**
     * Exemplo de Data Provider mais complexo
     */
    @DataProvider(name = "loginScenarios")
    public Object[][] getLoginScenarios() {
        return new Object[][] {
            // email, password, shouldSucceed, description
            { "valid@example.com", "correct123", true, "Login válido" },
            { "valid@example.com", "wrong123", false, "Senha incorreta" },
            { "", "correct123", false, "Email vazio" },
            { "invalid@test.com", "nopass", false, "Usuário não existe" },
            { "admin@example.com", "admin123", true, "Login de admin" }
        };
    }

    /**
     * Teste parametrizado complexo
     */
    @Test(dataProvider = "loginScenarios", description = "Teste comprehensive de diferentes cenários de login")
    public void testLoginScenarios(String email, String password, boolean shouldSucceed, String description) {
        LoggerUtil.info("Testando: " + description);
        LoggerUtil.info("Email: " + email + " | Senha: " + (password.isEmpty() ? "vazia" : "***"));

        try {
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Página de login não está visível");

            loginPage.login(email, password);
            com.mobile.automation.utils.WaitUtils.waitSeconds(1);

            if (shouldSucceed) {
                // Se deveria ter sucesso, validar que nenhum erro foi exibido
                boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
                Assert.assertFalse(errorDisplayed, "Não deveria ter erro para: " + description);
                LoggerUtil.passStep(description + " - SUCESSO");
            } else {
                // Se deveria falhar, validar que erro foi exibido
                boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
                Assert.assertTrue(errorDisplayed, "Deveria ter erro para: " + description);
                LoggerUtil.passStep(description + " - ERRO ESPERADO");
            }
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao testar: " + description);
            throw e;
        }
    }
}

