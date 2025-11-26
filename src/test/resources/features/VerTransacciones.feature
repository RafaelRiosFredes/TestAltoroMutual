Feature: Visualización de todas las transacciones en Altoro Mutual

  Scenario Outline: Visualizar todas las transacciones de la cuenta del usuario
    Given se abre el navegador en la página "https://demo.testfire.net/"
    And el usuario accede al login desde VerTransacciones haciendo click en "//*[@id='LoginLink']/font"

    # Login con datos de Hoja5 (Col 0 y 1)
    When en VerTransacciones el usuario completa "//input[@id='uid']" y "//input[@id='passw']" con las credenciales de la fila <fila>
    And presiona el botón de login en VerTransacciones "//input[@type='submit' and @value='Login']"

    # Navegación interna
    And el usuario realiza click en "//*[@id='MenuHyperLink2']" para acceder a la seccion de transacciones recientes

    # Validación
    Then Se deberia mostrar el campo "//*[@id='_ctl0__ctl0_Content_Main_MyTransactions']" con el historial de transacciones

    Examples:
      | fila | descripcion |
      | 1    | Admin User  |