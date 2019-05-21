package Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class HelloClient {

    private static HelloInterface look_up;

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        look_up = (HelloInterface) Naming.lookup("//localhost:5000/Server");
        String txt = JOptionPane.showInputDialog("What is your name?");

        String response = look_up.sayHello(txt);
        JOptionPane.showMessageDialog(null, response);

    }

}