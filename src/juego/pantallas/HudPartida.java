package juego.pantallas;

import contratos.Dibujable;
import java.awt.Color;
import java.awt.Graphics2D;
import juego.reglas.Marcador;
import ui.Texto;

/** Barra superior de la partida: puntaje, nivel, largo, mensajes y estado del deshacer. */
public class HudPartida implements Dibujable {

    // Atributos
    private final Marcador marcador;
    private final int ancho;

    // Constructor
    public HudPartida(Marcador marcador, int ancho) {
        if (marcador == null) {
            throw new IllegalArgumentException("el marcador no puede ser nulo");
        }
        if (ancho <= 0) {
            throw new IllegalArgumentException("el ancho debe ser positivo: " + ancho);
        }
        this.marcador = marcador;
        this.ancho = ancho;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(new Color(14, 18, 28));
        g.fillRect(0, 0, ancho, 60);

        Texto.izquierda(g, "Puntaje: " + marcador.getPuntaje(), 16, 26, 20, Color.WHITE);
        Texto.centrado(g, "Nivel " + marcador.getNivel(), ancho / 2.0, 26, 20, new Color(255, 214, 10));
        Texto.derecha(g, "Largo: " + marcador.getLongitud(), ancho - 16, 26, 20, Color.WHITE);

        String deshacer = "Z: deshacer (-" + marcador.getCostoDeshacer() + ")   Guardados: "
                + marcador.getMovimientosGuardados();
        Texto.izquierda(g, deshacer, 16, 49, 14, Color.LIGHT_GRAY);
        Texto.derecha(g, marcador.getMensaje(), ancho - 16, 49, 15, new Color(130, 220, 160));
    }
}
