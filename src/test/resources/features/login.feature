Feature: Inicio de sesión con validación de credenciales de admin



  Background:

    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"

    And el usuario realiza click en "//*[@id='LoginLink']/font" para dirigirse a la pagina de login



  Scenario: Inicio de sesión exitoso de administrador

    When el usuario ingresa en "//input[@id='uid']" el usuario "admin" y en "//input[@id='passw']" la contraseña "admin"

    And hace click en el boton de login "//input[@type='submit' and @value='Login']"

    Then Se deberia mostrar el campo "//h1" con el mensaje "Hello Admin User"



  Scenario Outline: Inicio de sesión erróneo de administrador

    When el usuario ingresa en "//input[@id='uid']" el usuario "<usuario>" y en "//input[@id='passw']" la contraseña "<contrasena>"

    And hace click en el boton de login "//input[@type='submit' and @value='Login']"

    Then Se deberia mostrar el campo "//span[@id='_ctl0__ctl0_Content_Main_message']" con el mensaje "<mensaje>"

    Examples:

      | usuario | contrasena | mensaje   |

      | leafar | 12345   | Login Failed |

      | savkita | 54321   | Login Failed |

      | rodri  | 1837    | Login Failed |

