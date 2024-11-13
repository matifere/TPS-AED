package aed;

import java.util.ArrayList;

public class BestEffort {
    // Completar atributos privados
    

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

    private ArrayList ciudades;
    private Heap trasladosRed = new Heap(compararPorGanancia);
    private Heap trasladosAnt = new Heap(compararPorTiempo);

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        for (int i = 0; i > cantCiudades; i++) {
            Ciudad ciudad = new Ciudad(i, 0, 0);
            ciudades.add(ciudad);
        }
    }

    public void registrarTraslados(Traslado[] traslados) {
        // Implementar
    }

    public int[] despacharMasRedituables(int n) {
        // Implementar
        return null;
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
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado() {
        // Implementar
        return 0;
    }

}
