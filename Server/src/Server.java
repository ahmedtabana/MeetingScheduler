/**
 * Created by Ahmed on 15-10-25.
 */
import java.io.*;
import java.net.*;

public class Server {

    private static DatagramSocket serverSocket;
    private static DatagramPacket receivePacket;
    private static DatagramPacket sendPacket;

    public Server() {
        try {
            serverSocket = getDatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public static void main(String[] args) throws Exception {

        Server myServer = new Server();
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while(true){

            myServer.createRecievePacket(receiveData);
            myServer.receive();


            String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RECEIVED Data: " + data);

            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("RECEIVED Address: " + IPAddress);

            int port = receivePacket.getPort();
            System.out.println("RECEIVED Port: " + port);


            String capitalizedSentence = data.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);


        }

    }

    private void receive() {
        try {
            serverSocket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createRecievePacket(byte[] receiveData) {
        receivePacket = new DatagramPacket(receiveData,receiveData.length);

    }


}
