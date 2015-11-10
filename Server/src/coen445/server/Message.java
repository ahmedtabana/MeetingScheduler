package coen445.server;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-09.
 */

public class Message implements Serializable{

    private DatagramPacket myReceivePacket;
    private String data;
    private InetAddress ipAddress;
    private int port;

    public Message(DatagramPacket myReceivePacket) {
        this.myReceivePacket = myReceivePacket;
    }

    public String getData() {
        return data;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public Message invoke() {
        data = new String(myReceivePacket.getData(), 0, myReceivePacket.getLength());
        System.out.println("RECEIVED Data: " + data);

        ipAddress = myReceivePacket.getAddress();
        System.out.println("RECEIVED Address: " + ipAddress);

        port = myReceivePacket.getPort();
        System.out.println("RECEIVED Port: " + port);
        return this;
    }
}
