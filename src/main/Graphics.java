package main;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Graphics {
	
	static JFrame window;
	
	public static void main(String[] args) {
		
		//windows settings
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setAutoRequestFocus(false);
		window.setTitle("PhileHub");
		window.setUndecorated(false);
		
		Panel Panel = new Panel();
		window.add(Panel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setResizable(false);
		
		
		//buttons settings
		
		/*
		btnManager btnM = new btnManager();
		
		btnM.createMenu(window);
		*/
		
		/*
		JButton buttonTest = new JButton();
		buttonTest.setText("test");
		buttonTest.setBounds(new Rectangle(400,400,500,500));
		window.add(buttonTest);
		*/
		//
		
		
		Panel.setup();
		Panel.startThread();
		
		
	}
}
