package org.example;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import static
        io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static
        io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.example.steps")//ruta de la carpeta steps
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary,json:target/cucumber-report.json")

public class RunCucumberTest {
}
