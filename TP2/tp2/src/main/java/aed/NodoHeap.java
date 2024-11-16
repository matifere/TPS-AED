package aed;

/*
 * la idea es poder tener un acceso a los indices entre los heaps cuando se elimina un elemento
 * 
 * esto nos ayuda a reducir la complejidad , nos queda en O(log(n)), de otra forma ,buscando el otro elemento a mano, tendriamos una complejidad O(n)
 */

public class NodoHeap<T> {
    T elemento;
    int indiceEnOtroHeap;

    public NodoHeap(T elemento, int indiceEnOtroHeap) {
        this.elemento = elemento;
        this.indiceEnOtroHeap = indiceEnOtroHeap;
    }
}
