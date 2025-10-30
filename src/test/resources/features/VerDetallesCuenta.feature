Feature: Visualización del balance de la cuenta Corporate-Admin

  Scenario: Visualizar el balance de la cuenta Corporate
    Given se inicia un nuevo navegador en la página "https://demo.testfire.net/"
    And el usuario accede al login haciendo click en "//*[@id='LoginLink']/font"
    When el usuario completa los campos "//input[@id='uid']" y "//input[@id='passw']" con las credenciales "admin" y "admin"
    And presiona el boton de login "//input[@type='submit' and @value='Login']"
    And el usuario selecciona la cuenta "800000 Corporate" en el menú desplegable "//select[@id='listAccounts']"
    And hace click en el boton GO "//*[@id='btnGetAccount']"
    Then Se valida que se muestra el campo "//h1" con el mensaje "Account History - 800000 Corporate"
