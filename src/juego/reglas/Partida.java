package juego.reglas;

import contratos.Actualizable;
import contratos.Dibujable;
import estructuras.Lista;
import estructuras.ListaDoble;
import estructuras.Pila;
import estructuras.PilaEnlazada;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import juego.entidades.Celda;
import juego.entidades.Comida;
import juego.entidades.Direccion;
import juego.entidades.Dorada;
import juego.entidades.Manzana;
import juego.entidades.Podrida;
import juego.entidades.Rejilla;
import juego.entidades.Serpiente;
import motor.Temporizador;


public class Partida implements Actualizable, Dibujable, Marcador {

    // Constantes
    public static final int COLUMNAS = 28;
    public static final int FILAS = 18;
    public static final int CASILLA = 30;
    public static final int ORIGEN_Y = 60;
    public static final int COSTO_DESHACER = 5;
    private static final int PUNTOS_POR_NIVEL = 60;
    private static final int MAXIMO_ESPECIALES = 2;

    // Atributos
    private final Rejilla rejilla;
    private final Random azar;
    private final Serpiente serpiente;
    private final Lista<Comida> comidas = new ListaDoble<>();
    private final Pila<Movimiento> historial = new PilaEnlazada<>();
    private final Temporizador relojEspeciales = new Temporizador(5.0);
    private EstadoPartida estado = EstadoPartida.JUGANDO;
    private String motivoFin = "";
    private int puntaje;
    private double acumulado;
    private String mensaje = "";
    private double mensajeRestante;

    // Constructores 
    public Partida() {
        this(new Rejilla(COLUMNAS, FILAS, CASILLA, 0, ORIGEN_Y), new Random());
    }

    public Partida(Rejilla rejilla, Random azar) {
        if (rejilla == null || azar == null) {
            throw new IllegalArgumentException("la rejilla y el azar son obligatorios");
        }
        if (rejilla.getColumnas() < 6 || rejilla.getFilas() < 4) {
            throw new IllegalArgumentException("la rejilla es demasiado chica");
        }
        this.rejilla = rejilla;
        this.azar = azar;
        Celda inicio = new Celda(rejilla.getColumnas() / 2, rejilla.getFilas() / 2);
        this.serpiente = new Serpiente(inicio, Direccion.DERECHA, 3, rejilla);
        reponerBasica();
        avisar("¡A comer!");
    }

    // Estado
    public EstadoPartida getEstado() {
        return estado;
    }

    public boolean estaJugando() {
        return estado == EstadoPartida.JUGANDO;
    }

    public String getMotivoFin() {
        return motivoFin;
    }

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public int cantidadComidas() {
        return comidas.tamanio();
    }

    // Marcador (para el HUD)
    @Override
    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public int getNivel() {
        return 1 + puntaje / PUNTOS_POR_NIVEL;
    }

    @Override
    public int getLongitud() {
        return serpiente.getLongitud();
    }

    @Override
    public int getMovimientosGuardados() {
        return historial.tamanio();
    }

    @Override
    public int getCostoDeshacer() {
        return COSTO_DESHACER;
    }

    @Override
    public String getMensaje() {
        return mensajeRestante > 0 ? mensaje : "";
    }

    
    public double getIntervaloPaso() {
        return Math.max(0.07, 0.16 - 0.015 * (getNivel() - 1));
    }

    // Entrada del jugador
    public void pedirGiro(Direccion direccion) {
        if (estaJugando()) {
            serpiente.encolarGiro(direccion);
        }
    }

    public boolean deshacer() {
        if (!estaJugando()) {
            return false;
        }
        if (historial.estaVacia()) {
            avisar("No hay nada para deshacer");
            return false;
        }
        Movimiento m = historial.tope();
        int resultante = puntaje - m.getPuntos() - COSTO_DESHACER;
        if (resultante < 0) {
            avisar("Deshacer cuesta " + COSTO_DESHACER + " puntos");
            return false;
        }
        historial.desapilar();
        serpiente.retroceder(m.getColaQuitada(), m.getPendienteAntes(), m.getDireccionAntes());
        if (m.getComidaRepuesta() != null) {
            quitarComida(m.getComidaRepuesta());
        }
        if (m.getComidaComida() != null) {
            comidas.agregar(m.getComidaComida());
        }
        puntaje = resultante;
        acumulado = 0;
        avisar("Deshecho (-" + COSTO_DESHACER + ")");
        return true;
    }

    // Ciclo de juego
    @Override
    public void actualizar(double dt) {
        if (mensajeRestante > 0) {
            mensajeRestante -= dt;
        }
        if (!estaJugando()) {
            return;
        }
        actualizarComidas(dt);
        acumulado += dt;
        while (estaJugando() && acumulado >= getIntervaloPaso()) {
            acumulado -= getIntervaloPaso();
            paso();
        }
    }

    // Paso de la serpiente
    private void paso() {
        Direccion direccionAntes = serpiente.getDireccion();
        serpiente.aplicarGiroPendiente();
        Celda destino = serpiente.proximaCabeza();

        if (!rejilla.contiene(destino)) {
            terminar(EstadoPartida.PERDIDA, "Chocaste con la pared");
            return;
        }
        Comida comida = comidaEn(destino);
        int crecimiento = serpiente.getPendiente() + (comida == null ? 0 : comida.getCrecimiento());
        boolean laColaSeMueve = crecimiento == 0;
        if (serpiente.ocupa(destino, laColaSeMueve)) {
            terminar(EstadoPartida.PERDIDA, "Te mordiste a vos misma");
            return;
        }

        int nivelAntes = getNivel();
        int pendienteAntes = serpiente.getPendiente();
        int delta = 0;
        if (comida != null) {
            quitarComida(comida);
            int nuevo = Math.max(0, puntaje + comida.getPuntos());
            delta = nuevo - puntaje;
            puntaje = nuevo;
            serpiente.crecer(comida.getCrecimiento());
            avisar(comida.getNombre() + (comida.getPuntos() >= 0 ? " +" : " ") + comida.getPuntos());
        }
        Celda colaQuitada = serpiente.avanzar(destino);

        Comida repuesta = null;
        if (comida != null && !hayBasica()) {
            repuesta = reponerBasica();
        }
        historial.apilar(new Movimiento(direccionAntes, pendienteAntes, colaQuitada, comida, repuesta, delta));

        if (getNivel() > nivelAntes) {
            avisar("¡Nivel " + getNivel() + "!");
        }
    }

    private void terminar(EstadoPartida nuevo, String motivo) {
        estado = nuevo;
        motivoFin = motivo;
        avisar(motivo);
    }

    // Comidas
    private void actualizarComidas(double dt) {
        for (int i = comidas.tamanio() - 1; i >= 0; i--) {
            Comida c = comidas.obtener(i);
            c.actualizar(dt);
            if (c.haExpirado()) {
                comidas.eliminar(i);
            }
        }
        if (relojEspeciales.avanzar(dt) && cantidadEspeciales() < MAXIMO_ESPECIALES) {
            Celda libre = celdaLibre();
            if (libre != null) {
                comidas.agregar(azar.nextInt(10) < 6 ? new Dorada(libre, rejilla) : new Podrida(libre, rejilla));
            }
        }
    }

    private Comida reponerBasica() {
        Celda libre = celdaLibre();
        if (libre == null) {
            terminar(EstadoPartida.GANADA, "¡Llenaste el tablero!");
            return null;
        }
        Comida nueva = new Manzana(libre, rejilla);
        comidas.agregar(nueva);
        return nueva;
    }

    private boolean hayBasica() {
        for (int i = 0; i < comidas.tamanio(); i++) {
            if (comidas.obtener(i).esBasica()) {
                return true;
            }
        }
        return false;
    }

    private int cantidadEspeciales() {
        int cantidad = 0;
        for (int i = 0; i < comidas.tamanio(); i++) {
            if (!comidas.obtener(i).esBasica()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    private Comida comidaEn(Celda celda) {
        for (int i = 0; i < comidas.tamanio(); i++) {
            Comida c = comidas.obtener(i);
            if (celda.mismaCeldaQue(c)) {
                return c;
            }
        }
        return null;
    }

    private void quitarComida(Comida comida) {
        int indice = comidas.indiceDe(comida);
        if (indice >= 0) {
            comidas.eliminar(indice);
        }
    }

    private boolean ocupada(Celda celda) {
        return serpiente.ocupa(celda) || comidaEn(celda) != null;
    }

    
    private Celda celdaLibre() {
        for (int intento = 0; intento < 80; intento++) {
            Celda c = new Celda(azar.nextInt(rejilla.getColumnas()), azar.nextInt(rejilla.getFilas()));
            if (!ocupada(c)) {
                return c;
            }
        }
        int total = rejilla.total();
        int inicio = azar.nextInt(total);
        for (int k = 0; k < total; k++) {
            int indice = (inicio + k) % total;
            Celda c = new Celda(indice % rejilla.getColumnas(), indice / rejilla.getColumnas());
            if (!ocupada(c)) {
                return c;
            }
        }
        return null;
    }

    private void avisar(String texto) {
        mensaje = texto;
        mensajeRestante = 1.6;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        Color claro = new Color(30, 38, 54);
        Color oscuro = new Color(26, 33, 48);
        for (int f = 0; f < rejilla.getFilas(); f++) {
            for (int c = 0; c < rejilla.getColumnas(); c++) {
                g.setColor((c + f) % 2 == 0 ? claro : oscuro);
                g.fillRect(rejilla.x(c), rejilla.y(f), rejilla.getTamanio(), rejilla.getTamanio());
            }
        }
        for (int i = 0; i < comidas.tamanio(); i++) {
            comidas.obtener(i).dibujar(g);
        }
        serpiente.dibujar(g);
    }
}
