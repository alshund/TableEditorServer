package serverView;

import observe.Observer;
import serverController.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shund on 16.05.2017.
 */
public class ServerPanel implements Observer {
    private JFrame serverPanel;
    private JTextArea logArea;
    private ServerController serverController;

    public ServerPanel(ServerController serverController) {
        this.serverController = serverController;
        this.serverController.getServer().addServerPanel(this);
        startServerPanel();
    }

    @Override
    public void printLog(String string) {
        logArea.append(string);
    }

    public void startServerPanel() {
        serverPanel = new JFrame("Server Panel");
        serverPanel.setLayout(new BorderLayout());
        serverPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverPanel.setSize(300, 400);
        addButton();
        addLogArea();
        serverPanel.setVisible(true);
    }

    private void addButton() {
        JButton startServer = new JButton("Start server");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                printLog("Start server.\n");
                serverController.startServer();
            }
        });
        JButton stopServer = new JButton("Stop server");
        stopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                printLog("Stop server.\n");
                serverController.stopServer();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(startServer);
        panel.add(stopServer);
        serverPanel.add(panel, BorderLayout.NORTH);
    }

    private void addLogArea() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        serverPanel.add(scrollPane, BorderLayout.CENTER);
    }
}
