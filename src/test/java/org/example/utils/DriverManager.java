package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static WebDriver driver;
    private static final int WAIT_BEFORE_CLOSE = 5000; // ⏳ Tiempo en milisegundos (5 segundos)

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
            System.out.println(" Navegador iniciado correctamente (DriverManager)");
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
            System.out.println(" Navegador cerrado correctamente (DriverManager)");
        }
    }
}

