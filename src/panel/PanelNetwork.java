package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelNetwork extends JPanel{

	public PanelNetwork () {
		
		this.setBounds(370,220,540,300);
		this.setLayout(null);
		this.setVisible(false);
		this.setBackground(new Color(146,168,171));
	}
	
	
	
	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.black);
		g2.drawRect(0, 0, 539, 299);
		g2.setColor(Color.white);
		
		Font previousFont = g2.getFont();
		g2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		g2.drawString("Share A Project", 185, 40);
		g2.setFont(new Font("SansSerif", Font.PLAIN, 15));
		g2.drawString("Project :", 30, 100);
		g2.drawString("Share With (IP Adress) :", 110, 150);
		
		g2.setFont(previousFont);
		
			
		g2.dispose();
	}
	
}
