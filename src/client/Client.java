package client;



import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Client {
    public static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 8081;

    public static void main(String[] args) {
        try {
            CommunicationClient clientGreeting = new CommunicationClient();

            FileHandler file = new FileHandler("client_log.txt", false);
            file.setFormatter(new SimpleFormatter());
            CommunicationClient.LOGGER.addHandler(file);
            CommunicationClient.LOGGER.setLevel(Level.ALL);

            clientGreeting.startConnection(SERVER_ADDRESS, PORT);
            clientGreeting.exchangesMessage();
            clientGreeting.stopConnection();

        } catch (IOException e) {
            CommunicationClient.LOGGER.severe(e.getMessage());
        }
    }
}
