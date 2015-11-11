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
        serverPort = getServerPortFromUser(serverPort);

        InetAddress serverIPAddress = null;
        serverIPAddress = getServerInetAddress(serverIPAddress);

        // should make this thread safe, not more than one object should use this at one time
        createServerSocket(serverPort, serverIPAddress);


    }

    private void createServerSocket(int serverPort, InetAddress serverIPAddress) {
        try {
            serverSocket = new DatagramSocket(serverPort,serverIPAddress);
            System.out.println("Server setup was successful");


        } catch (SocketException e) {
            System.out.println("Could not create server socket");
            e.printStackTrace();
        }
    }

    private InetAddress getServerInetAddress(InetAddress serverIPAddress) {
        try {
            serverIPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.out.println("Server IP address is not known");
            e.printStackTrace();
        }
        return serverIPAddress;
    }

    private int getServerPortFromUser(int serverPort) {
        String userInput;
        while (serverPort == 0 || serverPort < 1024) {

            System.out.println("Please enter a number greater than 1024");

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                userInput = bufferedReader.readLine();
                serverPort = Integer.parseInt(userInput);
            } catch (NumberFormatException ex) {
                System.out.println("Thi is not a number");
            } catch (IOException e) {


                e.printStackTrace();
            }


        }
        return serverPort;
    }

    private void displayServerInfo() {

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

            Info myInfo = new Info(sendData,IPAddress,port, serverSocket);

            Thread t = new Thread(myInfo);
            t.start();


        }

    }

    public static void main(String[] args) throws Exception {

        Server myServer = new Server();

        myServer.setup();
        myServer.displayServerInfo();
        myServer.listen();

    }

}
