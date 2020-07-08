package co.edu.escuelaing.arsw.tictactoe.entities;

import java.io.IOException;
import java.util.ArrayList;


import javax.websocket.Session;


//import org.springframework.data.annotation.Id;

public class Sala {
    //@Id
    private int id;
    private Jugador jugador1;
    private Jugador jugador2;
    private String[] tablero;
    private Jugador actual;
    private Jugador ganador;
    private String estado;
    private ArrayList<String> historial;
    /*
    public Sala(){

    }
    */
    public Sala(int id,Jugador jugador) {
        this.id=id;
        this.jugador1 = jugador;
        this.actual=jugador;
        this.jugador2 = null;
        this.ganador = null;
        this.tablero = new String[9];
        this.historial=new ArrayList<>();
    }
    /**
     * Agregar un nuevo jugador a la sala
     * @param nombre nombre del jugador
     * @param sesion Session del jugador
     */
    public void agregarJugador(String nombre, Session sesion) {
        System.out.println("ANTES"+tablero.length);
        if (jugador1 == null) {
            System.out.println("JOHANN ES RE GURRERO");
            Jugador jugador = new Jugador(nombre, sesion,"X");
            jugador1 = jugador;
        } else if (jugador2 == null) {
            Jugador jugador = new Jugador(nombre, sesion,"O");
            jugador2 = jugador;
        }
        enviarMovimiento();
        actualizarEstado();

    }
    /**
     * Elimina al jugador de la sala
     * @param sesion sesion del jugador
     */
    public void sacarJugador(Session sesion){
        if(sesion.equals(jugador1.getSesion())){
            System.out.println("P1");
            jugador1=null;
        }else if (sesion.equals(jugador2.getSesion())){
            jugador2=null;
            System.out.println("P1");
        }
    }
    /**
     * Realiza un movimiento para el jugador actual
     * @param mov int posición del tablero donde se realizará el movimiento
     */
    public void move(int mov) {
        tablero[mov]=actual.getSimbolo();
        enviarMovimiento();
        ganador = calGanador();
        if(actual.getSesion().equals(jugador1.getSesion())){
            actual=jugador2;
        }else{
            actual=jugador1;
        }
        actualizarEstado();

    }
    /**
     * Envia el movimiento para que el cliente actualice el tablero
     */
    public void enviarMovimiento(){
        String res = "";
        for(int i=0; i<tablero.length-1;i++){
            res+=tablero[i]+",";
        }
        res+=tablero[8];
        //System.out.println("ADD ");
        //System.out.println(res);
        historial.add(res);
        try {
            jugador1.getSesion().getBasicRemote().sendText("mov/"+ res);
            jugador2.getSesion().getBasicRemote().sendText("mov/"+ res);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Devuelve el tablero al estado anterior dado
     * @param estado int con el estado anterior 
     */
    public void devolver(int estado){
        //System.out.println("HOLA "+estado);
        //printArray(historial.get(estado));
        String res = historial.get(estado);
        //System.out.println(res);
        tablero=res.split(",");
        ArrayList<String> nuevo = new ArrayList<>();
        for(int i=0;i<estado+1;i++){
            nuevo.add(historial.get(i));
        }
        historial=nuevo;
        enviarMovimiento();
    }

    /**
     * Actualiza el estado de la sala
     */
    public void actualizarEstado() {
        if (ganador == null) {
            estado = "est/Turno de: " + actual.getNombre();
        } else {
            estado = "est/El ganador es: " + ganador.getNombre();
        }
        try {
            if(jugador1!=null){
                jugador1.getSesion().getBasicRemote().sendText(estado);
            }
            if(jugador2!=null){
                jugador2.getSesion().getBasicRemote().sendText(estado);
            }
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Dice si la sala está llena
     * @return boolean
     */
    public boolean isFull() {
        return (jugador1 != null && jugador2 != null);
    }
    /**
     * Calcula al ganador del juego
     * @return Jugador que gano
     */
    private Jugador calGanador() {
        Jugador gano = null;
        int[][] posibles = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
                { 2, 4, 6 }, };
        for (int i = 0; i < posibles.length; i++) {
            int a = posibles[i][0];
            int b = posibles[i][1];
            int c = posibles[i][2];

            if ((tablero[a] != null) && (tablero[b] != null) && (tablero[c] != null) && tablero[a].equals(tablero[b]) && tablero[b].equals(tablero[c])) {

                if (tablero[a].equals("X")) {
                    gano = jugador1;
                } else {
                    gano = jugador2;
                }
                return gano;
            }
        }
        return gano;

    }

    public Jugador getActual() {
        return actual;
    }

    public void setActual(Jugador actual) {
        this.actual = actual;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public String[] getTablero() {
        return tablero;
    }

    public void setTablero(String[] tablero) {
        this.tablero = tablero;
    }

    public ArrayList<String> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<String> historial) {
        this.historial = historial;
    }
    

}