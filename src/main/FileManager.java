package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
public class FileManager {
    public File pathsource;
    public File pathdestination;

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

    //file.exists()
    //file.isDirectory()
    //file.listFiles()
    //file.mkdir()
    //Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
    //abstract pathname : java.io.File
    //pathname string : java.lang.String
}
