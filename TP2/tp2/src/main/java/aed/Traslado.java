package aed;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }


    //no se si es la forma correcta pero creo que deberia andar para poder ordenar los elementos de forma distinta
    public int compareTo(Traslado traslado/* , int indice */) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
