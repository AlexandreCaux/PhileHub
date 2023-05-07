package main;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Hello extends Remote{
	void showMsg() throws RemoteException;
    public void uploadFile(byte[] fileData, String fileName) throws RemoteException;
    public byte[] downloadFile(String fileName) throws RemoteException;
    public File selectFile(String s) throws RemoteException;
    public List<String> listFiles(File f) throws RemoteException;
    public File selectFilewParent(File parent,String child) throws RemoteException;
    public boolean fileexists(File f) throws RemoteException;
    public boolean createdir(File f) throws RemoteException;
    public boolean fexist(File f) throws RemoteException;
    public long lmodified(File f) throws RemoteException;
    public boolean delf(File f) throws RemoteException;
    public boolean deldir(File f) throws RemoteException;
    public void addProject(Project project) throws RemoteException;
    public void addPath(Project project,PathOfProject path);
    public void removePath(Project project,PathOfProject path);
}
