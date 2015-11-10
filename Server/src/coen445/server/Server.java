package coen445.server; /**
 * Created by Ahmed on 15-10-25.
 */

import java.io.*;
import java.net.*;

public class Server{

    private static DatagramSocket serverSocket;

    public Server() {

    }


    private void setup()  {

        System.out.println("Please Configure Server");
        System.out.println("Enter the server port number");

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        int serverPort = 0;
        try {
            serverPort = Integer.parseInt( inFromUser.readLine() );

        } catch (IOException e) {

                System.out.println("Could not get server port number. Server port should be an integer");
                e.printStackTrace();
            }

        System.out.println("You have entered Server port: " + serverPort);
        
        InetAddress serverIPAddress = null;

        try {
            serverIPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.out.println("Server IP address is not known");
            e.printStackTrace();
        }
        // should make this thread safe, not more than one object should use this at one time
        try {
            serverSocket = new DatagramSocket(serverPort,serverIPAddress);

        } catch (SocketException e) {
            System.out.println("Could not create server socket");
            e.printStackTrace();
        }

        System.out.println("Server setup was successful");

    }

    private void dispalyServerInfo() {
        System.out.println("Server Port is set to: " + serverSocket.getLocalPort());
        System.out.println("Server Ip is set to: " + serverSocket.getLocalAddress());
    }

    public void listen(){

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

        byte[] sendData = new byte[1024];


        while(true){

            try {

                serverSocket.receive(receivePacket);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RECEIVED Data: " + data);

            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("RECEIVED Address: " + IPAddress);

            int port = receivePacket.getPort();
            System.out.println("RECEIVED Port: " + port);

            ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
            try {

                ObjectInputStream is = new ObjectInputStream(in);
                UDPMessage message = (UDPMessage) is.readObject();

                System.out.println("UDPMessage object received = "+ message);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            String capitalizedSentence = data.toUpperCase();
            sendData = capitalizedSentence.getBytes();

            Info myInfo = new Info(sendData,IPAddress,port, serverSocket);

            Thread t = new Thread(myInfo);
            t.start();


        }

    }

    public static void main(String[] args) throws Exception {

        Server myServer = new Server();
        myServer.setup();
        myServer.dispalyServerInfo();
        myServer.listen();

    }

}
