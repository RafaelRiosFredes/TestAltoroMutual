Feature: Inicio de sesión con validación de credenciales de admin



  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza click en "//*[@id='LoginLink']/font" para dirigirse a la pagina de login



  Scenario: Inicio de sesión exitoso de administrador
    When el usuario ingresa en "//input[@id='uid']" y en "//input[@id='passw']" las credenciales de la fila 1
    And hace click en el boton de login "//input[@type='submit' and @value='Login']"
    Then Se deberia mostrar el campo "//h1" con el mensaje de la fila 1



  Scenario Outline: Inicio de sesión erróneo de administrador
    When el usuario ingresa en "//input[@id='uid']" y en "//input[@id='passw']" las credenciales de la fila <fila>
    And hace click en el boton de login "//input[@type='submit' and @value='Login']"
    Then Se deberia mostrar el campo "//span[@id='_ctl0__ctl0_Content_Main_message']" con el mensaje de la fila <fila>

    Examples:
      | fila |
      | 2    |
      | 3    |
      | 4    |

