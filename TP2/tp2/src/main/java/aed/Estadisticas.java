package aed;

import java.util.ArrayList;

public class Estadisticas {

    private Heap<Ciudad> ciudadesPorSuper;
    private ArrayList<Integer> ciudadMayorGanancia;
    private ArrayList<Integer> ciudadMayorPerdida;

    public Estadisticas(
            Comparador<Ciudad> compararPorSuperavit) {
        this.ciudadesPorSuper = new Heap<>(compararPorSuperavit);
        this.ciudadMayorGanancia = new ArrayList<>();
        this.ciudadMayorPerdida = new ArrayList<>();
    }

    /*
     * 
     * FUNCIONES AUXILIARES
     * 
     */

    /*
     * sobre la complejidad de las funciones auxiliares:
     * como todas realizan OE, y no existe ningun bucle
     * la complejidad de ambas queda en O(1)
     */

    // funcion para chequear si la ciudad actual es candidata a mayor ganancia
    public void actualEsMayorGanancia(Ciudad ciudadCheck, ArrayList<Ciudad> ciudades) {

        int valorMaxActual;
        if (ciudadMayorGanancia.isEmpty()) {
            valorMaxActual = 0;
        } else {
            int idCiudad = ciudadMayorGanancia.get(0);
            valorMaxActual = ciudades.get(idCiudad).GananciaCiudad();
        }

        if (ciudadCheck.GananciaCiudad() > valorMaxActual) {
            ciudadMayorGanancia.clear();
            ciudadMayorGanancia.add(ciudadCheck.idCiudad());
        } else if (ciudadCheck.GananciaCiudad() == valorMaxActual) {
            if (!ciudadMayorGanancia.contains(ciudadCheck.idCiudad())) {
                ciudadMayorGanancia.add(ciudadCheck.idCiudad());
            }
        }
    }

    // funcion para chequear si la ciudad actual es candidata a mayor perdida

    public void actualEsMayorPerdida(Ciudad ciudadCheck, ArrayList<Ciudad> ciudades) {
        int valorMaxActual;
        if (ciudadMayorPerdida.isEmpty()) {
            valorMaxActual = 0;
        } else {
            int idCiudad = ciudadMayorPerdida.get(0);
            valorMaxActual = ciudades.get(idCiudad).PerdidaCiudad();
        }

        if (ciudadCheck.PerdidaCiudad() > valorMaxActual) {
            ciudadMayorPerdida.clear();
            ciudadMayorPerdida.add(ciudadCheck.idCiudad());
        } else if (ciudadCheck.PerdidaCiudad() == valorMaxActual) {
            if (!ciudadMayorPerdida.contains(ciudadCheck.idCiudad())) {
                ciudadMayorPerdida.add(ciudadCheck.idCiudad());
            }
        }

        

    }
    /*
     * 
     * FIN FUNCIONES AUX
     * 
     */

    public void insertarCiudadesSuperavit(Ciudad[] ciudades) {
        ciudadesPorSuper.insertar(ciudades);
    }

    public int obtenerCiudadConMayorSuperavit() {
        return ciudadesPorSuper.obtenerMaximo().idCiudad();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return new ArrayList<>(ciudadMayorGanancia);
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return new ArrayList<>(ciudadMayorPerdida);
    }
}
