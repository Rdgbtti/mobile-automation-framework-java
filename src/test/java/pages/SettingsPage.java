package com.mobile.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.mobile.automation.base.BasePage;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;

/**
 * SettingsPage - Exemplo de Page Object usando @FindBy (Page Factory)
 * Alternativa ao uso de By locators diretos
 */
public class SettingsPage extends BasePage {

    // Usando @FindBy para declaração de elementos
    @FindBy(xpath = "//text[@text='Settings']")
    private WebElement pageTitle;

    @FindBy(xpath = "//switch[@name='notifications']")
    private WebElement notificationsToggle;

    @FindBy(xpath = "//switch[@name='darkMode']")
    private WebElement darkModeToggle;

    @FindBy(xpath = "//button[@text='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@text='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//text[@text='Language']/../following-sibling::*")
    private WebElement languageSelector;

    @FindBy(xpath = "//text[@text='Version: 1.0.0']")
    private WebElement versionText;

    /**
     * Construtor
     */
    public SettingsPage(AppiumDriver driver, WaitUtils waitUtils) {
        super(driver, waitUtils);
        // Inicializa elementos com Page Factory
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifica se a página de settings está carregada
     */
    public boolean isSettingsPageLoaded() {
        LoggerUtil.info("Verificando se página de settings está carregada");
        try {
            waitUtils.waitForElementToBeVisible(org.openqa.selenium.By.xpath("//text[@text='Settings']"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Ativa/desativa notificações
     */
    public void toggleNotifications() {
        LoggerUtil.info("Alternando notificações");
        try {
            notificationsToggle.click();
            LoggerUtil.passStep("Notificações alternadas");
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao alternar notificações: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifica se notificações estão ativas
     */
    public boolean areNotificationsEnabled() {
        LoggerUtil.info("Verificando se notificações estão ativas");
        try {
            String isChecked = notificationsToggle.getAttribute("checked");
            return Boolean.parseBoolean(isChecked);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao verificar notificações: " + e.getMessage());
            return false;
        }
    }

    /**
     * Ativa/desativa modo escuro
     */
    public void toggleDarkMode() {
        LoggerUtil.info("Alternando modo escuro");
        try {
            darkModeToggle.click();
            LoggerUtil.passStep("Modo escuro alternado");
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao alternar modo escuro: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifica se modo escuro está ativo
     */
    public boolean isDarkModeEnabled() {
        LoggerUtil.info("Verificando se modo escuro está ativo");
        try {
            String isChecked = darkModeToggle.getAttribute("checked");
            return Boolean.parseBoolean(isChecked);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao verificar modo escuro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Salva as configurações
     */
    public void saveSettings() {
        LoggerUtil.info("Salvando configurações");
        try {
            saveButton.click();
            LoggerUtil.passStep("Configurações salvas");
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao salvar configurações: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Cancela as alterações
     */
    public void cancelSettings() {
        LoggerUtil.info("Cancelando alterações");
        try {
            cancelButton.click();
            LoggerUtil.passStep("Alterações canceladas");
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao cancelar: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtém versão do app
     */
    public String getAppVersion() {
        LoggerUtil.info("Obtendo versão do app");
        try {
            String text = versionText.getText();
            return text.replace("Version: ", "");
        } catch (Exception e) {
            LoggerUtil.error("Erro ao obter versão: " + e.getMessage());
            return "";
        }
    }

    /**
     * Alterna uma configuração genérica
     */
    public void toggleSetting(String settingName) {
        LoggerUtil.info("Alternando configuração: " + settingName);
        try {
            String xpath = String.format("//switch[@name='%s']", settingName);
            WebElement element = driver.findElement(org.openqa.selenium.By.xpath(xpath));
            element.click();
            LoggerUtil.passStep("Configuração '" + settingName + "' alternada");
        } catch (Exception e) {
            LoggerUtil.failStep("Erro ao alternar configuração: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtém valor de uma configuração
     */
    public String getSettingValue(String settingName) {
        LoggerUtil.info("Obtendo valor da configuração: " + settingName);
        try {
            String xpath = String.format("//text[@text='%s']/../following-sibling::*", settingName);
            WebElement element = driver.findElement(org.openqa.selenium.By.xpath(xpath));
            return element.getText();
        } catch (Exception e) {
            LoggerUtil.error("Erro ao obter valor da configuração: " + e.getMessage());
            return "";
        }
    }
}

