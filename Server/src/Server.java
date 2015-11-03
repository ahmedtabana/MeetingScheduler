/**
 * Created by Ahmed on 15-10-25.
 */
import java.io.*;
import java.net.*;

public class Server {

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

            String data = new String(myReceivePacket.getData(), 0, myReceivePacket.getLength());
            System.out.println("RECEIVED Data: " + data);

            InetAddress IPAddress = myReceivePacket.getAddress();
            System.out.println("RECEIVED Address: " + IPAddress);

            int port = myReceivePacket.getPort();
            System.out.println("RECEIVED Port: " + port);

            String capitalizedSentence = data.toUpperCase();
            sendData = capitalizedSentence.getBytes();

            myServer.createSendPacket(sendData, IPAddress, port);
            myServer.send();
        }

    }


}
