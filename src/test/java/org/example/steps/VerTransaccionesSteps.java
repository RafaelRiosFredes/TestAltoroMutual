package org.example.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerTransaccionesSteps {

    WebDriver driver;


    @Given("se abre el navegador en la página {string}")
    public void abrirNavegador(String url) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println(" Página abierta: " + url);
    }


    @Given("el usuario accede al login desde VerTransacciones haciendo click en {string}")
    public void irAlLoginVerTransacciones(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        loginLink.click();
        System.out.println(" Click en enlace de login (VerTransacciones).");
    }

    // =====================================================
    // Ingresar credenciales (RENOMBRADO para evitar duplicado)
    // =====================================================
    @When("en VerTransacciones el usuario completa {string} con {string} y {string} con la contraseña {string}")
    public void ingresarCredencialesVerTransacciones(String userXpath, String usuario, String passXpath, String contrasena) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userXpath))).sendKeys(usuario);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passXpath))).sendKeys(contrasena);
        System.out.println("Credenciales ingresadas (VerTransacciones).");
    }

    // =====================================================
    // Botón login (ya renombrado)
    // =====================================================
    @When("presiona el botón de login en VerTransacciones {string}")
    public void clickBotonLoginVerTransacciones(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en botón Login (VerTransacciones).");
    }

    // =====================================================
    // Acceder a transacciones
    // =====================================================
    @When("el usuario realiza click en {string} para acceder a la seccion de transacciones recientes")
    public void accederASeccionTransacciones(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        link.click();
        System.out.println(" Click en 'View Recent Transactions'.");
    }

    // =====================================================
    // Validar que se muestre la tabla
    // =====================================================
    @Then("Se deberia mostrar el campo {string} con el historial de transacciones")
    public void validarHistorial(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement tabla = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        assertTrue(tabla.isDisplayed(), " No se visualizó la tabla de transacciones.");
        System.out.println(" Se visualiza correctamente el historial de transacciones.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        driver.quit();
        System.out.println(" Navegador cerrado correctamente.");
    }
}
