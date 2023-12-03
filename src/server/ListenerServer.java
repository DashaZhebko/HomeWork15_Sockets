package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class ListenerServer {
    public static final Logger LOGGER = Logger.getLogger(ListenerServer.class.getName());
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        LOGGER.info("Server waiting for connection to the port " + port);
        clientSocket = serverSocket.accept();

        LOGGER.info("Client connected: " + clientSocket.getInetAddress());

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
    }

    public void exchangeMessages() throws IOException {

        sendMessage("Hello");
        String clientGreeting = receiveMessage();
        System.out.println("Client send: " + clientGreeting);

        if (clientGreeting.matches(".*[а-яА-Я].*")) {
            sendMessage("Що таке паляниця?");

            String clientAnswer = receiveMessage();

            if (clientAnswer.equalsIgnoreCase("Хліб")) {
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                sendMessage("Current time and date: " + currentTime.format(formatter));
            } else {
                sendMessage("Goodbye");
            }
        } else {
            sendMessage("Goodbye");
        }
    }

    public void stop() throws IOException {
        clientSocket.close();
        LOGGER.info("Client disconnected");
        serverSocket.close();
        LOGGER.info("Server disconnected");
    }

    private String receiveMessage() throws IOException {
        String msg = in.readLine();
        LOGGER.info("Client " + clientSocket.getInetAddress() + " send a message: " + msg);
        return msg;
    }

    private void sendMessage(String message) {
        out.println(message);
        LOGGER.info("Server send a message: " + message);
    }
}
