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
	
	JTextField nameProject = new JTextField();
	JButton removeBtn = new JButton();
	JButton addPathBtn = new JButton();
	

	public PanelSettings() {
		
		this.setPreferredSize(new Dimension(400, 400)); 
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.setOpaque(true);
			
		
	}
	
	public void createInterface(Panel panel) {
	
		
		
		nameProject.setBounds(20,80,200,28);
		
		
		
		removeBtn.setBounds(400,80,100,100);
		removeBtn.setText("remove project");
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			panel.projectM.listProject.remove(panel.projectM.indexProjectSelected);
			panel.projectM.indexProjectSelected = -1;
			removeInterface();
		    }
		});
		
		
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
	}
	
	public void addInterface() {
		
		this.add(addPathBtn);
		this.add(removeBtn);
		this.add(nameProject);
	}
	
	public void removeInterface() {
		this.remove(addPathBtn);
		this.remove(removeBtn);
		this.remove(nameProject);
	}
	
	
}
