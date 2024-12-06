package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<NodoHeap<T>> heap;
    private Comparador<T> comparador;
    private Heap<T> otroHeap; // atributo para mantener heaps conectados

    public Heap(Comparador<T> comparador) {
        this.heap = new ArrayList<>();
        this.comparador = comparador;
        this.otroHeap = null;
    }

    public int cardinal() {
        return heap.size();
    }

    public void conectarHeap(Heap<T> conn) {
        otroHeap = conn;
    }

    // las siguientes cuatro funciones son privadas ya que no las vamos a utilizar
    // fuera del funcionamiento interno del heap
    // todas tienen una complejidad O(1)
    private int obtenerPadre(int indice) {
        return (indice - 1) / 2;
    }

    private int obtenerHijoIzq(int indice) {
        return 2 * indice + 1;
    }

    private int obtenerHijoDerecho(int indice) {
        return 2 * indice + 2;
    }

    // Clase auxiliar para almacenar el valor y el índice en el otro heap
    private static class NodoHeap<T> {
        T valor;
        int indiceEnConectado;

        NodoHeap(T valor) {
            this.valor = valor;
            this.indiceEnConectado = 0; 
        }
    }

    private void cambiar(int i, int j) {
        NodoHeap<T> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);

        // Si el heap está conectado actualizamos el indice del otroheap
        if (otroHeap != null) {
            otroHeap.heap.get(heap.get(i).indiceEnConectado).indiceEnConectado = i;
            otroHeap.heap.get(heap.get(j).indiceEnConectado).indiceEnConectado = j;
        }
    }

    /*
     * sobre la complejidad de insertar:
     * insertar tiene una complejidad O(nlog(n)) (O(log(n)) en caso de que se
     * inserte un unico elemento)
     * 
     * El arbol tiene un largo O(log(n))
     * por lo tanto si en el peor caso se recorre siempre el arbol hasta el final n
     * veces, tenemos O(nlog(n))
     * esto se realiza en el siftUp
     * 
     */
    public void insertar(T[] traslados) {
        for (T traslado : traslados) {
            NodoHeap<T> nodo = new NodoHeap<>(traslado);

            heap.add(nodo);
            siftUp(heap.size() - 1);
            // insertamos en el otro heap (si existe otro relacionado)
            if (otroHeap != null) {
                NodoHeap<T> nodoConectado = new NodoHeap<>(traslado);
                otroHeap.heap.add(nodoConectado);
                int indiceEnConectado = otroHeap.heap.size() - 1;
                nodo.indiceEnConectado = indiceEnConectado;
                nodoConectado.indiceEnConectado = heap.size() - 1;
                otroHeap.siftUp(indiceEnConectado);
            }

        }

    }

    /*
     * la complejidad de eliminar primero nos queda en O(Log(n))
     * pues en principio tenemos unicamente OE hasta el siftDown que toma
     * complejidad O(log(n)), que igual que antes toma esa complejidad si tiene que
     * recorrer todo el arbol
     */

    public T eliminarPrimero() {

        if (heap.isEmpty()) return null;

        NodoHeap<T> nodo = heap.get(0);
        T valor = nodo.valor;
        NodoHeap<T> ultimo = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, ultimo);
            siftDown(0);
        }

        // Eliminar del otro heap
        if (otroHeap != null) {
            int indiceEnConectado = nodo.indiceEnConectado;
            NodoHeap<T> nodoConectado = otroHeap.heap.get(indiceEnConectado);
            NodoHeap<T> ultimoConectado = otroHeap.heap.remove(otroHeap.heap.size() - 1);

            if (indiceEnConectado < otroHeap.heap.size()) {
                otroHeap.heap.set(indiceEnConectado, ultimoConectado);
                otroHeap.siftDown(indiceEnConectado);
                otroHeap.siftUp(indiceEnConectado);
            }
        }

        return valor;

    }

    private void siftUp(int indice) {

        while (indice > 0) {
            int padreIndice = obtenerPadre(indice);
            if (comparador.comparar(heap.get(indice).valor, heap.get(padreIndice).valor) > 0) {
                cambiar(indice, padreIndice);
                indice = padreIndice;
            } else {
                break;
            }
        }

    }

    private void siftDown(int indice) {
        int tamaño = this.cardinal();
        while (true) {
            int hijoIzq = obtenerHijoIzq(indice);
            int hijoDer = obtenerHijoDerecho(indice);
            int mayor = indice;

            if (hijoIzq < tamaño && comparador.comparar(heap.get(hijoIzq).valor, heap.get(mayor).valor) > 0) {
                mayor = hijoIzq;
            }

            if (hijoDer < tamaño && comparador.comparar(heap.get(hijoDer).valor, heap.get(mayor).valor) > 0) {
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
        return heap.get(0).valor;
    }

    public void eliminarTodo() {
        heap.clear();
        otroHeap.eliminarTodo();
    }

    // devuelve el heap como ArrayList, segun el enunciado esto lo podemos tomar
    // como O(n) debido a que utiliza un bucle for con n
    public ArrayList<T> obtenerComoArrayList() {
        ArrayList<T> lista = new ArrayList<>();
        for (NodoHeap<T> nodo : heap) {
            lista.add(nodo.valor);
        }
        return lista;
    }

    // vamos a usar esto para reescribir los traslados entre heaps, como tiene una
    // complejidad O(n) que es menor a (O(n(log(T) + log(C)))), no nos va a afeactar
    // en la complejidad
    public void heapify(ArrayList<T> arrayAheap) {
        this.heap.clear();
        for (T valor : arrayAheap) {
            NodoHeap<T> nodo = new NodoHeap<>(valor);
            this.heap.add(nodo);
        }

        int ultimoNodo = (heap.size() - 2) / 2;
        for (int i = ultimoNodo; i >= 0; i--) {
            siftDown(i);
        }
    }

}
