package juego.reglas;

/** Contrato propio: los datos de la partida que muestra el HUD. */
public interface Marcador {

    int getPuntaje();

    int getNivel();

    int getLongitud();

    int getMovimientosGuardados();

    int getCostoDeshacer();

    String getMensaje();
}
