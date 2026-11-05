package juego.reglas;

import motor.Escena;


public interface FabricaDeEscenaFin {

    Escena crear(int puntaje, String motivo);
}
