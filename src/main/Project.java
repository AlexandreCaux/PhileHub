package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Project {

	String name;
	String dateOfCreation;
	boolean isSelected = false;
	List<PathOfProject> listPath = new LinkedList<>();
	
	public Project(String name) {
		this.name = name;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		dateOfCreation = dtf.format(LocalDateTime.now());
	}
	
	
	public void changeDateCreation(String date) {
		dateOfCreation = date;
	}
	
	public String getName() {
		return name;
	}
	
	public void changeName(String name) {
		this.name = name;
	}
	public List<PathOfProject> getListPath() {
		return listPath;
	}
	
	public void addPath(String path, String ip) {
		listPath.add(new PathOfProject(path,ip));
	}
	
	public void removePath(String path) {
		for(int i=0; i<listPath.size();i++) {
			if(listPath.get(i).getPath().equals(path)) {
				System.out.println(i);
				listPath.remove(i);
			}
		}
	}
	
	
	

	
	public void draw(Graphics2D g2, int x, int y) {
		
		int boxHeight = 70;
		int boxWidth = 480;
		
		if(isSelected) {
			g2.setColor(Color.blue);
		}
		else {
			g2.setColor(Color.lightGray);
		}
		g2.fillRect(x,y,boxWidth,boxHeight);
		g2.setColor(Color.white);
		g2.drawRect(x, y, boxWidth, boxHeight);
		g2.drawString(name, x+20, y+20);
		g2.drawString("Date Of Creation : " + dateOfCreation , x+20, y + 60);
		
	}
	
}
