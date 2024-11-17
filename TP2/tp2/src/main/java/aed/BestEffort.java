package aed;

import java.util.ArrayList;

public class BestEffort {

    /*
     * Ou yeaaaaaaa
     * 
     * NO COMMITEEES NADA QUE NO ESTE HECHO DEL TODO
     * tkm
     */

    // comparadores
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
    Comparador<Integer> compararPorMayor = new Comparador<Integer>() {
        @Override
        public int comparar(Integer c1, Integer c2) {
            return Integer.compare(c1, c2);
        }
    };
    Comparador<Integer> compararPorMenor = new Comparador<Integer>() {
        @Override
        public int comparar(Integer c1, Integer c2) {
            return -Integer.compare(c1, c2);
        }
    };

    // atributos para traslados/ciudades
    private ArrayList<Ciudad> ciudades;
    private Heap<Traslado> trasladosRed = new Heap(compararPorGanancia);
    private Heap<Traslado> trasladosAnt = new Heap(compararPorTiempo);

    // atributos para control de estadisticas
    private Heap<Ciudad> ciudadesPorSuper = new Heap(compararPorSuperavit);
    private Heap<Integer> ciudadMayorGanancia = new Heap(compararPorMayor);
    private Heap<Integer> ciudadMayorPerdida = new Heap(compararPorMenor);

    // atributos para promedio
    private int gananciaTotal = 0;
    private int contadorParaPromedio = 0;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        this.ciudades = new ArrayList<>();
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad ciudad = new Ciudad(i, 0, 0);
            ciudades.add(ciudad);
        }
        registrarTraslados(traslados);

    }

    public void registrarTraslados(Traslado[] traslados) {
        trasladosRed.insertar(traslados);
        trasladosAnt.insertar(traslados);
    }

    public int[] despacharMasRedituables(int n) {
        int limiteDespachos = Math.min(n, trasladosRed.cardinal());
        int[] devolver = new int[n];

        //agrego el limite al contador
        contadorParaPromedio += limiteDespachos;

        for (int i = 0; i < limiteDespachos; i++) {

            Traslado trasladoActual = trasladosRed.eliminarPrimero();
            devolver[i] = trasladoActual.id;

            System.out.println("Despachando pedido " + trasladoActual.id);

            ciudades.get(trasladoActual.origen).agregarGanancia(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // origen y le agrega la
            // ganancia

            ciudades.get(trasladoActual.destino).agregarPerdida(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // destino y le agrega
            // la perdida

            // chequeamos si la ciudad actual tiene la mayor ganancia
            actualEsMayorGanancia(ciudades.get(trasladoActual.origen));
            // chequeamos tambien si tiene la mayor perdida
            actualEsMayorPerdida(ciudades.get(trasladoActual.destino));

            // agrego al heap de superavit
            Ciudad[] ciudadesSuper = new Ciudad[2];
            ciudadesSuper[0] = ciudades.get(trasladoActual.origen);
            ciudadesSuper[1] = ciudades.get(trasladoActual.destino);
            ciudadesPorSuper.insertar(ciudadesSuper);

            //sumo la ganancia a la ganancia para promedio
            gananciaTotal += trasladoActual.gananciaNeta;

        }

        // hacemos que el otro heap tenga los mismos elementos
        trasladosAnt.heapify(trasladosRed.obtenerComoArrayList());
        return devolver;
    }

    // funcion para chequear si la ciudad actual es candidata a mayor ganancia
    private void actualEsMayorGanancia(Ciudad ciudadCheck) {
        Integer[] insertar = new Integer[1];
        insertar[0] = ciudadCheck.idCiudad();
        System.out
                .println("Ciudad " + ciudadCheck.idCiudad() + ":" + "Ganancia actual: " + ciudadCheck.GananciaCiudad());
        System.out.println("Ciudad " + ciudadCheck.idCiudad() + ":" + "Perdida actual: " + ciudadCheck.PerdidaCiudad());

        if (ciudadMayorGanancia.cardinal() == 0) {
            ciudadMayorGanancia.insertar(insertar);
        } else {

            int valorMaxActual = ciudades.get(ciudadMayorGanancia.obtenerMaximo())
                    .GananciaCiudad();

            if (valorMaxActual == ciudadCheck.GananciaCiudad()
                    && ciudadCheck.idCiudad() != ciudadesConMayorGanancia().get(0)) {
                ciudadMayorGanancia.insertar(insertar);

            } else if (valorMaxActual < ciudadCheck.GananciaCiudad()) {
                ciudadMayorGanancia.eliminarTodo();
                ciudadMayorGanancia.insertar(insertar);
            }
            // si es menor no hace nada

        }
    }

    // funcion para chequear si la ciudad actual es candidata a mayor perdida
    private void actualEsMayorPerdida(Ciudad ciudadCheck) {
        Integer[] insertar = new Integer[1];
        insertar[0] = ciudadCheck.idCiudad();
        System.out
                .println("Ciudad " + ciudadCheck.idCiudad() + ":" + "Ganancia actual: " + ciudadCheck.GananciaCiudad());
        System.out.println("Ciudad " + ciudadCheck.idCiudad() + ":" + "Perdida actual: " + ciudadCheck.PerdidaCiudad());
        if (ciudadMayorPerdida.cardinal() == 0) {
            ciudadMayorPerdida.insertar(insertar);
        } else {

            int valorMaxActual = ciudades.get(ciudadMayorPerdida.obtenerMaximo())
                    .PerdidaCiudad();

            if (valorMaxActual == ciudadCheck.PerdidaCiudad()
                    && ciudadCheck.idCiudad() != ciudadesConMayorPerdida().get(0)) {
                ciudadMayorPerdida.insertar(insertar);

            }
            if (valorMaxActual < ciudadCheck.PerdidaCiudad()) {
                ciudadMayorPerdida.eliminarTodo();
                ciudadMayorPerdida.insertar(insertar);
            }
            // si es menor no hace nada

        }
    }

    public int[] despacharMasAntiguos(int n) {
        int limiteDespachos = Math.min(n, trasladosAnt.cardinal());
        int[] devolver = new int[n];

        //agrego el limite al contador
        contadorParaPromedio += limiteDespachos;

        for (int i = 0; i < limiteDespachos; i++) {
            Traslado trasladoActual = trasladosAnt.eliminarPrimero();
            devolver[i] = trasladoActual.id; // los ordeno de forma creciente
            System.out.println("Despachando pedido " + trasladoActual.id);

            ciudades.get(trasladoActual.origen).agregarGanancia(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // origen y le agrega la
            // ganancia

            ciudades.get(trasladoActual.destino).agregarPerdida(trasladoActual.gananciaNeta); // toma la ciudad de
                                                                                              // destino y le agrega
            // la perdida

            // chequeamos si la ciudad actual tiene la mayor ganancia
            actualEsMayorGanancia(ciudades.get(trasladoActual.origen));
            // chequeamos tambien si tiene la mayor perdida
            actualEsMayorPerdida(ciudades.get(trasladoActual.destino));

            // agrego al heap de superavit
            Ciudad[] ciudadesSuper = new Ciudad[2];
            ciudadesSuper[0] = ciudades.get(trasladoActual.origen);
            ciudadesSuper[1] = ciudades.get(trasladoActual.destino);
            ciudadesPorSuper.insertar(ciudadesSuper);

            //sumo la ganancia a la ganancia para promedio
            gananciaTotal += trasladoActual.gananciaNeta;
        }

        // hacemos que el otro heap tenga los mismos elementos
        trasladosRed.heapify(trasladosAnt.obtenerComoArrayList());

        return devolver;
    }

    public int ciudadConMayorSuperavit() {

        return ciudadesPorSuper.obtenerMaximo().idCiudad();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return ciudadMayorGanancia.obtenerComoArrayList();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {

        return ciudadMayorPerdida.obtenerComoArrayList();
    }

    public int gananciaPromedioPorTraslado() {
        
        return gananciaTotal / contadorParaPromedio;
    }

}
