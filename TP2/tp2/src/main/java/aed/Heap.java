package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<T> heap;
    private Comparador<T> comparador;

    public Heap(Comparador<T> comparador) {
        this.heap = new ArrayList<>();
        this.comparador = comparador;
    }

    public int cardinal() {
        return heap.size();
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

    private void cambiar(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);

        if (heap.get(i) instanceof Traslado) {
            Traslado trasladoI = (Traslado) heap.get(i);
            Traslado trasladoJ = (Traslado) heap.get(j);

            if (comparador.toString().contains("Ganancia")) {
                trasladoI.RedCambiarIndice(i);
                trasladoJ.RedCambiarIndice(j);

            } else { // Si es el heap por tiempo
                trasladoI.AntCambiarIndice(i);
                trasladoJ.AntCambiarIndice(j);

            }
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
            heap.add(traslado);
            int indice = heap.size() - 1;
            if (traslado instanceof Traslado) {
                Traslado trasladoActual = (Traslado) traslado;
                if (comparador.toString().contains("Ganancia")) {
                    trasladoActual.RedCambiarIndice(indice);

                } else {
                    trasladoActual.AntCambiarIndice(indice);

                }
            }
            siftUp(indice);

        }
    }

    /*
     * la complejidad de eliminar primero nos queda en O(Log(n))
     * pues en principio tenemos unicamente OE hasta el siftDown que toma
     * complejidad O(log(n)), que igual que antes toma esa complejidad si tiene que
     * recorrer todo el arbol
     */

    public T eliminarPrimero() {
        if (heap.isEmpty()) {
            return null;
        }

        T max = heap.get(0);
        T ultimo = heap.get(heap.size() - 1);
        heap.set(0, ultimo);
        heap.remove(heap.size() - 1);

        siftDown(0);

        actualizarIndicesParciales(0);

        return max;
    }

    public void eliminarPorIndice(int indice) {
        T ultimo = heap.get(heap.size() - 1);
        heap.set(indice, ultimo);
        heap.remove(heap.size() - 1);

        if (indice < heap.size()) {
            if (comparador.comparar(heap.get(indice), heap.get(obtenerPadre(indice))) > 0) {
                siftUp(indice);
            } else {
                siftDown(indice);
            }

            actualizarIndicesParciales(indice);
        }
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

        if (heap.get(indice) instanceof Traslado) {
            Traslado traslado = (Traslado) heap.get(indice);
            if (comparador.toString().contains("Ganancia")) {
                traslado.RedCambiarIndice(indice);
            } else {
                traslado.AntCambiarIndice(indice);
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

    /*
     * sobre la siguiente funcion:
     * como sabemos que cuando eliminamos un elemento o lo insertamos esto NO afecta
     * a todos los elementos del heap, si no que solamente a la rama del arbol en la
     * que nos encontramos, podemos usar eso para obtener una funcion con
     * complejidad O(log(n)) que ordene los indices del heap.
     */

    private void actualizarIndicesParciales(int indice) {

        while (indice < heap.size()) {
            T elemento = heap.get(indice);
            if (elemento instanceof Traslado) {
                Traslado traslado = (Traslado) elemento;
                if (comparador.toString().contains("Ganancia")) {
                    traslado.RedCambiarIndice(indice);
                } else {
                    traslado.AntCambiarIndice(indice);
                }
            }

            int hijoIzq = obtenerHijoIzq(indice);
            int hijoDer = obtenerHijoDerecho(indice);

            if (hijoIzq < heap.size() && comparador.comparar(heap.get(hijoIzq), heap.get(indice)) > 0) {
                indice = hijoIzq;
            } else if (hijoDer < heap.size() && comparador.comparar(heap.get(hijoDer), heap.get(indice)) > 0) {
                indice = hijoDer;
            } else {
                break;
            }
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

    // la funion heapify contiene una complejidad O(n) por mas que se actualizen los
    // indices de los traslados, ya que la actualizacion contiene una complejidad de
    // O(log(n)) que es menor a O(n)
    public void heapify(ArrayList<T> arrayAheap) {
        this.heap = new ArrayList<>(arrayAheap);

        int ultimoNodo = (heap.size() - 2) / 2;

        
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i) instanceof Traslado) {
                Traslado traslado = (Traslado) heap.get(i);
                if (comparador.toString().contains("Ganancia")) {
                    traslado.RedCambiarIndice(i);
                } else {
                    traslado.AntCambiarIndice(i);
                }
            }
        }

        
        for (int i = ultimoNodo; i >= 0; i--) {
            siftDown(i);
        }
    }

    // funcion para debug

    @Override
    public String toString() {
        String finalString = new String();
        finalString = "[";
        if (heap.get(0) instanceof Traslado) {
            for (int indice = 0; indice < heap.size(); indice++) {
                Traslado traslado = (Traslado) heap.get(indice);
                if (comparador.toString().contains("Ganancia")) {
                    finalString += traslado.toString() + ", \n";
                } else {
                    finalString += traslado.toString() + ", \n";
                }
            }
            finalString += "]";
            return finalString;
        } else {
            return heap.toString();

        }

    }

}