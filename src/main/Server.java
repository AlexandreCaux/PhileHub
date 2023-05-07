package main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{

    public static void main(String args[]) {
        try {
            System.setProperty("java.security.policy", "server.policy");
            Registry registry = LocateRegistry.createRegistry(18531);
            ImpClasse service = new ImpClasse();
            Naming.rebind(String.format("rmi://%s:%d/ImpClasse","127.0.0.1",18531), service);
            System.out.println("Le Serveur est prêt...");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}