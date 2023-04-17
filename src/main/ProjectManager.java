package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

public class ProjectManager {
	
	List<Project> listProject = new LinkedList<>(); 	
	JFrame window;
	JScrollBar scrollBar=new JScrollBar(); 
	
	int indexProjectSelected = -1;
	
	public ProjectManager(JFrame window) {
	    this.window = window;
	}
	
	
	public void addProject(Project p) {
		listProject.add(p);
	}

	public void draw(Graphics2D g2) {
		
	    scrollBar.setBounds(0,0, 700,700);  
	    window.add(scrollBar);
		
		
		for(int i =0 ; i< listProject.size(); i++) {
			listProject.get(i).draw(g2, 100, 100+i*70);
		}
		
		
		
		
		
		
		g2.setColor(Color.gray);
		g2.fillRect(90, 620, 520, 400);
		g2.setColor(Color.white);
	}
	
}
