import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MetodosFile {
    private static final String NOMBRE_ARCHIVO = "contactos.txt";

    public static boolean agregarContacto(String nombre, String telefono) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true)) {
            fw.write(nombre + "," + telefono + System.lineSeparator());
            return true;
        } catch (IOException e) {
            System.out.println("Error al agregar: " + e.getMessage());
            return false;
        }
    }

    public static void mostrarContactos() {
        File f = new File(NOMBRE_ARCHIVO);
        if (!f.exists()) {
            System.out.println("No hay contactos aún.");
            return;
        }
        System.out.println("\n--- LISTA DE CONTACTOS (File) ---");
        try (FileReader fr = new FileReader(f)) {
            StringBuilder linea = new StringBuilder();
            int c, n = 1;
            while ((c = fr.read()) != -1) {
                if (c == '\n' || c == '\r') {
                    imprimirLinea(n++, linea.toString());
                    linea.setLength(0);
                } else {
                    linea.append((char) c);
                }
            }
            if (linea.length() > 0) imprimirLinea(n, linea.toString());
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
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
        try (FileReader fr = new FileReader(f)) {
            StringBuilder linea = new StringBuilder();
            int c, idx = 1;
            System.out.println("\n--- COINCIDENCIAS (File) ---");
            while ((c = fr.read()) != -1) {
                if (c == '\n' || c == '\r') {
                    if (coincide(linea.toString(), termLower)) {
                        imprimirLinea(idx, linea.toString());
                        count++;
                    }
                    idx++;
                    linea.setLength(0);
                } else {
                    linea.append((char) c);
                }
            }
            if (linea.length() > 0 && coincide(linea.toString(), termLower)) {
                imprimirLinea(idx, linea.toString());
                count++;
            }
            if (count == 0) System.out.println("Sin resultados.");
        } catch (IOException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return count;
    }

    public static boolean reemplazarContactoPorIndice(int indice1Base, String nuevoNombre, String nuevoTel) {
        String contenido = leerTodo();
        if (contenido == null) return false;
        String[] lineas = contenido.split("\\R");
        int idx = indice1Base - 1;
        if (idx < 0 || idx >= lineas.length) return false;

        String[] partes = partir(lineas[idx]);
        String nombreOld = partes[0];
        String telOld = partes[1];

        String nombre = (nuevoNombre == null || nuevoNombre.trim().isEmpty()) ? nombreOld : nuevoNombre.trim();
        String tel    = (nuevoTel == null    || nuevoTel.trim().isEmpty())    ? telOld    : nuevoTel.trim();

        lineas[idx] = nombre + "," + tel;
        return escribirTodo(String.join(System.lineSeparator(), lineas));
    }

    public static boolean eliminarContactoPorIndice(int indice1Base) {
        String contenido = leerTodo();
        if (contenido == null) return false;
        String[] lineas = contenido.split("\\R");
        int idx = indice1Base - 1;
        if (idx < 0 || idx >= lineas.length) return false;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineas.length; i++) {
            if (i == idx) continue;
            sb.append(lineas[i]).append(System.lineSeparator());
        }
        return escribirTodo(sb.toString().trim());
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

    private static String leerTodo() {
        File f = new File(NOMBRE_ARCHIVO);
        if (!f.exists()) return "";
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(f)) {
            int c;
            while ((c = fr.read()) != -1) sb.append((char) c);
            return sb.toString();
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return null;
        }
    }

    private static boolean escribirTodo(String contenido) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false)) {
            fw.write(contenido);
            if (!contenido.endsWith(System.lineSeparator()) && !contenido.isEmpty()) {
                fw.write(System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
            return false;
        }
    }
}
