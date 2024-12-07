package aed;

import java.util.ArrayList;

public class BestEffort {

    /*
     * 
     * COMPARADORES
     * 
     */
    Comparador<Traslado> compararPorGanancia = new Comparador<Traslado>() {
        @Override
        public int comparar(Traslado t1, Traslado t2) {
            return Integer.compare(t1.obtenerGananciaNeta(), t2.obtenerGananciaNeta());
        }
    };
    Comparador<Traslado> compararPorTiempo = new Comparador<Traslado>() {
        @Override
        public int comparar(Traslado t1, Traslado t2) {
            return -Integer.compare(t1.obtenerTiempo(), t2.obtenerTiempo());
        }
    };
    Comparador<Ciudad> compararPorSuperavit = new Comparador<Ciudad>() {
        @Override
        public int comparar(Ciudad c1, Ciudad c2) {
            if (Integer.compare(c1.devolverSuperavit(), c2.devolverSuperavit()) != 0) {
                return Integer.compare(c1.devolverSuperavit(), c2.devolverSuperavit());
            } else {
                return Integer.compare(c1.idCiudad(), c2.idCiudad());
            }
        }
    };

    /*
     * 
     * ATRIBUTOS
     * 
     */

    // atributos para traslados/ciudades
    private ArrayList<Ciudad> ciudades;
    public Heap<Traslado> trasladosRed = new Heap(compararPorGanancia);
    public Heap<Traslado> trasladosAnt = new Heap(compararPorTiempo);

    // atributos para control de estadisticas
    private Estadisticas estadisticas = new Estadisticas(compararPorSuperavit);


    // atributos para promedio
    public int gananciaTotal = 0;
    public int contadorParaPromedio = 0;

    /*
     * 
     * FUNCIONES ENUNCIADO
     * 
     */

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        /*
         * Sobre la complejidad:
         * como escencialmente estamos iterando sobre una cantidad fija de ciudades y
         * traslados, obtenemos una funcion lineal en ambos casos, tengamos en cuenta
         * que para los traslados estamos usando heapify lo que nos deja la complejidad
         * en O(n)
         * 
         */

        this.ciudades = new ArrayList<>();
        ArrayList<Traslado> trasladosParaHeapify = new ArrayList<>();
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad ciudad = new Ciudad(i, 0, 0);
            ciudades.add(ciudad);
        }
        for (int i = 0; i < traslados.length; i++) {

            trasladosParaHeapify.add(traslados[i]);
        }
        trasladosRed.heapify(trasladosParaHeapify);
        trasladosAnt.heapify(trasladosParaHeapify);

        //cuando se inicia el programa queremos coordinar los heaps redituables y anteriores
        trasladosRed.conectarHeap(trasladosAnt);
        trasladosAnt.conectarHeap(trasladosRed);

    }

    public void registrarTraslados(Traslado[] traslados) {
        /*
         * sobre la complejidad:
         * (mas info en la clase Heap)
         * cada insercion tiene un coste O(log(k))
         * como son n inserciones (tomando n como el largo de traslados) tenemos
         * O(nlog(n))
         */
        trasladosRed.insertar(traslados);
        //trasladosAnt.insertar(traslados);
    }

    public int[] despacharMasRedituables(int n) {

        /*
         * sobre la complejidad:
         * aca estamos usando la funcion insertar (nuevamente), como se comento antes
         * esta funcion toma una complejidad O(nlog(k)) siendo n la cantidad de
         * como la usamos tanto para la cantidad de ciudades como para los traslados
         * nos queda O(n(log(T)+log(C))
         * 
         * cabe aclarar que en el medio estamos haciendo operaciones con complejidades
         * menores que no afectan a lo anterior
         * 
         * TODO
         * hay que conectar los heaps para no tener que usar heapify
         */

        int limiteDespachos = Math.min(n, trasladosRed.cardinal());
        int[] devolver = new int[limiteDespachos];

        // agrego el limite al contador
        contadorParaPromedio += limiteDespachos;

        for (int i = 0; i < limiteDespachos; i++) {

            Traslado trasladoActual = trasladosRed.eliminarPrimero();
            devolver[i] = trasladoActual.id;

            ciudades.get(trasladoActual.origen).agregarGanancia(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // origen y le agrega la
            // ganancia

            ciudades.get(trasladoActual.destino).agregarPerdida(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // destino y le agrega
            // la perdida

            // chequeamos si la ciudad actual tiene la mayor ganancia
            estadisticas.actualEsMayorGanancia(ciudades.get(trasladoActual.origen), ciudades);
            // chequeamos tambien si tiene la mayor perdida
            estadisticas.actualEsMayorPerdida(ciudades.get(trasladoActual.destino), ciudades);

            // agrego al heap de superavit
            Ciudad[] ciudadesSuper = new Ciudad[2];
            ciudadesSuper[0] = ciudades.get(trasladoActual.origen);
            ciudadesSuper[1] = ciudades.get(trasladoActual.destino);
            //ciudadesPorSuper.insertar(ciudadesSuper);
            estadisticas.insertarCiudadesSuperavit(ciudadesSuper);
            // sumo la ganancia a la ganancia para promedio
            gananciaTotal += trasladoActual.gananciaNeta;

        }

        // hacemos que el otro heap tenga los mismos elementos
        //trasladosAnt.heapify(trasladosRed.obtenerComoArrayList());
        return devolver;
    }

    public int[] despacharMasAntiguos(int n) {

        /*
         * sobre la complejidad:
         * como este punto lo encaramos de la misma forma que el anterior, la
         * complejidad es la misma
         */

        int limiteDespachos = Math.min(n, trasladosAnt.cardinal());
        int[] devolver = new int[limiteDespachos];

        // agrego el limite al contador
        contadorParaPromedio += limiteDespachos;

        for (int i = 0; i < limiteDespachos; i++) {
            Traslado trasladoActual = trasladosAnt.eliminarPrimero();
            devolver[i] = trasladoActual.id; // los ordeno de forma creciente
            

            ciudades.get(trasladoActual.origen).agregarGanancia(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // origen y le agrega la
            // ganancia

            ciudades.get(trasladoActual.destino).agregarPerdida(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // destino y le agrega
            // la perdida

            // chequeamos si la ciudad actual tiene la mayor ganancia
            estadisticas.actualEsMayorGanancia(ciudades.get(trasladoActual.origen), ciudades);
            // chequeamos tambien si tiene la mayor perdida
            estadisticas.actualEsMayorPerdida(ciudades.get(trasladoActual.destino), ciudades);

            // agrego al heap de superavit
            Ciudad[] ciudadesSuper = new Ciudad[2];
            ciudadesSuper[0] = ciudades.get(trasladoActual.origen);
            ciudadesSuper[1] = ciudades.get(trasladoActual.destino);
            //ciudadesPorSuper.insertar(ciudadesSuper);
            estadisticas.insertarCiudadesSuperavit(ciudadesSuper);

            // sumo la ganancia a la ganancia para promedio
            gananciaTotal += trasladoActual.gananciaNeta;
        }

        // hacemos que el otro heap tenga los mismos elementos
        //trasladosRed.heapify(trasladosAnt.obtenerComoArrayList());

        return devolver;
    }

    // las siguientes funciones solo contienen una OE por lo que nos quedan en O(1)

    public int ciudadConMayorSuperavit() {

        return estadisticas.obtenerCiudadConMayorSuperavit();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return estadisticas.ciudadesConMayorGanancia();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {

        return estadisticas.ciudadesConMayorPerdida();
    }

    public int gananciaPromedioPorTraslado() {

        return gananciaTotal / contadorParaPromedio;
    }

}
