package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.utils.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class LoginSteps {

    static WebDriver driver;


    // Inicialización del navegador

    @Given("el navegador esta abierto en la pagina {string}")
    public void el_navegador_esta_abierto_en_la_pagina(String url) throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("Página abierta: " + url);
        String obj="Acceder_AltoroMutual";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    // Ir al login

    @Given("el usuario realiza click en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_click_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        loginLink.click();
        System.out.println(" Click en el enlace de login.");
        String obj="Click_en_el_enlace_de_login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");

    }

    // Ingresar credenciales
    @When("el usuario ingresa en {string} el usuario {string} y en {string} la contraseña {string}")
    public void ingresarCredenciales(String userXpath, String usuario, String passXpath, String contrasena) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userXpath)));
        userField.clear();
        userField.sendKeys(usuario);
        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passXpath)));
        passField.clear();
        passField.sendKeys(contrasena);
        System.out.println("Credenciales ingresadas: " + usuario);
        String obj="Credenciales_ingresadas_usuario";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }

    // Hacer click en el botón Login

    @When("hace click en el boton de login {string}")
    public void clickBotonLogin(String xpath) throws IOException {
        WebElement loginBtn = driver.findElement(By.xpath(xpath));
        loginBtn.click();
        System.out.println(" Click en el botón de login.");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("bank"),
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='_ctl0__ctl0_Content_Main_message']"))
                ));
        String obj="Click_en el botón de login";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");

    }

    // Validar mensaje

    @Then("Se deberia mostrar el campo {string} con el mensaje {string}")
    public void se_deberia_mostrar_el_campo_con_el_mensaje(String xpath, String mensajeEsperado) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensajeElemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        String texto = mensajeElemento.getText().trim();
        if (texto.isEmpty()) {
            texto = mensajeElemento.getAttribute("innerText").trim();
        }
        if (texto.contains(mensajeEsperado)) {
            System.out.println("El mensaje esperado se encontró correctamente: " + texto);
        } else {
            throw new AssertionError(" El texto no coincide. Esperado: " + mensajeEsperado + " | Obtenido: " + texto);
        }
        // Espera 5 segundos para visualizar el resultado antes de cerrar
        try {
            System.out.println(" Esperando 5 segundos antes de cerrar el navegador...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        driver.quit();
        System.out.println(" Navegador cerrado correctamente.");
        String obj="Navegador cerrado correctamente";
        Utility.captureScreenShot(driver,"evidencias\\"+obj+" "+Utility.GetTimeStampValue()+".png");
    }
}
