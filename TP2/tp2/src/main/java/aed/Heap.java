package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<Traslado> heap;

    public Heap() {
        heap = new ArrayList<>();

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

    //A partir de aca las cosas 'DEBERIAN' andar, todo esto esta unicamente en modo teorico xq todavia no implemente el compareTo

    private void siftUp(int index) {

        while (index > 0 && heap.get(index).compareTo(heap.get(obtenerPadre(index))) > 0) { 
            cambiar(index, obtenerPadre(index));
            index = obtenerPadre(index);
        }
    }

    private void siftDown(int index) {
        int largest = index;
        int left = obtenerHijoIzq(index);
        int right = obtenerHijoDerecho(index);

        if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0) {
            largest = left;
        }

        if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
            largest = right;
        }
        if (largest != index) {
            cambiar(index, largest);
            siftDown(largest);
        }
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
