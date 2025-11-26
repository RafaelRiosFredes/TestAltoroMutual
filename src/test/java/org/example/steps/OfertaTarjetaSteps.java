package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.example.utils.ExcelUtils;
import org.example.utils.Utility;
import org.openqa.selenium.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class OfertaTarjetaSteps {

    static WebDriver driver;
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja3";

    @Before
    public void setup() {
        driver = Utility.getDriver();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        // No cerramos el navegador
    }

    @Given("el navegador está abierto en {string}")
    public void abrirNavegadorEn(String url) throws IOException, InterruptedException {
        try {
            ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
        } catch (Exception e) {
            System.err.println("Error cargando Excel: " + e.getMessage());
        }

        driver.get(url);
        Thread.sleep(2000);
        System.out.println("Navegador abierto en: " + url);
        String obj = "acceso altoro mutual";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("el usuario hace click en {string} para ir a login")
    public void clickIrALogin(String xpath) throws IOException, InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println("Click en enlace de login.");
        String obj = "Click_en_boton_ir_login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("escribe el usuario en {string} con datos de la fila {int}")
    public void escribirUsuario(String xpath, int nroFila) throws IOException, InterruptedException {
        String usuario = ExcelUtils.getCellData(nroFila, 0);

        Thread.sleep(2000);
        WebElement user = driver.findElement(By.xpath(xpath));
        user.clear();
        user.sendKeys(usuario);
        System.out.println("Usuario ingresado (Fila " + nroFila + "): " + usuario);

        String obj = "escribe_usuario_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("escribe la clave en {string} con datos de la fila {int}")
    public void escribirClave(String xpath, int nroFila) throws IOException, InterruptedException {
        String clave = ExcelUtils.getCellData(nroFila, 1);

        Thread.sleep(2000);
        WebElement pass = driver.findElement(By.xpath(xpath));
        pass.clear();
        pass.sendKeys(clave);
        System.out.println("Clave ingresada (Fila " + nroFila + ")");

        String obj = "ingreso_clave_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("hace click en {string}")
    public void clickGenerico(String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println("Click ejecutado: " + xpath);
        String obj = "Click_generico";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("hace click en el enlace {string}")
    public void clickEnlace(String xpath) throws IOException, InterruptedException {
        // Manejo de pestañas
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        Thread.sleep(2000);
        WebElement enlace = driver.findElement(By.xpath(xpath));
        enlace.click();
        System.out.println("Click en enlace de aplicación de tarjeta.");

        String obj = "Click_app_tarjeta";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("escribe la clave de confirmación en {string} con datos de la fila {int}")
    public void escribirClaveConfirmacion(String xpath, int nroFila) throws IOException, InterruptedException {
        String claveConf = ExcelUtils.getCellData(nroFila, 2);

        Thread.sleep(2000);
        WebElement passConfirm = driver.findElement(By.xpath(xpath));
        passConfirm.clear();
        passConfirm.sendKeys(claveConf);
        System.out.println("Clave confirmación ingresada (Fila " + nroFila + "): " + claveConf);

        String obj = "Clave_confirmacion_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("hace click en el botón de envío {string}")
    public void clickSubmit(String xpath) throws IOException, InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println("Click en el botón Submit.");
        String obj = "Click_submit";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Then("debería ver en {string} el texto de la fila {int}")
    public void deberiaVerMensaje(String xpath, int nroFila) throws IOException, InterruptedException {
        String esperado = ExcelUtils.getCellData(nroFila, 3);

        Thread.sleep(3000);

        // Try-catch simple para evitar error si no encuentra el elemento
        String texto = "";
        try {
            WebElement elemento = driver.findElement(By.xpath(xpath));
            texto = elemento.getText().trim();
        } catch (NoSuchElementException e) {
            WebElement body = driver.findElement(By.tagName("body"));
            texto = body.getText().trim();
        }

        System.out.println("Validando Fila " + nroFila + ". Esperado: [" + esperado + "] vs Actual: [" + texto + "]");

        assertTrue("El texto no coincide. Esperado: " + esperado + " | Obtenido: " + texto,
                texto.contains(esperado));

        String obj = "Validacion_final_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }
}