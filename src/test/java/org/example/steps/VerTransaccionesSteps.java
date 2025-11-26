package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.example.utils.ExcelUtils;
import org.example.utils.Utility;
import org.openqa.selenium.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerTransaccionesSteps {

    static WebDriver driver;
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja5";

    @Before
    public void setup() {
        driver = Utility.getDriver();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        // No cerrar
    }

    @Given("se abre el navegador en la página {string}")
    public void abrirNavegador(String url) throws IOException, InterruptedException {
        try {
            ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
        } catch (Exception e) {
            System.err.println("Error cargando Excel: " + e.getMessage());
        }

        driver.get(url);
        Thread.sleep(2000);
        System.out.println(" Página abierta: " + url);
        String obj = "Página_abierta_transacciones";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("el usuario accede al login desde VerTransacciones haciendo click en {string}")
    public void irAlLoginVerTransacciones(String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement loginLink = driver.findElement(By.xpath(xpath));
        loginLink.click();
        System.out.println(" Click en enlace de login (VerTransacciones).");
        String obj = "Click_login_link_transacciones";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("en VerTransacciones el usuario completa {string} y {string} con las credenciales de la fila {int}")
    public void ingresarCredencialesVerTransacciones(String userXpath, String passXpath, int nroFila) throws IOException, InterruptedException {
        String usuario = ExcelUtils.getCellData(nroFila, 0);
        String contrasena = ExcelUtils.getCellData(nroFila, 1);

        Thread.sleep(2000);
        driver.findElement(By.xpath(userXpath)).sendKeys(usuario);
        driver.findElement(By.xpath(passXpath)).sendKeys(contrasena);

        System.out.println("Credenciales ingresadas (Fila " + nroFila + ")");
        String obj = "Credenciales_ingresadas_transacciones_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("presiona el botón de login en VerTransacciones {string}")
    public void clickBotonLoginVerTransacciones(String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en botón Login (VerTransacciones).");
        String obj = "Click_boton_login_transacciones";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("el usuario realiza click en {string} para acceder a la seccion de transacciones recientes")
    public void accederASeccionTransacciones(String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement link = driver.findElement(By.xpath(xpath));
        link.click();
        System.out.println(" Click en 'View Recent Transactions'.");
        String obj = "Click_view_recent_transactions";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Then("Se deberia mostrar el campo {string} con el historial de transacciones")
    public void validarHistorial(String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement tabla = driver.findElement(By.xpath(xpath));

        assertTrue(tabla.isDisplayed(), " No se visualizó la tabla de transacciones.");
        System.out.println(" Se visualiza correctamente el historial de transacciones.");

        String obj = "Historial_transacciones_visible";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }
}