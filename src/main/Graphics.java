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
	JPanel panelNewProject = new JPanel();
	int screenWidth = 1280;
	int screenHeight = 740;
	
	public static void configWindow() {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setAutoRequestFocus(false);
		window.setTitle("PhileHub");
		window.setUndecorated(false);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
		window.setLayout(null);
		window.setBackground(Color.gray);
		window.setSize(1280,736);
	}
	

	public static void main(String[] args) {
		
		configWindow();
	
		
		PanelNewProject panelNewP = new PanelNewProject();
		PanelBtnNew panelBtnN = new PanelBtnNew(panelNewP);
	
		
		PanelBtnSettings panelBtnS = new PanelBtnSettings();
		Panel panel = new Panel(window, panelBtnS);
		panel.setBounds(0,0,1280,736);
		panelBtnS.createBtn(panel, panelNewP, panelBtnN);
		panelBtnN.createBtn(panel,panelNewP, panelBtnS);
		
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		layeredPane.setBounds(0,0,1280,736);
	
		
		layeredPane.add(panelBtnN);
		layeredPane.add(panelNewP);
		layeredPane.add(panelBtnS);
		layeredPane.add(panel);
		
		

		window.add(layeredPane);	
		window.setLocationRelativeTo(null);
		
		panel.setup();
		panel.startThread();
		
	}	
}
