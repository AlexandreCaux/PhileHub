package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    public void uploadFile(byte[] fileData, String fileName) throws RemoteException{
        try{
            FileOutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(fileData);
            outputStream.close();
        } catch (IOException e){
            throw new RemoteException("Error uploading file: " + e.getMessage());
        }
    }

    @Override
    public byte[] downloadFile(String fileName) throws RemoteException{
        try {
            File file = new File(fileName);
            byte fileData[] = new byte[(int) file.length()];
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileData);
            inputStream.close();
            return fileData;
        } catch (IOException e){
            throw new RemoteException("Error downloading file: " + e.getMessage());
        }
    }

    @Override
    public File selectFile(String s){
        return(new File(s));
    }

    @Override
    public File[] listFiles(File f){ return(f.listFiles());}

    @Override
    public File selectFilewParent(File parent,String child){
        return(new File(parent,child));
    }

    @Override
    public boolean fileexists(File f){
        return(f.exists());
    }
}