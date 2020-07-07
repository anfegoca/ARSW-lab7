package co.edu.escuelaing.arsw.tictactoe.beans;

import javax.websocket.Session;

public class Jugador {
    
    private String nombre;
    private Session sesion;
    private String simbolo;

    public Jugador(String nombre, Session sesion, String simbolo){
        this.nombre=nombre;
        this.sesion=sesion;
        this.simbolo=simbolo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Session getSesion() {
        return sesion;
    }

    public void setSesion(Session sesion) {
        this.sesion = sesion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    
}