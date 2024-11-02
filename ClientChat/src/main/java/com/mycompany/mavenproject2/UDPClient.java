package com.mycompany.mavenproject2;

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.logging.*;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress remoteAddress;
    private int remotePort;
    private boolean running;
    private final byte[] R_buffer = new byte[1024];
    
    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private MessageListener listener;

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    public UDPClient() {
        running = false;
    }

     public void start(int localPort) throws SocketException {
    openLocalSocket(localPort);  // Bind to the local port
    // Start a thread to listen for incoming messages
    new Thread(() -> {
        while (running) {  // Check if the client is still running
            try {
                DatagramPacket packet = new DatagramPacket(R_buffer, R_buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                if (listener != null) {
                    listener.onMessageReceived(message);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
    }).start();
}

    
    public void updateConnection(String remoteIp, int remotePort) throws UnknownHostException {
        this.remoteAddress = InetAddress.getByName(remoteIp);
        this.remotePort = remotePort;
    }

    public void openLocalSocket(int localPort) throws SocketException {
        socket = new DatagramSocket(localPort);
        running = true;
    }

    public void sendMessage(String message) throws IOException {
        if (socket == null || remoteAddress == null) {
            throw new IllegalStateException("Socket or remote address not initialized.");
        }

        byte[] S_buffer = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(S_buffer, S_buffer.length, remoteAddress, remotePort);
        socket.send(sendPacket);
    }
    
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}

