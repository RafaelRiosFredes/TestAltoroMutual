package org.example.steps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class RealizarTransferenciaSteps {
    static WebDriver driver;

    @Before
    public void setup(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Given("se inicia navegador en la página {string}")
    public void seIniciaNavegadorEnLaPagina(String url) throws InterruptedException, IOException {
        driver.get(url);
        Thread.sleep(2000);
        String obj="acceso altoro mutual";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }
   
    @And("el usuario entra al login haciendo click en {string}")
    public void elUsuarioEntraAlLoginHaciendoClickEn(String xpath) throws InterruptedException, IOException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
        String obj="user entra a login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("el usuario rellena los campos {string} y {string} con las credenciales {string} y {string}")
    public void elUsuarioRellenaLosCamposYConLasCredencialesY(String userXpath, String passXpath, String username, String password) throws IOException {
        driver.findElement(By.xpath(userXpath)).click();
        driver.findElement(By.xpath(userXpath)).clear();
        driver.findElement(By.xpath(userXpath)).sendKeys(username);

        driver.findElement(By.xpath(passXpath)).click();
        driver.findElement(By.xpath(passXpath)).clear();
        driver.findElement(By.xpath(passXpath)).sendKeys(password);
        String obj="usuario rellena campos";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @And("hace click al boton de login {string}")
    public void haceClickAlBotonDeLogin(String xpath) throws InterruptedException, IOException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
        String obj="Click_en el botón de login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("el usuario hace click en {string} para dirigirse a la pagina de transferencias")
    public void el_usuario_realiza_click_en_para_dirigirse_a_la_pagina_de_transferencias(String xpath) throws InterruptedException, IOException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
        String obj="Click pagina transferencia";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }


    @And("el usuario selecciona la cuenta de origen {string} en el menú desplegable {string}")
    public void elUsuarioSeleccionaLaCuentaDeOrigenEnElMenúDesplegable(String cuenta, String xpath) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuenta);
        System.out.println(" Cuenta seleccionada: " + cuenta);
        String obj="usuario selecciona la cuenta de origen";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @And("selecciona la cuenta de destino {string} en el menú desplegable {string}")
    public void seleccionaLaCuentaDeDestinoEnElMenúDesplegable(String cuenta, String xpath) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Select select = new Select(selectElement);
        select.selectByVisibleText(cuenta);
        System.out.println(" Cuenta seleccionada: " + cuenta);
        String obj="selecciona la cuenta de destino";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @And("ingresa el monto a transferir {string} en el campo {string}")
    public void ingresaElMontoATransferirEnElCampo(String monto, String xpath) throws IOException {
        driver.findElement(By.xpath(xpath)).clear();
        driver.findElement(By.xpath(xpath)).sendKeys(monto);
        String obj="ingresa el monto a transferir";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @And("hace click en el boton {string}")
    public void haceClickEnElBoton(String xpath) throws InterruptedException, IOException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(1000);
        String obj="hace click en el boton transferir";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");

    }

    @Then("Se muestra el campo {string} con el mensaje {string}")
    public void seMuestraElCampoConElMensaje(String msgXpath, String mensaje) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(msgXpath)));
        String texto = msg.getText().trim();

        assertTrue("Mensaje actual: [" + texto + "]", texto.contains(mensaje));
        System.out.println(" Se visualiza correctamente la transferencia realizada");
        String obj="Se visualiza correctamente la transferencia realizada";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }


}
