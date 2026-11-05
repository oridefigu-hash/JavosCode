# JavosCode — Snake con deshacer

Juego 2D en Java hecho en equipo para la Game Jam Java (opción 2: Snake con deshacer).
Una serpiente crece al comer; con *Z* se deshace el último movimiento a cambio de puntos.

*Integrantes:* Oriana de Figueroa, Juan Pablo Zarza, Agustina Reviriego, Ignacio Grossetti, Villamayor Ludmila.

## Cómo jugar

Requisitos: JDK 11 o superior (javac y java en el PATH).

| Sistema | Compilar | Ejecutar |
|---|---|---|
| Linux / macOS | sh compilar.sh | sh ejecutar.sh |
| Windows | compilar.bat | ejecutar.bat |

Sin los scripts: javac -encoding UTF-8 -d out $(find src -name "*.java") y luego java -cp out juego.Main.

### Controles

- *Flechas o WASD:* girar
- *Z:* deshacer el último movimiento (cuesta 5 puntos)
- *P:* pausa
- *ESC:* volver
- *ENTER:* jugar (en el menú)

### Reglas

- *Manzana:* +10 puntos. *Dorada:* +30 puntos. *Podrida:* −10 puntos.
- Las comidas especiales (dorada y podrida) aparecen cada tanto, expiran y hay un máximo de 2 a la vez.
- Se sube de nivel cada 60 puntos y la serpiente se acelera.
- La partida termina si chocás con la pared o con tu propio cuerpo. Si llenás el tablero, ganás.
- Para deshacer hace falta que el puntaje no quede negativo: deshacer un movimiento en el que comiste una manzana descuenta esos 10 puntos más los 5 del costo.

## Estructura del proyecto


src/
├── motor/         Kit del docente: ventana, bucle, teclado, reloj, escenas
├── contratos/     Interfaces propias: Actualizable, Dibujable, Colisionable, Estadisticas
├── estructuras/   TADs propios: Lista, ListaDoble, Pila, PilaEnlazada, Cola, ColaEnlazada
├── ui/            Texto y HUD básico
├── juego/
│   ├── entidades/ Serpiente, Comida (abstracta) y sus subclases Manzana, Dorada, Podrida, Rejilla, Celda
│   ├── reglas/    Partida (reglas del juego), Movimiento, estado y marcador
│   └── pantallas/ EscenaMenu, EscenaPartida, EscenaFin y HUD de la partida
└── verificador/   Verificador del kit y prueba de nuestras estructuras


### Qué hace cada estructura de datos

- *Lista doblemente enlazada:* el cuerpo de la serpiente y las comidas del tablero.
- *Cola:* los giros pendientes (se aplican de a uno por paso).
- *Pila:* el historial de movimientos, para deshacer con Z.

### Verificar las estructuras


java -cp out verificador.PruebaMisEstructuras


Las tres estructuras pasan el verificador del kit.

## Conceptos de la materia aplicados

- *POO y encapsulamiento:* atributos privados, constructores que validan.
- *Herencia y polimorfismo:* Comida es abstracta y sus tres subclases se comportan distinto.
- *Interfaces:* contratos propios implementados por varias clases.
- *Paquetes por responsabilidad:* sin dependencias circulares.
- *TADs propios:* sin colecciones de java.util ni arreglos fuera de las estructuras.
