Feature: Realizar transferencias entre cuentas -Admin


  Scenario Outline: Realizar una transferencia entre cuentas usando datos desde Excel
    Given se inicia navegador en la página "https://demo.testfire.net/"
    And el usuario entra al login haciendo click en "//*[@id='LoginLink']/font"
    When el usuario rellena los campos "//input[@id='uid']" y "//input[@id='passw']" con los datos de la fila <NroFila>
    And hace click al boton de login "//input[@type='submit' and @value='Login']"
    And el usuario hace click en "//a[@id='MenuHyperLink3']" para dirigirse a la pagina de transferencias
    And el usuario selecciona la cuenta de origen en "//select[@id='fromAccount']" con los datos de la fila <NroFila>
    And selecciona la cuenta de destino en "//select[@id='toAccount']" con los datos de la fila <NroFila>
    And ingresa el monto a transferir en el campo "//input[@id='transferAmount']" con los datos de la fila <NroFila>
    And hace click en el boton "//input[@id='transfer']"
    Then Se muestra el campo "//span[@id='_ctl0__ctl0_Content_Main_postResp']/span" con el mensaje de la fila <NroFila>

    Examples:
      | NroFila |
      | 1       |
      | 2       |
      | 3       |
