package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

public class Client {

    //private static final int PORT = 48453;
    private static final int PORT = 18532;

    public static String cpath;
    public static String spath;

    //1 : server
    boolean net = true;
    boolean sserv = false;
    static boolean dserv = false;


    public static void main(String[] args) {
        try {
            // Récupérer le registre
            // Registry reg = LocateRegistry.getRegistry(null);
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 18532);
            Hello stub = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse","127.0.0.1",18532));

            // Recherche dans le registre de l'objet distant
            // Hello stub = (Hello) reg.lookup("Hello");

            // Appel de la méthode distante à l'aide de l'objet obtenu
            stub.showMsg();

            //Upload a file to the server
            byte[] fileData = Files.readAllBytes(Paths.get("myfile.txt"));
            stub.uploadFile(fileData, "Documents/test2/serverfile.txt");

            //Download a file from the server
            byte[] serverFileData = stub.downloadFile("Documents/test/serverfile.txt");
            Files.write(Paths.get("clientfile.txt"), serverFileData);

            //sur le serv : stub
            //local : ok

            //main
            File cfile = new File(cpath);
            File sfile = stub.selectFile(spath);
            //synchro
            //whatsource




        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
    public void sychronize(File a, File b){
        if(whatsource(a,b)==null){
            return;
        }
        else if(whatsource(a,b)==a){
            if(net){
                //b is server and destination
                dserv = true;
            }
            copy(a,b);
            rmdiff(a,b);
        }
        else{
            if(net){
                //b is server and source
                sserv = true;
            }
            copy(b,a);
            rmdiff(b,a);
        }
    }
    public static File whatsource(File a,File b){
        if(a.lastModified() > b.lastModified()){
            return a;
        }
        else if(a.lastModified() < b.lastModified()){
            return b;
        }
        else{ //Files are already synchronized
            return null;
        }

    }

    public static void copy(File ps,File pd){
        for(File f : Objects.requireNonNull(ps.listFiles())){
            File sc = new File(ps,f.getName());
            File dc = new File(pd,f.getName());
            if(f.isDirectory()){
                if(dc.exists()){
                    copy(sc,dc);
                }
                else{
                    if(dserv){
                        stub.createFile(dc);
                    }
                    dc.mkdir();
                    copy(sc,dc);
                }
            }
            else {
                if(dc.exists()) {
                    //check the last modified date
                    if(!(sc.lastModified() == dc.lastModified())){
                        dc.delete();
                        try {
                            Files.copy(sc.toPath(), dc.toPath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else {
                    try {
                        Files.copy(sc.toPath(), dc.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void rmdiff(File ps,File pd){
        for(File f : Objects.requireNonNull(pd.listFiles())){
            File sc = new File(ps,f.getName());
            File dc = new File(pd,f.getName());
            if(f.isDirectory()){
                if(sc.exists()){
                    rmdiff(sc,dc);
                }
                else{
                    deleteDirectory(dc);
                }
            }
            else {
                if(!(sc.exists())) {
                    dc.delete();
                }
            }
        }
    }

    public static Boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
