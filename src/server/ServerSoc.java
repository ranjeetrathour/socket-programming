package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSoc {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter writer;

    public static void main(String[] args) throws IOException {
        ServerSoc serverSoc = new ServerSoc();
        serverSoc.startServer(9093); // server Port
    }

    private void startServer(int port) throws IOException {
        System.out.println("Server is starting...");
        serverSocket = new ServerSocket(port);
        System.out.println("Server is waiting for client connection...");

        socket = serverSocket.accept();
        System.out.println("Client is connected to the server! "+socket.getInetAddress());

        // Fetching data from the client
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String clientData = bufferedReader.readLine();
        System.out.println("Client data received: " + clientData);

        // Sending acknowledgment to the client
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        writer.println("Acknowledgment: Data received successfully!");

        // Closing resources
        bufferedReader.close();
        writer.close();
        socket.close();
        serverSocket.close();
        System.out.println("Server has stopped.");
    }
}
