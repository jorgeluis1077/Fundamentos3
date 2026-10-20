
package arbolbinnombres;


    

class Nodo {

    String nombre;
    int expediente;
    Nodo izquierda;
    Nodo derecha;
    public Nodo(String nombre, int expediente){
        this.nombre = nombre;
        this.expediente = expediente;
        this.izquierda = null;
        this.derecha = null;
    }
}
class ArbolBinario {
    Nodo raiz;
    public ArbolBinario() {
        this.raiz = null;
    }
    public void insertar(String nombre, int expediente){
        raiz = insertarRecursivo(raiz, nombre, expediente);
    }
    private Nodo insertarRecursivo(Nodo nodo, String nombre, int expediente) {
        if (nodo == null) {
            return new Nodo(nombre, expediente);
        }
        if (nodo.nombre.compareTo(nombre) > 0){
            nodo.izquierda = insertarRecursivo(nodo.izquierda, nombre, expediente);
        } else if (nodo.nombre.compareTo(nombre) < 0){
            nodo.derecha = insertarRecursivo(nodo.derecha, nombre, expediente);
        }
        return nodo;
    }
    public void inOrden(){
        inOrdenRecursivo(raiz);
    }
    private void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null){
            inOrdenRecursivo(nodo.izquierda);
            System.out.println(nodo.nombre + " ");
            inOrdenRecursivo(nodo.derecha);
        }
    }
    public void inOrdenReversa(){
        inOrdenReversaRecursivo(raiz);
    }
    private void inOrdenReversaRecursivo(Nodo nodo) {
        if (nodo != null){
            inOrdenReversaRecursivo(nodo.derecha);
            System.out.println(nodo.nombre + " ");
            inOrdenReversaRecursivo(nodo.izquierda);
        }
    }
//    private void inOrdenBuscar(){
 //       inOrdenBuscar(raiz);
  //  }
    //private void inOrdenBuscarRecursivo(Nodo nodo){
//        if(nodo != null ){
  //          inOrdenBuscarRecursivo(nodo.nombre);
    //        System.out.println("Nombre econtrado" + this.nombre+" expediente:"+this.expediente);
//        }
//    }
}
public class ArbolBinNombres{
    public static void main(String [] args){
        ArbolBinario ArbolB = new ArbolBinario();
        ArbolB.insertar("Jorge Luis Guarista Tanori", 123);
        ArbolB.insertar("Diego", 1234);
        ArbolB.insertar("Sergio", 12132);
        ArbolB.insertar("Juan", 43543);
        ArbolB.insertar("Santiago",7654);
        ArbolB.insertar("Alex", 978978);
        ArbolB.insertar("Lupita", 456546);
        ArbolB.inOrden();
        System.out.println("Ahora en reversa:");
        ArbolB.inOrdenReversa();
    }
}
