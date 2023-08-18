package com.icbc.digitalhuman.udp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.*;
public class UdpClient {
    private String pythonServerIP = "127.0.0.1";  // Python服务器的IP地址
    private int pythonServerPort = 12345;         // Python服务器监听的端口号

    public String sendMessageAndGetResponse(String messageToSend) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress pythonServerAddress = InetAddress.getByName(pythonServerIP);

            byte[] sendData = messageToSend.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, pythonServerAddress, pythonServerPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            socket.close();

            return receivedMessage;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}