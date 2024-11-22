package buffer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * step 1: define all required variable like DatagramSocket, DatagramPacket, buffers for sending and receiving data.
 * step 2: initialize variables like socket creation and buffer to recieve data from client
 * step 3:  recieve data packet and convert it into byte array and then can do any processing as per user requirement
 * step 4:
 */
public class UdpServer {
    public static void main(String[] args) {
        DatagramSocket socket = null; // DatagramSocket to send and receive UDP packets
        DatagramPacket packet;        // Packet for sending data to the server (container that holds data)
        byte[] receivedDataFromClient;      // Byte array to store data to send
        ByteBuffer byteBuffer; // Buffer to hold integer data
        List<Integer> receivedDataInArray = new ArrayList<>();

        try {
            socket = new DatagramSocket(5001); // Bind socket to port 5001
            System.out.println("Server is started on port 5001...");

            while (true) {
                // Initialize buffer to receive data
                receivedDataFromClient = new byte[1024];
                packet = new DatagramPacket(receivedDataFromClient, receivedDataFromClient.length);

                // Receive data from client
                socket.receive(packet);

                // Wrap the received data into a ByteBuffer
                byteBuffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
                System.out.println("Received integers:");

                // Clear the list before storing new values
                receivedDataInArray.clear();

                // Read integers from ByteBuffer and store them in the list
                while (byteBuffer.remaining() >= Integer.BYTES) {
                    int receivedInt = byteBuffer.getInt();
                    receivedDataInArray.add(receivedInt);
                    System.out.println(receivedInt);
                }

                // Check if the received data contains 1776
                if (receivedDataInArray.contains(1776)) {
                    System.out.println("Can't send response because 1776 was received.");
                    return;
                } else {
                    // Prepare and send ACK response
                    String ackMessage = "ACK: Data received successfully";
                    byte[] ackBytes = ackMessage.getBytes();

                    InetAddress clientAddress = packet.getAddress(); // Get client address
                    int clientPort = packet.getPort();               // Get client port

                    DatagramPacket ackPacket = new DatagramPacket(ackBytes, ackBytes.length, clientAddress, clientPort);
                    socket.send(ackPacket); // Send acknowledgment to the client
                    System.out.println("ACK sent to client.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }

    }
}
