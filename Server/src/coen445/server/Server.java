package coen445.server; /**
 * Created by Ahmed on 15-10-25.
 */

import java.io.*;
import java.net.*;

public class Server{

    private static DatagramSocket serverSocket;
    private static DatagramPacket receivePacket;
    private static DatagramPacket sendPacket;

    public Server() {}

    private void setup() throws IOException {
        serverSocket = getDatagramSocket();
    }

    private static DatagramSocket getDatagramSocket() throws IOException {

        System.out.println("Please Configure Server");
        System.out.println("Enter the server port number");

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        int serverPort = Integer.parseInt( inFromUser.readLine() ) ;
        System.out.println("You have entered Server port: " + serverPort);

        // should make this thread safe, not more than one object should use this at one time
        DatagramSocket serverSocket = new DatagramSocket(serverPort);
        System.out.println("Server port is set to: " + serverSocket.getLocalPort());

        return serverSocket;
    }

    private void receive() throws IOException {
        serverSocket.receive(receivePacket);
    }

    private void createReceivePacket(byte[] receiveData) {
        receivePacket = new DatagramPacket(receiveData,receiveData.length);

    }

    private void createSendPacket(byte[] sendData, InetAddress IPAddress, int port) {
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
    }

    public DatagramPacket getReceivePacket() {
        return receivePacket;
    }

    public DatagramPacket getSendPacket() {
        return sendPacket;
    }

    public DatagramSocket getServerSocket(){ return serverSocket;}

    public void send() throws IOException {
        serverSocket.send(sendPacket);

    }


    public static void main(String[] args) throws Exception {

        Server myServer = new Server();
        myServer.setup();

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        myServer.createReceivePacket(receiveData);
        DatagramPacket myReceivePacket = myServer.getReceivePacket();

        while(true){

            myServer.receive();

            Message message = new Message(myReceivePacket).invoke();

            String data = message.getData();
            InetAddress IPAddress = message.getIpAddress();
            int port = message.getPort();

            String capitalizedSentence = data.toUpperCase();
            sendData = capitalizedSentence.getBytes();

            Info myInfo = new Info(receiveData,message.getIpAddress(),message.getPort(),myServer.getServerSocket());

            Thread t = new Thread(myInfo);
            t.start();

           // myServer.createSendPacket(sendData, IPAddress, port);
            //myServer.send();
        }

    }




    public static class Message {
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
}
