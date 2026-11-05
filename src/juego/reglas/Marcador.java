package juego.reglas;

//Datos del HUD
public interface Marcador {

    int getPuntaje();

    int getNivel();

    int getLongitud();

    int getMovimientosGuardados();

    int getCostoDeshacer();

    String getMensaje();
}
