Feature: Recibir oferta de tarjeta de la página - Admin

  # Abrimos la web y entramos al formulario de login
  Background:
    Given el navegador está abierto en "https://demo.testfire.net/"
    And el usuario hace click en "//*[@id='LoginLink']/font" para ir a login
    And escribe el usuario en "//input[@id='uid']" con "admin"
    And escribe la clave en "//input[@id='passw']" con "admin"
    And hace click en "//input[@type='submit' and @value='Login']"

  # Caso de prueba 3.1
  Scenario: Aceptar tarjeta pre aprobada con credenciales correctas
    When hace click en el enlace "//a[contains(text(),'Click here to apply')]"
    And escribe la clave de confirmación en "//input[@name='passwd']" con "admin"
    And hace click en "//input[@name='Submit' and @value='Submit']"
    Then debería ver en "//span[@id='_ctl0__ctl0_Content_Main_message']" el texto "Your new Altoro Mutual Gold VISA with a $10000 and 7.9% APR will be sent in the mail."

  # Caso de prueba 3.2
  Scenario: Aceptar tarjeta pre aprobada con credenciales erróneas
    When hace click en el enlace "//a[contains(text(),'Click here to apply')]"
    And escribe la clave de confirmación en "//input[@name='passwd']" con "admin1"
    And hace click en "//input[@name='Submit' and @value='Submit']"
    Then debería ver en "//span[@id='_ctl0__ctl0_Content_Main_message']" el texto "Login Failed: We're sorry, but this username or password was not found in our system. Please try again."
