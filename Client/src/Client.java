/**
 * Created by Ahmed on 15-10-25.
 */
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception{

        DatagramSocket clientSocket = getClientSocket();

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        int serverPort = getServerPort(inFromUser);
        InetAddress IPAddress = InetAddress.getByName("localhost");
        sendData = getBytes(inFromUser);

        DatagramPacket sendPacket = getSendPacket(sendData, serverPort, IPAddress);
        DatagramPacket receivePacket = getRecievePacket(receiveData);
        while(true) {


            clientSocket.connect(IPAddress,serverPort);

            clientSocket.send(sendPacket);
            clientSocket.receive(receivePacket);

            String modifiedSentence = new String(receivePacket.getData(), 0 ,receivePacket.getLength());
            System.out.println("FROM SERVER:" + modifiedSentence);
            sendPacket.setData(getBytes(inFromUser));


            clientSocket.disconnect();

        }

    }

    private static DatagramPacket getRecievePacket(byte[] receiveData) {
        return new DatagramPacket(receiveData,receiveData.length);
    }

    private static DatagramPacket getSendPacket(byte[] sendData, int serverPort, InetAddress IPAddress) {
        return new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
    }

    private static byte[] getBytes(BufferedReader inFromUser) throws IOException {
        byte[] sendData;
        System.out.println("Enter the data to be sent to the server:");
        String data = inFromUser.readLine();
        sendData = data.getBytes();
        return sendData;
    }

    private static int getServerPort(BufferedReader inFromUser) throws IOException {
        System.out.println("Please Configure Client");
        System.out.println("Enter the server port number that the client will connect to:");


        int serverPort = Integer.parseInt( inFromUser.readLine() ) ;
        System.out.println("You have entered Server port: " + serverPort);
        return serverPort;
    }

    private static DatagramSocket getClientSocket() throws SocketException {
        return new DatagramSocket();
    }
}
