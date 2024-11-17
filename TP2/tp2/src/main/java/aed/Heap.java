package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<T> heap;
    private Comparador<T> comparador;

    public Heap(Comparador<T> comparador) {
        this.heap = new ArrayList<>();
        this.comparador = comparador;
    }

    public int cardinal() { // esto es mas que nada para el test
        return heap.size();
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
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insertar(T[] traslados) {
        for (T traslado : traslados) {
            heap.add(traslado);
            siftUp(heap.size() - 1);
        }
    }

    public T eliminarPrimero() {

        System.out.println("Heap antes eliminacion: " + obtenerComoArrayList());

        T max = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        siftDown(0);
        System.out.println("Heap despues eliminacion: " + obtenerComoArrayList());
        return max;

    }

    private void siftUp(int indice) {

        while (indice > 0) {
            int padreIndice = obtenerPadre(indice);
            if (comparador.comparar(heap.get(indice), heap.get(padreIndice)) > 0) {
                cambiar(indice, padreIndice);
                indice = padreIndice;
            } else {
                break;
            }
        }

    }

    void siftDown(int indice) {
        int tamaño = this.cardinal();
        while (true) {
            int hijoIzq = obtenerHijoIzq(indice);
            int hijoDer = obtenerHijoDerecho(indice);
            int mayor = indice;

            if (hijoIzq < tamaño && comparador.comparar(heap.get(hijoIzq), heap.get(mayor)) > 0) {
                mayor = hijoIzq;
            }

            if (hijoDer < tamaño && comparador.comparar(heap.get(hijoDer), heap.get(mayor)) > 0) {
                mayor = hijoDer;
            }

            if (mayor == indice) {
                break;
            }
            cambiar(indice, mayor);
            indice = mayor;
        }
    }

    public T obtenerMaximo() {
        return heap.get(0);
    }

    public void eliminarTodo() {
        heap.clear();
    }

    // devuelve el heap como ArrayList, segun el enunciado esto lo podemos tomar
    // como O(1)
    public ArrayList<T> obtenerComoArrayList() {
        return new ArrayList<>(heap);
    }

    // vamos a usar esto para reescribir los traslados entre heaps, como tiene una
    // complejidad O(n) no va a afectar lo que nos piden (O(n(log(T) + log(C))))
    public void heapify(ArrayList<T> arrayAheap) {
        
        this.heap = new ArrayList<>(arrayAheap);

        int ultimoNodo = (heap.size() - 2) / 2;
        for (int i = ultimoNodo; i >= 0; i--) {
            siftDown(i);
        }
    }

}
