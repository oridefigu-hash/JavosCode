package juego.entidades;

/**
 * Contrato propio: todo lo que ocupa una casilla de la rejilla.
 * Lo implementan Celda, Comida (y sus subclases) y Serpiente.
 */
public interface Ubicable {

    int getColumna();

    int getFila();

    // Método por defecto: dos ubicables comparten casilla
    default boolean mismaCeldaQue(Ubicable otro) {
        return otro != null && getColumna() == otro.getColumna() && getFila() == otro.getFila();
    }
}
