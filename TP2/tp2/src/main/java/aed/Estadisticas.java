package aed;
import java.util.ArrayList;


public class Estadisticas<T extends Comparable<T>>{
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

    private Heap<Ciudad> ciudadesPorSuper = new Heap(compararPorSuperavit);
    private ArrayList ciudadMayorGanancia = new ArrayList();
    private ArrayList ciudadMayorPerdida = new ArrayList();

    public Estadisticas(Comparador<T> comparador) {
        this.ciudadesPorSuper = ciudadesPorSuper;
        this.ciudadMayorGanancia = ciudadMayorGanancia;
        this.ciudadMayorPerdida = ciudadMayorPerdida;
    }
}
