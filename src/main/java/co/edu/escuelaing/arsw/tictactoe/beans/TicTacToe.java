package co.edu.escuelaing.arsw.tictactoe.beans;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicTacToe {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TicTacToe.class);

        app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));
        app.run(args);
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080; // returns default port if heroku-port isn't set (i.e. onlocalhost)
    }
}