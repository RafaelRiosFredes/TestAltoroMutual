Feature: Realizar transferencias entre cuentas -Admin
  Background:
    Given se inicia navegador en la página "https://demo.testfire.net/"
    And el usuario entra al login haciendo click en "//*[@id='LoginLink']/font"
    When el usuario rellena los campos "//input[@id='uid']" y "//input[@id='passw']" con las credenciales "admin" y "admin"
    And hace click al boton de login "//input[@type='submit' and @value='Login']"
  Scenario: Realizar una transferencia entre cuentas existentes
    When el usuario hace click en "//a[@id='MenuHyperLink3']" para dirigirse a la pagina de transferencias
    And el usuario selecciona la cuenta de origen "800001 Checking" en el menú desplegable "//select[@id='fromAccount']"
    And selecciona la cuenta de destino "800000 Corporate" en el menú desplegable "//select[@id='toAccount']"
    And ingresa el monto a transferir "1000" en el campo "//input[@id='transferAmount']"
    And hace click en el boton "//input[@id='transfer']"
    Then Se muestra el campo "//span[@id='_ctl0__ctl0_Content_Main_postResp']/span" con el mensaje "1000.0 was successfully transferred from Account 800001 into Account 800000"
