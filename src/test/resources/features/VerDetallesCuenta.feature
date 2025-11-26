Feature: Visualización del balance de la cuenta Corporate-Admin

  Scenario Outline: Visualizar el balance de la cuenta
    Given se inicia un nuevo navegador en la página "https://demo.testfire.net/"
    And el usuario accede al login haciendo click en "//*[@id='LoginLink']/font"

    # Login usando Hoja4 Col 0 y 1
    When el usuario completa los campos "//input[@id='uid']" y "//input[@id='passw']" con las credenciales de la fila <fila>
    And presiona el boton de login "//input[@type='submit' and @value='Login']"

    # Selección de cuenta usando Hoja4 Col 2
    And el usuario selecciona la cuenta de la fila <fila> en el menú desplegable "//select[@id='listAccounts']"
    And hace click en el boton GO "//*[@id='btnGetAccount']"

    # Validación usando Hoja4 Col 3
    Then Se valida que se muestra el campo "//h1" con el mensaje de la fila <fila>

    Examples:
      | fila | descripcion               |
      | 1    | Cuenta Corporate (Admin)  |
      | 2    | Cuenta Checking (Admin)   |