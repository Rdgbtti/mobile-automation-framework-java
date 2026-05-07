package com.mobile.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import com.mobile.automation.base.BasePage;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;

/**
 * LoginPage - Exemplo de Page Object para tela de login
 * Utiliza padrão Page Object Model (POM)
 */
public class LoginPage extends BasePage {

    // Localizadores dos elementos
    private final By emailField = By.xpath("//input[@placeholder='Email']");
    private final By passwordField = By.xpath("//input[@placeholder='Password']");
    private final By loginButton = By.xpath("//button[@text='Login']");
    private final By errorMessage = By.xpath("//text[@text='Invalid credentials']");
    private final By forgotPasswordLink = By.xpath("//a[@text='Forgot Password?']");

    /**
     * Construtor
     */
    public LoginPage(AppiumDriver driver, WaitUtils waitUtils) {
        super(driver, waitUtils);
    }

    /**
     * Preenche o campo de email
     */
    public void enterEmail(String email) {
        LoggerUtil.info("Preenchendo email: " + email);
        sendKeys(emailField, email);
    }

    /**
     * Preenche o campo de senha
     */
    public void enterPassword(String password) {
        LoggerUtil.info("Preenchendo senha");
        sendKeys(passwordField, password);
    }

    /**
     * Clica no botão de login
     */
    public void clickLoginButton() {
        LoggerUtil.info("Clicando no botão de login");
        click(loginButton);
    }

    /**
     * Realiza login com email e senha
     */
    public void login(String email, String password) {
        LoggerUtil.info("Realizando login com email: " + email);
        waitForElement(emailField);
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Verifica se a mensagem de erro é exibida
     */
    public boolean isErrorMessageDisplayed() {
        LoggerUtil.info("Verificando se mensagem de erro é exibida");
        return isElementDisplayed(errorMessage);
    }

    /**
     * Obtém o texto da mensagem de erro
     */
    public String getErrorMessage() {
        LoggerUtil.info("Obtendo texto da mensagem de erro");
        return getText(errorMessage);
    }

    /**
     * Clica no link de "Esqueci a Senha"
     */
    public void clickForgotPasswordLink() {
        LoggerUtil.info("Clicando no link de esqueci a senha");
        click(forgotPasswordLink);
    }

    /**
     * Verifica se a página de login está carregada
     */
    public boolean isLoginPageDisplayed() {
        LoggerUtil.info("Verificando se página de login está carregada");
        return isElementDisplayed(emailField) && isElementDisplayed(passwordField);
    }
}

