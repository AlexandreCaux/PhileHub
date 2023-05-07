package main;

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
		List<PathOfProject> listPathClient = new LinkedList<PathOfProject>();
		Project projectClient = new Project(project.getName());
		for(PathOfProject path : listPathServer) {
			if(path.isLocal()) {
				String ipLocal;
				final DatagramSocket datagramSocket = new DatagramSocket();
			    datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
			    ip = datagramSocket.getLocalAddress().getHostAddress();
				projectClient.addPath(path.getPath(), ip);
			}
			else {
				projectClient.addPath(path.getPath(), path.getIP());
			}
		}
		
		Registry reg = LocateRegistry.getRegistry("127.0.0.1", 18531);
        Hello serv = (Hello) Naming.lookup(String.format("rmi://%s:%d/ImpClasse", "127.0.0.1", 18531));
		serv.addProject(projectClient);
		//lancer dans projectM addProject(projectClient)
	}	
	
	

    public void run(){
        while(true){
        	
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
