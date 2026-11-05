package estructuras;

/**
 * Cola con nodos enlazados. En el juego guarda los giros que el jugador
 * pidió con el teclado y que la serpiente todavía no aplicó (FIFO).
 */
public class ColaEnlazada<T> implements Cola<T> {

    // Nodo interno
    private static class Nodo<T> {
        private final T dato;
        private Nodo<T> siguiente;

        private Nodo(T dato) {
            this.dato = dato;
        }
    }

    // Atributos
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamanio;

    // Operaciones
    @Override
    public void encolar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (ultimo == null) {
            primero = nuevo;
        } else {
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
        tamanio++;
    }

    @Override
    public T desencolar() {
        if (primero == null) {
            throw new IllegalStateException("la cola está vacía");
        }
        T dato = primero.dato;
        primero = primero.siguiente;
        if (primero == null) {
            ultimo = null;
        }
        tamanio--;
        return dato;
    }

    @Override
    public T frente() {
        if (primero == null) {
            throw new IllegalStateException("la cola está vacía");
        }
        return primero.dato;
    }

    // Consultas
    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @Override
    public void vaciar() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }
}
