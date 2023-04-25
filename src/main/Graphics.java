package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;

public class Graphics {
	
	static JFrame window;
	int screenWidth = 1280; // 1280
	int screenHeight = 736; // 736
	
	public static void main(String[] args) {
		
		//windows settings
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setAutoRequestFocus(false);
		window.setTitle("PhileHub");
		window.setUndecorated(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
		
		

		window.setBackground(Color.gray);
		window.setSize(1280,736);
		
		PanelSettings panelS = new PanelSettings();
		Panel panel = new Panel(window, panelS);
		panel.setBounds(0,0,1280,736);
		panelS.setBounds(500,500,1280,736);
		panelS.setBackground(new Color(0,0,0,0));
		panelS.createInterface(panel);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0,0,1280,736);
	
	
		
		
		
		layeredPane.add(panelS);
		layeredPane.add(panel);
		
		
		

		window.add(layeredPane);
		
	
		
		
		panel.setup();
		panel.startThread();
		
	}
}
