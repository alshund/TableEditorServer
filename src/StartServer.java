import server.Server;
import serverController.ServerController;
import serverView.ServerPanel;

/**
 * Created by shund on 16.05.2017.
 */
public class StartServer {
    public static void main(String[] ard){
        Server server = new Server();
        ServerController serverController = new ServerController(server);
        new ServerPanel(serverController);
    }
}
