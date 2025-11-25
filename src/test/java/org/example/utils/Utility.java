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

 // Inicia el navegador solo una vez, aplica configuraciones, y luego lo cierra correctamente al final.

    private static WebDriver driver;
    private static final int WAIT_BEFORE_CLOSE = 5000; // Tiempo en milisegundos (5 segundos)

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--incognito");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
            System.out.println(" Navegador iniciado correctamente (Utility)");
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            try {
                // Esperar unos segundos antes de cerrar el navegador
                System.out.println("⏳ Esperando " + (WAIT_BEFORE_CLOSE / 10000) + " segundos antes de cerrar...");
                Thread.sleep(WAIT_BEFORE_CLOSE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            driver.quit();
            driver = null;
            System.out.println(" Navegador cerrado correctamente (Utility)");
        }
    }

    public static void captureScreenShot (WebDriver webDriver, String filePath)throws IOException
    {
        TakesScreenshot screenshot=((TakesScreenshot) webDriver);
        File screenFile = screenshot.getScreenshotAs(OutputType.FILE);
        File DestinoFile = new File(filePath);
        FileUtils.copyFile(screenFile, DestinoFile);
    }

    public static String GetTimeStampValue () throws IOException
    {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        String timestamp = time.toString();
        String systime= timestamp.replace(":", "-");
        return systime;
    }
}

