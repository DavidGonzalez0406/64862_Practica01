Practica 01
Gestor de Contactos

Practica realizada en Java que permite administrar una agenda de contactos con operaciones básicas:

El proyecto incluye dos implementaciones para manejar los archivos:
- MetodosFile.java** → uso de `FileReader` / `FileWriter`
- MetodosBuffer.java** → uso de `BufferedReader` / `BufferedWriter`

---Proceso para ejecutar el programa--

Ubicarse en la carpeta del proyecto:

cd "C:\Ubicacion"


Compilar las clases:

javac -d . src/MetodosFile.java src/MetodosBuffer.java GestorContactos.java


Ejecutar el programa:

java GestorContactos


Seguir las opciones del menú:

1 → Operar con File 

2 → Operar con Buffer

3 → Eliminar archivo de contactos

4 → Salir
