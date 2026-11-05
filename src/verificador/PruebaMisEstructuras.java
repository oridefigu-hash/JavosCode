package verificador;

import estructuras.ColaEnlazada;
import estructuras.ListaDoble;
import estructuras.PilaEnlazada;

public class PruebaMisEstructuras {

    // Punto de entrada
    public static void main(String[] args) {
        VerificadorEstructuras.verificarLista(new ListaDoble<Integer>());
        VerificadorEstructuras.verificarPila(new PilaEnlazada<Integer>());
        VerificadorEstructuras.verificarCola(new ColaEnlazada<Integer>());
    }
}
