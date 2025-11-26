package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
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

public class OfertaTarjetaSteps {

    static WebDriver driver;
    // Definimos la ruta y usamos "Hoja3" para esta prueba específica
    private static final String EXCEL_PATH = "src/test/resources/testData/dataTransferFondos.xlsx";
    private static final String EXCEL_SHEET = "Hoja3";

    // --- SETUP Y TEARDOWN ---
    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador cerrado correctamente.");
        }
    }

    // --- PASOS (STEPS) ---

    @Given("el navegador está abierto en {string}")
    public void abrirNavegadorEn(String url) throws IOException {
        // IMPORTANTE: Cargamos la Hoja3 aquí, al inicio de la prueba
        try {
            ExcelUtils.setExcelFileSheet(EXCEL_PATH, EXCEL_SHEET);
        } catch (Exception e) {
            System.err.println("Error cargando Excel: " + e.getMessage());
        }

        driver.get(url);
        System.out.println("Navegador abierto en: " + url);
        String obj = "acceso altoro mutual";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("el usuario hace click en {string} para ir a login")
    public void clickIrALogin(String xpath) throws IOException {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("Click en enlace de login.");
        String obj = "Click_en_boton_ir_login";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    // --- LOGIN CON EXCEL (Columna 0 y 1) ---
    @Given("escribe el usuario en {string} con datos de la fila {int}")
    public void escribirUsuario(String xpath, int nroFila) throws IOException {
        String usuario = ExcelUtils.getCellData(nroFila, 0); // Columna 0: Usuario Login

        WebElement user = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        user.clear();
        user.sendKeys(usuario);
        System.out.println("Usuario ingresado (Fila " + nroFila + "): " + usuario);

        String obj = "escribe_usuario_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("escribe la clave en {string} con datos de la fila {int}")
    public void escribirClave(String xpath, int nroFila) throws IOException {
        String clave = ExcelUtils.getCellData(nroFila, 1); // Columna 1: Clave Login

        WebElement pass = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        pass.clear();
        pass.sendKeys(clave);
        System.out.println("Clave ingresada (Fila " + nroFila + ")");

        String obj = "ingreso_clave_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @Given("hace click en {string}")
    public void clickGenerico(String xpath) throws IOException {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("Click ejecutado: " + xpath);
        String obj = "Click_generico";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    // --- FLUJO DE TARJETA ---

    @When("hace click en el enlace {string}")
    public void clickEnlace(String xpath) throws IOException {
        // Manejo de pestañas por si acaso
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement enlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        enlace.click();
        System.out.println("Click en enlace de aplicación de tarjeta.");

        String obj = "Click_app_tarjeta";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("escribe la clave de confirmación en {string} con datos de la fila {int}")
    public void escribirClaveConfirmacion(String xpath, int nroFila) throws IOException {
        String claveConf = ExcelUtils.getCellData(nroFila, 2); // Columna 2: Clave Confirmación

        WebElement passConfirm = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        passConfirm.clear();
        passConfirm.sendKeys(claveConf);
        System.out.println("Clave confirmación ingresada (Fila " + nroFila + "): " + claveConf);

        String obj = "Clave_confirmacion_" + nroFila;
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    @When("hace click en el botón de envío {string}")
    public void clickSubmit(String xpath) throws IOException {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("Click en el botón Submit.");
        String obj = "Click_submit";
        Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
    }

    // --- VALIDACIÓN FINAL (Columna 3) ---
    @Then("debería ver en {string} el texto de la fila {int}")
    public void deberiaVerMensaje(String xpath, int nroFila) throws IOException {
        String esperado = ExcelUtils.getCellData(nroFila, 3); // Columna 3: Mensaje esperado

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            String texto = elemento.getText().trim();

            System.out.println("Validando Fila " + nroFila + ". Esperado: [" + esperado + "] vs Actual: [" + texto + "]");

            assertTrue("El texto no coincide. Esperado: " + esperado + " | Obtenido: " + texto,
                    texto.contains(esperado));

        } catch (TimeoutException e) {
            // Fallback: buscar en el body si el elemento específico falla
            WebElement body = driver.findElement(By.tagName("body"));
            String textoBody = body.getText().trim();

            System.out.println("Timeout en elemento. Buscando en body...");
            assertTrue("No se encontró el texto en la página. Esperado: " + esperado,
                    textoBody.contains(esperado));
        } finally {
            String obj = "Validacion_final_" + nroFila;
            Utility.captureScreenShot(driver, "evidencias\\" + obj + " " + Utility.GetTimeStampValue() + ".png");
        }
    }
}