package Server;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends GameApplication implements Runnable {

    private final int PLAYER_ID;
    private BufferedReader input;
    private PrintWriter output;
    private GameServer gameServer;
    private Socket client;
    private int currentPlayers;
    private final int MAX_PLAYERS;

    public ClientHandler(int playerID, Socket client, BufferedReader input, PrintWriter output, GameServer gameServer, int currentPlayers, int MAX_PLAYERS) {
        this.PLAYER_ID = playerID;
        this.client = client;
        this.input = input;
        this.output = output;
        this.gameServer = gameServer;
        this.currentPlayers = currentPlayers;
        this.MAX_PLAYERS = MAX_PLAYERS;
    }

    @Override
    public void run() {
        System.out.println();
        System.out.println("Player Connected - " + PLAYER_ID);
        output.println("\n\rPlayer Connected - " + PLAYER_ID + "\n\r");
        output.println("Waiting for: " + (MAX_PLAYERS - currentPlayers - 1) + ", player's to join\n\r");

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new Main().start(new Stage());
                } catch (Exception e) {
                }
            }
        });

        System.out.println(client.toString());
    }

    public PrintWriter getOutput() {
        return output;
    }

    public int getPlayerID() {
        return PLAYER_ID;
    }

    public void quit() {

        try {
            client.close();
        } catch (Exception e) {
        }
    }
}
