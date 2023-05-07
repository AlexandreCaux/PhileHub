package main;

import java.util.LinkedList;
import java.util.List;

import panel.Panel;

public class ProjectSynchro implements Runnable {
	
	Panel panel;
	List<FileManager> listFileM = new LinkedList<FileManager>();
	
	public ProjectSynchro(Panel panel) {
		this.panel = panel;
	}

    public void run(){
        while(true){
        	
        	System.out.println("uwu");
        	listFileM.clear();
        	for(Project project : panel.projectM.getListProject()) {
        		FileManager fileM = new FileManager(project);
        		fileM.synchronize();
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
