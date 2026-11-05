package juego.entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Podrida extends Comida {

    // Constructor
    public Podrida(Celda celda, Rejilla rejilla) {
        super(celda, rejilla, 8.0);
    }

    // Comportamiento propio
    @Override
    public int getPuntos() {
        return -10;
    }

    @Override
    public int getCrecimiento() {
        return 0;
    }

    @Override
    public String getNombre() {
        return "Podrida";
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        if (!visibleAhora()) {
            return;
        }
        double cx = centroX();
        double cy = centroY();
        double r = getRejilla().getTamanio() * 0.34;
        g.setColor(new Color(120, 70, 160));
        g.fill(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));
        g.setColor(new Color(90, 150, 70, 170));
        g.fill(new Ellipse2D.Double(cx - r * 0.2, cy - r * 0.1, r * 0.9, r * 0.8));
        g.setColor(new Color(30, 20, 40));
        g.setStroke(new BasicStroke(2f));
        int o = (int) (r * 0.35);
        g.drawLine((int) cx - o - 3, (int) cy - o - 3, (int) cx - o + 3, (int) cy - o + 3);
        g.drawLine((int) cx - o + 3, (int) cy - o - 3, (int) cx - o - 3, (int) cy - o + 3);
        g.drawLine((int) cx + o - 3, (int) cy - o - 3, (int) cx + o + 3, (int) cy - o + 3);
        g.drawLine((int) cx + o + 3, (int) cy - o - 3, (int) cx + o - 3, (int) cy - o + 3);
        g.drawArc((int) cx - o, (int) cy + 1, o * 2, o, 0, 180);
    }
}
