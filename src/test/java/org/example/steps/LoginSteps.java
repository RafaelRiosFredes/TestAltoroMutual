package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.utils.ExcelUtils;
import org.example.utils.Utility;
import org.openqa.selenium.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    static WebDriver driver;
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja2";

    @Before
    public void setup() {
        driver = Utility.getDriver();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        // No cerramos el navegador para reutilizarlo
    }

    @Given("el navegador esta abierto en la pagina {string}")
    public void el_navegador_esta_abierto_en_la_pagina(String url) throws IOException, InterruptedException {
        ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
        driver.get(url);
        Thread.sleep(2000); // Espera fija
        String obj = "Acceder_AltoroMutual";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("el usuario realiza click en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_click_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000); // Esperamos antes de buscar el elemento
        driver.findElement(By.xpath(xpath)).click();

        System.out.println(" Click en el enlace de login.");
        String obj = "Click_en_el_enlace_de_login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("el usuario ingresa en {string} y en {string} las credenciales de la fila {int}")
    public void elUsuarioIngresaCredencialesDeLaFila(String userXpath, String passXpath, int nroFila) throws IOException, InterruptedException {
        String usuario = ExcelUtils.getCellData(nroFila, 0);
        String contrasena = ExcelUtils.getCellData(nroFila, 1);

        Thread.sleep(2000); // Espera antes de interactuar con los campos

        WebElement userField = driver.findElement(By.xpath(userXpath));
        userField.clear();
        userField.sendKeys(usuario);

        WebElement passField = driver.findElement(By.xpath(passXpath));
        passField.clear();
        passField.sendKeys(contrasena);

        System.out.println("Credenciales ingresadas desde Excel (Fila " + nroFila + "): " + usuario);
        String obj = "Credenciales_ingresadas_fila_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("hace click en el boton de login {string}")
    public void haceClickEnElBotonDeLogin(String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println(" Click en el botón de login.");

        // Esperamos un tiempo prudente para que cargue la siguiente página
        Thread.sleep(3000);

        String obj = "Click_en_boton_login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Then("Se deberia mostrar el campo {string} con el mensaje de la fila {int}")
    public void seDeberiaMostrarElCampoConElMensajeDeLaFila(String xpath, int nroFila) throws IOException, InterruptedException {
        String mensajeEsperado = ExcelUtils.getCellData(nroFila, 2);

        Thread.sleep(2000); // Esperamos a que el mensaje aparezca
        WebElement mensajeElemento = driver.findElement(By.xpath(xpath));

        String texto = mensajeElemento.getText().trim();
        if (texto.isEmpty()) {
            texto = mensajeElemento.getAttribute("innerText").trim();
        }

        System.out.println("Validando fila " + nroFila + ". Esperado: [" + mensajeEsperado + "] - Actual: [" + texto + "]");

        assertTrue("El texto no coincide. Esperado: " + mensajeEsperado + " | Obtenido: " + texto,
                texto.contains(mensajeEsperado));

        String obj = "Validacion_Exitosa_Fila_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }
}