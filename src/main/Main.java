package main;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;
	
	public static void main(String[] args) {
		
		
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
		
		//test
		JButton buttonTest = new JButton();
		buttonTest.setText("UwU");
		buttonTest.setBounds(new Rectangle(400,400,500,500));
		window.add(buttonTest);
		//
		
		
		Panel.setup();
		Panel.startThread();
		
		
	}
}
