package aed;

public class Traslado implements Comparable<Traslado> {

    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public int devolverID() {
        return id;
    }

    public int obtenerGananciaNeta() {
        return gananciaNeta;
    }

    public int obtenerTiempo() {
        return timestamp;
    }

    // para que no se rompa todo hay que crear una comparacion 'por defecto'
    @Override
    public int compareTo(Traslado otro) {
        return Integer.compare(this.gananciaNeta, otro.gananciaNeta);
    }
}
