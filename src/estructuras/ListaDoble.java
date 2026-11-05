package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista doblemente enlazada. Se usa como cuerpo de la serpiente:
 * insertar la cabeza (posición 0) y quitar la cola (última posición)
 * cuestan O(1) porque se llega a cada extremo sin recorrer nada.
 */
public class ListaDoble<T> implements Lista<T>, Iterable<T> {

    // Nodo interno
    private static class Nodo<T> {
        private T dato;
        private Nodo<T> anterior;
        private Nodo<T> siguiente;

        private Nodo(T dato) {
            this.dato = dato;
        }
    }

    // Atributos
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamanio;

    // Altas
    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (ultimo == null) {
            primero = nuevo;
        } else {
            nuevo.anterior = ultimo;
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
        tamanio++;
    }

    @Override
    public void insertar(int indice, T elemento) {
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("índice " + indice + ", tamaño " + tamanio);
        }
        if (indice == tamanio) {
            agregar(elemento);
            return;
        }
        Nodo<T> actual = nodoEn(indice);
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.siguiente = actual;
        nuevo.anterior = actual.anterior;
        if (actual.anterior == null) {
            primero = nuevo;
        } else {
            actual.anterior.siguiente = nuevo;
        }
        actual.anterior = nuevo;
        tamanio++;
    }

    // Consultas
    @Override
    public T obtener(int indice) {
        validar(indice);
        return nodoEn(indice).dato;
    }

    @Override
    public int indiceDe(T elemento) {
        int i = 0;
        for (Nodo<T> n = primero; n != null; n = n.siguiente) {
            boolean iguales = n.dato == null ? elemento == null : n.dato.equals(elemento);
            if (iguales) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Bajas
    @Override
    public T eliminar(int indice) {
        validar(indice);
        Nodo<T> actual = nodoEn(indice);
        if (actual.anterior == null) {
            primero = actual.siguiente;
        } else {
            actual.anterior.siguiente = actual.siguiente;
        }
        if (actual.siguiente == null) {
            ultimo = actual.anterior;
        } else {
            actual.siguiente.anterior = actual.anterior;
        }
        tamanio--;
        return actual.dato;
    }

    @Override
    public void vaciar() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    // Extras: búsqueda por valor y recorrido con for-each
    public boolean contiene(T elemento) {
        return indiceDe(elemento) >= 0;
    }

    public boolean quitar(T elemento) {
        int indice = indiceDe(elemento);
        if (indice < 0) {
            return false;
        }
        eliminar(indice);
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = primero;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (actual == null) {
                    throw new NoSuchElementException();
                }
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }

    // Auxiliares
    private void validar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("índice " + indice + ", tamaño " + tamanio);
        }
    }

    // Se parte del extremo más cercano
    private Nodo<T> nodoEn(int indice) {
        if (indice < tamanio / 2) {
            Nodo<T> n = primero;
            for (int i = 0; i < indice; i++) {
                n = n.siguiente;
            }
            return n;
        }
        Nodo<T> n = ultimo;
        for (int i = tamanio - 1; i > indice; i--) {
            n = n.anterior;
        }
        return n;
    }
}
