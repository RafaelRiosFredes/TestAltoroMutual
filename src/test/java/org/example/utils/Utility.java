package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    private static WebDriver driver;

    // Patrón Singleton: Solo crea el driver si no existe
    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Movimos los steps aca
            options.addArguments("--start-maximized");
            options.addArguments("--incognito");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--disable-infobars");

            driver = new ChromeDriver(options);

            // Shutdown Hook para cerrar el navegador para cuando cierre por completo
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (driver != null) {
                    driver.quit();
                    driver = null;
                    System.out.println(" Navegador cerrado por ShutdownHook.");
                }
            }));

            System.out.println(" Navegador iniciado correctamente (Utility)");
        }
        return driver;
    }

    // Ya no llamaremos a esto manualmente en cada test
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void captureScreenShot(WebDriver webDriver, String filePath) throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) webDriver);
        File screenFile = screenshot.getScreenshotAs(OutputType.FILE);
        File DestinoFile = new File(filePath);
        FileUtils.copyFile(screenFile, DestinoFile);
    }

    public static String GetTimeStampValue() {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        String timestamp = time.toString();
        return timestamp.replace(":", "-");
    }
}