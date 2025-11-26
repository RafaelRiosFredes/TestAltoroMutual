package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.utils.ExcelUtils;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    static WebDriver driver;
    // Definimos la ruta y la hoja específica para Login
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja2";

    @Before
    public void setup() throws IOException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // NOTA: No cargamos el Excel aquí para evitar conflictos con Transferencias.
        // Lo haremos en el primer @Given.
    }

    @After
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Given("el navegador esta abierto en la pagina {string}")
    public void el_navegador_esta_abierto_en_la_pagina(String url) throws IOException, InterruptedException {
        // --- CORRECCIÓN IMPORTANTE ---
        // Forzamos la carga de la Hoja2 aquí. Así, aunque se haya ejecutado antes
        // el código de Transferencias (Hoja1), aquí lo sobrescribimos a Hoja2.
        ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
        // -----------------------------

        driver.get(url);
        Thread.sleep(2000);
        System.out.println("Página abierta: " + url);
        String obj="Acceder_AltoroMutual";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Given("el usuario realiza click en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_click_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        loginLink.click();

        System.out.println(" Click en el enlace de login.");
        String obj="Click_en_el_enlace_de_login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("el usuario ingresa en {string} y en {string} las credenciales de la fila {int}")
    public void elUsuarioIngresaCredencialesDeLaFila(String userXpath, String passXpath, int nroFila) throws IOException {
        // Leemos usuario y contraseña usando el número de fila recibido desde el feature
        String usuario = ExcelUtils.getCellData(nroFila, 0);
        String contrasena = ExcelUtils.getCellData(nroFila, 1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userXpath)));
        userField.clear();
        userField.sendKeys(usuario);

        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passXpath)));
        passField.clear();
        passField.sendKeys(contrasena);

        System.out.println("Credenciales ingresadas desde Excel (Fila " + nroFila + "): " + usuario);
        String obj="Credenciales_ingresadas_fila_" + nroFila;
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("hace click en el boton de login {string}")
    public void haceClickEnElBotonDeLogin(String xpath) throws IOException {
        WebElement loginBtn = driver.findElement(By.xpath(xpath));
        loginBtn.click();
        System.out.println(" Click en el botón de login.");

        // Esperamos a que la URL cambie O aparezca un mensaje de error/éxito
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("bank"),
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='_ctl0__ctl0_Content_Main_message']")),
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Hello')]"))
                ));

        String obj="Click_en_boton_login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Then("Se deberia mostrar el campo {string} con el mensaje de la fila {int}")
    public void seDeberiaMostrarElCampoConElMensajeDeLaFila(String xpath, int nroFila) throws IOException {
        // Leemos el mensaje esperado de la columna 2 del Excel
        String mensajeEsperado = ExcelUtils.getCellData(nroFila, 2);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensajeElemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        String texto = mensajeElemento.getText().trim();
        if (texto.isEmpty()) {
            texto = mensajeElemento.getAttribute("innerText").trim();
        }

        System.out.println("Validando fila " + nroFila + ". Esperado: [" + mensajeEsperado + "] - Actual: [" + texto + "]");

        // Validamos que el texto en pantalla contenga el texto del Excel
        assertTrue("El texto no coincide. Esperado: " + mensajeEsperado + " | Obtenido: " + texto,
                texto.contains(mensajeEsperado));

        String obj="Validacion_Exitosa_Fila_" + nroFila;
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }
}