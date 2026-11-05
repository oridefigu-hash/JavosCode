package juego;

import juego.pantallas.EscenaFin;
import juego.pantallas.EscenaMenu;
import juego.pantallas.EscenaPartida;
import motor.Escena;
import motor.Ventana;


public class Main {

    // Punto de entrada
    public static void main(String[] args) {
        Ventana.abrir("Snake con deshacer", 840, 600, menu());
    }

    // Menú -> partida
    private static Escena menu() {
        return new EscenaMenu(() -> partida());
    }

    // Partida -> fin (o menú)
    private static Escena partida() {
        return new EscenaPartida((puntaje, motivo) -> fin(puntaje, motivo), () -> menu());
    }

    // Fin -> partida o menú
    private static Escena fin(int puntaje, String motivo) {
        return new EscenaFin(puntaje, motivo, () -> partida(), () -> menu());
    }
}
