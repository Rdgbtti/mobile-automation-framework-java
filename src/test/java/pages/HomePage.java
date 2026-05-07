package com.mobile.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import com.mobile.automation.base.BasePage;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;

/**
 * HomePage - Exemplo de Page Object para tela inicial
 */
public class HomePage extends BasePage {

    // Localizadores
    private final By titleElement = By.xpath("//text[@text='Welcome']");
    private final By logoutButton = By.xpath("//button[@text='Logout']");
    private final By profileButton = By.xpath("//button[@text='Profile']");
    private final By settingsButton = By.xpath("//button[@text='Settings']");
    private final By userGreeting = By.xpath("//text[contains(@text, 'Hello')]");

    /**
     * Construtor
     */
    public HomePage(AppiumDriver driver, WaitUtils waitUtils) {
        super(driver, waitUtils);
    }

    /**
     * Verifica se a página inicial está carregada
     */
    public boolean isHomePageLoaded() {
        LoggerUtil.info("Verificando se homepage está carregada");
        return isElementDisplayed(titleElement);
    }

    /**
     * Obtém a mensagem de boas-vindas
     */
    public String getWelcomeMessage() {
        LoggerUtil.info("Obtendo mensagem de boas-vindas");
        return getText(userGreeting);
    }

    /**
     * Clica em Profile
     */
    public void clickProfile() {
        LoggerUtil.info("Clicando em Profile");
        click(profileButton);
    }

    /**
     * Clica em Settings
     */
    public void clickSettings() {
        LoggerUtil.info("Clicando em Settings");
        click(settingsButton);
    }

    /**
     * Realiza logout
     */
    public void logout() {
        LoggerUtil.info("Realizando logout");
        click(logoutButton);
    }

    /**
     * Verifica se o botão de logout está visível
     */
    public boolean isLogoutButtonVisible() {
        LoggerUtil.info("Verificando visibilidade do botão logout");
        return isElementDisplayed(logoutButton);
    }
}

