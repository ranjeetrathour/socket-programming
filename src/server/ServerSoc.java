package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSoc {
    private ServerSocket serverSocket;  // To listen for incoming client connections
    private Socket socket;  // To represent the connection with the client
    private BufferedReader bufferedReader;  // To read data from the client
    private PrintWriter writer;  // To send data to the client

    public static void main(String[] args) throws IOException {
        ServerSoc serverSoc = new ServerSoc();  // Create an instance of ServerSoc
        serverSoc.startServer(9093);  // Start the server on port 9093
    }

    private void startServer(int port) throws IOException {
        System.out.println("Server is starting...");
        serverSocket = new ServerSocket(port);  // Create a ServerSocket to listen on the specified port
        System.out.println("Server is waiting for client connection...");

        socket = serverSocket.accept();  // Wait for a client to connect
        System.out.println("Client is connected to the server! " + socket.getInetAddress());  // Print the IP address of the client

        // Fetching data from the client
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Wrap the client's input stream in a BufferedReader
        String clientData = bufferedReader.readLine();  // Read data sent by the client
        System.out.println("Client data received: " + clientData);  // Print the data received from the client

        // Sending acknowledgment to the client
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);  // Wrap the socket's output stream in a PrintWriter
        writer.println("Acknowledgment: Data received successfully!");  // Send acknowledgment to the client

        // Closing resources
        socket.close();
        serverSocket.close();

        System.out.println("Server has stopped.");
    }
}
