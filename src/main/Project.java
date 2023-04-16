package main;

import java.awt.Graphics2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Project {

	String name;
	LocalDateTime dateOfCreation;
	List<String> listPath = new LinkedList<>();
	
	public Project(String name) {
		this.name = name;
		dateOfCreation = LocalDateTime.now();
	}
	
	public void addPath(String path) {
		listPath.add(path);
	}
	
	public void draw(Graphics2D g2, int x, int y) {
		
		int boxHeight = 70;
		int boxWidth = 500;
		
		
		g2.drawRect(x, y, boxWidth, boxHeight);
		g2.drawString(name, x+20, y+20);
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		g2.drawString("Date Of Creation : " + dtf.format(dateOfCreation) , x+20, y + 60);
		
		
	}
	
}
