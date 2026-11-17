
package grafos_salon;

import java.util.ArrayList;

public class GrafoListas {
    ArrayList<Nodos> nodos;
    public GrafoListas(String texto) {
        creaListaVertices(texto);
       agregaAristas(texto);
    }
    private void creaListaVertices(String texto) {
        String[] lineas = texto.split("\\R");
        nodos = new ArrayList<>();
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
    private void agregarVertice(String nombre) {
        for (Nodos nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return; // El vértice ya existe
            }
        }
        nodos.add(new Nodos(nombre));
    }
    public void display() {
        for (Nodos nodo : nodos) {
            nodo.display();
        }
    }
   private void agregaAristas(String texto) {
        String[] lineas = texto.split("\\R");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            int peso = 0;
            if (etiqueta >= 0) {
                String pesoStr = arista.substring(etiqueta + 7, arista.indexOf(']', etiqueta));
                pesoStr = pesoStr.replace("\"", "").trim();
                try {
                    peso = Integer.parseInt(pesoStr.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el peso a entero: " + pesoStr);
                }
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                Nodos nodo1 = obtenerNodoPorNombre(extremos[0].trim());
                Nodos nodo2 = obtenerNodoPorNombre(extremos[1].trim());
                if (nodo1 != null && nodo2 != null) {
                    nodo1.agregarAdyacente(nodo2, peso);
                    nodo2.agregarAdyacente(nodo1, peso); // Para grafos no dirigidos
                }
            }
        }
    }
    private void agregarAdyacente(Nodos nodoOrigen, Nodos nodoDestino, int peso) {
        for (Nodos n : nodos) {
            if (n.getNombre().equals(nodoOrigen.getNombre())) {
                n.agregarAdyacente(nodoDestino, peso);
                return;
            }
        }
    }

    private Nodos obtenerNodoPorNombre(String nombre) {
        for (Nodos nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null; // Retorna null si no se encuentra el nodo
    } 
    public void DFS(String verticeInicial, String VerticeFinal){
        int numVertices = nodos.size();
        boolean[] visitado = new boolean[numVertices];
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(VerticeFinal);

        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Stack<Nodos> stack = new java.util.Stack<>();
        stack.push(nodoInicial);
        while (!stack.isEmpty()) {
            Nodos nodoActual = stack.pop();
            if (!nodoActual.isVisitado()) {
                nodoActual.setVisitado(true);
                System.out.print(nodoActual.getNombre() + " ");
                if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    return; 
                }
            }
            // Agregar vecinos no visitados al stack
            for (Nodos vecino : nodoActual.getAdyacentes()) {
                if (!vecino.isVisitado()) {
                    stack.push(vecino);
                }
            }
        }
    }
    public void BFS(String verticeInicial, String VerticeFinal) {
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(VerticeFinal);
        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Queue<Nodos> queue = new java.util.LinkedList<>();      
        queue.add(nodoInicial);
        nodoInicial.setVisitado(true);

        while (!queue.isEmpty()) {
            Nodos nodoActual = queue.poll();
            System.out.print(nodoActual.getNombre() + " ");
            if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return; 
            }
            for (Nodos vecino : nodoActual.getAdyacentes()) {
                if (!vecino.isVisitado()) {
                    vecino.setVisitado(true); 
                    queue.add(vecino);
                }
            }
        }
        System.out.println("\nNo se encontró un camino hacia el vértice final.");
    }
    public String caminoMasCortoBFS(String verticeInicial, String verticeFinal) {
    Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
    Nodos nodoFinal = obtenerNodoPorNombre(verticeFinal);

    if (nodoInicial == null || nodoFinal == null) {
        return "Uno o ambos vértices no existen en el grafo.";
    }

    java.util.Map<Nodos, Integer> distancias = new java.util.HashMap<>();
    java.util.Map<Nodos, Nodos> anteriores = new java.util.HashMap<>();
    
    java.util.PriorityQueue<NodoDistancia> colaPrioridad = new java.util.PriorityQueue<>(
        java.util.Comparator.comparingInt(nd -> nd.distancia)
    );

    // Inicializar distancias para todos los nodos conocidos
    for (Nodos n : nodos) {
        distancias.put(n, Integer.MAX_VALUE);
    }
    distancias.put(nodoInicial, 0);
    colaPrioridad.add(new NodoDistancia(nodoInicial, 0));

    while (!colaPrioridad.isEmpty()) {
        NodoDistancia actualObj = colaPrioridad.poll();
        Nodos nodoActual = actualObj.nodo;
        int distanciaActual = actualObj.distancia;
        Integer distanciaRegistrada = distancias.get(nodoActual);
        if (distanciaRegistrada == null || distanciaActual > distanciaRegistrada) {
            continue;
        }

        if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
            break;
        }

        for (Nodos vecino : nodoActual.getAdyacentes()) {
            if (!distancias.containsKey(vecino)) {
                distancias.put(vecino, Integer.MAX_VALUE);
            }

            int pesoArista = nodoActual.getPesoAdyacente(vecino);
            int nuevaDistancia = distanciaActual + pesoArista;

            if (nuevaDistancia < distancias.get(vecino)) {
                distancias.put(vecino, nuevaDistancia);
                anteriores.put(vecino, nodoActual);
                colaPrioridad.add(new NodoDistancia(vecino, nuevaDistancia));
            }
        }
    }
    Integer distanciaFinal = distancias.get(nodoFinal);
    if (distanciaFinal == null || distanciaFinal == Integer.MAX_VALUE) {
        return "No existe un camino entre " + verticeInicial + " y " + verticeFinal + ".";
    }

    java.util.ArrayList<String> camino = new java.util.ArrayList<>();
    Nodos actual = nodoFinal;
    while (actual != null) {
        camino.add(0, actual.getNombre());
        actual = anteriores.get(actual);
    }

    return "El camino más corto de " + verticeInicial + " a " + verticeFinal + " es: " + String.join(" -> ", camino);
}
    private static class NodoDistancia {
    Nodos nodo;
    int distancia;

    public NodoDistancia(Nodos nodo, int distancia) {
        this.nodo = nodo;
        this.distancia = distancia;
    }
}
}
    