package aed;

import java.util.ArrayList;

public class BestEffort {

    // comparadores
    Comparador<Traslado> compararPorGanancia = new Comparador<Traslado>() {
        @Override
        public int comparar(Traslado t1, Traslado t2) {
            return t1.obtenerGananciaNeta() - t2.obtenerGananciaNeta();
        }
    };
    Comparador<Traslado> compararPorTiempo = new Comparador<Traslado>() {
        @Override
        public int comparar(Traslado t1, Traslado t2) {
            return t1.obtenerTiempo() - t2.obtenerTiempo();
        }
    };

    // atributos privados
    private ArrayList<Ciudad> ciudades;
    private Heap trasladosRed = new Heap(compararPorGanancia);
    private Heap trasladosAnt = new Heap(compararPorTiempo);

    private ArrayList<Integer> ciudadMayorGanancia = new ArrayList<>();
    private ArrayList<Integer> ciudadMayorPerdida = new ArrayList<>();

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
        int[] devolver = new int[n];
        for (int i = 0; i < n; i++) {
            Traslado trasladoActual = trasladosRed.eliminarPrimero();
            devolver[i] = trasladoActual.id;
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

        }
        return devolver;
    }

    // funcion para chequear si la ciudad actual es candidata a mayor ganancia
    private void actualEsMayorGanancia(Ciudad ciudadCheck) {
        if (ciudadMayorGanancia.size() == 0) {
            ciudadMayorGanancia.add(ciudadCheck.idCiudad());
        } else {
            int valorMaxActual = ciudades.get(ciudadMayorGanancia.get(0)).GananciaCiudad();

            if (valorMaxActual == ciudadCheck.GananciaCiudad()) {
                ciudadMayorGanancia.add(ciudadCheck.idCiudad());

            } else if (valorMaxActual < ciudadCheck.GananciaCiudad()) {
                ciudadMayorGanancia = new ArrayList<>();
                ciudadMayorGanancia.add(ciudadCheck.idCiudad());
            }
            // si es menor no hace nada
        }
    }

    // funcion para chequear si la ciudad actual es candidata a mayor perdida
    private void actualEsMayorPerdida(Ciudad ciudadCheck) {
        if (ciudadMayorPerdida.size() == 0) {
            ciudadMayorPerdida.add(ciudadCheck.idCiudad());
        } else {
            int valorMaxActual = ciudades.get(ciudadMayorPerdida.get(0)).PerdidaCiudad();

            if (valorMaxActual == ciudadCheck.PerdidaCiudad()) {
                ciudadMayorPerdida.add(ciudadCheck.idCiudad());

            } else if (valorMaxActual < ciudadCheck.PerdidaCiudad()) {
                ciudadMayorPerdida = new ArrayList<>();
                ciudadMayorPerdida.add(ciudadCheck.idCiudad());
            }
            // si es menor no hace nada
        }
    }

    public int[] despacharMasAntiguos(int n) {
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit() {
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return ciudadMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {

        return ciudadMayorPerdida;
    }

    public int gananciaPromedioPorTraslado() {
        // Implementar
        return 0;
    }

}
