package grafos_salon;

import java.util.ArrayList;

// Guarista Tanori Jorge Luis
// Actualizado con arcosDFS, arcosBFS y despliegaArcos

class GrafoArrays {
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;
    
    // Nuevos atributos solicitados
    private ArrayList<String> arcosDFS;
    private ArrayList<String> arcosBFS;
    
    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        arcosDFS = null;
        arcosBFS = null;
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
    
        String formatoColumna = "%-12s";
        String formatoNumero  = "%-12d";
        
        System.out.printf(formatoColumna, ""); 
        for (int i = 0; i < numVertices; i++) {
            System.out.printf(formatoColumna, vertices[i]);
        }
        System.out.println();
        
        for (int i = 0; i < numVertices; i++) {
            System.out.printf(formatoColumna, vertices[i]);
            for (int j = 0; j < numVertices; j++) {
                System.out.printf(formatoNumero, matrizAdyacencia[i][j]);
            }
            System.out.println();
        }
    }

    public void DFS(String verticeInicial, String verticeFinal) {
        boolean[] visitado = new boolean[numVertices];
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);
        
        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vertices no existen en el grafo.");
            return;
        }
        
        arcosDFS = new ArrayList<>();
        System.out.println("\nRecorrido DFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        stack.push(indiceInicial);
        
        int indicePadre = -1;
        // Para registrar arcos de forma simplificada, podemos almacenar un arreglo auxiliar de procedencia o registrar transiciones
        int[] padre = new int[numVertices];
        for(int i=0; i<numVertices; i++) padre[i] = -1;

        while (!stack.isEmpty()) {
            int indiceActual = stack.pop();
            
            if (!visitado[indiceActual]) {
                visitado[indiceActual] = true;
                System.out.print(vertices[indiceActual] + " ");
                
                if (indiceActual != indiceInicial) {
                    arcosDFS.add(vertices[padre[indiceActual]] + " -- " + vertices[indiceActual]);
                }
                
                if (indiceActual == indiceFinal) {
                    System.out.println("\nSe ha alcanzado el vertice final.");
                    break;
                }
                
                for (int i = 0; i < numVertices; i++) {
                    if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]) {
                        padre[i] = indiceActual;
                        stack.push(i);
                    }
                }
            }
        }
    }

    public void BFS(String verticeInicial, String verticeFinal) {
        boolean[] visitado = new boolean[numVertices];
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);
        
        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vertices no existen en el grafo.");
            return;
        }
        
        arcosBFS = new ArrayList<>();
        System.out.println("\nRecorrido BFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        queue.add(indiceInicial);
        
        int[] padre = new int[numVertices];
        for(int i=0; i<numVertices; i++) padre[i] = -1;
        visitado[indiceInicial] = true;

        while (!queue.isEmpty()) {
            int indiceActual = queue.poll();
            System.out.print(vertices[indiceActual] + " ");
            
            if (indiceActual != indiceInicial) {
                arcosBFS.add(vertices[padre[indiceActual]] + " -- " + vertices[indiceActual]);
            }
            
            if (indiceActual == indiceFinal) {
                System.out.println("\nSe ha alcanzado el vertice final.");
                break;
            }
            
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]) {
                    visitado[i] = true;
                    padre[i] = indiceActual;
                    queue.add(i);
                }
            }
        }
    }

    // Método despliegaArcos solicitado en las instrucciones
    public void despliegaArcos() {
        System.out.println("ARCOS");
        
        // 1. Desplegar arcos de DFS y BFS
        System.out.println("Arcos recorridos en DFS:");
        if (arcosDFS != null) {
            for (String arco : arcosDFS) {
                System.out.println("  - " + arco);
            }
        } else {
            System.out.println("  (DFS no se ha ejecutado aún)");
        }
        
        System.out.println("\nArcos recorridos en BFS:");
        if (arcosBFS != null) {
            for (String arco : arcosBFS) {
                System.out.println("  - " + arco);
            }
        } else {
            System.out.println("  (BFS no se ha ejecutado aún)");
        }
        
        // 2. Número total de vértices, arcos totales posibles y arcos ocupados
        int totalVertices = numVertices;
        int arcosTotalesTeoricos = (numVertices * (numVertices - 1)) / 2; // Grafo no dirigido simple
        int arcosOcupados = 0;
        
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] != 0) {
                    arcosOcupados++;
                }
            }
        }
        
        System.out.println("\nEstadisticas del Grafo");
        System.out.println("Numero total de vertices: " + totalVertices);
        System.out.println("Numero total de arcos posibles: " + arcosTotalesTeoricos);
        System.out.println("Numero total de arcos ocupados (en matriz): " + arcosOcupados);
        
        // 3. Chequear si no son nulos y comparar cuál tiene menos elementos
        if (arcosDFS != null && arcosBFS != null) {
            System.out.println("\nComparacion de Recorridos");
            System.out.println("Cantidad de arcos en DFS: " + arcosDFS.size());
            System.out.println("Cantidad de arcos en BFS: " + arcosBFS.size());
            
            if (arcosDFS.size() < arcosBFS.size()) {
                System.out.println("👉 DFS utilizo menos elementos (arcos) para alcanzar el objetivo.");
            } else if (arcosBFS.size() < arcosDFS.size()) {
                System.out.println("👉 BFS utilizo menos elementos (arcos) para alcanzar el objetivo.");
            } else {
                System.out.println("👉 Ambos recorridos utilizaron la misma cantidad de elementos (arcos).");
            }
        } else {
            System.out.println("\n(Nota: Ejecuta tanto DFS como BFS antes de realizar la comparacion)");
        }
        System.out.println("========================================");
    }
}

public class Grafos {
    public static void main(String[] args) {
        String text = cargaArchivo("Grafo_salon2.txt");
        System.out.println("Contenido del archivo:\n");
        System.out.println(text); 
        
        GrafoArrays grafo = new GrafoArrays(text);
        grafo.populateMatrizAdyacencia(text);
        grafo.display();
        
        // Ejecución de algoritmos de búsqueda solicitados
        grafo.DFS("Arcangel", "Lucia");
        grafo.BFS("Arcangel", "Lucia");
        
        // Llamada al método despliegaArcos con los requerimientos de la tarea
        grafo.despliegaArcos();
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