/**
 * Created by Ahmed on 15-10-25.
 */
import coen445.server.UDPMessage;

import java.io.*;
import java.net.*;

public class Client {


    DatagramSocket socket;

    Client (){

    }


    public void connect(){

        try {

            socket = new DatagramSocket();

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);



//            BufferedReader inFromUser =
//                    new BufferedReader(new InputStreamReader(System.in));

//            int serverPort = getServerPort(inFromUser);
              int serverPort = 9993;

            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
//            sendData = getBytes(inFromUser);
            //DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);

            UDPMessage message = new UDPMessage("Cancel", 100);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(message);
            System.out.println("From Client, creating message object:");
            System.out.println(message.toString());
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, serverPort);


            while(true) {


                socket.connect(IPAddress, serverPort);

                socket.send(sendPacket);
                socket.receive(receivePacket);

//                String modifiedSentence = new String(receivePacket.getData(), 0 ,receivePacket.getLength());
//                System.out.println("FROM SERVER:" + modifiedSentence);
//                sendPacket.setData(getBytes(inFromUser));


                socket.disconnect();

            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{

        Client client = new Client();
        client.connect();

    }





    private  byte[] getBytes(BufferedReader inFromUser) throws IOException {
        byte[] sendData;
        System.out.println("Enter the data to be sent to the server:");
        String data = inFromUser.readLine();
        sendData = data.getBytes();
        return sendData;
    }

    private int getServerPort(BufferedReader inFromUser) throws IOException {
        System.out.println("Please Configure Client");
        System.out.println("Enter the server port number that the client will connect to:");


        int serverPort = Integer.parseInt( inFromUser.readLine() ) ;
        System.out.println("You have entered Server port: " + serverPort);
        return serverPort;
    }
}
