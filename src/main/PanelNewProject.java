package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelNewProject extends JPanel {

	public PanelNewProject() {
		this.setBounds(370,100,540,500);
		this.setLayout(null);
		
		
		JButton test = new JButton("test");
		test.setBounds(20,10,50,40);
		test.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("ouai c vrai");
		    }
		});
		this.add(test);
	}
	
	public void hide() {
		this.setVisible(false);
	}
	public void show() {
		this.setVisible(true);
	}
}
