package serverController;

import server.Server;

/**
 * Created by shund on 18.05.2017.
 */
public class ServerController {
    private Server server;
    private Thread serverThread;

    public ServerController(Server server) {
        this.server = server;
    }

    public void startServer() {
        serverThread = new Thread(server);
        serverThread.start();
    }

    public void stopServer() {
        server.stopServer();
        serverThread.interrupt();
    }

    public Server getServer() {
        return server;
    }
}
