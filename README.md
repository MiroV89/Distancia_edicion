﻿# Distancia edicion
 
 Sean dos cadenas de caracteres, X e Y, de un alfabeto finito. Sea n la longitud de la cadena X y m la longitud de la cadena Y. La cadena X se puede transformar en la cadena Y realizando los siguientes tipos de operaciones:
- Borrado: borrar un carácter de la cadena X.
- Inserción: insertar uno de los caracteres de la cadena Y en la cadena X.
- Sustitución: sustituir uno de los caracteres de la cadena X por uno de los de la cadena Y.
Se define la distancia mínima de edición como el número mínimo de operaciones, de entre los tres tipos anteriores, necesarias para transformar la cadena X en la cadena Y.
Por ejemplo, considérense las palabras colchon y colacao. La distancia de edición entre ambas palabras es 3, ya que se necesitan al menos tres operaciones para cambiar de una a otra: 

1. colchon → colachon (Inserción de 'c' entre 'l' y 'c') 
2. colachon → colacaon (Sustitucion de 'h' por 'a') 
3. colacaon → colacao (Borrado de 'n' al final de la palabra 1)


La salida el programa en este caso es:

Distancia: 3

Insercion 4 colachon

Sustitucion 6 colacaon

Borrado 8 colacao


