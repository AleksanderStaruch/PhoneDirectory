package RMI_IIOP;

import Phone.PhoneDirectory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private static PhoneDirectory phoneDirectory;
    private static final String NAME="SERVER";
    private static final int PORT=4000;
    private Socket clientSocket;

    private Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        String ClientName=null;
        try {
            log("Client connected: "+getClientInfo(clientSocket));
            log("Stream collecting");

            InputStream sis = clientSocket.getInputStream();
            OutputStream sos = clientSocket.getOutputStream();
            InputStreamReader sisr = new InputStreamReader(sis);
            OutputStreamWriter sosr = new OutputStreamWriter(sos);
            BufferedReader br = new BufferedReader(sisr);
            BufferedWriter bw = new BufferedWriter(sosr);

            ClientName =br.readLine();
            log("Client: "+ClientName);
            bw.write("HELLO "+ClientName);
            bw.newLine();
            bw.flush();

            boolean czyGra =true;
            while(czyGra){
                String command = br.readLine();

                log("Client: "+ClientName+" SENDS: "+command);
                boolean b;
                switch(command){
                    case "GET":
                        command=br.readLine();
                        String s=phoneDirectory.getPhoneNumber(command);

                        bw.write(s);
                        bw.newLine();
                        bw.flush();
                        break;
                    case "ADD":
                        command=br.readLine();
                        b=phoneDirectory.addPhoneNumber(
                                command.split(" ")[0],
                                command.split(" ")[1]
                        );

                        bw.write(""+b);
                        bw.newLine();
                        bw.flush();
                        break;
                    case "REPLACE":
                        command=br.readLine();
                        b=phoneDirectory.replacePhoneNumber(
                                command.split(" ")[0],
                                command.split(" ")[1]
                        );

                        bw.write(""+b);
                        bw.newLine();
                        bw.flush();


                        break;
                    case "BYE":
                        bw.write("BYE BYE");
                        bw.newLine();
                        bw.flush();

                        czyGra=false;
                        break;
                    default:
                        log("ERROR BAD COMMAND!");
                        bw.write("ERROR BAD COMMAND!");
                        bw.newLine();
                        bw.flush();
                        break;
                }
            }
        } catch (Exception e) {
            log("Error handling Client " + ClientName + ": " + e);
        } finally {
            try {
                log("PLAYER clientSocket closing");
                clientSocket.close();
                log("Connection with Client " + ClientName + " closed");
                log("TCP_IP.Client clientSocket closed");
            } catch (IOException e) {
                log("Couldn't close a clientSocket, what's going on?");
            }
        }
    }

    private static void log(String message){
        System.out.println(NAME+": "+message);
        System.out.flush();
    }
    private String getClientInfo(Socket clientSocket){
        String clientIP=clientSocket.getInetAddress().getHostAddress();
        int clientPort = clientSocket.getPort();

        return "[" + clientIP + "]:"+clientPort;
    }

    public static void main(String[] args) throws IOException {
        boolean working=true;
        phoneDirectory= new PhoneDirectory("phoneDirectory.txt");
        log("Start");
        log("Server serverSocket creation");
        ServerSocket serverSocket= new ServerSocket(PORT);
        log("Server serverSocket created");
        log("Server listening");

        try {
            while (working) {
                Thread thread=new Thread(new Server(serverSocket.accept()));
                thread.start();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            log("Service ended");
            log("Server serverSocket closing");
            serverSocket.close();
            log("Server serverSocket closed");
            log("End");
        }
    }

}
