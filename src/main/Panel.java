package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;



public class Panel extends JPanel {

	// Screen settings

	public final int screenWidth = 1280; // 1280
	public final int screenHeight = 736; // 736
	
	

	public Panel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); 
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
	}
	
	
	
	/*
	BufferedImage Screen;
	Graphics2D g2;
	
	
	//FPS
	int FPS = 100;

	//System
	Thread gameThread;

	
	//State


	public Panel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); 
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
	}
	

	public void setup() {
		Screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)Screen.getGraphics();
		
		
	}
	
	
	
	

	
	
	public void startThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1_000_000_000 / FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {

			
			// 1 update info
			update();

			// 2 draw screen
			drawToScreen();

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

	public void update() {
		
	}
	
	
	public void drawToScreen() {
		
		
		g2.dispose();
	}	
	*/
}
