package org.example.steps;

import io.cucumber.java.en.*;
import org.example.utils.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CambiarConstrasenaSteps {

    WebDriver driver = DriverManager.getDriver();

    // =====================================================
    // Paso 1: Navegar a la URL principal
    // =====================================================
    @Given("al navegar hasta la url {string}")
    public void navegarHastaLaUrl(String url) {
        driver.get(url);
        System.out.println(" Navegando a la URL: " + url);
    }

    // =====================================================
    // Paso 2: Click en enlaces (Login, Edit Users, etc.)
    // =====================================================
    @When("hacemos click en el link {string}")
    public void hacemosClickEnElLink(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        link.click();
        System.out.println(" Click realizado en el enlace.");
    }

    @When("hacer click en el link Edit Users {string}")
    public void hacerClickEnElLinkEditUsers(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        link.click();
        System.out.println(" Click en 'Edit Users'.");
    }

    // =====================================================
    // Paso 3: Ingreso de credenciales
    // =====================================================
    @When("coloca en el campo usuario {string} el texto {string}")
    public void colocarUsuario(String xpath, String texto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        input.clear();
        input.sendKeys(texto);
        System.out.println(" Usuario ingresado: " + texto);
    }

    @When("coloca en el campo password {string} el texto {string}")
    public void colocarPassword(String xpath, String texto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        input.clear();
        input.sendKeys(texto);
        System.out.println(" Contraseña ingresada.");
    }

    // =====================================================
    // Paso 4: Click en botón Login
    // =====================================================
    @When("hacer click sobre el boton Login {string}")
    public void clickBotonLogin(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        boton.click();
        System.out.println(" Click en botón Login.");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("bank"));
    }

    // =====================================================
    // Paso 5: Seleccionar usuario en lista desplegable
    // =====================================================
    @When("seleccionar el usuario {string} con valor {string}")
    public void seleccionarUsuario(String xpath, String valor) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Select dropdown = new Select(selectElement);
        dropdown.selectByValue(valor);
        System.out.println(" Usuario seleccionado: " + valor);
    }

    // =====================================================
    // Paso 6: Colocar nueva contraseña y confirmar
    // =====================================================
    @When("colocar en el campo nueva contraseña {string} el texto {string}")
    public void colocarNuevaContrasena(String xpath, String texto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        input.clear();
        input.sendKeys(texto);
        System.out.println(" Nueva contraseña ingresada.");
    }

    @When("colocar en el campo confirmar contraseña {string} el texto {string}")
    public void colocarConfirmarContrasena(String xpath, String texto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        input.clear();
        input.sendKeys(texto);
        System.out.println(" Confirmación de contraseña ingresada.");
    }

    // =====================================================
    // Paso 7: Click en "Change Password"
    // =====================================================
    @When("hacer click sobre el boton Change Password {string}")
    public void clickChangePassword(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        boton.click();
        System.out.println(" Click en 'Change Password'.");
    }

    // =====================================================
    // Paso 8: Validar mensaje de éxito
    // =====================================================
    @Then("Presenta el mensaje {string}")
    public void presentaElMensaje(String mensajeEsperado) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + mensajeEsperado + "')]"))
        );

        assertTrue(mensaje.isDisplayed(), " No se encontró el mensaje esperado.");
        System.out.println(" Se muestra el mensaje esperado: " + mensajeEsperado);

        try {
            System.out.println(" Esperando 10 segundos antes de cerrar el navegador...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        DriverManager.closeDriver();
    }

    // =====================================================
    // Paso alternativo: Validar que la página sigue visible
    // =====================================================
    @Then("La página debe mantenerse visible después del cambio de contraseña")
    public void validarPaginaVisible() {
        try {
            Thread.sleep(10000);
            assertTrue(driver.getCurrentUrl().contains("admin"),
                    " La página no se mantiene visible tras el cambio de contraseña.");
            System.out.println(" La página se mantiene visible tras el cambio de contraseña.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            DriverManager.closeDriver();
        }
    }
}
