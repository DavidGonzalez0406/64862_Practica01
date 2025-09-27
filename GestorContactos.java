import java.util.Scanner;


public class GestorContactos {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== GESTOR DE CONTACTOS ===");
            System.out.println("1) Operar con File");
            System.out.println("2) Operar con Buffer");
            System.out.println("3) Eliminar archivo de contactos");
            System.out.println("4) Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: submenuFile(); break;
                case 2: submenuBuffer(); break;
                case 3: {
                    boolean okF = MetodosFile.eliminarArchivo();
                    boolean okB = MetodosBuffer.eliminarArchivo();
                    System.out.println(okF || okB
                            ? "Archivo eliminado correctamente."
                            : "No se encontró el archivo para eliminar.");
                    break;
                }
                case 4: System.out.println("¡Hasta pronto!"); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private static void submenuFile() {
        int op;
        do {
            System.out.println("\n--- Operaciones con File ---");
            System.out.println("1) Agregar contacto");
            System.out.println("2) Mostrar contactos");
            System.out.println("3) Buscar contacto (coincidencia parcial)");
            System.out.println("4) Reemplazar datos de un contacto");
            System.out.println("5) Eliminar un contacto");
            System.out.println("6) Volver");
            System.out.print("Opción: ");
            op = leerEntero();

            switch (op) {
                case 1: agregar(true); break;
                case 2: MetodosFile.mostrarContactos(); break;
                case 3: buscar(true); break;
                case 4: reemplazar(true); break;
                case 5: eliminar(true); break;
                case 6: break;
                default: System.out.println("Opción no válida.");
            }
        } while (op != 6);
    }

    private static void submenuBuffer() {
        int op;
        do {
            System.out.println("\n--- Operaciones con Buffer ---");
            System.out.println("1) Agregar contacto");
            System.out.println("2) Mostrar contactos");
            System.out.println("3) Buscar contacto (coincidencia parcial)");
            System.out.println("4) Reemplazar datos de un contacto");
            System.out.println("5) Eliminar un contacto");
            System.out.println("6) Volver");
            System.out.print("Opción: ");
            op = leerEntero();

            switch (op) {
                case 1: agregar(false); break;
                case 2: MetodosBuffer.mostrarContactos(); break;
                case 3: buscar(false); break;
                case 4: reemplazar(false); break;
                case 5: eliminar(false); break;
                case 6: break;
                default: System.out.println("Opción no válida.");
            }
        } while (op != 6);
    }

    // ---------- Flujo común ----------
    private static void agregar(boolean usarFile) {
        System.out.println("\n--- AGREGAR CONTACTO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String tel = scanner.nextLine().trim();
        if (nombre.isEmpty() || tel.isEmpty()) {
            System.out.println("Nombre y teléfono son obligatorios.");
            return;
        }
        boolean ok = usarFile
                ? MetodosFile.agregarContacto(nombre, tel)
                : MetodosBuffer.agregarContacto(nombre, tel);
        System.out.println(ok ? "Contacto agregado." : "No se pudo agregar el contacto.");
    }

    private static void buscar(boolean usarFile) {
        System.out.println("\n--- BUSCAR CONTACTO ---");
        System.out.print("Ingrese nombre o parte del nombre: ");
        String termino = scanner.nextLine().trim();
        if (termino.isEmpty()) {
            System.out.println("Ingrese un término de búsqueda.");
            return;
        }
        if (usarFile) MetodosFile.buscarContacto(termino);
        else MetodosBuffer.buscarContacto(termino);
    }

    private static void reemplazar(boolean usarFile) {
        System.out.println("\n--- REEMPLAZAR DATOS ---");
        System.out.print("Nombre del contacto a editar (coincidencia parcial): ");
        String termino = scanner.nextLine().trim();
        if (termino.isEmpty()) {
            System.out.println("Ingrese un término de búsqueda.");
            return;
        }
        int count = usarFile
                ? MetodosFile.listarCoincidencias(termino)
                : MetodosBuffer.listarCoincidencias(termino);

        if (count <= 0) {
            System.out.println("Sin coincidencias.");
            return;
        }
        System.out.print("Elija el número del contacto a editar: ");
        int idx = leerEntero();

        System.out.print("Nuevo nombre (Enter para conservar): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nuevo teléfono (Enter para conservar): ");
        String nuevoTel = scanner.nextLine();

        boolean ok = usarFile
                ? MetodosFile.reemplazarContactoPorIndice(idx, nuevoNombre, nuevoTel)
                : MetodosBuffer.reemplazarContactoPorIndice(idx, nuevoNombre, nuevoTel);

        System.out.println(ok ? "Contacto actualizado." : "No se pudo actualizar.");
    }

    private static void eliminar(boolean usarFile) {
        System.out.println("\n--- ELIMINAR CONTACTO ---");
        System.out.print("Nombre o parte del nombre: ");
        String termino = scanner.nextLine().trim();

        int count = usarFile
                ? MetodosFile.listarCoincidencias(termino)
                : MetodosBuffer.listarCoincidencias(termino);

        if (count <= 0) {
            System.out.println("Sin coincidencias.");
            return;
        }
        System.out.print("Elija el número del contacto a eliminar: ");
        int idx = leerEntero();

        boolean ok = usarFile
                ? MetodosFile.eliminarContactoPorIndice(idx)
                : MetodosBuffer.eliminarContactoPorIndice(idx);

        System.out.println(ok ? "Contacto eliminado." : "No se pudo eliminar.");
    }

    private static int leerEntero() {
        while (true) {
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
