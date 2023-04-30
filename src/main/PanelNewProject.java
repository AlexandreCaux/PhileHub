package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelNewProject extends JPanel {
	
	String pathSelected = "";
	boolean errorType = false;
	
	public PanelNewProject() {
		this.setBounds(370,195,540,350);
		this.setLayout(null);
		this.setVisible(false);
		this.setBackground(new Color(146,168,171));
	}
	
	public void pathSelect(String pathSelected) {
		this.pathSelected = pathSelected;
	}
	
	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.black);
		g2.drawRect(0, 0, 539, 349);
		g2.setColor(Color.white);
		
		Font previousFont = g2.getFont();
		g2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		g2.drawString("Create A New Project", 150, 40);
		g2.setFont(new Font("SansSerif", Font.PLAIN, 15));
		g2.drawString("Name :", 140, 100);
		g2.drawString("Path Selected : " + pathSelected, 15, 200);
		
		if(errorType) {
			g2.setColor(Color.red);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
			g2.drawString("You need to select a name and a path !", 100, 320);
			g2.setColor(Color.white);
		}
		
		
		g2.setFont(previousFont);
		
			
		g2.dispose();
	}
	
}