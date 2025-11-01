package org.example.steps;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfertaTarjetaSteps {

    WebDriver driver;

    // ====== Background ======
    @Given("el navegador está abierto en {string}")
    public void abrirNavegadorEn(String url) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("🧭 Navegador abierto en: " + url);
    }

    @Given("el usuario hace click en {string} para ir a login")
    public void clickIrALogin(String xpath) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("➡️ Click en enlace de login.");
    }

    @Given("escribe el usuario en {string} con {string}")
    public void escribirUsuario(String xpath, String usuario) {
        WebElement user = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        user.clear();
        user.sendKeys(usuario);
        System.out.println("📝 Usuario ingresado: " + usuario);
    }

    @Given("escribe la clave en {string} con {string}")
    public void escribirClave(String xpath, String clave) {
        WebElement pass = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        pass.clear();
        pass.sendKeys(clave);
        System.out.println("🔒 Clave ingresada.");
    }

    @Given("hace click en {string}")
    public void clickGenerico(String xpath) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("✅ Click ejecutado: " + xpath);
    }

    // ====== Escenarios (3.1 y 3.2) ======
    @When("hace click en el enlace {string}")
    public void clickEnlace(String xpath) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("🟡 Click en 'Click here to apply'.");
    }

    @When("escribe la clave de confirmación en {string} con {string}")
    public void escribirClaveConfirmacion(String xpath, String clave) {
        WebElement passConfirm = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        passConfirm.clear();
        passConfirm.sendKeys(clave);
        System.out.println("🔐 Clave de confirmación ingresada.");
    }

    @Then("debería ver en {string} el texto {string}")
    public void deberiaVerMensaje(String xpath, String esperado) {
        WebElement msg = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        // Validación contiene (algunas veces el sitio agrega espacios o saltos)
        String texto = msg.getText().trim();
        boolean coincide = texto.contains(esperado);
        System.out.println("📣 Texto mostrado: " + texto);
        assertTrue(coincide, "El texto no coincide.\nEsperado (contiene): " + esperado + "\nObtenido: " + texto);

        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        driver.quit();
        System.out.println("🧹 Navegador cerrado correctamente.");
    }
}
