package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

public class ProjectManager {
	
	List<Project> listProject = new LinkedList<>(); 	
	
	int indexProjectSelected = -1;
	int yScroll = 0;
	
	
	int maxY = 620;
	int minY = 100;
	int minX = 100;
	int maxX = 600;
	
	
	public void addProject(Project p) {
		listProject.add(p);

		
	}

	public void scroll(boolean isUp) {
		int yScrollAdd = 30;
		if(!isUp && yScroll > (maxY - minY) - (listProject.size() * 70) ) {
			yScroll -= yScrollAdd;
		}
		else if(isUp && yScroll < 0) {
			yScroll += yScrollAdd;
			if(yScroll > 0) {
				yScroll = 0;
			}
		}
		
		
		
	}
	
	public void selectProject(int y) {
		for(int i=0; i< listProject.size();i++) {
			if(y > 100+i*70 + yScroll && y < 100 + (i+1)*70 ) {
				if(indexProjectSelected != -1) {
					listProject.get(indexProjectSelected).isSelected = false;
				}
				indexProjectSelected = i;
				listProject.get(indexProjectSelected).isSelected = true;
				
			}
		}
	}
	
	
	public void draw(Graphics2D g2) {
	
			
		for(int i =0 ; i< listProject.size(); i++) {
			listProject.get(i).draw(g2, 100, 100+i*70 + yScroll);
		}
		
		
		
		
		g2.setColor(Color.gray);
		g2.fillRect(90, maxY, 520, 400);
		g2.fillRect(90, 0, maxX-90+10 , minY);
		g2.setColor(Color.white);
		
		if(indexProjectSelected != -1) {
			
		}
		else{
			g2.drawString("Select a project to change its settings", 800, 400);
		}
		
	}
	
}
