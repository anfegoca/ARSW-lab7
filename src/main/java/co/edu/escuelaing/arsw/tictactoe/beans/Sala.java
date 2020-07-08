package co.edu.escuelaing.arsw.tictactoe.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.websocket.Session;

public class Sala {

    private Jugador jugador1;
    private Jugador jugador2;
    private String[] tablero;
    private Jugador actual;
    private Jugador ganador;
    private String estado;
    private ArrayList<String> historial;

    public Sala(Jugador jugador) {
        this.jugador1 = jugador;
        this.actual=jugador;
        this.jugador2 = null;
        this.ganador = null;
        this.tablero = new String[9];
        this.historial=new ArrayList<>();
    }

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
    public void sacarJugador(Session sesion){
        if(sesion.equals(jugador1.getSesion())){
            System.out.println("P1");
            jugador1=null;
        }else if (sesion.equals(jugador2.getSesion())){
            jugador2=null;
            System.out.println("P1");
        }
    }

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
    public void enviarMovimiento(){
        String res = "";
        for(int i=0; i<tablero.length-1;i++){
            res+=tablero[i]+",";
        }
        res+=tablero[8];
        System.out.println("ADD ");
        System.out.println(res);
        historial.add(res);
        try {
            jugador1.getSesion().getBasicRemote().sendText("mov/"+ res);
            jugador2.getSesion().getBasicRemote().sendText("mov/"+ res);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void devolver(int estado){
        System.out.println("HOLA "+estado);
        //printArray(historial.get(estado));
        String res = historial.get(estado);
        System.out.println(res);
        tablero=res.split(",");
        ArrayList<String> nuevo = new ArrayList<>();
        for(int i=0;i<estado+1;i++){
            nuevo.add(historial.get(i));
        }
        historial=nuevo;
        enviarMovimiento();
    }
    public void printArray(String[] array){
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
    }

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

    public boolean isFull() {
        return (jugador1 != null && jugador2 != null);
    }

    public Jugador calGanador() {
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

}