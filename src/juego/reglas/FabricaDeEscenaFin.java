package juego.reglas;

import motor.Escena;

/** Crea la pantalla de fin a partir del resultado de la partida. */
public interface FabricaDeEscenaFin {

    Escena crear(int puntaje, String motivo);
}
