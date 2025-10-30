Feature: Visualización de todas las transacciones en Altoro Mutual

  Scenario: Visualizar todas las transacciones de la cuenta del usuario
    Given se abre el navegador en la página "https://demo.testfire.net/"
    And el usuario accede al login desde VerTransacciones haciendo click en "//*[@id='LoginLink']/font"
    When en VerTransacciones el usuario completa "//input[@id='uid']" con "admin" y "//input[@id='passw']" con la contraseña "admin"
    And presiona el botón de login en VerTransacciones "//input[@type='submit' and @value='Login']"
    And el usuario realiza click en "//*[@id='MenuHyperLink2']" para acceder a la seccion de transacciones recientes
    Then Se deberia mostrar el campo "//*[@id='_ctl0__ctl0_Content_Main_MyTransactions']" con el historial de transacciones
