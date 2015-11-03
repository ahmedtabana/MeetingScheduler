/**
 * Created by Ahmed on 15-10-25.
 */
import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws Exception {


        DatagramSocket serverSocket = getDatagramSocket();

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while(true){

            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            serverSocket.receive(receivePacket);


            String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RECEIVED Data: " + data);

            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("RECEIVED Address: " + IPAddress);

            int port = receivePacket.getPort();
            System.out.println("RECEIVED Port: " + port);


            String capitalizedSentence = data.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);


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
}
