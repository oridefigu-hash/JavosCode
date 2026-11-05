package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import juego.reglas.FabricaDeEscena;
import motor.Escena;
import ui.Texto;

/** Pantalla de fin: muestra el puntaje y permite volver a jugar o ir al menú. */
public class EscenaFin extends Escena {

    // Atributos
    private final int puntaje;
    private final String motivo;
    private final FabricaDeEscena otraPartida;
    private final FabricaDeEscena menu;

    // Constructor
    public EscenaFin(int puntaje, String motivo, FabricaDeEscena otraPartida, FabricaDeEscena menu) {
        if (puntaje < 0) {
            throw new IllegalArgumentException("el puntaje no puede ser negativo: " + puntaje);
        }
        if (otraPartida == null || menu == null) {
            throw new IllegalArgumentException("faltan las fábricas de escenas");
        }
        this.puntaje = puntaje;
        this.motivo = motivo == null ? "" : motivo;
        this.otraPartida = otraPartida;
        this.menu = menu;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        if (getEntrada().fuePresionada(KeyEvent.VK_ENTER)) {
            getContexto().irA(otraPartida.crear());
        }
        if (getEntrada().fuePresionada(KeyEvent.VK_M) || getEntrada().fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().irA(menu.crear());
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();
        g.setColor(new Color(32, 20, 30));
        g.fillRect(0, 0, ancho, alto);

        Texto.centrado(g, "FIN DEL JUEGO", ancho / 2.0, 150, 56, new Color(230, 90, 80));
        Texto.centrado(g, motivo, ancho / 2.0, 200, 22, Color.LIGHT_GRAY);
        Texto.centrado(g, "Puntaje", ancho / 2.0, 285, 24, Color.LIGHT_GRAY);
        Texto.centrado(g, String.valueOf(puntaje), ancho / 2.0, 360, 80, new Color(255, 214, 10));
        Texto.centrado(g, "ENTER: jugar de nuevo     M: menú", ancho / 2.0, alto - 90, 22, Color.WHITE);
    }
}
