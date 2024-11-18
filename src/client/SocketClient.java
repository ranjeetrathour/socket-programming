package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * In this socket programming example, we are using the TCP protocol.
 * We can send only stream data to the server and receive stream data from the server.
 */
public class SocketClient {
    private Socket socket = null;
    private OutputStreamWriter writer = null;
    private PrintWriter printWriter = null;

    public static void main(String[] args) throws Exception {
        String serverIP = "192.168.43.79"; // Server's IP address where we want to connect
        Integer serverPort = 9093; // Port number on the server where the server is listening
        String dataSendToServer = "I am client data"; // The data we want to send to the server
        SocketClient socketClient = new SocketClient();
        socketClient.sendClientRequestToServer(serverIP, serverPort, dataSendToServer);
    }

    private void sendClientRequestToServer(String serverIP, Integer serverPort, String dataSendToServer) throws Exception {
        // Step 1: Establish a connection with the server by providing server IP and port
        socket = new Socket(serverIP, serverPort);
        System.out.println("Client is connected to the server");

        // Step 2: Convert the socket's output stream into a writer for sending text data
        //  socket output streams are byte-based, but we need a character stream for sending text
        writer = new OutputStreamWriter(socket.getOutputStream()); // Convert the byte OutputStream into a Writer

        // Step 3: Wrap the OutputStreamWriter with PrintWriter to format the data before sending
        // PrintWriter is used to send formatted data like lines of text, handling auto-flushing
        printWriter = new PrintWriter(socket.getOutputStream()); // Create a PrintWriter from the socket's output stream

        // Step 4: Send the data to the server
        printWriter.println(dataSendToServer);
        printWriter.flush();

        System.out.println("Data sent to the server: " + dataSendToServer); // Confirm that the data has been sent

        //Step 5: Accept ack which is return by server
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String serverResponse = bufferedReader.readLine();
        System.out.println("server send ack "+serverResponse);
        socket.close();
        System.out.println("Client connection closed");
    }
}
