package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.example.utils.ExcelUtils;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*; // Para Select

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class VerDetallesCuentaSteps {

    static WebDriver driver;
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja4";

    @Before
    public void setup() {
        driver = Utility.getDriver();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        // No cerramos
    }

    @Given("se inicia un nuevo navegador en la página {string}")
    public void abrirNavegadorEnPagina(String url) throws IOException, InterruptedException {
        try {
            ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
        } catch (Exception e) {
            System.out.println("Error cargando Excel: " + e.getMessage());
        }

        driver.get(url);
        Thread.sleep(2000);
        System.out.println(" Navegador abierto en: " + url);
        String obj = "Inicio_navegador_detalles";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("el usuario accede al login haciendo click en {string}")
    public void clickEnLinkLogin(String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en enlace de login.");
        String obj = "Click_login_link";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("el usuario completa los campos {string} y {string} con las credenciales de la fila {int}")
    public void ingresarCredencialesCuenta(String userXpath, String passXpath, int nroFila) throws IOException, InterruptedException {
        String usuario = ExcelUtils.getCellData(nroFila, 0);
        String contrasena = ExcelUtils.getCellData(nroFila, 1);

        Thread.sleep(2000);
        driver.findElement(By.xpath(userXpath)).sendKeys(usuario);
        driver.findElement(By.xpath(passXpath)).sendKeys(contrasena);

        System.out.println(" Credenciales ingresadas (Fila " + nroFila + ")");
        String obj = "Credenciales_ingresadas_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("presiona el boton de login {string}")
    public void clickEnLogin(String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en botón Login.");
        String obj = "Click_boton_login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("el usuario selecciona la cuenta de la fila {int} en el menú desplegable {string}")
    public void seleccionarCuenta(int nroFila, String xpathSelect) throws IOException, InterruptedException {
        String cuenta = ExcelUtils.getCellData(nroFila, 2);

        Thread.sleep(2000);
        WebElement selectElement = driver.findElement(By.xpath(xpathSelect));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuenta);

        System.out.println(" Cuenta seleccionada: " + cuenta);
        String obj = "Cuenta_seleccionada_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("hace click en el boton GO {string}")
    public void clickBotonGo(String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en botón GO.");
        String obj = "Click_boton_GO";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Then("Se valida que se muestra el campo {string} con el mensaje de la fila {int}")
    public void validarMensaje(String xpath, int nroFila) throws IOException, InterruptedException {
        String mensajeEsperado = ExcelUtils.getCellData(nroFila, 3);

        Thread.sleep(2000);
        WebElement elemento = driver.findElement(By.xpath(xpath));
        String texto = elemento.getText().trim();

        System.out.println("Validando Fila " + nroFila + ". Esperado: [" + mensajeEsperado + "] - Actual: [" + texto + "]");

        assertTrue(" El texto no coincide. Esperado: " + mensajeEsperado + " | Obtenido: " + texto,
                texto.contains(mensajeEsperado));

        String obj = "Validacion_exitosa_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }
}