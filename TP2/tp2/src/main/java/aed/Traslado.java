package aed;

public class Traslado implements Comparable<Traslado> {

    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    // para los heaps
    int indiceRed;
    int indiceAnt;

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

    @Override
    public String toString() {
        return "Traslado{id=" + id + ", origen=" + origen + ", destino=" + destino + ", ganancia=" + gananciaNeta
                + ", timestamp=" + timestamp + "}";
    }

    public void RedCambiarIndice( int nuevoIndice){
        indiceRed = nuevoIndice;
    }
    public int RedObtener(){
        return indiceRed;
    }
    public void AntCambiarIndice( int nuevoIndice){
        indiceAnt = nuevoIndice;
    }
    public int AntObtener(){
        return indiceAnt;
    }
}
