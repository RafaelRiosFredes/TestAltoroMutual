package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*; // Aunque no usemos WebDriverWait, Select está aquí a veces
import org.example.utils.ExcelUtils;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class RealizarTransferenciaSteps {
    static WebDriver driver;
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja1";

    @Before
    public void setup() throws IOException {
        driver = Utility.getDriver();
        driver.manage().deleteAllCookies();
        ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
    }

    @After
    public void tearDown() {
        // No cerramos el navegador
    }

    @Given("se inicia navegador en la página {string}")
    public void seIniciaNavegadorEnLaPagina(String url) throws InterruptedException, IOException {
        driver.get(url);
        Thread.sleep(2000);
        String obj = "acceso altoro mutual";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("el usuario entra al login haciendo click en {string}")
    public void elUsuarioEntraAlLoginHaciendoClickEn(String xpath) throws InterruptedException, IOException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(xpath)).click();
        String obj = "user entra a login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("el usuario rellena los campos {string} y {string} con las credenciales {string} y {string}")
    public void elUsuarioRellenaLosCamposYConLasCredencialesY(String userXpath, String passXpath, String username, String password) throws IOException, InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(userXpath)).click();
        driver.findElement(By.xpath(userXpath)).clear();
        driver.findElement(By.xpath(userXpath)).sendKeys(username);

        driver.findElement(By.xpath(passXpath)).click();
        driver.findElement(By.xpath(passXpath)).clear();
        driver.findElement(By.xpath(passXpath)).sendKeys(password);
        String obj = "usuario rellena campos";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("hace click al boton de login {string}")
    public void haceClickAlBotonDeLogin(String xpath) throws InterruptedException, IOException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
        String obj = "Click_en el botón de login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("el usuario hace click en {string} para dirigirse a la pagina de transferencias")
    public void el_usuario_realiza_click_en_para_dirigirse_a_la_pagina_de_transferencias(String xpath) throws InterruptedException, IOException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(xpath)).click();
        String obj = "Click pagina transferencia";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("el usuario selecciona la cuenta de origen {string} en el menú desplegable {string}")
    public void elUsuarioSeleccionaLaCuentaDeOrigenEnElMenúDesplegable(String cuenta, String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement selectElement = driver.findElement(By.xpath(xpath));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuenta);
        System.out.println(" Cuenta seleccionada: " + cuenta);
        String obj = "usuario selecciona la cuenta de origen";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("selecciona la cuenta de destino {string} en el menú desplegable {string}")
    public void seleccionaLaCuentaDeDestinoEnElMenúDesplegable(String cuenta, String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement selectElement = driver.findElement(By.xpath(xpath));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuenta);
        System.out.println(" Cuenta seleccionada: " + cuenta);
        String obj = "selecciona la cuenta de destino";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("ingresa el monto a transferir {string} en el campo {string}")
    public void ingresaElMontoATransferirEnElCampo(String monto, String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).clear();
        driver.findElement(By.xpath(xpath)).sendKeys(monto);
        String obj = "ingresa el monto a transferir";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("hace click en el boton {string}")
    public void haceClickEnElBoton(String xpath) throws InterruptedException, IOException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        String obj = "hace click en el boton transferir";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Then("Se muestra el campo {string} con el mensaje {string}")
    public void seMuestraElCampoConElMensaje(String msgXpath, String mensaje) throws IOException, InterruptedException {
        Thread.sleep(3000); // Esperar a que se procese la transferencia
        WebElement msg = driver.findElement(By.xpath(msgXpath));
        String texto = msg.getText().trim();

        assertTrue("Mensaje actual: [" + texto + "]", texto.contains(mensaje));
        System.out.println(" Se visualiza correctamente la transferencia realizada");
        String obj = "Se visualiza correctamente la transferencia realizada";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    // --- Métodos Data Driven (Excel) con Thread.sleep ---

    @When("el usuario rellena los campos {string} y {string} con los datos de la fila {int}")
    public void elUsuarioRellenaLosCamposConLosDatosDeLaFila(String userXpath, String passXpath, int nroFila) throws IOException, InterruptedException {
        String username = ExcelUtils.getCellData(nroFila, 0);
        String password = ExcelUtils.getCellData(nroFila, 1);

        Thread.sleep(2000);
        driver.findElement(By.xpath(userXpath)).click();
        driver.findElement(By.xpath(userXpath)).clear();
        driver.findElement(By.xpath(userXpath)).sendKeys(username);

        driver.findElement(By.xpath(passXpath)).click();
        driver.findElement(By.xpath(passXpath)).clear();
        driver.findElement(By.xpath(passXpath)).sendKeys(password);

        String obj = "usuario rellena campos desde Excel fila " + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("el usuario selecciona la cuenta de origen en {string} con los datos de la fila {int}")
    public void elUsuarioSeleccionaLaCuentaDeOrigenConDatosDeLaFila(String xpath, int nroFila) throws IOException, InterruptedException {
        String cuentaOrigen = ExcelUtils.getCellData(nroFila, 2);

        Thread.sleep(2000);
        WebElement selectElement = driver.findElement(By.xpath(xpath));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuentaOrigen);

        System.out.println("Cuenta de origen seleccionada (fila " + nroFila + "): " + cuentaOrigen);
        String obj = "cuenta origen fila " + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("selecciona la cuenta de destino en {string} con los datos de la fila {int}")
    public void seleccionaLaCuentaDeDestinoConDatosDeLaFila(String xpath, int nroFila) throws IOException, InterruptedException {
        String cuentaDestino = ExcelUtils.getCellData(nroFila, 3);

        Thread.sleep(2000);
        WebElement selectElement = driver.findElement(By.xpath(xpath));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuentaDestino);

        System.out.println("Cuenta de destino seleccionada (fila " + nroFila + "): " + cuentaDestino);
        String obj = "cuenta destino fila " + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @And("ingresa el monto a transferir en el campo {string} con los datos de la fila {int}")
    public void ingresaElMontoATransferirEnElCampoConDatosDeLaFila(String xpath, int nroFila) throws IOException, InterruptedException {
        String monto = ExcelUtils.getCellData(nroFila, 4);

        Thread.sleep(1000);
        WebElement amountField = driver.findElement(By.xpath(xpath));
        amountField.clear();
        amountField.sendKeys(monto);

        String obj = "monto transferencia fila " + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Then("Se muestra el campo {string} con el mensaje de la fila {int}")
    public void seMuestraElCampoConElMensajeDeLaFila(String msgXpath, int nroFila) throws IOException, InterruptedException {
        String mensajeEsperado = ExcelUtils.getCellData(nroFila, 5);

        Thread.sleep(3000);
        WebElement msg = driver.findElement(By.xpath(msgXpath));
        String texto = msg.getText().trim();

        assertTrue("Mensaje actual: [" + texto + "]", texto.contains(mensajeEsperado));
        System.out.println("Se visualiza correctamente la transferencia fila " + nroFila);

        String obj = "mensaje transferencia fila " + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }
}