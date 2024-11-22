package buffer;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

/**
 * Step 1: Define all required variables (e.g., DatagramSocket, DatagramPacket, buffers for sending and receiving data).
 * Step 2: Initialize variables (create socket, prepare server IP and port, and set up buffer for data).
 * Step 3: Add data to a ByteBuffer, convert it to a byte array, and prepare for sending.
 * Step 4: Create and send a DatagramPacket to the server with the prepared byte array.
 * Step 5: Handle server response.
 * Step 6: Clean up resources by closing the socket.
 */
public class UdpClient {

    public static void main(String[] args) {

        // Step 1: Call the UDP client function to send data to the UDP server.
        sendDataToUdpServer("192.168.1.150", 5001, new int[]{14776, 1234567, 8765432});
    }

    /**
     * @param serverAddress The server's IP address (as a String).
     * @param serverPort The server's port number.
     * @param data The integer array to be sent.
     */
    private static void sendDataToUdpServer(String serverAddress, int serverPort, int[] data) {
        DatagramSocket socket = null; // DatagramSocket to send and receive UDP packets
        DatagramPacket packet;        // Packet for sending data to the server (container that holds data)
        byte[] sendDataToClient;      // Byte array to store data to send
        byte[] receiveBuffer;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // Buffer to hold integer data
        InetAddress serverIP;         // Server IP address

        try {
            // Step 2: Initialize the socket
            socket = new DatagramSocket();
            System.out.println("Client is starting..........");

            // Step 3: Add data to the ByteBuffer
            for (int value : data) {
                byteBuffer.putInt(value); // Write each integer to the buffer
            }

            // Step 4: Prepare data to send by converting the ByteBuffer to a byte array
            sendDataToClient = convertByteBufferByteArrayToSendData(byteBuffer);
            System.out.println("Data is converted to bytes: " + sendDataToClient.toString());

            // Step 5: Define server details and create the packet
            serverIP = InetAddress.getByName(serverAddress); // Resolve server's IP address
            packet = new DatagramPacket(sendDataToClient, sendDataToClient.length, serverIP, serverPort); // Create a packet with data

            // Step 6: Send the packet to the server
            socket.send(packet);
            System.out.println("Packet sent to server: " + serverIP + ":" + serverPort);

            // Step 7: Prepare to receive acknowledgment from server
            receiveBuffer = new byte[1024];
            packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(packet); // Receive server's response

            // Step 8: Display the acknowledgment message
            String ackMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Acknowledgment from server: " + ackMessage);

        } catch (SocketException e) {
            // Handle socket creation or configuration issues
            throw new RuntimeException("Socket error: " + e.getMessage(), e);
        } catch (UnknownHostException e) {
            // Handle issues related to resolving server hostname
            throw new RuntimeException("Invalid server IP: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle issues related to I/O operations
            throw new RuntimeException("I/O error: " + e.getMessage(), e);
        } finally {
            // Step 7: Close the socket to release resources
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed.");
            }
        }
    }

    /**
     * Converts the ByteBuffer data to a byte array for sending.
     *
     * @param byteBuffer The ByteBuffer containing the data.
     * @return The byte array with the data ready to be sent.
     */
    private static byte[] convertByteBufferByteArrayToSendData(ByteBuffer byteBuffer){
        byte[] preparedData = new byte[byteBuffer.position()]; // Create a byte array of the correct size
        byteBuffer.flip(); // Switch from write mode to read mode
        byteBuffer.get(preparedData); // Read data from the buffer into the byte array
        return preparedData;
    }
}
