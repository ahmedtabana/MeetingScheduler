package coen445.server; /**
 * Created by Ahmed on 15-10-25.
 */

import java.io.*;
import java.net.*;

public class Server{

    private static DatagramSocket serverSocket;

    public Server() {

    }


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
        myServer.listen();

    }
}
