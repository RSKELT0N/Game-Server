package Server;

import java.io.IOException;

public class Controller {

    private GameServer gameServer;

    public Controller(int port, int maxPlayers) throws IOException, InterruptedException {
        gameServer = new GameServer(port, maxPlayers);
        gameServer.setPort(3000);
        gameServer.setMaxPlayers(2);
        gameServer.launchServer();
    }
}

