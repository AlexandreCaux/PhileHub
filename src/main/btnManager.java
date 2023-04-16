package main;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class btnManager {


	public void createMenu(JFrame window) {
		
		JButton buttonTest = new JButton();
		buttonTest.setText("test");
		buttonTest.setBounds(100,100,100,20);
		window.add(buttonTest);
	}
	

}
