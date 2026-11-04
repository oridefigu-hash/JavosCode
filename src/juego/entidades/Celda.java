package juego.entidades;

/** Casilla de la rejilla. Es inmutable: para "moverla" se crea otra. */
public final class Celda implements Ubicable {

    // Atributos
    private final int columna;
    private final int fila;

    // Constructor
    public Celda(int columna, int fila) {
        this.columna = columna;
        this.fila = fila;
    }

    // Consultas
    @Override
    public int getColumna() {
        return columna;
    }

    @Override
    public int getFila() {
        return fila;
    }

    // Casilla vecina en una dirección (sobrecargado: una o varias casillas)
    public Celda desplazar(Direccion direccion) {
        return desplazar(direccion, 1);
    }

    public Celda desplazar(Direccion direccion, int pasos) {
        if (direccion == null) {
            throw new IllegalArgumentException("la dirección no puede ser nula");
        }
        return new Celda(columna + direccion.getDc() * pasos, fila + direccion.getDf() * pasos);
    }

    // Igualdad por valor
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Celda)) {
            return false;
        }
        Celda c = (Celda) otro;
        return columna == c.columna && fila == c.fila;
    }

    @Override
    public int hashCode() {
        return 31 * columna + fila;
    }

    @Override
    public String toString() {
        return "(" + columna + "," + fila + ")";
    }
}
