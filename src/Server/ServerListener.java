package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Port number to listen for client connections

        while (true) { // Continuous loop to accept client connections
            try (ServerSocket s_socket = new ServerSocket(port)) {
                Socket c_socket = s_socket.accept(); // Accept a client connection
                Server client_handle = new Server(c_socket); // Create a new Server thread to handle the client
                client_handle.start(); // Start the Server thread to handle client communication
            }
        }
    }
}