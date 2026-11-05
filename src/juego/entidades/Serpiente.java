package juego.entidades;

import contratos.Dibujable;
import estructuras.Cola;
import estructuras.ColaEnlazada;
import estructuras.ListaDoble;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;


public class Serpiente implements Ubicable, Dibujable {

    // Constantes
    private static final int MAXIMO_GIROS = 3;
    private static final Color COLOR_CABEZA = new Color(70, 200, 110);
    private static final Color COLOR_COLA = new Color(25, 110, 70);

    // Atributos
    private final Rejilla rejilla;
    private final ListaDoble<Celda> cuerpo = new ListaDoble<>();
    private final Cola<Direccion> giros = new ColaEnlazada<>();
    private Direccion direccion;
    private Direccion ultimaPedida;
    private int pendiente;

    // Constructor
    public Serpiente(Celda cabeza, Direccion direccion, int longitud, Rejilla rejilla) {
        if (rejilla == null) {
            throw new IllegalArgumentException("la rejilla no puede ser nula");
        }
        if (direccion == null) {
            throw new IllegalArgumentException("la dirección no puede ser nula");
        }
        if (longitud < 1) {
            throw new IllegalArgumentException("la longitud mínima es 1: " + longitud);
        }
        this.rejilla = rejilla;
        this.direccion = direccion;
        this.ultimaPedida = direccion;
        for (int i = 0; i < longitud; i++) {
            Celda c = cabeza.desplazar(direccion.opuesta(), i);
            if (!rejilla.contiene(c)) {
                throw new IllegalArgumentException("la serpiente no entra en la rejilla");
            }
            cuerpo.agregar(c);
        }
    }

    // Consultas
    public Direccion getDireccion() {
        return direccion;
    }

    public int getLongitud() {
        return cuerpo.tamanio();
    }

    public Celda getCabeza() {
        return cuerpo.obtener(0);
    }

    public Celda getCola() {
        return cuerpo.obtener(cuerpo.tamanio() - 1);
    }

    public int getPendiente() {
        return pendiente;
    }

    @Override
    public int getColumna() {
        return getCabeza().getColumna();
    }

    @Override
    public int getFila() {
        return getCabeza().getFila();
    }

    
    public boolean ocupa(Celda celda) {
        return ocupa(celda, false);
    }

    public boolean ocupa(Celda celda, boolean ignorarCola) {
        int limite = ignorarCola ? cuerpo.tamanio() - 1 : cuerpo.tamanio();
        int i = 0;
        for (Celda c : cuerpo) {
            if (i >= limite) {
                break;
            }
            if (c.equals(celda)) {
                return true;
            }
            i++;
        }
        return false;
    }

    
    public boolean encolarGiro(Direccion pedida) {
        if (pedida == null) {
            throw new IllegalArgumentException("la dirección no puede ser nula");
        }
        if (giros.tamanio() >= MAXIMO_GIROS) {
            return false;
        }
        if (pedida == ultimaPedida || pedida.esOpuesta(ultimaPedida)) {
            return false;
        }
        giros.encolar(pedida);
        ultimaPedida = pedida;
        return true;
    }

    public void aplicarGiroPendiente() {
        if (!giros.estaVacia()) {
            Direccion siguiente = giros.desencolar();
            if (!siguiente.esOpuesta(direccion)) {
                direccion = siguiente;
            }
        }
        if (giros.estaVacia()) {
            ultimaPedida = direccion;
        }
    }

    public int girosPendientes() {
        return giros.tamanio();
    }

    // Movimiento
    public Celda proximaCabeza() {
        return getCabeza().desplazar(direccion);
    }

    public void crecer(int segmentos) {
        if (segmentos < 0) {
            throw new IllegalArgumentException("no se puede crecer una cantidad negativa: " + segmentos);
        }
        pendiente += segmentos;
    }

    
    public Celda avanzar(Celda nuevaCabeza) {
        if (nuevaCabeza == null) {
            throw new IllegalArgumentException("la cabeza no puede ser nula");
        }
        cuerpo.insertar(0, nuevaCabeza);
        if (pendiente > 0) {
            pendiente--;
            return null;
        }
        return cuerpo.eliminar(cuerpo.tamanio() - 1);
    }

    // Deshace un avance: quita la cabeza y devuelve la cola
    public void retroceder(Celda colaQuitada, int pendienteAntes, Direccion direccionAntes) {
        if (cuerpo.tamanio() <= 1 && colaQuitada == null) {
            throw new IllegalStateException("no se puede retroceder más");
        }
        cuerpo.eliminar(0);
        if (colaQuitada != null) {
            cuerpo.agregar(colaQuitada);
        }
        pendiente = pendienteAntes;
        direccion = direccionAntes;
        giros.vaciar();
        ultimaPedida = direccionAntes;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int tam = rejilla.getTamanio();
        int total = cuerpo.tamanio();
        int i = 0;
        for (Celda c : cuerpo) {
            double t = total <= 1 ? 0 : (double) i / (total - 1);
            g.setColor(mezclar(COLOR_CABEZA, COLOR_COLA, t));
            g.fill(new RoundRectangle2D.Double(rejilla.x(c.getColumna()) + 2, rejilla.y(c.getFila()) + 2,
                    tam - 4, tam - 4, 10, 10));
            i++;
        }
        dibujarOjos(g);
    }

    private void dibujarOjos(Graphics2D g) {
        Celda cabeza = getCabeza();
        double cx = rejilla.x(cabeza.getColumna()) + rejilla.getTamanio() / 2.0;
        double cy = rejilla.y(cabeza.getFila()) + rejilla.getTamanio() / 2.0;
        double adelante = rejilla.getTamanio() * 0.18;
        double costado = rejilla.getTamanio() * 0.2;
        int dc = direccion.getDc();
        int df = direccion.getDf();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int lado = -1; lado <= 1; lado += 2) {
            double ox = cx + dc * adelante + (-df) * costado * lado;
            double oy = cy + df * adelante + dc * costado * lado;
            g.setColor(Color.WHITE);
            g.fill(new Ellipse2D.Double(ox - 4, oy - 4, 8, 8));
            g.setColor(Color.BLACK);
            g.fill(new Ellipse2D.Double(ox - 2 + dc, oy - 2 + df, 4, 4));
        }
    }

    private static Color mezclar(Color a, Color b, double t) {
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int gr = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        return new Color(r, gr, bl);
    }
}
