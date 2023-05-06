package main;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote{
    void showMsg() throws RemoteException;
    public void uploadFile(byte[] fileData, String fileName) throws RemoteException;
    public byte[] downloadFile(String fileName) throws RemoteException;
    public File selectFile(String s);
    public File[] listFiles(File f);
    public File selectFilewParent(File parent,String child);
    public boolean fileexists(File f);
    public boolean createdir(File f);
    public boolean fexist(File f);
    public long lmodified(File f);
    public boolean delf(File f);
    public boolean deldir(File f);
}
