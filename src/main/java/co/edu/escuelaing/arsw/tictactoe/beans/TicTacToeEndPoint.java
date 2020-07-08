package co.edu.escuelaing.arsw.tictactoe.beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.escuelaing.arsw.tictactoe.entities.Jugador;
import co.edu.escuelaing.arsw.tictactoe.entities.Sala;
//import co.edu.escuelaing.arsw.tictactoe.repository.SalaRepository;

@Component
@ServerEndpoint("/bbService")
public class TicTacToeEndPoint {

    private static final Logger logger = Logger.getLogger(TicTacToeEndPoint.class.getName());
    
    //@Autowired
    //private SalaRepository repository;
    
    

    

    static Hashtable<Integer, Sala> salas = new Hashtable<>();

    @OnMessage
    public void processPoint(String message, Session session) {
        String[] msg = message.split(" ");
        //System.out.println(msg[0] + "Sala" + msg[1] + "NOmbre: " + msg[2] + ". From session: " + session);
        
        if ("Sala".equals(msg[0])) {
            Integer num = Integer.parseInt(msg[1]);
            //System.out.println("REPOS "+repository);
            //Optional<Sala> cons = repository.findById(num);
            //Sala sala = cons.get();
            if(!salas.containsKey(num)){
            //if (sala==null) {
                Sala nueva = new Sala(num,new Jugador(msg[2],session,"X"));
                //repository.save(nueva);
                salas.put(num, nueva);

            } else {
                Sala sala = salas.get(num);
                if (sala.isFull()) {
                    try {
                        session.getBasicRemote().sendText("est/ La sala está llena");
                    } catch (IOException e) {
                        logger.log(Level.INFO, e.toString());
                    }
                } else {
                    
                    sala.agregarJugador(msg[2], session);
                }
            }
        } else if ("Mov".equals(msg[0])) {

            Integer num = Integer.parseInt(msg[1]);
            Sala sala = salas.get(num);
            //Optional<Sala> cons = repository.findById(num);
            //Sala sala = cons.get();
            Integer mov = Integer.parseInt(msg[3]);
            
            if (!sala.isFull()) {
                try {
                    session.getBasicRemote().sendText("est/ invite a otro jugador a la sala para comenzar");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {

                if (sala.getActual().getNombre().equals(msg[2])) {
                    sala.move(mov);
                } else {
                    try {
                        session.getBasicRemote().sendText("est/ No es su turno");
                    } catch (IOException e) {
                        logger.log(Level.INFO, e.toString());
                    }
                }
            }

        }else if("His".equals(msg[0])){
            Integer num = Integer.parseInt(msg[1]);
            //Optional<Sala> sala= repository.findById(num);
            Sala sala = salas.get(num);
            Integer estado = Integer.parseInt(msg[3]);
            //sala.get().devolver(estado);
            sala.devolver(estado);
        }

    }

    @OnOpen
    public void openConnection(Session session) {
        logger.log(Level.INFO, "Connection opened.");
        try {
            session.getBasicRemote().sendText("Connection established.");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        //List<Sala> salas = repository.findAll();
        for(Sala s: salas.values()){
            s.sacarJugador(session);
        }
        logger.log(Level.INFO, "Connection closed.");
    }

    @OnError
    public void error(Session session, Throwable t) {
        logger.log(Level.INFO, t.toString());
        t.printStackTrace();
        logger.log(Level.INFO, "Connection error.");
    }
}