package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-03.
 */
public class Info implements Runnable {

    byte[] sendData;
    InetAddress IPAddress;
    int port;

    DatagramSocket socket;


    Info(byte[] sendData, InetAddress IPAddress, int port, DatagramSocket socket){

        this.sendData = sendData;
        this.IPAddress = IPAddress;
        this.port = port;
        this.socket = socket;
    }
    @Override
    public void run() {

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
