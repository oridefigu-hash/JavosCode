package juego.entidades;

/** Las cuatro direcciones en que puede ir la serpiente. */
public enum Direccion {
    ARRIBA(0, -1),
    ABAJO(0, 1),
    IZQUIERDA(-1, 0),
    DERECHA(1, 0);

    // Atributos
    private final int dc;
    private final int df;

    // Constructor
    Direccion(int dc, int df) {
        this.dc = dc;
        this.df = df;
    }

    // Consultas
    public int getDc() {
        return dc;
    }

    public int getDf() {
        return df;
    }

    public boolean esOpuesta(Direccion otra) {
        return otra != null && dc == -otra.dc && df == -otra.df;
    }

    public Direccion opuesta() {
        switch (this) {
            case ARRIBA:
                return ABAJO;
            case ABAJO:
                return ARRIBA;
            case IZQUIERDA:
                return DERECHA;
            default:
                return IZQUIERDA;
        }
    }
}
