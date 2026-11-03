package grafos_salon;

// Guarista Tanori Jorge Luis
// 30/10/2026

class GrafoArrays {
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;
    
    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
    }
    
    private void creaTablaVertices(String origen) {
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length * 2];
        numVertices = 0;
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }
    
    private void agregarVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return;
            }
        }
        vertices[numVertices] = vertice;
        numVertices++;
    }
    
    public void display() {
        System.out.println("Vertices del grafo:");
        displayVertices();
        displayMatriz();
    }
    
    private void displayVertices() {
        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i]);
        }
    }
    
    private int obtenerIndiceVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return i;
            }
        }
        return -1;
    }
    
    private void agregarArista(String vertice1, String vertice2, int peso) {
        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);
        if (indice1 >= 0 && indice2 >= 0) {
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso;
        }
    }

    public void populateMatrizAdyacencia(String origen) {
        String[] lineas = origen.split("\\R");
        
        for (String linea : lineas) {
            String arista = linea.trim();
            
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            
            int peso = 1; 
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                String textoEtiqueta = arista.substring(etiqueta);
                String soloNumeros = textoEtiqueta.replaceAll("[^0-9]", "");
                if (!soloNumeros.isEmpty()) {
                    peso = Integer.parseInt(soloNumeros);
                }
                arista = arista.substring(0, etiqueta);
            }
            
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                String v1 = extremos[0].trim();
                String v2 = extremos[1].trim();
                agregarArista(v1, v2, peso);
            }
        }
    }

    public void displayMatriz() {
        System.out.println("\nMatriz de Adyacencia:");
    
    // Definimos un ancho fijo para cada columna (ej. 12 caracteres)
    String formatoColumna = "%-12s";
    String formatoNumero  = "%-12d";
    
    // Imprimir cabecera de columnas
    System.out.printf(formatoColumna, ""); // Espacio en blanco de la esquina superior izquierda
    for (int i = 0; i < numVertices; i++) {
        System.out.printf(formatoColumna, vertices[i]);
    }
    System.out.println();
    
    // Imprimir las filas de la matriz
    for (int i = 0; i < numVertices; i++) {
        // Nombre del vértice al inicio de la fila
        System.out.printf(formatoColumna, vertices[i]);
        for (int j = 0; j < numVertices; j++) {
            System.out.printf(formatoNumero, matrizAdyacencia[i][j]);
        }
        System.out.println();
    }
}
}
public class Grafos {

    public static void main(String[] args) {
        String text = cargaArchivo("Grafo_salon.txt");
        System.out.println("Contenido del archivo:\n");
        System.out.println(text); 
        
        // 1. Crear el grafo (aquí lee los vértices y dimensiona la matriz)
        GrafoArrays grafo = new GrafoArrays(text);
        
        // 2. Mostrar la lista de vértices detectados
        grafo.display();
        
        // CORREGIDO: 3. Poblar la matriz procesando de nuevo el texto para meter los pesos
        grafo.populateMatrizAdyacencia(text);
        

    }

    public static String cargaArchivo(String ruta) {
        String contenido = "";
        try {
            java.io.File archivo = new java.io.File(ruta);
            java.util.Scanner scanner = new java.util.Scanner(archivo);
            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + ruta);
        }
        return contenido;
    }
}
