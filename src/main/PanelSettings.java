package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class PanelSettings extends JPanel {

	public PanelSettings() {
		
		this.setPreferredSize(new Dimension(400, 400)); 
		this.setBackground(Color.gray);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setOpaque(true);
			
		
	}
	
	public void addInterface(Panel panel) {
		
		JTextField nameProject = new JTextField();
		nameProject.setBounds(20,80,200,28);
		nameProject.setVisible(true);
		
		JButton removeBtn = new JButton();
		removeBtn.setBounds(400,80,100,100);
		removeBtn.setText("remove project");
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			panel.projectM.listProject.remove(panel.projectM.indexProjectSelected);
			panel.projectM.indexProjectSelected = -1;
		    }
		});
		
		
		JButton addPathBtn = new JButton();
		addPathBtn.setBounds(800,80,100,100);
		addPathBtn.setText("addPath");
		addPathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser choose = new JFileChooser();
				choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue=choose.showOpenDialog(addPathBtn);
				if(returnValue==JFileChooser.APPROVE_OPTION){
				   String path =choose.getSelectedFile().getAbsolutePath();
				   panel.projectM.listProject.get(panel.projectM.indexProjectSelected).addPath(path);
				}
		    }
		});
		
		this.add(nameProject);
		this.add(removeBtn);
		this.add(addPathBtn);
		
		
	}
	
	
	
}
