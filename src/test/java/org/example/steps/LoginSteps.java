package org.example.steps;



import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;

import io.cucumber.java.en.Then;

import io.cucumber.java.Before;

import io.cucumber.java.After;



import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;



public class LoginSteps {



    static WebDriver driver;



    // === Hooks ===

    @Before

    public void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");

        options.addArguments("--disable-popup-blocking");

        options.addArguments("--no-default-browser-check");

        options.addArguments("--disable-infobars");

        options.addArguments("--user-data-dir=/tmp/chrome-test-profile");



        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();

    }



    @After

    public void tearDown() {

        if (driver != null) {

            driver.quit();

            driver = null;

        }

    }



    // === Steps ===

    @Given("el navegador esta abierto en la pagina {string}")

    public void el_navegador_esta_abierto_en_la_pagina(String url) throws InterruptedException {

        driver.get(url);

        Thread.sleep(1000);

    }



    @Given("el usuario realiza click en {string} para dirigirse a la pagina de login")

    public void el_usuario_realiza_click_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws InterruptedException {

        driver.findElement(By.xpath(xpath)).click();

        Thread.sleep(800);

    }



    @When("el usuario ingresa en {string} el usuario {string} y en {string} la contraseña {string}")

    public void el_usuario_ingresa_en_y_en_la(String xpath_username, String username, String xpath_password, String password) throws InterruptedException {

        WebElement user = driver.findElement(By.xpath(xpath_username));

        user.clear();

        user.sendKeys(username);



        WebElement pass = driver.findElement(By.xpath(xpath_password));

        pass.clear();

        pass.sendKeys(password);



        Thread.sleep(500);

    }



    @When("hace click en el boton de login {string}")

    public void hace_click_en_el_boton_de_login(String xpath) throws InterruptedException {

        driver.findElement(By.xpath(xpath)).click();

        Thread.sleep(1500); // deja tiempo para que cargue la nueva página

    }



    @Then("Se deberia mostrar el campo {string} con el mensaje {string}")

    public void se_deberia_mostrar_el_campo_con_el_mensaje(String xpath, String message) {

        try {

            WebElement element = driver.findElement(By.xpath(xpath));

            String text = element.getText().trim();



            if (text.isEmpty()) {

                text = element.getAttribute("innerText").trim();

            }



            if (text.contains(message)) {

                System.out.println("✅ El mensaje esperado se encontró correctamente: " + text);

            } else {

                throw new RuntimeException("❌ El texto no coincide.\nEsperado: " + message + "\nObtenido: " + text);

            }

        } catch (NoSuchElementException e) {

            throw new RuntimeException("❌ No se encontró el elemento con xpath: " + xpath);

        }

    }

}