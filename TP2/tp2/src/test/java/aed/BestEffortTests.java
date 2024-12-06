package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init() {
        // Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 0, 1, 400, 20),
                new Traslado(3, 3, 4, 500, 50),
                new Traslado(4, 4, 3, 500, 11),
                new Traslado(5, 1, 0, 1000, 40),
                new Traslado(6, 1, 0, 1000, 41),
                new Traslado(7, 6, 3, 2000, 42)
        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2)
                    encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_con_mas_ganancia_de_a_varios() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void despachar_mas_viejo_de_a_uno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_mas_viejo_de_a_varios() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void despachar_mixtos() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        System.err.println(sis.ciudadesConMayorPerdida());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void agregar_traslados() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 0, 1, 10001, 5),
                new Traslado(9, 0, 1, 40000, 2),
                new Traslado(10, 0, 1, 50000, 3),
                new Traslado(11, 0, 1, 50000, 4),
                new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);

        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        System.err.println(sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void promedio_por_traslado() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 1, 2, 1452, 5),
                new Traslado(9, 1, 2, 334, 2),
                new Traslado(10, 1, 2, 24, 3),
                new Traslado(11, 1, 2, 333, 4),
                new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());

    }

    @Test
    void mayor_superavit() {
        Traslado[] nuevos = new Traslado[] {
                new Traslado(1, 3, 4, 1, 7),
                new Traslado(7, 6, 5, 40, 6),
                new Traslado(6, 5, 6, 3, 5),
                new Traslado(2, 2, 1, 41, 4),
                new Traslado(3, 3, 4, 100, 3),
                new Traslado(4, 1, 2, 30, 2),
                new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }

    /*
     * 
     * TESTS PROPIOS
     * 
     */

    /*
     * 
     * TRASLADOS
     * 
     */

 

    @Test
    void ciudades_sin_traslados() {
        Traslado[] pocosTraslados = new Traslado[] {
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 3, 4, 200, 20)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, pocosTraslados);

        sis.despacharMasRedituables(2);
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());
    }

    // intentamos despachar elementos de mas
    @Test
    void despachar_de_mas() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(10);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
    }

    /*
     *
     * HEAP
     * 
     */

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

    Comparador<Integer> compararPorMayor= new Comparador<Integer>() {
        @Override
        public int comparar(Integer t1, Integer t2) {
            return Integer.compare(t1, t2);
        }
    };
    Comparador<Integer> compararPorMenor= new Comparador<Integer>() {
        @Override
        public int comparar(Integer t1, Integer t2) {
            return -Integer.compare(t1, t2);
        }
    };

    @Test
    void comportamiento_multiples_Heaps() {
        Heap conjuntoMayor = new Heap<>(compararPorMayor);
        Heap conjuntoMenor =new Heap<>(compararPorMenor);
        Integer[] nuevo = new Integer[10]; 
        for (int i = 0; i < nuevo.length; i++) {
            nuevo[i] = i;
        }
        assertEquals(0, conjuntoMayor.cardinal());
        assertEquals(0, conjuntoMenor.cardinal());
        conjuntoMayor.conectarHeap(conjuntoMenor);
        conjuntoMenor.conectarHeap(conjuntoMayor);
        conjuntoMayor.insertar(nuevo);
        assertEquals(10, conjuntoMayor.cardinal());
        assertEquals(10, conjuntoMenor.cardinal());

        conjuntoMenor.eliminarPrimero();
        assertEquals(9, conjuntoMayor.cardinal());

        

    }

    @Test
    void nuevo_conjunto_vacio() {
        Heap conjunto = new Heap(compararPorGanancia);

        assertEquals(0, conjunto.cardinal());
    }

    @Test
    void insertar_un_traslado() {
        Heap conjunto = new Heap(compararPorGanancia);
        Traslado[] nuevo = new Traslado[] {
                new Traslado(1, 3, 4, 1, 7),

        };

        assertEquals(0, conjunto.cardinal());
        conjunto.insertar(nuevo);
        assertEquals(1, conjunto.cardinal());
        assertEquals(nuevo[0], conjunto.obtenerMaximo());

    }

    @Test
    void insertar_varios_elementos_por_ganancia() {
        Heap conjunto = new Heap(compararPorGanancia);
        Traslado[] nuevo = new Traslado[] {
                new Traslado(1, 3, 4, 10, 8),
                new Traslado(2, 2, 2, 50, 6),
                new Traslado(4, 4, 1, 75, 5),
                new Traslado(3, 1, 3, 20, 7),
                new Traslado(5, 5, 4, 35, 9)
        };

        assertEquals(0, conjunto.cardinal());
        conjunto.insertar(nuevo);
        assertEquals(5, conjunto.cardinal());
        assertEquals(nuevo[2], conjunto.obtenerMaximo());

    }

    @Test
    void insertar_varios_elementos_por_tiempo() {
        Heap conjunto = new Heap(compararPorTiempo);
        Traslado[] nuevo = new Traslado[] {
                new Traslado(8, 9, 3, 12, 2),
                new Traslado(7, 6, 5, 11, 5),
                new Traslado(3, 2, 1, 45, 6),
                new Traslado(4, 3, 2, 60, 8)
        };

        assertEquals(0, conjunto.cardinal());
        conjunto.insertar(nuevo);
        assertEquals(4, conjunto.cardinal());
        assertEquals(nuevo[3], conjunto.obtenerMaximo());
    }

    @Test
    void eliminar_primer_elemento() {
        Heap conjunto = new Heap(compararPorGanancia);
        Traslado[] nuevo = new Traslado[] {
                new Traslado(9, 2, 4, 80, 3),
                new Traslado(6, 7, 1, 100, 4),
                new Traslado(5, 8, 2, 25, 5),
                new Traslado(3, 6, 3, 15, 2),
                new Traslado(7, 4, 1, 55, 9),
                new Traslado(2, 5, 5, 40, 1)
        };

        assertEquals(0, conjunto.cardinal());
        conjunto.insertar(nuevo);
        assertEquals(6, conjunto.cardinal());
        assertEquals(nuevo[1], conjunto.obtenerMaximo());
        assertEquals(6, conjunto.cardinal());
        assertEquals(nuevo[1], conjunto.eliminarPrimero());
        assertEquals(5, conjunto.cardinal());
        assertEquals(nuevo[0], conjunto.obtenerMaximo());

    }

    @Test
void stress_test() {
    int cantCiudades = 100;
    int cantidadTraslados = 100000;

    Traslado[] stressTraslados = new Traslado[cantidadTraslados];

    for (int id = 1; id <= cantidadTraslados; id++) {
        int origen = (id - 1) % cantCiudades;
        int destino = id % cantCiudades;
        if (origen == destino) {
            destino = (destino + 1) % cantCiudades;
        }
        int gananciaNeta = id;
        int tiempo = id;

        stressTraslados[id - 1] = new Traslado(id, origen, destino, gananciaNeta, tiempo);
    }

    BestEffort sis = new BestEffort(cantCiudades, stressTraslados);

    

    sis.despacharMasRedituables(50000);
    assertTrue(sis.gananciaPromedioPorTraslado() > 0);

    sis.despacharMasAntiguos(30000);
    assertTrue(sis.gananciaPromedioPorTraslado() > 0);

    assertTrue(sis.ciudadesConMayorGanancia().contains(99));
}


}
