package Server;

import java.io.IOException;

public interface IGameServer {
    void launchServer() throws IOException, InterruptedException;
    void addClients() throws IOException, InterruptedException;
}
