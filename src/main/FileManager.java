package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class FileManager {
    public File pathsource;
    public File pathdestination;

    public void copy(File ps,File pd) throws IOException {
        for(File f : ps.listFiles()){
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
                }
                else {
                    Files.copy(sc.toPath(), dc.toPath());
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
