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
	List<String> listPath = new LinkedList<>();
	
	public Project(String name) {
		this.name = name;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		dateOfCreation = dtf.format(LocalDateTime.now());
	}
	
	public void changeDateCreation(String date) {
		dateOfCreation = date;
	}
	public void changeName(String name) {
		this.name = name;
	}
	
	public void addPath(String path) {
		listPath.add(path);
	}
	
	public void removePath(String path) {
		listPath.remove(path);
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
