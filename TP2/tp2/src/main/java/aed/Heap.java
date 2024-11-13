package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<Traslado> heap;
    private Comparador<Integer> comparador;

    public Heap(Comparador<Integer> comparador) {
        this.heap = new ArrayList<>();
        this.comparador = comparador;
    }
    
    // las siguientes tres funciones tienen sentido unicamente si el indice esta
    // entre 0<=indice<|heap|

    public int obtenerPadre(int indice) {
        return (indice - 1) / 2;
    }

    public int obtenerHijoIzq(int indice) {
        return 2 * indice + 1;
    }

    public int obtenerHijoDerecho(int indice) {
        return 2 * indice + 2;
    }

    private void cambiar(int i, int j) {
        Traslado temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insertar(Traslado value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }


    private void siftUp(int indice) {

        while (indice > 0) { 
            int padreIndice = obtenerPadre(indice);
            if (comparador.comparar(heap.get(indice), heap.get(padreIndice)) > 0) {
                cambiar(indice, padreIndice);
                indice = padreIndice;
            }
            else {
                break;
            }
        }
        
    }

    void siftDown(int i){

    }

    public void heapify(ArrayList<Traslado> elements) {
        heap = new ArrayList<>(elements);
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public Traslado obtenerMaximo() {
        return heap.get(0);
    }
}
