package org.example.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.utils.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerTransaccionesSteps {

    // Obtenemos el mismo navegador compartido por el LoginSteps
    private final WebDriver driver = DriverManager.getDriver();

    // =====================================================
    // Paso: Click en el menú "View Recent Transactions"
    // =====================================================
    @When("el usuario realiza click en {string} para acceder a la seccion de transacciones recientes")
    public void accederASeccionTransacciones(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            link.click();
            System.out.println("Click en 'View Recent Transactions'.");
        } catch (TimeoutException e) {
            throw new RuntimeException(" No se encontró el enlace de transacciones recientes: " + xpath);
        } catch (NoSuchWindowException e) {
            System.err.println("Ventana del navegador cerrada inesperadamente. Reiniciando driver...");
            DriverManager.closeDriver();
            throw e;
        }
    }

    // =====================================================
    // Paso: Validar que se muestre el historial de transacciones
    // =====================================================
    @Then("Se deberia mostrar el campo {string} con el historial de transacciones")
    public void validarHistorial(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement tabla = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            assertTrue(tabla.isDisplayed(), " No se visualizó la tabla de transacciones.");
            System.out.println(" Se visualiza correctamente el historial de transacciones.");

        } catch (TimeoutException e) {
            throw new RuntimeException(" No se encontró el elemento con XPath: " + xpath + " después de 15 segundos.");
        } finally {
            // Cierra el navegador al final de la prueba
            DriverManager.closeDriver();
        }
    }
}
