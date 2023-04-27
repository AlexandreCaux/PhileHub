package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;



public class Panel extends JPanel implements Runnable {

	// Screen settings

	public final int screenWidth = 1280; // 1280
	public final int screenHeight = 736; // 736
	
	JFrame window;
	PanelSettings panelS;

	public Panel(JFrame window, PanelSettings panelS) {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); 
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.setOpaque(true);
		this.addMouseListener(mouseD);
		this.addMouseWheelListener(mouseWheelD);
		this.window= window;
		this.panelS = panelS;
		
	}
	



	//FPS
	int FPS = 100;

	//System
	Thread thread;
	ProjectManager projectM = new ProjectManager();
	MouseDetection mouseD = new MouseDetection(this);
	MouseWheelDetection mouseWheelD = new MouseWheelDetection(this);


	public void setup() {
		mouseD.getPanelS(panelS);
		projectM.addProject(new Project("Projet 1"));
		projectM.addProject(new Project("Projet 2"));
		projectM.addProject(new Project("Projet 3"));
		projectM.addProject(new Project("Projet 1"));
		projectM.addProject(new Project("Projet 2"));
		projectM.addProject(new Project("Projet 3"));
		projectM.addProject(new Project("Projet 1"));
		projectM.addProject(new Project("Projet 2"));
		projectM.addProject(new Project("Projet 3"));
		projectM.addProject(new Project("Projet 1"));
		projectM.addProject(new Project("Projet 2"));
		projectM.addProject(new Project("Projet 3"));
		
	}
	
	public void startThread() {

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		

		double drawInterval = 1_000_000_000 / FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (thread != null) {
			
			
			window.repaint();

			// FPS
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1_000_000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);

				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	
	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.setColor(Color.white);
		
		g2.setColor(Color.black);
		
		projectM.draw(g2);
		
		drawInterface(g2);
		drawTextSettings(g2);
		
			
		g2.dispose();
	}
	
	public void drawInterface(Graphics2D g2) {
		
		Font previousFont = g2.getFont();
		g2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		
		//draw texts
		g2.drawString("Your Projects", 280, 80);
		g2.drawString("Project Settings", 800 , 80);
		
		g2.setFont(previousFont);
		
		//drawLines
		
		g2.drawRect(90, 40, 500, 610);
		g2.drawLine(90, 90, 590, 90);
		
		g2.drawRect(690, 40, 500, 610);
		g2.drawLine(690, 90, 1190, 90);
		
	}
	
	public void drawTextSettings(Graphics2D g2) {
		if(projectM.indexProjectSelected != -1) {
			Font previousFont = g2.getFont();
			g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
			g2.drawString("Name :", 820, 140);
			g2.drawLine(690, 250, 1190, 250);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 25));
			g2.drawString("Paths Settings", 850, 280);
			g2.drawLine(690, 300, 1190, 300);
			g2.setFont(previousFont);
		}
	}
	
	
}
