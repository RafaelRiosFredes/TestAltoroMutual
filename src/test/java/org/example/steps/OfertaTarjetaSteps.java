package org.example.steps;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfertaTarjetaSteps {

    WebDriver driver;

    // ====== Background: abrir navegador y loguearse ======
    @Given("el navegador está abierto en {string}")
    public void abrirNavegadorEn(String url) throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("Navegador abierto en: " + url);
        String obj="acceso altoro mutual";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");


    }

    @Given("el usuario hace click en {string} para ir a login")
    public void clickIrALogin(String xpath) throws IOException {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("Click en enlace de login.");
        String obj="Click_en el botón de login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Given("escribe el usuario en {string} con {string}")
    public void escribirUsuario(String xpath, String usuario) throws IOException {
        WebElement user = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        user.clear();
        user.sendKeys(usuario);
        System.out.println("Usuario ingresado: " + usuario);
        String obj="escribe usuario";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Given("escribe la clave en {string} con {string}")
    public void escribirClave(String xpath, String clave) throws IOException {
        WebElement pass = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        pass.clear();
        pass.sendKeys(clave);
        System.out.println("Clave ingresada.");
        String obj="ingreso clave";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @Given("hace click en {string}")
    public void clickGenerico(String xpath) throws IOException {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("Click ejecutado: " + xpath);
        String obj="Click_en el botón ingresar ";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    // ====== Escenarios (3.1 y 3.2) ======

    @When("hace click en el enlace {string}")
    public void clickEnlace(String xpath) throws IOException {
        // Cambiar a la pestaña del dashboard si el login abrió una nueva
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Esperar y hacer click en el enlace "Here" (apply.jsp)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement enlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        enlace.click();
        System.out.println("Click en enlace de aplicación de tarjeta.");

        // Si el clic abre una nueva pestaña (raro, pero posible), cambiar también a ella
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
        String obj="Click en la app de tarjeta";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("escribe la clave de confirmación en {string} con {string}")
    public void escribirClaveConfirmacion(String xpath, String clave) throws IOException {
        WebElement passConfirm = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        passConfirm.clear();
        passConfirm.sendKeys(clave);
        System.out.println("Clave de confirmación ingresada.");
        String obj="Clave correcta";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    @When("hace click en el botón de envío {string}")
    public void clickSubmit(String xpath) throws IOException {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        System.out.println("Click en el botón Submit.");
        String obj="Click_en el botón submit";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    // ====== Validación final ======
    @Then("debería ver en {string} el texto {string}")
    public void deberiaVerMensaje(String xpath, String esperado) throws IOException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Reintenta si ocurre un StaleElementReferenceException
            for (int intento = 1; intento <= 2; intento++) {
                try {
                    WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                    String texto = elemento.getText().trim();

                    if (texto.contains(esperado)) {
                        System.out.println("Mensaje detectado correctamente: " + esperado);
                        break;
                    } else if (intento == 2) {
                        System.out.println("Texto parcial detectado (intento " + intento + "): "
                                + texto.substring(0, Math.min(200, texto.length())) + "...");
                        assertTrue(texto.contains(esperado),
                                "El texto esperado no se encontró en el elemento visible.");
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("Elemento recargado, reintentando detección...");
                    Thread.sleep(1500); // pequeña pausa para permitir que el DOM se actualice
                }
            }

        } catch (TimeoutException e) {
            WebElement body = driver.findElement(By.tagName("body"));
            String textoBody = body.getText().trim();

            if (textoBody.contains(esperado)) {
                System.out.println("Mensaje encontrado en el cuerpo de la página: " + esperado);
            } else {
                System.out.println("No se encontró el texto esperado. Texto parcial del body: "
                        + textoBody.substring(0, Math.min(200, textoBody.length())) + "...");
            }

            assertTrue(textoBody.contains(esperado),
                    "No se encontró el texto esperado en la página completa.");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                Thread.sleep(2000); // pequeña pausa para observar el resultado
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            driver.quit();
            System.out.println("Navegador cerrado correctamente.");
        }
        String obj="texto error";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }
}
