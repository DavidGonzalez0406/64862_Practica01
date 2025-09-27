import java.io.*;

public class MetodosBuffer {
    private static final String NOMBRE_ARCHIVO = "contactos.txt";

    public static boolean agregarContacto(String nombre, String telefono) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            bw.write(nombre + "," + telefono);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error al agregar (Buffer): " + e.getMessage());
            return false;
        }
    }

    public static void mostrarContactos() {
        File f = new File(NOMBRE_ARCHIVO);
        if (!f.exists()) {
            System.out.println("No hay contactos aún.");
            return;
        }
        System.out.println("\n--- LISTA DE CONTACTOS (Buffer) ---");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            int n = 1;
            while ((linea = br.readLine()) != null) {
                imprimirLinea(n++, linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer (Buffer): " + e.getMessage());
        }
    }

    public static void buscarContacto(String termino) {
        listarCoincidencias(termino);
    }

    public static int listarCoincidencias(String termino) {
        File f = new File(NOMBRE_ARCHIVO);
        if (!f.exists()) {
            System.out.println("No hay contactos aún.");
            return 0;
        }
        String termLower = termino.toLowerCase();
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            int idx = 1;
            System.out.println("\n--- COINCIDENCIAS (Buffer) ---");
            while ((linea = br.readLine()) != null) {
                if (coincide(linea, termLower)) {
                    imprimirLinea(idx, linea);
                    count++;
                }
                idx++;
            }
            if (count == 0) System.out.println("Sin resultados.");
        } catch (IOException e) {
            System.out.println("Error al buscar (Buffer): " + e.getMessage());
        }
        return count;
    }

    public static boolean reemplazarContactoPorIndice(int indice1Base, String nuevoNombre, String nuevoTel) {
        File f = new File(NOMBRE_ARCHIVO);
        if (!f.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder();
            String linea;
            int idx = 1;
            while ((linea = br.readLine()) != null) {
                if (idx == indice1Base) {
                    String[] p = partir(linea);
                    String nombre = (nuevoNombre == null || nuevoNombre.trim().isEmpty()) ? p[0] : nuevoNombre.trim();
                    String tel    = (nuevoTel == null    || nuevoTel.trim().isEmpty())    ? p[1] : nuevoTel.trim();
                    sb.append(nombre).append(",").append(tel).append(System.lineSeparator());
                } else {
                    sb.append(linea).append(System.lineSeparator());
                }
                idx++;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {
                bw.write(sb.toString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al actualizar (Buffer): " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarContactoPorIndice(int indice1Base) {
        File f = new File(NOMBRE_ARCHIVO);
        if (!f.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder();
            String linea;
            int idx = 1;
            while ((linea = br.readLine()) != null) {
                if (idx != indice1Base) {
                    sb.append(linea).append(System.lineSeparator());
                }
                idx++;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {
                bw.write(sb.toString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al eliminar (Buffer): " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarArchivo() {
        File f = new File(NOMBRE_ARCHIVO);
        return f.exists() && f.delete();
    }

    // ---------- Utilidades ----------
    private static void imprimirLinea(int n, String linea) {
        if (linea == null || linea.trim().isEmpty()) return;
        String[] p = partir(linea);
        System.out.printf("%d) %s - %s%n", n, p[0], p[1]);
    }

    private static boolean coincide(String linea, String terminoLower) {
        if (linea == null || linea.trim().isEmpty()) return false;
        String[] p = partir(linea);
        return p[0].toLowerCase().contains(terminoLower);
    }

    private static String[] partir(String linea) {
        String[] p = linea.split(",", 2);
        String nombre = p.length > 0 ? p[0].trim() : "";
        String tel    = p.length > 1 ? p[1].trim() : "";
        return new String[]{nombre, tel};
    }
}
