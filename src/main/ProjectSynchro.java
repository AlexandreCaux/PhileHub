package main;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;

import panel.Panel;

public class ProjectSynchro extends Thread implements Serializable {
	
	Panel panel;
	List<FileManager> listFileM = new LinkedList<FileManager>();
	
	public ProjectSynchro(Panel panel) {
		this.panel = panel;
	}
	
	
	public void sendProject(Project project, String ip) throws UnknownHostException, SocketException, RemoteException, MalformedURLException, NotBoundException {
		
		List<PathOfProject> listPathServer = project.getListPath();
		Project projectClient = new Project(project.getName());
		for(PathOfProject path : listPathServer) {
			if(path.isLocal()) {
				String ipLocal;
				final DatagramSocket datagramSocket = new DatagramSocket();
			    datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
			    ipLocal = datagramSocket.getLocalAddress().getHostAddress();
				projectClient.addPath(path.getPath(), ipLocal);
			}
			else {
				projectClient.addPath(path.getPath(), path.getIP());
			}
		}
		Registry reg = LocateRegistry.getRegistry(ip, 18532);
        Hello serv = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse", ip, 18532));
		serv.addProject(projectClient);
		//lancer dans projectM addProject(projectClient)
	}
	
	public void removePathProject(Project project,PathOfProject path) throws UnknownHostException, SocketException, RemoteException, MalformedURLException, NotBoundException{
		for(PathOfProject pathProject : project.getListPath()) {
			final DatagramSocket datagramSocket = new DatagramSocket();
		    datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
			String ipLocal = datagramSocket.getLocalAddress().getHostAddress();
			if(!pathProject.isLocal()) {
				Registry reg = LocateRegistry.getRegistry(path.getIP(), 18532);
		        Hello serv = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse", path.getIP(), 18532));
				if(ipLocal.equals(path.getIP())) {
					serv.addPath(project, new PathOfProject(path.getPath(),"0"));
				}
				else {
					serv.addPath(project, path);
				}
			}
		}
	}
	
	public void addPathProject(Project project, PathOfProject path) throws UnknownHostException, SocketException, RemoteException, MalformedURLException, NotBoundException{
		for(PathOfProject pathProject : project.getListPath()) {
			final DatagramSocket datagramSocket = new DatagramSocket();
		    datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
			String ipLocal = datagramSocket.getLocalAddress().getHostAddress();
			if(!pathProject.isLocal()) {
				Registry reg = LocateRegistry.getRegistry(path.getIP(), 18532);
		        Hello serv = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse", path.getIP(), 18532));
				if(ipLocal.equals(path.getIP())) {
					serv.removePath(project, new PathOfProject(path.getPath(),"0"));
				}
				else {
					serv.removePath(project, path);
				}
			}
		}
	}
	

    public void run(){
        while(true){
        	
        	try {
				panel.projectM.loadSave();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	listFileM.clear();
        	for(Project project : panel.projectM.getListProject()) {
        		FileManager fileM = new FileManager(project);
				try {
					fileM.synchronize();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
        	
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
    }
	
}
