package main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

public class FileManager extends Thread{
    public File c;
    public File s;
    boolean net;
    static boolean sserv = false;
    static boolean dserv = false;
    static Hello stub;
    public FileManager(String a, String b, boolean net){
        this.c = new File(a);
        if(net){
            this.s = stub.selectFile(b);
        }
        else{
            this.s = new File(b);
        }
        this.net = net;
    }

    public void run(){
        while(true){
            if(net){
                try {
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 18532);
                    stub = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse","127.0.0.1",18532));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            sychronize(this.c,this.s);
            try {
                Thread.sleep(60000);
            }
            catch (InterruptedException e) {
                System.err.println(e.toString());
                e.printStackTrace();
            }
        }
    }

    /*
    public static void main(String[] args){
        String a = "C:\\Users\\alexa\\OneDrive\\Travail\\1_Info\\1.12 Java\\Projet\\TestsLocal\\one";
        String b = "C:\\Users\\alexa\\OneDrive\\Travail\\1_Info\\1.12 Java\\Projet\\TestsLocal\\two";
        new FileManager(a,b).start();
    }
     */

    public static void copy(File ps,File pd) {
        File[] lf;
        if(sserv){ //la source est le serveur
            lf = stub.listFiles(ps);
        } else {
            lf = ps.listFiles();
        }
            for (File f : lf){
                File sc;
                File dc;
                if(sserv) {
                    sc = stub.selectFilewParent(ps,f.getName());
                    dc = new File(pd, f.getName());
                }
                else if(dserv) { //la destination est le serveur
                    sc= new File(ps, f.getName());
                    dc = stub.selectFilewParent(pd,f.getName());
                }
                else {
                    sc= new File(ps, f.getName());
                    dc = new File(pd, f.getName());
                }

                if (f.isDirectory()) {
                    if (dc.exists()) {
                        copy(sc, dc);
                    } else {
                        dc.mkdir();
                        copy(sc, dc);
                    }
                }
                else {
                    if (dc.exists()) {
                        //check the last modified date
                        if (!(sc.lastModified() == dc.lastModified())) {
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

    public static Boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
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

    public void sychronize(File c, File s){
        if(whatsource(c,s)==null){
            return;
        }
        else if(whatsource(c,s)==c){
            if(net){
                dserv = true;
            }
            copy(c,s);
            rmdiff(c,s);
        }
        else{
            if(net){
                sserv = true;
            }
            copy(s,c);
            rmdiff(s,c);
        }
    }
}
