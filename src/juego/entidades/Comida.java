package juego.entidades;

import contratos.Actualizable;
import contratos.Dibujable;
import java.awt.Graphics2D;


public abstract class Comida implements Ubicable, Dibujable, Actualizable {

    // Constantes
    public static final double PERMANENTE = 0;

    // Atributos
    private final Celda celda;
    private final Rejilla rejilla;
    private final boolean temporal;
    private double vidaRestante;

    // Constructor: duración <= 0 significa que no desaparece sola
    protected Comida(Celda celda, Rejilla rejilla, double duracion) {
        if (rejilla == null) {
            throw new IllegalArgumentException("la rejilla no puede ser nula");
        }
        if (celda == null || !rejilla.contiene(celda)) {
            throw new IllegalArgumentException("la comida debe estar dentro de la rejilla: " + celda);
        }
        if (Double.isNaN(duracion) || duracion < 0) {
            throw new IllegalArgumentException("duración inválida: " + duracion);
        }
        this.celda = celda;
        this.rejilla = rejilla;
        this.temporal = duracion > 0;
        this.vidaRestante = duracion;
    }

    // Lo que cambia según el tipo de comida
    public abstract int getPuntos();

    public abstract int getCrecimiento();

    public abstract String getNombre();

    // Gancho: la comida básica es la que siempre tiene que haber en el tablero
    public boolean esBasica() {
        return false;
    }

    // Ubicación
    public Celda getCelda() {
        return celda;
    }

    @Override
    public int getColumna() {
        return celda.getColumna();
    }

    @Override
    public int getFila() {
        return celda.getFila();
    }

    protected Rejilla getRejilla() {
        return rejilla;
    }

    // Vida útil
    @Override
    public void actualizar(double dt) {
        if (temporal && vidaRestante > 0) {
            vidaRestante = Math.max(0, vidaRestante - dt);
        }
    }

    public boolean haExpirado() {
        return temporal && vidaRestante <= 0;
    }

    public double getVidaRestante() {
        return vidaRestante;
    }

    // Parpadeo durante los últimos 2 segundos de vida
    protected boolean visibleAhora() {
        if (!temporal || vidaRestante > 2.0) {
            return true;
        }
        return ((int) (vidaRestante * 6)) % 2 == 0;
    }

    // Ayudas de dibujo
    protected double centroX() {
        return rejilla.x(celda.getColumna()) + rejilla.getTamanio() / 2.0;
    }

    protected double centroY() {
        return rejilla.y(celda.getFila()) + rejilla.getTamanio() / 2.0;
    }

    @Override
    public abstract void dibujar(Graphics2D g);
}
