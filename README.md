# JavosCode - Snake con deshacer

Juego 2D en Java hecho en equipo para la Game Jam Java (opción 2: Snake con deshacer).
Una serpiente crece al comer y, con la tecla Z, se deshace el último movimiento a cambio de puntos.

Integrantes: Oriana de Figueroa, Juan Pablo Zarza, Agustina Reviriego, Florencia Reviriego, Ignacio Grossetti.

## Cómo jugar

Requisito: tener instalado el JDK 11 o superior (que funcionen `javac` y `java` en la terminal).

### Paso 1: compilar

- Windows: `compilar.bat`
- Linux o macOS: `sh compilar.sh`

### Paso 2: ejecutar

- Windows: `ejecutar.bat juego.Main`
- Linux o macOS: `sh ejecutar.sh juego.Main`
- Otra opción en cualquier sistema: `java -cp out juego.Main`

Importante: hay que escribir `juego.Main` después del nombre del script. Si se ejecuta sin eso, el script busca otra clase y el juego no se abre.

## Controles

- Flechas o WASD: girar
- Z: deshacer el último movimiento (cuesta 5 puntos)
- P: pausa
- ESC: volver
- ENTER: jugar (en el menú)

## Reglas

- Manzana: +10 puntos.
- Dorada: +30 puntos.
- Podrida: -10 puntos.
- Las comidas especiales (dorada y podrida) aparecen cada tanto, desaparecen solas y nunca hay más de 2 a la vez.
- Se sube de nivel cada 60 puntos y la serpiente se acelera.
- La partida termina si la serpiente choca con la pared o con su propio cuerpo. Si se llena el tablero, se gana.
- Deshacer no puede dejar el puntaje en negativo. Si en ese movimiento se comió una manzana, se descuentan esos 10 puntos más los 5 del costo.

## Estructura del proyecto

Todo el código está dentro de la carpeta `src`, dividido en paquetes según su responsabilidad:

- `motor`: kit del docente (ventana, bucle de juego, teclado, reloj y escenas).
- `contratos`: interfaces propias (Actualizable, Dibujable, Colisionable y Estadisticas).
- `estructuras`: estructuras de datos propias (Lista, ListaDoble, Pila, PilaEnlazada, Cola y ColaEnlazada).
- `ui`: texto y HUD básico.
- `juego/entidades`: Serpiente, Comida (clase abstracta) con sus subclases Manzana, Dorada y Podrida, además de Rejilla y Celda.
- `juego/reglas`: Partida (las reglas del juego), Movimiento, estado de la partida y marcador.
- `juego/pantallas`: EscenaMenu, EscenaPartida, EscenaFin y el HUD de la partida.
- `verificador`: verificador del kit y prueba de nuestras estructuras.

## Estructuras de datos

- Lista doblemente enlazada: guarda el cuerpo de la serpiente y las comidas del tablero.
- Cola: guarda los giros pendientes y los aplica de a uno por paso.
- Pila: guarda el historial de movimientos para poder deshacer con la tecla Z.

## Pruebas de las estructuras (opcional)

El kit del docente incluye un verificador que prueba automáticamente que nuestra Lista, nuestra Pila y nuestra Cola funcionan correctamente. No hace falta para jugar.

Para correrlo, primero hay que compilar el proyecto y después ejecutar:

`java -cp out verificador.PruebaMisEstructuras`

Si todo está bien, muestra un mensaje por cada estructura, por ejemplo "Lista: 36 de 36 verificaciones correctas -> OK". Las tres estructuras pasan todas las verificaciones.

## Conceptos de la materia aplicados

- POO y encapsulamiento: atributos privados y constructores que validan.
- Herencia y polimorfismo: Comida es abstracta y sus tres subclases se comportan distinto.
- Interfaces: contratos propios implementados por varias clases.
- Paquetes por responsabilidad: sin dependencias circulares.
- Estructuras propias: sin colecciones de java.util ni arreglos fuera de las estructuras.
