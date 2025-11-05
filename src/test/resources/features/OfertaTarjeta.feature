Feature: Recibir oferta de tarjeta de la página - Admin

  Background:
    Given el navegador está abierto en "https://demo.testfire.net/"
    And el usuario hace click en "//*[@id='LoginLink']/font" para ir a login
    And escribe el usuario en "//input[@id='uid']" con "admin"
    And escribe la clave en "//input[@id='passw']" con "admin"
    And hace click en "//input[@type='submit' and @value='Login']"

  Scenario: Aceptar tarjeta pre aprobada con credenciales correctas
    When hace click en el enlace "//a[@href='apply.jsp']"
    And escribe la clave de confirmación en "//input[@name='passwd']" con "admin"
    And hace click en el botón de envío "//input[@name='Submit' and @value='Submit']"
    Then debería ver en "//span[@id='_ctl0__ctl0_Content_Main_lblMessage']" el texto "Your new Altoro Mutual Gold VISA with a $10000 and 7.9% APR will be sent in the mail."


  Scenario: Aceptar tarjeta pre aprobada con credenciales erróneas
    When hace click en el enlace "//a[@href='apply.jsp']"
    And escribe la clave de confirmación en "//input[@name='passwd']" con "admin1"
    And hace click en el botón de envío "//input[@name='Submit' and @value='Submit']"
    Then debería ver en "//span[@id='_ctl0__ctl0_Content_Main_message']" el texto "Login Failed: We're sorry, but this username or password was not found in our system. Please try again."
