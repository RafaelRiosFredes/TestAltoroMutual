Feature: Visualización de todas las transacciones en Altoro Mutual

  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza click en "//*[@id='LoginLink']/font" para dirigirse a la pagina de login

  Scenario: Visualizar todas las transacciones de la cuenta del usuario
    When el usuario ingresa en "//input[@id='uid']" el usuario "admin" y en "//input[@id='passw']" la contraseña "admin"
    And hace click en el boton de login "//input[@type='submit' and @value='Login']"
    And el usuario realiza click en "//*[@id='MenuHyperLink2']" para acceder a la seccion de transacciones recientes
    Then Se deberia mostrar el campo "//*[@id='_ctl0__ctl0_Content_Main_MyTransactions']" con el historial de transacciones
