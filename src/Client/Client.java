package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    int port = 12345; // Port number to connect to the server
    String ip = "localhost"; // IP address of the server
    String in_data; // Variable to store incoming data from the server
    ObjectOutputStream server_write; // Output stream to write data to the server

    public Client() throws IOException, ClassNotFoundException {
        try (
                Socket client_socket = new Socket(ip, port); // Create a socket connection to the server
                ObjectOutputStream server_write = new ObjectOutputStream(client_socket.getOutputStream());
                ObjectInputStream server_read = new ObjectInputStream(client_socket.getInputStream())
        ) {
            this.server_write = server_write;

            Display dp = new Display(server_write); // Create a display object to interact with the user
            server_write.writeObject(dp.c_name); // Write the client's name to the server

            while ((in_data = (String) server_read.readObject()) != null) {
                dp.area.append(in_data + "\n"); // Append the incoming data to the display's text area
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client c = new Client(); // Create a client object to start the client application
    }
}
