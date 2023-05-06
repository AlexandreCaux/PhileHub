package main;

import java.io.*;
import java.nio.file.Files;
import java.rmi.Naming;
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

    public static void copyfilesrmi(File source,File target){
        if(sserv){
            //retrieve file data
            //put it in the right place
            try {
                byte[] data = stub.downloadFile(source.toString());
                try (FileOutputStream outputStream = new FileOutputStream(target.toString())) {
                    outputStream.write(data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else{ //the destination is the server
            //send the data
            try {
                byte fileData[] = new byte[(int) source.length()];
                FileInputStream inputStream = new FileInputStream(source);
                inputStream.read(fileData);
                inputStream.close();
                stub.uploadFile(fileData,target.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void copy(File ps,File pd) {
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
                        if(dserv){
                            stub.createdir(dc);
                        }
                        else{
                            dc.mkdir();
                        }
                        copy(sc, dc);
                    }
                }
                else {
                    boolean ex = false;
                    long sclm=0;
                    long dclm=0;
                    if(sserv){
                        dclm=dc.lastModified();
                        sclm=stub.lmodified(sc);
                    }
                    else if(dserv){
                        ex=stub.fexist(dc);
                        dclm=stub.lmodified(dc);
                        sclm=sc.lastModified();
                    }
                    else{
                        ex=dc.exists();
                    }
                    if(ex){//check the last modified date
                        if (!(sclm == dclm)){
                            if(dserv){
                                stub.delf(dc);
                            }
                            else{
                                dc.delete();
                            }
                                if(net){
                                    copyfilesrmi(sc,dc);
                                }
                                else{
                                    try {
                                        Files.copy(sc.toPath(), dc.toPath());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    else {
                        if(net){
                            copyfilesrmi(sc,dc);
                        }
                        else{
                            try {
                                Files.copy(sc.toPath(), dc.toPath());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }

    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static void rmdiff(File ps,File pd){
        File[] lf;
        if(dserv){ //la destination est le serveur
            lf = stub.listFiles(pd);
        } else {
            lf = pd.listFiles();
        }
        for(File f : lf){
            File sc = new File(ps,f.getName());
            File dc = new File(pd,f.getName());
            if(f.isDirectory()){
                boolean a;
                if(sserv){
                    a=stub.fexist(sc);
                }
                else{
                    a=sc.exists();
                }
                if(a){
                    rmdiff(sc,dc);
                }
                else{
                    if(dserv){
                        stub.deldir(dc);
                    }
                    else{
                        deleteDirectory(dc);
                    }
                }
            }
            else {
                boolean b;
                if(sserv){
                    b=stub.fexist(sc);
                }
                else{
                    b=sc.exists();
                }
                if(!b) {
                    if(dserv){
                        stub.delf(dc);
                    }
                    else {
                        dc.delete();
                    }
                }
            }
        }
    }

    public static File whatsource(File fserv,File fclient){
        long fslm= stub.lmodified(fserv);
        long fclm= fclient.lastModified();
        if(fslm > fclm){
            return fserv;
        }
        else if(fslm < fclm){
            return fclient;
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
