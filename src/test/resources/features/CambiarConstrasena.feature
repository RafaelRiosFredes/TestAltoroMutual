Feature: Cambiar contraseña de usuario desde admin

  Background:
    Given al navegar hasta la url "https://demo.testfire.net/"
    And hacemos click en el link "//*[@id='LoginLink']/font"
    And coloca en el campo usuario "//*[@id='uid']" el texto "admin"
    And coloca en el campo password "//*[@id='passw']" el texto "admin"
    And hacer click sobre el boton Login "//*[@id='login']/table/tbody/tr[3]/td[2]/input"

  Scenario: Cambio de contraseña de un usuario
    When hacer click en el link Edit Users "//*[@id='_ctl0__ctl0_Content_Administration']/ul/li/a"
    And seleccionar el usuario "//*[@id='username']" con valor "jsmith"
    And colocar en el campo nueva contraseña "/html/body/table[2]/tbody/tr/td[2]/div/table/tbody/tr[6]/td[2]/input" el texto "12345"
    And colocar en el campo confirmar contraseña "/html/body/table[2]/tbody/tr/td[2]/div/table/tbody/tr[6]/td[3]/input" el texto "12345"
    And hacer click sobre el boton Change Password "/html/body/table[2]/tbody/tr/td[2]/div/table/tbody/tr[6]/td[4]/input"
    Then La página debe mantenerse visible después del cambio de contraseña
