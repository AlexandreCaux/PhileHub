package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FileManager extends Thread{
    public File filea;
    public File fileb;
    public FileManager(String a, String b){
        this.filea = new File(a);
        this.fileb = new File(b);
    }

    public void run(){
        while(true){
            sychronize(this.filea,this.fileb);
            try {
                Thread.sleep(60000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args){
        String a = "C:\\Users\\alexa\\OneDrive\\Travail\\1_Info\\1.12 Java\\Projet\\TestsLocal\\one";
        String b = "C:\\Users\\alexa\\OneDrive\\Travail\\1_Info\\1.12 Java\\Projet\\TestsLocal\\two";
        new FileManager(a,b).start();
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

    public void sychronize(File a, File b){
        if(whatsource(a,b)==null){
            return;
        }
        else if(whatsource(a,b)==a){
            copy(a,b);
            rmdiff(a,b);
        }
        else{
            copy(b,a);
            rmdiff(b,a);
        }
    }
}
