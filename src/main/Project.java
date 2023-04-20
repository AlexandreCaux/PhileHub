package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Project {

	String name;
	LocalDateTime dateOfCreation;
	boolean isSelected = false;
	List<String> listPath = new LinkedList<>();
	
	public Project(String name) {
		this.name = name;
		dateOfCreation = LocalDateTime.now();
	}
	
	public void addPath(String path) {
		System.out.print(name);
		listPath.add(path);
	}
	
	public void draw(Graphics2D g2, int x, int y) {
		
		int boxHeight = 70;
		int boxWidth = 500;
		
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
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		g2.drawString("Date Of Creation : " + dtf.format(dateOfCreation) , x+20, y + 60);
		
		
	}
	
}
