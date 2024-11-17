package aed;

public class Ciudad implements Comparable<Ciudad> {

    private int id;
    private int gananciaCiudad;
    private int perdidaCiudad;
    private int superavit;

    public Ciudad(int id, int gananciaCiudad, int perdidaCiudad) {
        this.id = id;
        this.gananciaCiudad = gananciaCiudad;
        this.perdidaCiudad = perdidaCiudad;
        calculoSuperavit();
    }

    public int idCiudad() {
        return id;
    }

    public int GananciaCiudad() {
        return gananciaCiudad;
    }

    public int PerdidaCiudad() {
        return perdidaCiudad;
    }

    public void agregarGanancia(int ganancia) {
        this.gananciaCiudad += ganancia;
        calculoSuperavit();
    }

    public void agregarPerdida(int perdida) {
        this.perdidaCiudad += perdida;
        calculoSuperavit();
    }

    public void calculoSuperavit() {
        this.superavit = gananciaCiudad - perdidaCiudad;
    }

    public int devolverSuperavit() {
        return superavit;
    }

    @Override
    public int compareTo(Ciudad otro) {
        return Integer.compare(this.superavit, otro.superavit);
    }
}
