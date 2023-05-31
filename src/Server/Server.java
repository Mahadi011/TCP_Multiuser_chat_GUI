package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private Socket c_socket; // Socket to communicate with a client
    String client_name; // Name of the client

    static List<ObjectOutputStream> output_list = new ArrayList<>(); // List of output streams to send messages to all clients

    public Server(Socket c_socket) {
        this.c_socket = c_socket;
    }

    public void run() {
        try (
                ObjectOutputStream client_write = new ObjectOutputStream(c_socket.getOutputStream());
                ObjectInputStream client_read = new ObjectInputStream(c_socket.getInputStream())
        ) {
            output_list.add(client_write); // Add the client's output stream to the list

            Object in_data;
            Boolean flag = false;

            while ((in_data = client_read.readObject()) != null) {
                if (!flag) {
                    flag = true;
                    client_name = (String) in_data;
                    send_message((String) in_data + " has logged in."); // Send a message notifying about the client's login
                } else {
                    send_message(client_name + ": " + in_data); // Send a message from the client
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void send_message(String in_data) throws IOException {
        for (ObjectOutputStream out : output_list) {
            out.writeObject(in_data); // Send the message to all connected clients
        }
    }
}






