Feature: Recibir oferta de tarjeta de la página - Admin

  Scenario Outline: Validar solicitud de tarjeta pre aprobada
    Given el navegador está abierto en "https://demo.testfire.net/"
    And el usuario hace click en "//*[@id='LoginLink']/font" para ir a login
    And escribe el usuario en "//input[@id='uid']" con datos de la fila <fila>
    And escribe la clave en "//input[@id='passw']" con datos de la fila <fila>
    And hace click en "//input[@type='submit' and @value='Login']"
    When hace click en el enlace "//a[@href='apply.jsp']"
    And escribe la clave de confirmación en "//input[@name='passwd']" con datos de la fila <fila>
    And hace click en el botón de envío "//input[@name='Submit' and @value='Submit']"
    Then debería ver en "<xpath_mensaje>" el texto de la fila <fila>

    Examples:
      | fila | xpath_mensaje                                   | descripcion |
      | 1    | //span[@id='_ctl0__ctl0_Content_Main_lblMessage'] | Exitoso     |
      | 2    | //span[@id='_ctl0__ctl0_Content_Main_message']    | Fallido     |