package server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    public final static Logger LOGGER = Logger.getLogger(ListenerServer.class.getName());

    public static final int PORT = 8081;

    public static void main(String[] args) {
        try {
            ListenerServer server = new ListenerServer();

            FileHandler fileHandler = new FileHandler("server_log.txt", false);
            fileHandler.setFormatter(new SimpleFormatter());
            ListenerServer.LOGGER.addHandler(fileHandler);
            ListenerServer.LOGGER.setLevel(Level.ALL);

            server.start(PORT);
            server.exchangeMessages();
            server.stop();

        } catch (IOException e) {
            ListenerServer.LOGGER.severe(e.getMessage());
        }
    }
}
