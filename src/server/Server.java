package server;

import observe.Observable;
import observe.Observer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shund on 16.05.2017.
 */
public class Server implements Runnable, Observable{
    private final int PORT = 9000;

    private ServerSocket serverSocket;
    private List<ClientStream> clientStreamList;
    private List<Observer> observerList;
    private Logger logger = Logger.getLogger(ClientStream.class);

    public Server() {
        clientStreamList = new LinkedList<ClientStream>();
        observerList = new LinkedList<Observer>();
    }

    @Override
    public void addServerPanel(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeServerPanel(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3220);
            while(true) {
                printLog("Server ready for use");
                Socket socket = serverSocket.accept();
                printLog(socket.getInetAddress() + " connected");
                ClientStream clientStream = new ClientStream(this, socket);
                clientStreamList.add(clientStream);
                new Thread(clientStream).start();
            }
        } catch (IOException e) {
            printLog(e.getMessage());
        }
    }

    public void stopServer() {
        try {
            Iterator<ClientStream> clientStreamIterator = clientStreamList.iterator();
            while (clientStreamIterator.hasNext()) {
                clientStreamIterator.next().shutConnection();
            }
            serverSocket.close();
        } catch (IOException e) {
            printLog(e.getMessage());
        }
    }

    public void removeClient(ClientStream clientStream) {
        clientStreamList.remove(clientStream);
    }

    public void printLog(String log) {
        for (Observer observer : observerList) {
            observer.printLog(log + "!\n");
        }
        logger.error(log);
    }
}
