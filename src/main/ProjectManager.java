package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

public class ProjectManager {
	
	List<Project> listProject = new LinkedList<>();
	int indexProjectSelected = -1;
	
	public void addProject(Project p) {
		listProject.add(p);
	}

	public void draw(Graphics2D g2) {
		
		for(int i =0 ; i< listProject.size(); i++) {
			listProject.get(i).draw(g2, 100, 100+i*70);
		}
		
		
		
		
		g2.setColor(Color.gray);
		g2.fillRect(90, 620, 520, 400);
		g2.setColor(Color.white);
	}
	
}
