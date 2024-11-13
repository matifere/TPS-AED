package aed;

import java.util.Comparator;

public class sortByGanancia implements Comparator<Traslado> {
    public int compare(Traslado a, Traslado b)
    {
        return Integer.compare(a.gananciaNeta, b.gananciaNeta);
    }
}
