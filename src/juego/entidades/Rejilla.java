package juego.entidades;

/** Geometría del tablero: cuántas casillas hay y dónde se dibujan en pantalla. */
public class Rejilla {

    // Atributos
    private final int columnas;
    private final int filas;
    private final int tamanio;
    private final int origenX;
    private final int origenY;

    // Constructor
    public Rejilla(int columnas, int filas, int tamanio, int origenX, int origenY) {
        if (columnas <= 0 || filas <= 0) {
            throw new IllegalArgumentException("la rejilla necesita columnas y filas positivas");
        }
        if (tamanio <= 0) {
            throw new IllegalArgumentException("el tamaño de casilla debe ser positivo: " + tamanio);
        }
        this.columnas = columnas;
        this.filas = filas;
        this.tamanio = tamanio;
        this.origenX = origenX;
        this.origenY = origenY;
    }

    // Consultas
    public int getColumnas() {
        return columnas;
    }

    public int getFilas() {
        return filas;
    }

    public int getTamanio() {
        return tamanio;
    }

    public int getOrigenX() {
        return origenX;
    }

    public int getOrigenY() {
        return origenY;
    }

    public int getAnchoPx() {
        return columnas * tamanio;
    }

    public int getAltoPx() {
        return filas * tamanio;
    }

    public int total() {
        return columnas * filas;
    }

    // Coordenadas de pantalla de una casilla
    public int x(int columna) {
        return origenX + columna * tamanio;
    }

    public int y(int fila) {
        return origenY + fila * tamanio;
    }

    // Pertenencia (sobrecargado: por coordenadas o por casilla)
    public boolean contiene(int columna, int fila) {
        return columna >= 0 && columna < columnas && fila >= 0 && fila < filas;
    }

    public boolean contiene(Celda celda) {
        return celda != null && contiene(celda.getColumna(), celda.getFila());
    }
}
