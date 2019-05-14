package RMI_IIOP;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends JFrame {

    boolean close=true;
    public Client(String name)  {
        setSize(500,300);
        setLayout(new BorderLayout());

        JPanel upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(1,2));
        this.add(upPanel,BorderLayout.PAGE_START);

        JTextArea text=new JTextArea();text.setBackground(Color.LIGHT_GRAY);upPanel.add(text);
        JButton send =new JButton("SEND");upPanel.add(send);
        JTextArea server = new JTextArea();server.setBackground(Color.GRAY);this.add(server,BorderLayout.CENTER);


        Socket socket;
        try {
            final String serverName="localhost";
            InetAddress serverIP = InetAddress.getByName(serverName);
            socket= new Socket(serverIP,4000);

            System.out.println("Client: " + name);

            InputStream sisR = socket.getInputStream();
            OutputStream sosR = socket.getOutputStream();
            InputStreamReader sisrR = new InputStreamReader(sisR);
            OutputStreamWriter sosrR = new OutputStreamWriter(sosR);
            BufferedReader brR = new BufferedReader(sisrR);
            BufferedWriter bwR = new BufferedWriter(sosrR);

            bwR.write(name);
            bwR.newLine();
            bwR.flush();
            server.append("Server: "+brR.readLine()+"\n\r");

            send.addActionListener(e->{
                String com=text.getText().split(" ")[0];
                String arg1;
                String arg2;
                if(close){
                    try{
                        switch(com){
                            case "GET":
                                bwR.write("GET");
                                bwR.newLine();
                                bwR.flush();
                                arg1=text.getText().split(" ")[1];
                                bwR.write(arg1);
                                bwR.newLine();
                                bwR.flush();

                                server.append("Server: "+brR.readLine()+"\n\r");
                                break;
                            case "ADD":
                                bwR.write("ADD");
                                bwR.newLine();
                                bwR.flush();
                                arg1=text.getText().split(" ")[1];
                                arg2=text.getText().split(" ")[2];
                                bwR.write(arg1+" "+arg2);
                                bwR.newLine();
                                bwR.flush();

                                server.append("Server: "+brR.readLine()+"\n\r");
                                break;
                            case "REPLACE":
                                bwR.write("REPLACE");
                                bwR.newLine();
                                bwR.flush();
                                arg1=text.getText().split(" ")[1];
                                arg2=text.getText().split(" ")[2];
                                bwR.write(arg1+" "+arg2);
                                bwR.newLine();
                                bwR.flush();

                                server.append("Server: "+brR.readLine()+"\n\r");
                                break;
                            case "BYE":
                                bwR.write("BYE");
                                bwR.newLine();
                                bwR.flush();

                                server.append("Server: "+brR.readLine()+"\n\r");
                                close=false;
                                break;
                            default:
                                bwR.write("ERROR BAD COMMAND");
                                bwR.newLine();
                                bwR.flush();
                                break;
                        }
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
                text.setText("");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setTitle("Client");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args){
        new Client("Olek");
    }
}
