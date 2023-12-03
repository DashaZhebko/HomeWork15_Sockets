package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class CommunicationClient {
    public static final Logger LOGGER = Logger.getLogger(CommunicationClient.class.getName());
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader consoleReader;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void exchangesMessage() throws IOException {
        while (clientSocket.isConnected()) {
            receiveMessage();
            sendMessage();
        }
    }

    public void stopConnection() throws IOException {
        clientSocket.close();
        LOGGER.info("Client disconnected");
    }

    private void sendMessage() throws IOException {
        System.out.println("Your answer: ");
        String textMessage = consoleReader.readLine();
        out.println(textMessage);
        LOGGER.info("Client answer " + textMessage);
    }

    private void receiveMessage() throws IOException {
        LOGGER.info("Server: " + in.readLine());
    }


}
