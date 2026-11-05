package juego.entidades;


public interface Ubicable {

    int getColumna();

    int getFila();

    
    default boolean mismaCeldaQue(Ubicable otro) {
        return otro != null && getColumna() == otro.getColumna() && getFila() == otro.getFila();
    }
}
