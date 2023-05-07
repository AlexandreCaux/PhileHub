package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

// implémenter l'interface créée à l'étape 1
public class ImpClasse extends UnicastRemoteObject implements Hello {
	
	
    protected ImpClasse() throws RemoteException {
        super();
    }

    @Override
    public void showMsg() throws RemoteException {
        System.out.println("Hello World !");
    }

    @Override
    public void uploadFile(byte[] fileData, String targetpath) throws RemoteException{ //client to server
        //retrieve file data
        //put it in the right place
        try {
            try (FileOutputStream outputStream = new FileOutputStream(targetpath)) {
                outputStream.write(fileData);
            } catch (IOException ignored) {
                System.out.println("IOException uploadfile");
            }
        } catch (RuntimeException ignored) {
            System.out.println("IOException uploadfile");
        }
    }

    @Override
    public byte[] downloadFile(String fileName) throws RemoteException{ //server to client
        try {
            File file = new File(fileName);
            byte fileData[] = new byte[(int) file.length()];
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileData);
            inputStream.close();
            return fileData;
        } catch (IOException e){
            System.out.println("IOException uploadfile");
            return null;
        }
    }

    @Override
    public File selectFile(String s){
        return(new File(s));
    }

    @Override
    public List<String> listFiles(File f){
        List<String> flist = new LinkedList<>();
        for(File a : f.listFiles()){
            flist.add(a.toString());
        }
        return(flist);
    }

    @Override
    public File selectFilewParent(File parent,String child){
        return(new File(parent,child));
    }

    @Override
    public boolean fileexists(File f){
        return(f.exists());
    }

    @Override
    public boolean createdir(File f){return f.mkdir();}

    @Override
    public boolean fexist(File f){return(f.exists());}

    @Override
    public long lmodified(File f){
        return f.lastModified();
    }

    @Override
    public boolean delf(File f){
        return f.delete();
    }

    @Override
    public boolean deldir(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deldir(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    
    @Override
    public void addProject(Project project) {
    	
		try {
			BufferedReader data = new BufferedReader(new FileReader("dataProject.txt"));
			System.out.println(data.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
    }
    
}