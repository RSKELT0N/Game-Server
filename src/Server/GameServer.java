package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameServer extends GameApplication {

    private Queue<ClientHandler> waitingList = new PriorityQueue<>();
    private int maxPlayers = 2;
    private int currentPlayers = 0;
    private ServerSocket server;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<ClientHandler> clients;
    private int port = 2000;
    private int setID;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    @Override
    public void launchServer() throws IOException, InterruptedException {
        System.out.println("Creating Server...");
        //Initialising clients array
        clients = new ArrayList<>();
        //Initialising server Instance
        server = new ServerSocket(port);
        System.out.println("Server Initialised");

        //Calling addClients method
        addClients();
    }

    @Override
    public void addClients() throws IOException {

        Socket client = null;
        boolean userWaiting = false;
        while (true) {
            if (waitingList.isEmpty() && currentPlayers < maxPlayers) {
                client = server.accept();
            } else {
                client = server.accept();
                userWaiting = true;
            }

                //Checking capacity of server is full
                if (currentPlayers < maxPlayers) {
                    if(!userWaiting) {
                        //setting PlayerID
                        setID = currentPlayers + 1;

                        //Initialising the output Stream
                        output = new PrintWriter(client.getOutputStream(), true);
                        //Initialising the output Stream
                        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        //Creating a new thread for the client
                        ClientHandler newClient = new ClientHandler(setID, client, input, output, this, currentPlayers, maxPlayers);
                        new Thread(newClient).start();
                        clients.add(newClient);
                        currentPlayers++;
                    } else
                        new Thread(waitingList.poll()).start();
                } else {
                    //setting PlayerID
                    setID = currentPlayers + 1;
                    //Initialising the output Stream
                    output = new PrintWriter(client.getOutputStream(), true);
                    //Initialising the output Stream
                    input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //Creating a new thread for the client
                    ClientHandler newClient = new ClientHandler(setID, client, input, output, this, currentPlayers, maxPlayers);
                    waitingList.add(newClient);
                    currentPlayers++;
                    waitingList.peek().getOutput().println("You are in a queue\n\rPosition: "+(currentPlayers-maxPlayers)+"\n\r");
                    while (currentPlayers < maxPlayers) {
                        break;
                    }
                }
        }

    }

    public ArrayList<ClientHandler> collectClient() {
        return clients;
    }

    public void setMaxPlayers(int i) {
        if (i > 5) throw new IllegalArgumentException("Cant create a server with a max Player quantity higher than 4");
        this.maxPlayers = i;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public void setUp(GameServer gameServer) throws IOException, InterruptedException {

    }

    public void leave(int playerID,ClientHandler player) {
        synchronized (collectClient()) {
            for(int i = 0; i < clients.size(); i++) {
                if(clients.get(i).getPlayerID() == playerID)
                    clients.get(i).quit();
                currentPlayers--;
            }
        }
    }
}
