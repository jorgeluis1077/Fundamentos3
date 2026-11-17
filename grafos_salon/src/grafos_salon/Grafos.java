package grafos_salon;
public class Grafos {
    public static void main(String[] args) {
        String texto = cargaArchivo("grafo_salon.txt");
        System.out.println("Contenido del archivo:\n" + texto);
        GrafoArray grafo = new GrafoArray(texto);
        grafo.display();
        grafo.DFS("Arcangel", "Sergio");
        grafo.BFS("Sergio", "Arcangel");
        String caminoDijkstra = grafo.caminoMasCorto("Arcangel", "Sergio");
        System.out.println(caminoDijkstra);
        System.out.println("GrafoListas");
        //Aqui empieza lo peedido de compeltar GrafoListas
        System.out.println("Utilizacio de GrafoListas");
        //Pruebas con lista:
        GrafoListas grafoListas = new GrafoListas(texto);
        grafoListas.display();
        grafoListas.DFS("Arcangel", "Sergio");
        grafoListas.display();
        System.out.println();
        reiniciarVisitadosListas(grafoListas);
        // Lo pedido probar BFS
        grafoListas.BFS("Arcangel", "Sergio");
        reiniciarVisitadosListas(grafoListas);
        //Luego el camino mas corto
        String caminoDijkstraListas = grafoListas.caminoMasCortoBFS("Arcangel", "Sergio");
        System.out.println(caminoDijkstraListas);
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
    //Este se agrego para que quede limpio despues de hacer dfs para que bfs quede limpio y no con datos sueltos
    public static void reiniciarVisitadosListas(GrafoListas grafoListas) {
        for (Nodos n : grafoListas.nodos) {
            n.setVisitado(false);
        }
    }
}