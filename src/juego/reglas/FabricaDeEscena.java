package juego.reglas;

import motor.Escena;

/**
 * Crea una escena nueva. Se usa para pasar de una pantalla a otra sin que
 * los paquetes se conozcan entre sí (así no hay dependencias circulares).
 */
public interface FabricaDeEscena {

    Escena crear();
}
