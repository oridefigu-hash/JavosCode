package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import juego.entidades.Direccion;
import juego.reglas.FabricaDeEscena;
import juego.reglas.FabricaDeEscenaFin;
import juego.reglas.Partida;
import motor.Entrada;
import motor.Escena;
import ui.Texto;


public class EscenaPartida extends Escena {

    // Constantes
    private static final double ESPERA_FIN = 1.2;

    // Atributos
    private final FabricaDeEscenaFin pantallaFin;
    private final FabricaDeEscena menu;
    private Partida partida;
    private HudPartida hud;
    private boolean pausa;
    private double esperaFin;

    // Constructor
    public EscenaPartida(FabricaDeEscenaFin pantallaFin, FabricaDeEscena menu) {
        if (pantallaFin == null || menu == null) {
            throw new IllegalArgumentException("faltan las fábricas de escenas");
        }
        this.pantallaFin = pantallaFin;
        this.menu = menu;
    }

    // Entrada a la escena
    @Override
    public void alEntrar() {
        partida = new Partida();
        hud = new HudPartida(partida, getContexto().getAncho());
        pausa = false;
        esperaFin = 0;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        Entrada entrada = getEntrada();

        if (entrada.fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().irA(menu.crear());
            return;
        }
        if (entrada.fuePresionada(KeyEvent.VK_P)) {
            pausa = !pausa;
        }
        if (pausa) {
            return;
        }

        leerGiros(entrada);
        if (entrada.fuePresionada(KeyEvent.VK_Z)) {
            partida.deshacer();
        }
        partida.actualizar(dt);

        if (!partida.estaJugando()) {
            esperaFin += dt;
            if (esperaFin >= ESPERA_FIN) {
                getContexto().irA(pantallaFin.crear(partida.getPuntaje(), partida.getMotivoFin()));
            }
        }
    }

    private void leerGiros(Entrada entrada) {
        if (entrada.fuePresionada(KeyEvent.VK_UP) || entrada.fuePresionada(KeyEvent.VK_W)) {
            partida.pedirGiro(Direccion.ARRIBA);
        }
        if (entrada.fuePresionada(KeyEvent.VK_DOWN) || entrada.fuePresionada(KeyEvent.VK_S)) {
            partida.pedirGiro(Direccion.ABAJO);
        }
        if (entrada.fuePresionada(KeyEvent.VK_LEFT) || entrada.fuePresionada(KeyEvent.VK_A)) {
            partida.pedirGiro(Direccion.IZQUIERDA);
        }
        if (entrada.fuePresionada(KeyEvent.VK_RIGHT) || entrada.fuePresionada(KeyEvent.VK_D)) {
            partida.pedirGiro(Direccion.DERECHA);
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();
        g.setColor(new Color(14, 18, 28));
        g.fillRect(0, 0, ancho, alto);
        partida.dibujar(g);
        hud.dibujar(g);

        if (pausa) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 60, ancho, alto - 60);
            Texto.centrado(g, "PAUSA", ancho / 2.0, alto / 2.0 + 20, 56, Color.WHITE);
            Texto.centrado(g, "P para seguir", ancho / 2.0, alto / 2.0 + 60, 20, Color.LIGHT_GRAY);
        }
        if (!partida.estaJugando()) {
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRect(0, 60, ancho, alto - 60);
            Texto.centrado(g, partida.getMotivoFin(), ancho / 2.0, alto / 2.0 + 20, 36, Color.WHITE);
        }
    }
}
