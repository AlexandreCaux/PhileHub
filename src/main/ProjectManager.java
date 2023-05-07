package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

import panel.PanelBtnSettings;

public class ProjectManager {
	
	List<Project> listProject = new LinkedList<>(); 	
	
	int indexProjectSelected = -1;
	int yScroll = 0;
	
	
	int maxY = 640;
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
	
	
	public void selectProject(int y, PanelBtnSettings panelBtnS) {
		for(int i=0; i< listProject.size();i++) {
			if(y > minY+i*70 + yScroll && y < minY + (i+1)*70 ) {
				panelBtnS.removeBtn();
				if(indexProjectSelected != -1) {
					listProject.get(indexProjectSelected).isSelected = false;
				}
				indexProjectSelected = i;
				listProject.get(indexProjectSelected).isSelected = true;
				panelBtnS.addBtn(this);
				panelBtnS.nameProjectText(listProject.get(i).name);
				
			}
		}
	}
	
	public List<Project> getListProject(){
		return listProject;
	}
	
	public Project findProject(String name) {
		for(Project project : listProject) {
			if(project.name == name) {
				return project;
			}
		}
		return null;
	}
	
	public int getIndexSelectedProject() {
		return indexProjectSelected;
	}
	
	public Project getElementListProject(int index) {
		return listProject.get(index);
	}
	
	public Project getSelectionedProject() {
		return listProject.get(indexProjectSelected);
	}
	
	public void removeProject(int index) {
		listProject.remove(index);
	}
	
	public void changeIndexSelection(int index) {
		indexProjectSelected = index;
	}
	
	public void save() {
		try {
			File data = new File("dataProject.txt");
			data.createNewFile();
			FileWriter writer = new FileWriter("dataProject.txt");
			for(Project project : listProject) {
				writer.write(project.name + "{\n" + project.dateOfCreation + "\n" );
				for(PathOfProject path : project.listPath) {
					writer.write(path.getIP() + ": " + path.getPath() + "\n");
				}
				writer.write("}\n");
			}
			writer.write(";");
			writer.close();
		} catch (IOException e) {
			 e.printStackTrace();	
		}
	}
	
	public void loadSave() throws IOException {
		
		listProject.clear();
		BufferedReader data = new BufferedReader(new FileReader("dataProject.txt"));
		String line = data.readLine();
			
		while(!line.equals(";")) {
			String name = line.substring(0, line.length() - 1);
			Project project = new Project(name);
			line = data.readLine();
			project.changeDateCreation(line);
			line = data.readLine();
			while(!line.equals("}")) {
				String[] separationPath = line.split(": ");
				project.addPath(separationPath[1], separationPath[0]);
				line = data.readLine();
			}
			listProject.add(project);
			line = data.readLine();
		}
	}
	
	
	public void draw(Graphics2D g2) {
	
			
		for(int i =0 ; i< listProject.size(); i++) {
			listProject.get(i).draw(g2, minX, minY+i*70 + yScroll);
		}
		
		
		
		
		g2.setColor(Color.gray);
		g2.fillRect(90, maxY, 520, 400);
		g2.fillRect(90, 0, maxX-90+10 , minY);
		g2.setColor(Color.white);
		
		if(indexProjectSelected != -1) {
			
		}
		else{
			Font previousFont = g2.getFont();
			g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
			g2.drawString("Select a project to change its settings", 780, 350);
			g2.setFont(previousFont);
		}
		
	}
	
}
