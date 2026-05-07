package com.mobile.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.AppiumDriver;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil - Utilitário para capturar screenshots (Framework)
 */
public class ScreenshotUtil {
    private static final String SCREENSHOT_DIR = "target/screenshots";

    static {
        try {
            new File(SCREENSHOT_DIR).mkdirs();
        } catch (Exception e) {
            LoggerUtil.error("Erro ao criar diretório de screenshots: " + e.getMessage());
        }
    }

    public static String takeScreenshot(AppiumDriver driver, String screenshotName) {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS").format(new Date());
            String fileName = String.format("%s-%s.png", screenshotName, timestamp);
            String filePath = Paths.get(SCREENSHOT_DIR, fileName).toString();

            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), Paths.get(filePath));

            LoggerUtil.info("Screenshot capturado: " + filePath);
            return filePath;
        } catch (Exception e) {
            LoggerUtil.error("Erro ao capturar screenshot: " + e.getMessage(), e);
            return null;
        }
    }

    public static String takeScreenshotWithTimestamp(AppiumDriver driver) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return takeScreenshot(driver, "screenshot-" + timestamp);
    }

    public static File getScreenshotFile(AppiumDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            LoggerUtil.error("Erro ao obter arquivo de screenshot: " + e.getMessage(), e);
            return null;
        }
    }
}

