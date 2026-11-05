package juego.pantallas;

import estructuras.Lista;
import estructuras.ListaDoble;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import juego.entidades.Celda;
import juego.entidades.Comida;
import juego.entidades.Dorada;
import juego.entidades.Manzana;
import juego.entidades.Podrida;
import juego.entidades.Rejilla;
import juego.reglas.FabricaDeEscena;
import motor.Escena;
import motor.Temporizador;
import ui.Texto;


public class EscenaMenu extends Escena {

    // Atributos
    private final FabricaDeEscena nuevaPartida;
    private final Lista<Comida> muestras = new ListaDoble<>();
    private final Temporizador parpadeo = new Temporizador(0.55);
    private boolean visible = true;

    // Constructor
    public EscenaMenu(FabricaDeEscena nuevaPartida) {
        if (nuevaPartida == null) {
            throw new IllegalArgumentException("falta la fábrica de la partida");
        }
        this.nuevaPartida = nuevaPartida;
    }

    
    @Override
    public void alEntrar() {
        muestras.vaciar();
        int tam = 36;
        int x = getContexto().getAncho() / 2 - 230;
        int y = 262;
        muestras.agregar(new Manzana(new Celda(0, 0), new Rejilla(1, 1, tam, x, y)));
        muestras.agregar(new Dorada(new Celda(0, 0), new Rejilla(1, 1, tam, x + 160, y)));
        muestras.agregar(new Podrida(new Celda(0, 0), new Rejilla(1, 1, tam, x + 320, y)));
        visible = true;
        parpadeo.reiniciar();
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        if (parpadeo.avanzar(dt)) {
            visible = !visible;
        }
        if (getEntrada().fuePresionada(KeyEvent.VK_ENTER) || getEntrada().fuePresionada(KeyEvent.VK_SPACE)) {
            getContexto().irA(nuevaPartida.crear());
        }
        if (getEntrada().fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().salir();
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();
        g.setColor(new Color(16, 22, 34));
        g.fillRect(0, 0, ancho, alto);

        Texto.centrado(g, "SNAKE", ancho / 2.0, 120, 72, new Color(70, 200, 110));
        Texto.centrado(g, "con deshacer", ancho / 2.0, 160, 28, new Color(255, 214, 10));

        Texto.centrado(g, "Comidas", ancho / 2.0, 235, 20, Color.WHITE);
        int x = ancho / 2 - 230;
        for (int i = 0; i < muestras.tamanio(); i++) {
            Comida c = muestras.obtener(i);
            c.dibujar(g);
            String detalle = (c.getPuntos() > 0 ? "+" : "") + c.getPuntos() + " pts";
            Texto.izquierda(g, c.getNombre(), x + 160 * i + 44, 285, 16, Color.WHITE);
            Texto.izquierda(g, detalle, x + 160 * i + 44, 305, 14, Color.LIGHT_GRAY);
        }

        Texto.centrado(g, "Flechas o WASD: girar", ancho / 2.0, 380, 18, Color.LIGHT_GRAY);
        Texto.centrado(g, "Z: deshacer el último movimiento (cuesta 5 puntos)", ancho / 2.0, 408, 18, Color.LIGHT_GRAY);
        Texto.centrado(g, "P: pausa     ESC: volver", ancho / 2.0, 436, 18, Color.LIGHT_GRAY);

        if (visible) {
            Texto.centrado(g, "ENTER para jugar", ancho / 2.0, 510, 26, Color.WHITE);
        }
    }
}
