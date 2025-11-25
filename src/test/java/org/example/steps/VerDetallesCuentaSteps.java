package org.example.steps;

import io.cucumber.java.en.*;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerDetallesCuentaSteps {

    WebDriver driver;


    @Given("se inicia un nuevo navegador en la página {string}")
    public void abrirNavegadorEnPagina(String url) throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println(" Navegador abierto en: " + url);
        String obj="se inicia un nuevo navegador en la página";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Given("el usuario accede al login haciendo click en {string}")
    public void clickEnLinkLogin(String xpath) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println(" Click en enlace de login (VerDetallesCuenta).");
        String obj="Click en enlace de login (VerDetallesCuenta)";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }


    @When("el usuario completa los campos {string} y {string} con las credenciales {string} y {string}")
    public void ingresarCredencialesCuenta(String userXpath, String passXpath, String usuario, String contrasena) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userXpath))).sendKeys(usuario);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passXpath))).sendKeys(contrasena);
        System.out.println(" Credenciales ingresadas en VerDetallesCuenta.");
        String obj="Credenciales ingresadas en VerDetallesCuenta";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("presiona el boton de login {string}")
    public void clickEnLogin(String xpath) throws IOException {
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en botón Login.");
        String obj="Click en botón Login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("el usuario selecciona la cuenta {string} en el menú desplegable {string}")
    public void seleccionarCuenta(String cuenta, String xpathSelect) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSelect)));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuenta);
        System.out.println(" Cuenta seleccionada: " + cuenta);
        String obj="Cuenta seleccionada";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("hace click en el boton GO {string}")
    public void clickBotonGo(String xpath) throws IOException {
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en botón GO.");
        String obj="Click en botón GO";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Then("Se valida que se muestra el campo {string} con el mensaje {string}")
    public void validarMensaje(String xpath, String mensajeEsperado) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        String texto = elemento.getText().trim();
        assertTrue(texto.contains(mensajeEsperado),
                " El texto no coincide. Esperado: " + mensajeEsperado + " | Obtenido: " + texto);

        System.out.println(" Se muestra el mensaje esperado: " + texto);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String obj="Se muestra el mensaje esperado";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
        driver.quit();
        System.out.println(" Navegador cerrado correctamente.");

    }
}
