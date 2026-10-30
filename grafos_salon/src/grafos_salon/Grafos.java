package grafos_salon;
class GrafoArrays{
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;
    
    public GrafoArrays(String origen){
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
    }
    
    private void creaTablaVertices(String origen){
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length*2];
        numVertices = 0;
        for(String linea : lineas){
            String arista = linea.trim();
            if(arista.startsWith("{")){
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            if(etiqueta >= 0){
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if(extremos.length == 2){
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }
    
    private void agregarVertice(String vertice){
        for(int i=0; i < numVertices; i++){
            if(vertices[i].equals(vertice)){
                return;
            }
        }
        vertices[numVertices] = vertice;
        numVertices++;
    }
    
    public void display(){
        System.out.println("Vertices del grafo:");
        displayVertices();
    }
    
    private void displayVertices(){
        for(int i = 0; i < numVertices; i++){
            // CORREGIDO: Cambiado de vertices[1] a vertices[i]
            System.out.println(vertices[i]);
        }
    }
    
    private int obtenerIndiceVertice(String vertice){
        for(int i = 0; i < numVertices; i++){
            // CORREGIDO: Cambiado de vertices[1] a vertices[i]
            if(vertices[i].equals(vertice)){
                return i;
            }
        }
        return -1;
    }
    
    private void agregarArista(String vertice1, String vertice2, int peso){
        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);
        if(indice1 >= 0 && indice2 >= 0){
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso;
        }
    }
}
public class Grafos {

    public static void main(String[] args) {
        String text = cargaArchivo("Grafo_salon.txt");
        System.out.println("Contenido del archivo:\n");
        System.out.println(text); // Impresión del contenido cargado
        GrafoArrays grafo = new GrafoArrays(text);
        grafo.display();
    }

    // Se agrega 'static' aquí para poder llamarlo desde el main
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

    

