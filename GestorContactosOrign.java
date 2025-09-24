import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestorContactosOrign   {
    private static final String NOMBRE_ARCHIVO = "contactos.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    agregarContacto();
                    break;
                case 2:
                    mostrarContactos();
                    break;
                case 3:
                    buscarContacto();
                    break;
                case 4:
                    eliminarArchivo();
                    break;
                case 5:
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTOR DE CONTACTOS ===");
        System.out.println("1. Agregar contacto");
        System.out.println("2. Mostrar todos los contactos");
        System.out.println("3. Buscar contacto por nombre");
        System.out.println("4. Eliminar archivo de contactos");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // COMPLETAR ESTOS MÉTODOS

    private static void agregarContacto() {
        String nombreContacto;
        System.out.println("\n--- AGREGAR CONTACTO ---");
        System.out.print("Introducir nombre del contacto:");
        nombreContacto = scanner.nextLine();
        System.out.println("\n"+nombreContacto);
        // TODO: Pedir nombre y teléfono al usuario
        // TODO: Abrir archivo en modo APPEND (añadir)
        // TODO: Escribir el contacto en el formato: nombre,telefono
        // TODO: Cerrar el archivo y mostrar mensaje de éxito
    }

    private static void mostrarContactos() {
        System.out.println("\n--- LISTA DE CONTACTOS ---");


        
        // TODO: Verificar si el archivo existe
        // TODO: Leer el archivo carácter por carácter
        // TODO: Mostrar todos los contactos con formato
    }

    private static void buscarContacto() {
        System.out.println("\n--- BUSCAR CONTACTO ---");
        System.out.print("Ingrese el nombre a buscar: ");
        String nombreBuscado = scanner.nextLine();
        
        // TODO: Leer el archivo y buscar contactos que coincidan
        // TODO: Mostrar solo los contactos que coincidan
    }

    private static void eliminarArchivo() {
        // TODO: Crear objeto File y eliminar el archivo
        // TODO: Verificar si se eliminó correctamente
    }
}

