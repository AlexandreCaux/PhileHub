package main;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

public class FileManager extends Thread{
    Project p;
    public FileManager(Project proj){
        this.p = proj;
    }

    public static void copyfilesrmi(PathOfProject source,PathOfProject dest){
        if(!(source.isLocal())){
            //retrieve file data
            //put it in the right place
            try {
                byte[] data = source.serv.downloadFile(source.toString());
                try (FileOutputStream outputStream = new FileOutputStream(dest.path)) {
                    outputStream.write(data);
                } catch (IOException ignored) {
                    System.out.println("IOException 1 copyfilesrmi");
                }
            }
            catch (RemoteException ignored) {
                System.out.println("RemoteException copyfilesrmi");
            }
        }
        else{ //the destination is the server
            //send the data
            try {
                File fsource = new File(source.path);
                byte fileData[] = new byte[(int) fsource.length()];
                FileInputStream inputStream = new FileInputStream(fsource);
                inputStream.read(fileData);
                inputStream.close();
                dest.serv.uploadFile(fileData, dest.path);
            } catch (IOException ignored) {
                System.out.println("IOException 2 copyfilesrmi");
            }
        }
    }

    public void copy(PathOfProject source,PathOfProject dest) throws RemoteException {
        File sf = new File(source.path);
        File df = new File(dest.path);
        List<File> lf = new LinkedList<>();
        List<String> lsf = new LinkedList<>();
        if(!(source.isLocal())){ //la source est le serveur
            lsf = source.serv.listFiles(sf);
            for(String s : lsf){
                File f = new File(s);
                lf.add(f);
            }
        }
        else {
            for(File f : sf.listFiles()){
                lf.add(f);
            }
        }
        for (File f : lf){
            File sc;
            File dc;
            if(!(source.isLocal())) {
                sc = source.serv.selectFilewParent(sf,f.getName());
                dc = new File(df, f.getName());
            }
            else if(!(dest.isLocal())) { //la destination est le serveur
                sc= new File(sf, f.getName());
                dc = dest.serv.selectFilewParent(df,f.getName());
            }
            else {
                sc= new File(sf, f.getName());
                dc = new File(df, f.getName());
            }
            PathOfProject nsource = new PathOfProject(sc.toString(), source.ip);
            nsource.serv = source.serv;
            PathOfProject ndest = new PathOfProject(dc.toString(), dest.ip);
            ndest.serv = dest.serv;

            if (f.isDirectory()) {
                if (dc.exists()) {
                    copy(nsource, ndest);
                }
                else {
                    if(!(dest.isLocal())){
                        dest.serv.createdir(dc);
                    }
                    else{
                        dc.mkdir();
                    }
                    copy(nsource, ndest);
                }
            }
            else {
                boolean ex = false;
                long sclm=0;
                long dclm=0;
                if(!(source.isLocal())){
                    dclm=dc.lastModified();
                    sclm=source.serv.lmodified(sc);
                }
                else if(!(dest.isLocal())){
                    ex=dest.serv.fexist(dc);
                    dclm=dest.serv.lmodified(dc);
                    sclm=sc.lastModified();
                }
                else{
                    ex=dc.exists();
                }
                if(ex){//check the last modified date
                    if (!(sclm == dclm)){
                        if(!(dest.isLocal())){
                            dest.serv.delf(dc);
                        }
                        else{
                            dc.delete();
                        }
                            if(!(source.isLocal() && dest.isLocal())){
                                copyfilesrmi(nsource,ndest);
                            }
                            else{
                                try {
                                    Files.copy(sc.toPath(), dc.toPath(), COPY_ATTRIBUTES);
                                } catch (IOException ignored) {
                                    System.out.println("IOException 1 copy");
                                }
                            }
                        }
                    }
                else {
                    if(!(source.isLocal() && dest.isLocal())){
                        copyfilesrmi(nsource,ndest);
                    }
                    else{
                        try {
                            Files.copy(sc.toPath(), dc.toPath(), COPY_ATTRIBUTES);
                        } catch (IOException ignored) {
                            System.out.println("IOException 2 uploadfile");
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

    public static void rmdiff(PathOfProject source,PathOfProject dest) throws RemoteException {
        List<File> lf = new LinkedList<>();
        List<String> lsf = new LinkedList<>();
        File fsource = new File(source.path);
        File fdest = new File(dest.path);
        if(!(source.isLocal())){ //la destination est le serveur
            lsf = source.serv.listFiles(fdest);
            for(String s : lsf){
                File f = new File(s);
                lf.add(f);
            }
        } else {
            for(File f : fdest.listFiles()){
                lf.add(f);
            }
        }
        for(File f : lf){
            File sc = new File(fsource,f.getName());
            File dc = new File(fdest,f.getName());
            if(f.isDirectory()){
                boolean a;
                if(!(source.isLocal())){
                    a=source.serv.fexist(sc);
                }
                else{
                    a=sc.exists();
                }
                if(a){
                    PathOfProject nsource = new PathOfProject(sc.toString(), source.ip);
                    nsource.serv = source.serv;
                    PathOfProject ndest = new PathOfProject(dc.toString(), source.ip);
                    ndest.serv = dest.serv;
                    rmdiff(nsource,ndest);
                }
                else{
                    if(!(dest.isLocal())){
                        dest.serv.deldir(dc);
                    }
                    else{
                        deleteDirectory(dc);
                    }
                }
            }
            else {
                boolean b;
                if(!(source.isLocal())){
                    b=source.serv.fexist(sc);
                }
                else{
                    b=sc.exists();
                }
                if(!b) {
                    if(!(dest.isLocal())){
                        dest.serv.delf(dc);
                    }
                    else {
                        dc.delete();
                    }
                }
            }
        }
    }

    public PathOfProject whatsource() throws RemoteException { //NB:all serv will be initialized
        PathOfProject src = null;
        long lmd = 0;
        for (PathOfProject ppath : p.getListPath()) {
            if (ppath.isLocal()) {
                File f = new File(ppath.path);
                if (f.lastModified() > lmd) {
                    lmd = f.lastModified();
                    src = ppath;
                }
            } else { //path is not local
                try {
                    Registry reg = LocateRegistry.getRegistry(ppath.ip, 18532); //ip = 127.0.0.1
                    ppath.serv = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse", ppath.ip, 18532));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                File fs = new File(ppath.path);
                if (ppath.serv.lmodified(fs) > lmd) {
                    lmd = ppath.serv.lmodified(fs);
                    src = ppath;
                }
            }
        }
        return src;
    }

    public void synchronize() throws RemoteException {
        PathOfProject source = whatsource();
        //synchronize by pairs including source everytime
        for (PathOfProject dest : p.getListPath()){
            if(!(dest.equals(source)) && !(!source.isLocal() && !dest.isLocal())){
                //destination cannot be the same as source
                //if both source and destination are on other devices then this one is not concerned
                copy(source,dest);
                rmdiff(source,dest);
            }
        }
    }
}
