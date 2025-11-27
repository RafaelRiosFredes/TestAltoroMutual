package org.example.Ejecutador;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features"},glue = {"org.example.steps"},
plugin = {"json:target/cucumber.json","pretty","html:target/cucumber-reports/report.html",
        "junit:target/cucumber-results.xml"},monochrome = true)
public class RunCucumberTest {
}


//features = {"src/test/resources/features"}  TODOS LOS TEST//
//(features = {"./src/test/resources/features/RealizarTransferencia.feature"} Realizar transferencia
//(features = {"src/test/resources/features/login.feature"}  LOGIN
//(features = {"src/test/resources/features/OfertaTarjeta.feature"}  OFERTA TARJETA
//(features = {"src/test/resources/features/VerDetallesCuenta.feature"} vr detalles cuenta
//(features = {"src/test/resources/features/VerTransacciones.feature"} Ver transacciones