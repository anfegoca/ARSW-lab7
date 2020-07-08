# Tic Tac Toe

En este proyecto se realizó el popular juego de Tic Tac Toe en una versión online, en la cual se podrán crear salas para jugar con 2 jugadores.

## Getting Started

Para hacer uso del proyecto solo debe clonar el repositorio o descargarlo directamente, para ejectarlo lo hacemos por medio de la consola usando el siguiente comando.

```bash
mvn exec:java -Dexec.mainClass="co.edu.escuelaing.arsw.tictactoe.beans.TicTacToe"
```

O puede jugar directamente desde la siguiente dirección:

https://boiling-waters-06268.herokuapp.com/

### Prerequisites

Para usar el proyecto se necesitan tener las siguientes versiones de java o superior:

```
java version "1.8"
javac version "1.8"
Apache Maven 3.6.3
```

### Installing

Para usar el proyecto decargamos directamente el proyecto o lo clonamos de la siguiente manera:

En la consola nos vamos al directorio donde vamos a clonar el repositorio y ponemos el siguiente comando:

```bash
git clone https://github.com/anfegoca/ARSW-lab7.git

```
![clone](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/1.png)

En el direcctorio nos quedará la carpeta del proyecto y dentro veremos las siguiente carpetas

![direc](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/2.png)

Para modificar abrimos el proyecto con cualquier editor, en este caso usamos Visual Studio Code y ya podemos modificar cualquier clase del proyecto

![edit](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/3.png)


## Running the tests

Para ver el funcionamiento, ejecutamos el programa, abrimos la siguiente pagina y ya podremos usar la aplicación

http://localhost:8080/

Se le pedirá al usuario ingresar el nombre y número de la sala en la cual quiere jugar, si la sala no existe se creará y si ya está llena tambien se le avisará

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/4.png)

Si creamos una nueva sala e intentamos jugar, no se va a poder y nos pedirá compartir el número de la sala con otro jugador

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/5.png)

Cuando hayan 2 jugadores en la sala empezará el juego, se informará quien debe dar el primer movimiento.

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/7.png)

Mientras ambos jugadores van jugando se lleva el registro del historial de movimientos.

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/9.png)

Cuando un jugador gané el juego nos informará con un mensaje

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/10.png)

Si queremos volver a un estado anterior solo debemos presionar el estado al cual queremos volver, en este caso vamos a devolvernos una jugada

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/11.png)

Ahora si queremos volver a jugar solo debemos volver al primer estado

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/12.png)


Tambien podemos usar la aplicación usando la siguiente dirección

https://boiling-waters-06268.herokuapp.com/


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/projects/spring-boot) - Framework


## Versioning

El versionamiento se realizó a través de [github](https://github.com/anfegoca/ARSW-lab7.git)

## Authors

* **Andrés González** - [anfegoca](https://github.com/anfegoca)


## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details

## Design

![class](https://github.com/anfegoca/ARSW-lab7/blob/master/resources/7.png)


## JavaDoc

La documentación del proyecto la puede encontrar [aquí](https://github.com/anfegoca/ARSW-lab7/tree/master/site/apidocs)
