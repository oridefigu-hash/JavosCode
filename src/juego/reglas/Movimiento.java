package juego.reglas;

import juego.entidades.Celda;
import juego.entidades.Comida;
import juego.entidades.Direccion;


public final class Movimiento {

    // Atributos
    private final Direccion direccionAntes;
    private final int pendienteAntes;
    private final Celda colaQuitada;
    private final Comida comidaComida;
    private final Comida comidaRepuesta;
    private final int puntos;

    // Constructor
    public Movimiento(Direccion direccionAntes, int pendienteAntes, Celda colaQuitada,
                      Comida comidaComida, Comida comidaRepuesta, int puntos) {
        if (direccionAntes == null) {
            throw new IllegalArgumentException("la dirección anterior no puede ser nula");
        }
        if (pendienteAntes < 0) {
            throw new IllegalArgumentException("el crecimiento pendiente no puede ser negativo");
        }
        this.direccionAntes = direccionAntes;
        this.pendienteAntes = pendienteAntes;
        this.colaQuitada = colaQuitada;
        this.comidaComida = comidaComida;
        this.comidaRepuesta = comidaRepuesta;
        this.puntos = puntos;
    }

    // Consultas
    public Direccion getDireccionAntes() {
        return direccionAntes;
    }

    public int getPendienteAntes() {
        return pendienteAntes;
    }

    public Celda getColaQuitada() {
        return colaQuitada;
    }

    public Comida getComidaComida() {
        return comidaComida;
    }

    public Comida getComidaRepuesta() {
        return comidaRepuesta;
    }

    public int getPuntos() {
        return puntos;
    }
}
