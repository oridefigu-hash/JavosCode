package juego.entidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/** Comida básica: 10 puntos, hace crecer 1 y no desaparece. */
public class Manzana extends Comida {

    // Constructor
    public Manzana(Celda celda, Rejilla rejilla) {
        super(celda, rejilla, PERMANENTE);
    }

    // Comportamiento propio
    @Override
    public int getPuntos() {
        return 10;
    }

    @Override
    public int getCrecimiento() {
        return 1;
    }

    @Override
    public String getNombre() {
        return "Manzana";
    }

    @Override
    public boolean esBasica() {
        return true;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        double cx = centroX();
        double cy = centroY();
        double r = getRejilla().getTamanio() * 0.36;
        g.setColor(new Color(214, 48, 49));
        g.fill(new Ellipse2D.Double(cx - r, cy - r + 1, r * 2, r * 2));
        g.setColor(new Color(255, 255, 255, 110));
        g.fill(new Ellipse2D.Double(cx - r * 0.55, cy - r * 0.6, r * 0.5, r * 0.5));
        g.setColor(new Color(110, 70, 30));
        g.fillRect((int) cx - 1, (int) (cy - r - 3), 3, 6);
        g.setColor(new Color(60, 170, 80));
        g.fill(new Ellipse2D.Double(cx + 1, cy - r - 4, r * 0.7, r * 0.4));
    }
}
