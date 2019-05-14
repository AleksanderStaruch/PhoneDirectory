package Test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class HelloServer extends UnicastRemoteObject implements HelloInterface{

    private static final long serialVersionUID = 1L;

    protected HelloServer() throws RemoteException {
        super();
    }

    public String sayHello(String name) throws RemoteException{
        System.err.println(name + " is trying to contact!");
        return "Server says hello to " + name;
    }

    public static void main(String[] args){
        try {
            Naming.rebind("rmi://localhost:1099/Server", new HelloServer());
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}