package juego.entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/** Comida rara: 30 puntos, hace crecer 2 y desaparece a los 6 segundos. */
public class Dorada extends Comida {

    // Constructor
    public Dorada(Celda celda, Rejilla rejilla) {
        super(celda, rejilla, 6.0);
    }

    // Comportamiento propio
    @Override
    public int getPuntos() {
        return 30;
    }

    @Override
    public int getCrecimiento() {
        return 2;
    }

    @Override
    public String getNombre() {
        return "Dorada";
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
        g.setColor(new Color(255, 214, 10, 70));
        g.fill(new Ellipse2D.Double(cx - r * 1.5, cy - r * 1.5, r * 3, r * 3));
        g.setColor(new Color(255, 195, 0));
        g.fill(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));
        g.setColor(new Color(255, 245, 160));
        g.fill(new Ellipse2D.Double(cx - r * 0.55, cy - r * 0.55, r * 1.1, r * 1.1));
        g.setColor(new Color(255, 255, 255, 220));
        g.setStroke(new BasicStroke(2f));
        int d = (int) (r * 1.6);
        g.drawLine((int) cx, (int) cy - d, (int) cx, (int) cy + d);
        g.drawLine((int) cx - d, (int) cy, (int) cx + d, (int) cy);
    }
}
